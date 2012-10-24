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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.jbpm.handlers.BaseSynchronousWorkItemHandler;

@Service
public class HandleNewPlayerWorkItemHandler extends
BaseSynchronousWorkItemHandler {

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		List<String> playersList = (List<String>) workItem
				.getParameter("playersList");
		String email = (String) workItem.getParameter("item");

		boolean newPlayerBlocked = (Boolean) workItem
				.getParameter("newPlayerBlocked");

		Map<String, Object> data = new HashMap<String, Object>();

		if (!newPlayerBlocked) {
			playersList.add(email);
		}

		data.put("playersList", playersList);

		// Notify manager that work item has been completed, pass
		// data
		manager.completeWorkItem(workItem.getId(), data);
	}

	@Override
	protected void setWorkItemId() {
		this.workItemId = "HandleNewPlayer";
	}
}
