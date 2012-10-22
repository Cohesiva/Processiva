package com.cohesiva.processes.jbpm.handlers;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

/*
 * Base class for asynchronous workitem handlers.
 */
public class BaseAsynchronousWorkItemHandler implements WorkItemHandler {

	protected StatefulKnowledgeSession ksession;

	public void setKSession(StatefulKnowledgeSession ksession) {
		this.ksession = ksession;
	}

	// Execution of work item handler.
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {		
	}

	// Code executed when handler is aborted.
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}

}
