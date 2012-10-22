package com.cohesiva.processes.jbpm.service.processes;

/*
 * Service for managing additional constraints for displaying processes 
 * to users that are authorized to view them
 */
public interface IProcessesConstraintsService {
	/*
	 * Returns true if user with userId is allowed to start process with id processId,
	 * false otherwise
	 */
	public boolean isProcessAllowed(String processId, String userId);

}
