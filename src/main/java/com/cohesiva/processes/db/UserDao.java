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
	/*BASKET PLAYER END*/
}
