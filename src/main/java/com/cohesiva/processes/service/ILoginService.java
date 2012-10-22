package com.cohesiva.processes.service;

import javax.servlet.http.HttpSession;

/* 
 * Service for logging users in
 */
public interface ILoginService {
	public String loginGoogle();

	public void handleLogged(String email, String firstName, String surname,
			HttpSession session);
}
