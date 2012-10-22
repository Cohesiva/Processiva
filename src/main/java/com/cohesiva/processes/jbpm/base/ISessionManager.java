package com.cohesiva.processes.jbpm.base;

import org.drools.definition.process.Process;

public interface ISessionManager {
	public int getLatestSessionId();
	
	public void saveLatestSessionId(int sessionId);
}
