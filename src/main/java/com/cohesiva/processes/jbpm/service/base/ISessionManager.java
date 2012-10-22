package com.cohesiva.processes.jbpm.service.base;

/*
 * Service for managing jbpm session
 */
public interface ISessionManager {
	public int getLatestSessionId();
	
	public void saveLatestSessionId(int sessionId);
}
