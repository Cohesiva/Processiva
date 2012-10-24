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

package com.cohesiva.processes.jbpm.serviceImpl.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.jbpm.processes.ProcessivaProcess;
import com.cohesiva.processes.jbpm.service.auth.IAuthorizationService;
import com.cohesiva.processes.jbpm.service.processes.IProcessService;

@Service
public class AuthorizationService implements IAuthorizationService {

	@Autowired
	private IProcessService processService;

	private static Map<String, List<String>> userGroupsMapping = new HashMap<String, List<String>>();

	public AuthorizationService() {
		mapUserGroups();
	}

	public List<String> getUserGroups(String userId) {
		return userGroupsMapping.get(userId);
	}

	public boolean isAuthorized(String processId, String userId) {
		ProcessivaProcess processivaProc = processService
				.getProcessivaProcess(processId);

		List<String> allowedGroups = null;

		if (processivaProc != null) {
			allowedGroups = processivaProc.getAuthorizedGroups();
		}

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
}
