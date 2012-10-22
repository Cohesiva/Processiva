package com.cohesiva.processes.jbpm.handlers.basket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

import com.cohesiva.processes.db.User;
import com.cohesiva.processes.db.UserDao;

public class BasketWeeklySummaryWorkItemHandler implements WorkItemHandler {

	private UserDao userDao;

	public BasketWeeklySummaryWorkItemHandler(UserDao userDao) {
		this.userDao = userDao;
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		List<String> playersList = (List<String>) workItem
				.getParameter("playersList");
		String date = (String) workItem.getParameter("date");
		String emailFooter = (String) workItem.getParameter("emailFooter");

		int size = 0;

		if (playersList != null) {
			size = playersList.size();
		}

		StringBuilder summary = new StringBuilder(
				"W dniu "
						+ date
						+ " na zajęcia koszykówki wybiera się następująca liczba zawodników: "
						+ size + ".<br />");

		if (size > 0) {
			summary.append("<br />Lista:<br />");

			for(int i=0; i<size-1; i++) {
			
			//for (String email : playersList) {
				User user = userDao.getUser(playersList.get(i));

				summary.append("- "+user.getFirstName() + " " + user.getSurname()
						+ " (" + user.getEmail() + "),<br />");
			}

			User user = userDao.getUser(playersList.get(size-1));
			
			summary.append("- "+user.getFirstName() + " " + user.getSurname()
					+ " (" + user.getEmail() + ").<br />");
			
			summary.append("<br />Miłej zabawy!" + emailFooter);
		}

		Map<String, Object> results = new HashMap<String, Object>();

		results.put("summaryReport", summary.toString());

		manager.completeWorkItem(workItem.getId(), results);
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}
}
