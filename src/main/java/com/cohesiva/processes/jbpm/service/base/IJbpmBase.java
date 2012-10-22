package com.cohesiva.processes.jbpm.service.base;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.process.workitem.wsht.MinaHTWorkItemHandler;

/*
 * Service for controlling jbpm engine
 */
public interface IJbpmBase {

	public StatefulKnowledgeSession getSession();	
	
	public MinaHTWorkItemHandler getWorkItemHandler();	
}
