package com.cohesiva.processes.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cohesiva.processes.service.ILoginService;

@Controller
public class UsersController {

	@Autowired
	private ILoginService loginService;

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpSession session) {
		session.removeAttribute("loggedEmail");

		return "redirect:/humanTasks";
	}

	@RequestMapping(value = "/loginGoogle")
	public String loginGoogle(HttpServletRequest request) {
		String redirectUrl = loginService.loginGoogle();

		return "redirect:" + redirectUrl;
	}

	@RequestMapping(value = "/handleLogged")
	public String handleLogged(HttpServletRequest request, HttpSession session) {

		String email = request.getParameter("openid.ext1.value.Email");

		String firstName = request.getParameter("openid.ext1.value.FirstName");
		String surname = request.getParameter("openid.ext1.value.LastName");

		if (email != null) {
			loginService.handleLogged(email, firstName, surname, session);
		}
		
		return "redirect:/humanTasks";
	}
}
