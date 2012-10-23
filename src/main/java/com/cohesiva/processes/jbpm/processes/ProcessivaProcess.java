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
package com.cohesiva.processes.jbpm.processes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ProcessivaProcess {
	protected String processId;
	
	protected String startProcessInfo;
	
	protected List<String> authorizedGroups;

	protected ProcessivaProcess() {
		initProcessId();
		initStartProcessInfo();
		initAuthorizedGroups();
	}
	
	// All authorized users can view process at any time by default
	public boolean isAllowedToViewNow(String userId) {
		return true;
	}
	
	public Map<String, Object> getInitData() {
		Map<String, Object> data = new HashMap<String, Object>();
		
		return data;	
	}

	protected abstract void initProcessId();
	
	protected void initStartProcessInfo() {};
	
	// All users can start process by default
	protected void initAuthorizedGroups(){ 
		this.authorizedGroups = new ArrayList<String>(Arrays.asList("ALL"));
	}
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setStartProcessInfo(String startProcessInfo) {
		this.startProcessInfo = startProcessInfo;
	}

	public void setAuthorizedGroups(List<String> authorizedGroups) {
		this.authorizedGroups = authorizedGroups;
	}

	public String getStartProcessInfo() {
		return startProcessInfo;
	}

	public List<String> getAuthorizedGroups() {
		return authorizedGroups;
	}
	
}
