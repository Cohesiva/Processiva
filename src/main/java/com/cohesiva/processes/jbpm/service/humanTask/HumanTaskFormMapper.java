package com.cohesiva.processes.jbpm.service.humanTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/*
 * This service contains mappings between human tasks and coresponding controllers and views. 
 */
@Service
public class HumanTaskFormMapper {
	// statyczna bo uzywana w EL na jsp
	private static Map<String, String> urlMappings = new HashMap<String, String>();

	private Map<String, String> viewMappings = new HashMap<String, String>();

	// statyczna bo uzywana w EL na jsp
	private static List<String> immediateTasks = new ArrayList<String>();

	public HumanTaskFormMapper() {
		mapUrls();
		mapViews();
		mapImmediateTasks();
	}

	public String getViewMapping(String processId, String taskName) {
		String key = processId + "_" + taskName;

		return viewMappings.get(key);
	}

	// statyczna bo uzywana w EL na jsp
	public static String getUrlMapping(String processId, String taskName) {
		String key = processId + "_" + taskName;

		return urlMappings.get(key);
	}

	// statyczna bo uzywana w EL na jsp
	public static Boolean isTaskImmediete(String processId, String taskName) {
		String key = processId + "_" + taskName;

		return immediateTasks.contains(key);
	}

	private void mapUrls() {
		urlMappings.put("com.sample.bpmn.poc_Value confirmation",
				"/pocProcess/confirmValue");
		urlMappings.put(
				"com.cohesiva.basket.subscribe_confirmBasketSubscription",
				"/basketSubProcess/confirm");
		urlMappings.put("com.cohesiva.basket.payment_confirmBasketPayment",
				"/basketPaymentProcess/confirm");
		urlMappings.put(
				"com.cohesiva.basket.weekly_BasketPresenceConfirmation",
				"/basketWeeklyProcess/confirm");
		urlMappings.put("com.cohesiva.basket.weekly_CallBasketOff",
				"/basketWeeklyProcess/callBasketOff");
		urlMappings.put("com.cohesiva.hr.getCV_configureCV",
				"/generateCVProcess/configureCV");
	}

	private void mapViews() {
		viewMappings.put("com.sample.bpmn.poc_Value confirmation",
				"jsp/human_tasks/forms/pocProcess/confirmValueForm.jsp");
		viewMappings.put(
				"com.cohesiva.basket.subscribe_confirmBasketSubscription",
				"jsp/human_tasks/forms/basket/confirmSubscriptionForm.jsp");
		viewMappings.put("com.cohesiva.basket.payment_confirmBasketPayment",
				"jsp/human_tasks/forms/basket/confirmPaymentForm.jsp");
		viewMappings.put(
				"com.cohesiva.basket.weekly_BasketPresenceConfirmation",
				"jsp/human_tasks/forms/basket/confirmPresenceForm.jsp");
		viewMappings.put("com.cohesiva.basket.weekly_CallBasketOff",
				"jsp/human_tasks/forms/basket/callBasketOffForm.jsp");
	}

	private void mapImmediateTasks() {
		immediateTasks.add("com.sample.bpmn.poc_Value confirmation");
		immediateTasks
				.add("com.cohesiva.basket.subscribe_confirmBasketSubscription");
		immediateTasks.add("com.cohesiva.basket.payment_confirmBasketPayment");
		immediateTasks
				.add("com.cohesiva.basket.weekly_BasketPresenceConfirmation");
		immediateTasks.add("com.cohesiva.basket.weekly_CallBasketOff");
	}
}
