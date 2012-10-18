package com.cohesiva.processes.jbpm.handlers.basket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

public class HandleNewPlayerWorkItemHandler implements WorkItemHandler {

	// private UserDao userDao;

	// private final int BASKET_PAYMENT = 10;

	/*
	 * public HandleNewPlayerWorkItemHandler(UserDao userDao) { this.userDao =
	 * userDao; }
	 */

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		List<String> playersList = (List<String>) workItem
				.getParameter("playersList");
		String email = (String) workItem.getParameter("item");

		boolean newPlayerBlocked = (Boolean) workItem
				.getParameter("newPlayerBlocked");

		Map<String, Object> data = new HashMap<String, Object>();

		if (!newPlayerBlocked) {
			playersList.add(email);

			// userDao.modifyBalance(email, -BASKET_PAYMENT);
		}

		data.put("playersList", playersList);

		// Notify manager that work item has been completed, pass
		// data
		manager.completeWorkItem(workItem.getId(), data);
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}
}
