package com.cohesiva.processes.jbpm.handlers.basket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	
	private final int SUFFICIENT_PLAYERS_NUMBER = 10;
	private final int MAX_PLAYERS_NUMBER = 12;
	
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

				Date now = new Date();

				for (User user : subscribers) {
					if (user.getPlayerInfo().getBalance() > 0) {
						Date expirationDate = user.getPlayerInfo()
								.getBalanceExpirationDate();

						if (expirationDate != null) {
							Calendar expDate = Calendar.getInstance();
							expDate.setTime(expirationDate);
							expDate.add(Calendar.DATE, 1);

							if (now.before(expDate.getTime())) {
								list.add(user.getEmail());
								continue;
							} else {
								//wyzeruj gosciowi konto
								user.getPlayerInfo().setBalance(0);
								userDao.persist(user);
							}
						}
					}

					guestList.add(user.getEmail());
				}

				Map<String, Object> data = new HashMap<String, Object>();

				data.put("list", list);
				data.put("guestList", guestList);

				List<String> playersList = new Vector<String>();
				data.put("playersList", playersList);
				data.put("sufficientPlayersNumber", SUFFICIENT_PLAYERS_NUMBER);
				data.put("maxPlayersNumber", MAX_PLAYERS_NUMBER);
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
