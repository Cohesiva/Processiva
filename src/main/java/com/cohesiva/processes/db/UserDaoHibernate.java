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
package com.cohesiva.processes.db;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cohesiva.processes.db.basket.PlayerInfo;

@Repository
public class UserDaoHibernate implements UserDao {
	private SessionFactory sessionFactory;

	public UserDaoHibernate() {
	}

	@Autowired
	public UserDaoHibernate(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	// Stores a new user.
	@Transactional
	public void persist(User user) {

		// store if there is no user with such email already
		if (getUser(user.getEmail()) == null) {
			currentSession().save(user);
		} else {
			currentSession().merge(user);
		}
	}

	// Retrieves all the users.
	@Transactional
	public List<User> getAllUsers() {
		List<User> users = currentSession().createQuery("from User").list();

		return users;
	}

	// Gets user with given email.
	@Transactional
	public User getUser(String email) {

		User user = null;

		List<User> users = currentSession().createCriteria(User.class)
				.add(Restrictions.eq("email", email)).list();

		if (users.size() > 0) {
			user = users.get(0);
		}

		return user;
	}

	@Transactional
	public boolean isSubscribingBasket(String email) {

		User user = this.getUser(email);

		if (user != null && user.getPlayerInfo() != null) {
			return user.getPlayerInfo().isSubscribing();
		}

		return false;
	}

	@Transactional
	public int getBasketBalance(String email) {
		User user = this.getUser(email);

		if (user != null && user.getPlayerInfo() != null) {
			return user.getPlayerInfo().getBalance();
		}

		return 0;
	}

	@Transactional
	public void setBasketSubscription(String email) {
		User user = this.getUser(email);

		if (user != null) {
			if (user.getPlayerInfo() != null) {

				if (user.getPlayerInfo().isSubscribing() == true) {
					return;
				}

				user.getPlayerInfo().setSubscribing(true);
			} else {
				PlayerInfo playerInfo = new PlayerInfo(0, true);
				user.setPlayerInfo(playerInfo);
				playerInfo.setUser(user);
			}

			this.persist(user);
		}
	}

	@Transactional
	public void unsetBasketSubscription(String email) {
		User user = this.getUser(email);

		if (user != null) {
			if (user.getPlayerInfo() != null) {

				if (user.getPlayerInfo().isSubscribing() == false) {
					return;
				}

				user.getPlayerInfo().setSubscribing(false);
			} else {
				PlayerInfo playerInfo = new PlayerInfo(0, false);
				user.setPlayerInfo(playerInfo);
				playerInfo.setUser(user);
			}

			this.persist(user);
		}
	}

	@Transactional
	public int modifyBalance(String email, int amount) {
		int balance = 0;

		User user = this.getUser(email);

		if (user != null && user.getPlayerInfo() != null) {
			balance = user.getPlayerInfo().getBalance();
			balance += amount;
			user.getPlayerInfo().setBalance(balance);

			this.persist(user);
		}

		return balance;
	}

	@Transactional
	public int getBalance(String email) {
		int balance = 0;

		User user = this.getUser(email);

		if (user != null && user.getPlayerInfo() != null) {
			balance = user.getPlayerInfo().getBalance();
		}

		return balance;
	}

	@Transactional
	public List<User> getSubscribers() {
		List<User> users = currentSession().createQuery(
				"select u from User u where u.playerInfo.isSubscribing = true")
				.list();

		return users;
	}

	@Transactional
	public Date getBalanceExpirationDate(String email) {
		Date expirationDate = null;

		User user = this.getUser(email);

		if (user != null && user.getPlayerInfo() != null) {
			expirationDate = user.getPlayerInfo().getBalanceExpirationDate();
		}

		return expirationDate;
	}

	@Transactional
	public void setBalanceExpirationDate(String email, Date date) {
		User user = this.getUser(email);

		if (user != null && user.getPlayerInfo() != null) {
			user.getPlayerInfo().setBalanceExpirationDate(date);
		}

		this.persist(user);
	}

	@Transactional
	public List<String> getUsersEmails() {
		List<String> userEmails = currentSession().createQuery(
				"select email from User").list();

		return userEmails;
	}

	@Transactional
	public boolean isBalanceValid(String email) {
		boolean result = false;

		User user = this.getUser(email);

		if (user != null && user.getPlayerInfo() != null) {
			int balance = user.getPlayerInfo().getBalance();

			if (balance <= 0) {
				return false;
			}

			Date expirationDate = user.getPlayerInfo()
					.getBalanceExpirationDate();

			if (expirationDate != null) {
				Date now = new Date();

				Calendar expDate = Calendar.getInstance();
				expDate.setTime(expirationDate);
				expDate.add(Calendar.DATE, 1);

				if (now.before(expDate.getTime())) {
					result = true;
				} else {
					return false;
				}
			}
		}

		return result;
	}

}
