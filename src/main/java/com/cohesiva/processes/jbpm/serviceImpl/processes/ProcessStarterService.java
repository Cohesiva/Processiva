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
package com.cohesiva.processes.jbpm.serviceImpl.processes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.definition.process.Process;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.jbpm.processes.ProcessivaProcess;
import com.cohesiva.processes.jbpm.service.base.IJbpmBase;
import com.cohesiva.processes.jbpm.service.processes.IProcessService;
import com.cohesiva.processes.jbpm.service.processes.IProcessStarterService;
import com.cohesiva.processes.jbpm.service.processes.IProcessesVariablesService;

@Service
public class ProcessStarterService implements IProcessStarterService {

	@Autowired
	IJbpmBase jbpmBase;

	@Autowired
	private IProcessService processService;

	@Autowired
	private IProcessesVariablesService processVariables;

	public String getStartInfo(String procId) {
		ProcessivaProcess processivaProc = processService
				.getProcessivaProcess(procId);

		String result = "Process started!";

		if (processivaProc != null) {
			result = processivaProc.getStartProcessInfo();
		}

		return result;
	}

	private Map<String, Object> getStartProcessData(String processId) {
		Map<String, Object> result = null;

		ProcessivaProcess processivaProc = processService
				.getProcessivaProcess(processId);

		if (processivaProc != null) {
			result = processivaProc.getInitData();
		}

		return result;
	}

	public void startProcess(String processId, String userId) {
		StatefulKnowledgeSession session = jbpmBase.getSession();

		Map<String, Object> params = this.getStartProcessData(processId);

		if (params == null) {
			params = new HashMap<String, Object>();
		}

		params.put("userEmail", userId);

		String emailFooter = processVariables.getEmailFooter();
		params.put("emailFooter", emailFooter);

		session.startProcess(processId, params);
	}

	public boolean isProcessStartedByUser(String processId, String userId) {
		boolean result = false;

		Process proc = processService.getProcess(processId);

		if (proc != null) {
			String procName = proc.getName();

			List<String> runningInstancesNames = processService
					.getRunningInstancesNames(userId);

			if (runningInstancesNames.contains(procName)) {
				return true;
			}
		}

		return result;
	}

}
