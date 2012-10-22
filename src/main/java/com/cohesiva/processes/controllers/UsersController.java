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
