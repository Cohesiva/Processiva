/*
 * #%L
 * Processiva Business Processes Platform
 * %%
 * Copyright (C) 2012 Cohesiva
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
package com.cohesiva.processes.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;
import org.openid4java.message.ax.FetchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.db.User;
import com.cohesiva.processes.db.UserDao;
import com.cohesiva.processes.service.ILoginService;

@Service(value = "loginService")
public class LoginService implements ILoginService {

	@Autowired
	private UserDao userDao;

	private static final String googleOpenIdUrl = "https://www.google.com/accounts/o8/id";

	private String callbackUrl;

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String loginGoogle() {
		String redirectUrl = null;

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

		return redirectUrl;
	}

	public void handleLogged(String email, String firstName, String surname,
			HttpSession session) {

		if (email != null) {
			System.out.println("Logged email: " + email);
			if (userDao.getUser(email) == null) {

				userDao.persist(new User(email, firstName, surname));
			}
			session.setAttribute("loggedEmail", email);
		}
	}
}
