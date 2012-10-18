package com.cohesiva.processes.jbpm.base;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.process.workitem.wsht.MinaHTWorkItemHandler;

public interface IJbpmBase {

	public StatefulKnowledgeSession getSession();	
	
	public MinaHTWorkItemHandler getWorkItemHandler();	
}
