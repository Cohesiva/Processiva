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

import java.util.Date;
import java.util.List;

public interface UserDao {
	public void persist(User user);

	public List<User> getAllUsers();
	
	public List<String> getUsersEmails();
	
	public User getUser(String email);
	
	/*BASKET PLAYER*/
	public boolean isSubscribingBasket(String email);
	
	public int getBasketBalance(String email);
	
	public void setBasketSubscription(String email);
	
	public void unsetBasketSubscription(String email);
	
	public int modifyBalance(String email, int amount);
	
	public int getBalance(String email);
	
	public List<User> getSubscribers();
	
	public Date getBalanceExpirationDate(String email);
	
	public void setBalanceExpirationDate(String email, Date date);
	
	public boolean isBalanceValid(String email);
	/*BASKET PLAYER END*/
}
