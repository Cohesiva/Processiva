package com.cohesiva.processes.jbpm.handlers.basket;

import java.util.List;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

import com.cohesiva.processes.db.UserDao;

public class RemoveSubscriberWorkItemHandler implements WorkItemHandler {

	private UserDao userDao;

	public RemoveSubscriberWorkItemHandler(UserDao userDao) {
		this.userDao = userDao;
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		List<String> playersList = (List<String>) workItem
				.getParameter("playersList");
		String email = (String) workItem.getParameter("userEmail");

		userDao.unsetBasketSubscription(email);

		// Notify manager that work item has been completed, pass
		// data
		manager.completeWorkItem(workItem.getId(), null);
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}
}
