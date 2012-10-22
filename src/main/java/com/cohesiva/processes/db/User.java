package com.cohesiva.processes.db;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cohesiva.processes.db.basket.PlayerInfo;

@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	// Persistent Fields:
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	private String email;
	private Date signingDate;
	private String firstName;
	private String surname;
	
	@OneToOne(mappedBy="user", cascade=CascadeType.ALL)
    private PlayerInfo playerInfo;

	// Constructors:
	public User() {
	}

	public User(String email) {
		this.email = email;
		this.signingDate = new Date(System.currentTimeMillis());
	}
	
	public User(String email, String firstName, String surname) {
		this.email = email;
		this.signingDate = new Date(System.currentTimeMillis());
		this.firstName = firstName;
		this.surname = surname;
	}

	// String Representation:
	@Override
	public String toString() {
		return email + " (signed on " + signingDate + ")";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getSigningDate() {
		return signingDate;
	}

	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public void setPlayerInfo(PlayerInfo playerInfo) {
		this.playerInfo = playerInfo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
}
