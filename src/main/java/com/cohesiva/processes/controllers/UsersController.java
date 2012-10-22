package com.cohesiva.processes.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;
import org.openid4java.message.ax.FetchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cohesiva.processes.db.User;
import com.cohesiva.processes.db.UserDao;


@Controller
public class UsersController {

	@Autowired
	private UserDao userDao;

	private static final String googleOpenIdUrl = "https://www.google.com/accounts/o8/id";
	private static final String callbackUrl = "http://localhost:4080/Jbpm_processes/handleLogged";
	//private static final String callbackUrl = "http://processiva.cohesiva.com/handleLogged";
	//1522
	
	// private static final String callbackUrl =
	// "http://193.239.56.114:4080/Jbpm_processes/handleLogged";

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpSession session) {
		session.removeAttribute("loggedEmail");

		return "redirect:/humanTasks";
		//return "redirect:http://processiva.cohesiva.com/humanTasks";	
	}

	@RequestMapping(value = "/loginGoogle")
	public String loginGoogle(HttpServletRequest request) {

		String redirectUrl = "";

		try {
			System.out.println("logowanie z googla");

			ConsumerManager manager = new ConsumerManager();

			String _returnURL = callbackUrl;

			// perform discovery on the user-supplied identifier
			List discoveries;

			discoveries = manager.discover(googleOpenIdUrl);

			// attempt to associate with the OpenID provider
			// and retrieve one service endpoint for authentication
			DiscoveryInformation discovered = manager.associate(discoveries);

			// store the discovery information in the user's session for
			// later use
			// leave out for stateless operation / if there is no
			// session

			// obtain a AuthRequest message to be sent to the OpenID
			// provider
			FetchRequest fetch = FetchRequest.createFetchRequest();

			fetch.addAttribute("FirstName",
					"http://schema.openid.net/namePerson/first", true);
			fetch.addAttribute("LastName",
					"http://schema.openid.net/namePerson/last", true);
			fetch.addAttribute("Email",
					"http://schema.openid.net/contact/email", true);

			// wants up to three email addresses
			fetch.setCount("Email", 3);

			AuthRequest authReq = manager.authenticate(discovered, _returnURL);
			authReq.addExtension(fetch);

			redirectUrl = authReq.getDestinationUrl(true);
			// throw new
			// RedirectToUrlException(authReq.getDestinationUrl(true));
		} catch (DiscoveryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConsumerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:" + redirectUrl;
	}

	@RequestMapping(value = "/handleLogged")
	public String handleLogged(HttpServletRequest request, HttpSession session) {

		String email = request.getParameter("openid.ext1.value.Email");

		if (email != null) {
			System.out.println("Logged email: " + email);
			if (userDao.getUser(email) == null) {

				String firstName = request
						.getParameter("openid.ext1.value.FirstName");
				String surname = request
						.getParameter("openid.ext1.value.LastName");

				userDao.persist(new User(email, firstName, surname));
			}
			session.setAttribute("loggedEmail", email);
		}

		return "redirect:/humanTasks";
		//return "redirect:http://processiva.cohesiva.com/humanTasks";	
	}
}
