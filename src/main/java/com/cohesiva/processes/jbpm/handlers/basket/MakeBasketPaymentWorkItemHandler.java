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

	// private final int PRIZE = 50;

	public MakeBasketPaymentWorkItemHandler(UserDao userDao) {
		this.userDao = userDao;
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		List<String> playersList = (List<String>) workItem
				.getParameter("playersList");
		String email = (String) workItem.getParameter("userEmail");
		String emailFooter = (String) workItem.getParameter("emailFooter");
		int prize = (Integer) workItem.getParameter("carnetPrize");

		int balance = userDao.modifyBalance(email, prize);

		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, 2);

		Date expirationDate = now.getTime();

		userDao.setBalanceExpirationDate(email, expirationDate);

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String expDate = dateFormat.format(expirationDate);

		Map<String, Object> results = new HashMap<String, Object>();

		String answerBody = "Zakupiłeś karnet Cohesiva Basket.<br />";

		answerBody += "Stan twojego konta Cohesiva Basket:<br />- wartość: <strong>"
				+ balance
				+ " PLN</strong>,<br />- ważność: <strong>"
				+ expDate
				+ "</strong>." + emailFooter;

		results.put("answerBody", answerBody);
		// Notify manager that work item has been completed, pass
		// data
		manager.completeWorkItem(workItem.getId(), results);
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}
}
