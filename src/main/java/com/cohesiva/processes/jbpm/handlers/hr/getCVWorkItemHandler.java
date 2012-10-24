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
package com.cohesiva.processes.jbpm.handlers.hr;

import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.jbpm.handlers.BaseAsynchronousWorkItemHandler;

@Service
public class getCVWorkItemHandler extends BaseAsynchronousWorkItemHandler{

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

		new Thread(new Runnable() {

			private WorkItem workItem;

			public Runnable setData(WorkItem workItem) {
				this.workItem = workItem;
				return this;
			}

			public void run() {			
				String documentType = (String) workItem.getParameter("documentType");
				String heroEmail = (String) workItem.getParameter("heroEmail");
				
				System.out.println("Generowanie CV dla "+heroEmail+" w formacie "+documentType);

				Map<String, Object> data = new HashMap<String, Object>();
				data.put("document", "cv jakies");

				System.out.println("getCV work item handler finished.");

				// Notify manager that work item has been completed, pass
				// data
				ksession.getWorkItemManager().completeWorkItem(
						workItem.getId(), data);
			}
		}.setData(workItem)).start();
	}

	@Override
	protected void setWorkItemId() {
		this.workItemId = "GetCV";
	}
}
