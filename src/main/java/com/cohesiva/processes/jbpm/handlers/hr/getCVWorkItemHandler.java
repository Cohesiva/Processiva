package com.cohesiva.processes.jbpm.handlers.hr;

import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;

import com.cohesiva.processes.jbpm.handlers.BaseAsynchronousWorkItemHandler;

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
}
