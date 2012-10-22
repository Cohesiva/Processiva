package com.cohesiva.processes.jbpm.handlers.basket;

import java.util.List;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

import com.cohesiva.processes.db.UserDao;

public class MakePaymentsWorkItemHandler implements WorkItemHandler {

	private UserDao userDao;

	public MakePaymentsWorkItemHandler(UserDao userDao) {
		this.userDao = userDao;
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		List<String> playersList = (List<String>) workItem
				.getParameter("playersList");
		
		int basketCarnetPayment =  (Integer) workItem
				.getParameter("eventCarnetPrize");

		// { Take payment from people who declared to come to basket and have positive balance
		for (String email : playersList) {
			if (userDao.isBalanceValid(email)) {
				System.out.println("Making payment of player: " + email);
				userDao.modifyBalance(email, -basketCarnetPayment);
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
