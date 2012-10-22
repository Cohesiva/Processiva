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

import com.cohesiva.processes.db.User;
import com.cohesiva.processes.db.UserDao;
import com.cohesiva.processes.jbpm.handlers.BaseAsynchronousWorkItemHandler;

public class LoadPlayersListsWorkItemHandler extends
		BaseAsynchronousWorkItemHandler {
	
	private UserDao userDao;
	
	public LoadPlayersListsWorkItemHandler(UserDao userDao) {
		this.userDao = userDao;
	}

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
}
