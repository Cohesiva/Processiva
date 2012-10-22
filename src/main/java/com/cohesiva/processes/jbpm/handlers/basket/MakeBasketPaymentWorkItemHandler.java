package com.cohesiva.processes.jbpm.handlers.basket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

import com.cohesiva.processes.db.UserDao;

public class MakeBasketPaymentWorkItemHandler implements WorkItemHandler {

	private UserDao userDao;

	private final int PRIZE = 50;

	public MakeBasketPaymentWorkItemHandler(UserDao userDao) {
		this.userDao = userDao;
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		List<String> playersList = (List<String>) workItem
				.getParameter("playersList");
		String email = (String) workItem.getParameter("userEmail");
		String emailFooter = (String) workItem.getParameter("emailFooter");

		int balance = userDao.modifyBalance(email, PRIZE);

		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, 2);

		Date expirationDate = now.getTime();

		userDao.setBalanceExpirationDate(email, expirationDate);

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String expDate = dateFormat.format(expirationDate);

		Map<String, Object> results = new HashMap<String, Object>();

		/*
		 * //stan twojego konta... jak w mejlu results.put("answerBody",
		 * "Zakupiłeś karnet Cohesiva Basket. Stan twojego konta to: " + balance
		 * + " PLN ważne do " + expDate + ".");
		 */
		String answerBody = "Zakupiłeś karnet Cohesiva Basket.<br />";

		answerBody += "Stan twojego konta Cohesiva Basket:<br />- wartość: <strong>"
				+ balance
				+ " PLN</strong>,<br />- ważność: <strong>"
				+ expDate + "</strong>."+emailFooter;

		results.put("answerBody", answerBody);
		// Notify manager that work item has been completed, pass
		// data
		manager.completeWorkItem(workItem.getId(), results);
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}
}
