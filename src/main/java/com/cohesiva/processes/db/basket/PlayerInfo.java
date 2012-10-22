package com.cohesiva.processes.db.basket;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.cohesiva.processes.db.User;

@Entity
@Table(name="players")
public class PlayerInfo implements Serializable {
	private static final long serialVersionUID = 1L;
		
	// Persistent Fields:
	@Id
	@GeneratedValue(generator="gen")
	@GenericGenerator(name="gen", strategy="foreign", parameters=@Parameter(name="property", value="user"))
	Long userId;
	private int balance;
	private boolean isSubscribing;
	
	@Type(type="date")
	private Date balanceExpirationDate;
	
	@OneToOne
    @PrimaryKeyJoinColumn
    private User user;

	// Constructors:
	public PlayerInfo() {
	}
	
	public PlayerInfo(int balance, boolean isSubscribing) {
		this.balance = balance;
		this.isSubscribing = isSubscribing;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public boolean isSubscribing() {
		return isSubscribing;
	}

	public void setSubscribing(boolean isSubscribing) {
		this.isSubscribing = isSubscribing;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getBalanceExpirationDate() {
		return balanceExpirationDate;
	}

	public void setBalanceExpirationDate(Date balanceExpirationDate) {
		this.balanceExpirationDate = balanceExpirationDate;
	}

	
}
