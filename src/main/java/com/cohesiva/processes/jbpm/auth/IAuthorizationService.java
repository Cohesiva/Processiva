package com.cohesiva.processes.jbpm.auth;

import java.util.List;

public interface IAuthorizationService {
	/*
	 * Returns a list of groups that the user with id userId is assigned to.
	 */
	public List<String> getUserGroups(String userId);
	
	/*
	 * Returns true if user with id userId is authorized to start process with id processId,
	 * otherwise returns false.
	 */
	public boolean isAuthorized(String processId, String userId);
}
