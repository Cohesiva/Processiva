package com.cohesiva.processes.jbpm.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements IAuthorizationService {

	private static Map<String, List<String>> userGroupsMapping = new HashMap<String, List<String>>();

	private static Map<String, List<String>> processGroupsMapping = new HashMap<String, List<String>>();

	public AuthorizationService() {
		mapUserGroups();
		mapProcessGroups();
	}

	public List<String> getUserGroups(String userId) {
		return userGroupsMapping.get(userId);
	}

	private List<String> getProcAllowedGroups(String processId) {
		return processGroupsMapping.get(processId);
	}

	public boolean isAuthorized(String processId, String userId) {
		List<String> allowedGroups = getProcAllowedGroups(processId);

		if (allowedGroups == null) {
			return false;
		} else if (allowedGroups.contains("ALL")) {
			return true;
		}

		List<String> userGroups = getUserGroups(userId);

		if (userGroups != null) {
			for (String allowedGroup : allowedGroups) {
				if (userGroups.contains(allowedGroup)) {
					return true;
				}
			}
		}

		return false;
	}

	private void mapUserGroups() {
		List<String> damianGroups = new ArrayList<String>();
		damianGroups.add("group 1");
		userGroupsMapping.put("damian.kardanski@cohesiva.com", damianGroups);

		List<String> kardanskiGroups = new ArrayList<String>();
		kardanskiGroups.add("asd");
		userGroupsMapping.put("kardanski.damian@gmail.com", kardanskiGroups);
	}

	private void mapProcessGroups() {
		List<String> pocProcGroups = new ArrayList<String>();
		pocProcGroups.add("group 1");
		processGroupsMapping.put("com.sample.bpmn.poc", pocProcGroups);

		List<String> basketSubscribeGroups = new ArrayList<String>();
		basketSubscribeGroups.add("ALL");
		processGroupsMapping.put("com.cohesiva.basket.subscribe",
				basketSubscribeGroups);

		List<String> basketUnsubscribeGroups = new ArrayList<String>();
		basketUnsubscribeGroups.add("ALL");
		processGroupsMapping.put("com.cohesiva.basket.unsubscribe",
				basketUnsubscribeGroups);

		List<String> basketPaymentGroups = new ArrayList<String>();
		basketPaymentGroups.add("ALL");
		processGroupsMapping.put("com.cohesiva.basket.payment",
				basketPaymentGroups);
		
		List<String> basketBalanceInquiryGroups = new ArrayList<String>();
		basketBalanceInquiryGroups.add("ALL");
		processGroupsMapping.put("com.cohesiva.basket.balance.inquiry",
				basketBalanceInquiryGroups);

		/*
		List<String> testGroups = new ArrayList<String>();
		testGroups.add("ALL");
		processGroupsMapping.put("com.cohesiva.test", testGroups);
		*/
		
		/*
		List<String> basketWeeklyGroup = new ArrayList<String>();
		basketWeeklyGroup.add("ALL");
		processGroupsMapping.put("com.cohesiva.basket.weekly", basketWeeklyGroup);
		*/
		
		/*
		List<String> getCVGroup = new ArrayList<String>();
		getCVGroup.add("ALL");
		processGroupsMapping.put("com.cohesiva.hr.getCV", getCVGroup);
		*/
		
	}
}
