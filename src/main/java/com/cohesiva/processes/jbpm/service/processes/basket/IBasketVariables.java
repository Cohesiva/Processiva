package com.cohesiva.processes.jbpm.service.processes.basket;

/*
 * Service for managing variables connected with basket processes
 */
public interface IBasketVariables {
	public String getBasketSigningFinalHour();

	public String getBasketInformAllSubscribersHour();

	public String getBasketMakePaymentsHour();

	public String getBasketStartHour();

	public Integer getBasketMaxPlayersNumber();

	public int getBasketSufficientPlayersNumber();

	public int getCarnetPrize();

	public int getEventCarnetPrize();

	public int getEventNonCarnetPrize();
}
