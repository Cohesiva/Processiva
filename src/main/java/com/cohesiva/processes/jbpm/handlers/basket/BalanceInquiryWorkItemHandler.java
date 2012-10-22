package com.cohesiva.processes.jbpm.handlers.basket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

import com.cohesiva.processes.db.UserDao;

public class BalanceInquiryWorkItemHandler implements WorkItemHandler {

	private UserDao userDao;

	public BalanceInquiryWorkItemHandler(UserDao userDao) {
		this.userDao = userDao;
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		List<String> playersList = (List<String>) workItem
				.getParameter("playersList");
		String email = (String) workItem.getParameter("userEmail");
		
		String emailFooter = (String) workItem.getParameter("emailFooter");

		int balance = userDao.getBasketBalance(email);
		Date expirationDate = userDao.getBalanceExpirationDate(email);

		/*
		String answerBody = "Stan twojego konta to " + balance + " PLN.";

		if (expirationDate != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String expDate = dateFormat.format(expirationDate);

			answerBody += " Karnet ważny do " + expDate + ".";
		}
		*/
		
		String answerBody = "Stan twojego konta Cohesiva Basket: <br />- wartość: <strong>"+balance+" PLN</strong>";

		if (expirationDate != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String expDate = dateFormat.format(expirationDate);

			answerBody += ",<br />- ważność: <strong>" + expDate +"</strong>";
		}

		answerBody += "." + emailFooter;
		
		Map<String, Object> results = new HashMap<String, Object>();

		results.put("answerBody", answerBody);

		// Notify manager that work item has been completed, pass
		// data
		manager.completeWorkItem(workItem.getId(), results);
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}
}
