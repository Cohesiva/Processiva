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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.db.User;
import com.cohesiva.processes.db.UserDao;
import com.cohesiva.processes.jbpm.handlers.BaseAsynchronousWorkItemHandler;

@Service
public class LoadPlayersListsWorkItemHandler extends
		BaseAsynchronousWorkItemHandler {
	
	@Autowired
	private UserDao userDao;

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		new Thread(new Runnable() {

			private WorkItem workItem;

			public Runnable setData(WorkItem workItem) {
				this.workItem = workItem;
				return this;
			}

			public void run() {						
				System.out.println("Inicjalizowanie list subskrybentów.");

				List<User> subscribers = userDao.getSubscribers();

				List<String> list = new Vector<String>();
				List<String> guestList = new Vector<String>();

				for (User user : subscribers) {
					if (user.getPlayerInfo().getBalance() > 0) {
						
						boolean isBalanceValid = userDao.isBalanceValid(user.getEmail());
										
						if (!isBalanceValid) {
							//wyzeruj gosciowi konto
							user.getPlayerInfo().setBalance(0);
							userDao.persist(user);
						} else {
							list.add(user.getEmail());
							continue;
						}
					}

					guestList.add(user.getEmail());
				}

				Map<String, Object> data = new HashMap<String, Object>();

				data.put("list", list);
				data.put("guestList", guestList);

				List<String> playersList = new Vector<String>();
				data.put("playersList", playersList);
				data.put("subscribers", subscribers);
				data.put("newPlayerBlocked", false);				
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				String today = dateFormat.format(new Date());
				data.put("date", today);
				
				System.out.println("Listy subskrybentów zainicjalizowane.");

				// Notify manager that work item has been completed, pass
				// data
				ksession.getWorkItemManager().completeWorkItem(
						workItem.getId(), data);

			}
		}.setData(workItem)).start();
	}

	@Override
	protected void setWorkItemId() {
		this.workItemId = "LoadPlayersList";
	}
}
