/*
 * #%L
 * Processiva Business Processes Platform
 * %%
 * Copyright (C) 2012 Cohesiva
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
package com.cohesiva.processes.jbpm.handlers;

import javax.annotation.PostConstruct;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

/*
 * Base class for Work Item Handler
 */
public abstract class BaseWorkItemHandler implements WorkItemHandler{
	protected String workItemId;
	
	@PostConstruct
	protected void init() {
		setWorkItemId();
	}

	protected abstract void setWorkItemId();

	// Execution of work item handler.
	public abstract void executeWorkItem(WorkItem workItem,
			WorkItemManager manager);

	// Code executed when handler is aborted.
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}
	
	public String getWorkItemId(){
		return workItemId;
	}
}
