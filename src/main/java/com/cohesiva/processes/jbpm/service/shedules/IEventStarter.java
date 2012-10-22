package com.cohesiva.processes.jbpm.service.shedules;

/*
 * Service for starting processes and trigerring events in processes for
 * external scheduler usage
 */
public interface IEventStarter {
	public void startBasketWeeklyProcess();
	
	public void startProcess(String processId);	
	
	public void signal(String signal, String processId);
}
