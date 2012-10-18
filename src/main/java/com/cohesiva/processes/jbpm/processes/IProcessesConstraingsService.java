package com.cohesiva.processes.jbpm.processes;

public interface IProcessesConstraingsService {
	/*
	 * Returns true if user with userId is allowed to start process with id processId,
	 * false otherwise
	 */
	public boolean isProcessAllowed(String processId, String userId);

}
