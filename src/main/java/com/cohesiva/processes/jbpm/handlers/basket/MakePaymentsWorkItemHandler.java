package com.cohesiva.processes.jbpm.handlers.basket;

import java.util.List;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

import com.cohesiva.processes.db.UserDao;

public class MakePaymentsWorkItemHandler implements WorkItemHandler {

	private UserDao userDao;

	private final int BASKET_PAYMENT = 10;

	public MakePaymentsWorkItemHandler(UserDao userDao) {
		this.userDao = userDao;
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		List<String> playersList = (List<String>) workItem
				.getParameter("playersList");

		List<String> usersWithPositiveBalance = (List<String>) workItem
				.getParameter("list");

		// { Take payment from people who declared to come to basket and have positivie balance
		for (String email : playersList) {
			if (usersWithPositiveBalance.contains(email)) {
				System.out.println("Making payment of player: " + email);
				userDao.modifyBalance(email, -BASKET_PAYMENT);
			}
		}
		// }

		// Notify manager that work item has been completed, pass
		// data
		manager.completeWorkItem(workItem.getId(), null);
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}
}
