package com.cohesiva.processes.jbpm.serviceImpl.processes.basket;

import org.springframework.stereotype.Service;

import com.cohesiva.processes.jbpm.service.processes.basket.IBasketVariables;

@Service(value = "basketVariables")
public class BasketVariables implements IBasketVariables {
	private String basketSigningFinalHour;

	private String basketInformAllSubscribersHour;

	private String basketStartHour;

	private String basketMakePaymentsHour;

	private Integer basketMaxPlayersNumber;

	private int basketSufficientPlayersNumber;

	private int carnetPrize;

	private int eventCarnetPrize;

	private int eventNonCarnetPrize;

	public String getBasketSigningFinalHour() {
		return basketSigningFinalHour;
	}

	public void setBasketSigningFinalHour(String basketSigningFinalHour) {
		this.basketSigningFinalHour = basketSigningFinalHour;
	}

	public String getBasketInformAllSubscribersHour() {
		return basketInformAllSubscribersHour;
	}

	public void setBasketInformAllSubscribersHour(
			String basketInformAllSubscribersHour) {
		this.basketInformAllSubscribersHour = basketInformAllSubscribersHour;
	}

	public String getBasketStartHour() {
		return basketStartHour;
	}

	public void setBasketStartHour(String basketStartHour) {
		this.basketStartHour = basketStartHour;
	}

	public String getBasketMakePaymentsHour() {
		return basketMakePaymentsHour;
	}

	public void setBasketMakePaymentsHour(String basketMakePaymentsHour) {
		this.basketMakePaymentsHour = basketMakePaymentsHour;
	}

	public Integer getBasketMaxPlayersNumber() {
		return basketMaxPlayersNumber;
	}

	public void setBasketMaxPlayersNumber(Integer basketMaxPlayersNumber) {
		this.basketMaxPlayersNumber = basketMaxPlayersNumber;
	}

	public int getBasketSufficientPlayersNumber() {
		return basketSufficientPlayersNumber;
	}

	public void setBasketSufficientPlayersNumber(
			int basketSufficientPlayersNumber) {
		this.basketSufficientPlayersNumber = basketSufficientPlayersNumber;
	}

	public int getCarnetPrize() {
		return carnetPrize;
	}

	public void setCarnetPrize(int carnetPrize) {
		this.carnetPrize = carnetPrize;
	}

	public int getEventCarnetPrize() {
		return eventCarnetPrize;
	}

	public void setEventCarnetPrize(int eventCarnetPrize) {
		this.eventCarnetPrize = eventCarnetPrize;
	}

	public int getEventNonCarnetPrize() {
		return eventNonCarnetPrize;
	}

	public void setEventNonCarnetPrize(int eventNonCarnetPrize) {
		this.eventNonCarnetPrize = eventNonCarnetPrize;
	}
}
