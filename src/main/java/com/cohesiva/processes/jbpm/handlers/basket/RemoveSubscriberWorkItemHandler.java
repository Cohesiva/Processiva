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

import java.util.List;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.db.UserDao;
import com.cohesiva.processes.jbpm.handlers.BaseSynchronousWorkItemHandler;

@Service
public class RemoveSubscriberWorkItemHandler extends BaseSynchronousWorkItemHandler {

	@Autowired
	private UserDao userDao;

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		List<String> playersList = (List<String>) workItem
				.getParameter("playersList");
		String email = (String) workItem.getParameter("userEmail");

		userDao.unsetBasketSubscription(email);

		// Notify manager that work item has been completed, pass
		// data
		manager.completeWorkItem(workItem.getId(), null);
	}

	@Override
	protected void setWorkItemId() {
		this.workItemId = "RemoveSubscriber";
	}
}
