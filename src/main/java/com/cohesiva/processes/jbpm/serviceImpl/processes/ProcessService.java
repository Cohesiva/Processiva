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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.drools.definition.process.Process;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.jbpm.persistence.processinstance.ProcessInstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.db.jbpm.ProcessInstanceInfoDao;
import com.cohesiva.processes.jbpm.processes.ProcessivaProcess;
import com.cohesiva.processes.jbpm.service.auth.IAuthorizationService;
import com.cohesiva.processes.jbpm.service.base.IJbpmBase;
import com.cohesiva.processes.jbpm.service.processes.IProcessService;
import com.cohesiva.processes.jbpm.service.processes.IProcessesConstraintsService;

@Service
public class ProcessService implements IProcessService {

	@Autowired
	IJbpmBase jbpmBase;

	@Autowired
	private IAuthorizationService authorizationService;

	@Autowired
	private IProcessesConstraintsService processesContraintsService;

	@Autowired
	private ProcessInstanceInfoDao processInstanceInfoDao;

	@Autowired
	private List<ProcessivaProcess> processivaProcesses;

	public ProcessInstance getProcessInstance(long processInstanceId) {
		StatefulKnowledgeSession ksession = jbpmBase.getSession();

		if (ksession != null) {
			return ksession.getProcessInstance(processInstanceId);
		} else {
			return null;
		}
	}

	public Process getProcess(String processId) {
		StatefulKnowledgeSession ksession = jbpmBase.getSession();

		if (ksession != null) {
			return ksession.getKnowledgeBase().getProcess(processId);
		} else {
			return null;
		}
	}

	public List<Process> getAuthorizedProcesses(String userId) {
		List<Process> result = null;

		if (userId != null) {
			Collection<Process> processesCollection = jbpmBase.getSession()
					.getKnowledgeBase().getProcesses();

			List<Process> processes = new ArrayList<Process>(
					processesCollection);

			result = new ArrayList<Process>();

			for (Process process : processes) {
				String processId = process.getId();

				if (authorizationService.isAuthorized(processId, userId)
						&& processesContraintsService.isProcessAllowed(
								processId, userId)) {
					result.add(process);
				}
			}
		}

		return result;
	}

	public List<String> getRunningInstancesNames(String userId) {
		List<String> result = null;

		if (userId != null) {
			result = new ArrayList<String>();

			List<Long> runningProcessesIds = processInstanceInfoDao
					.getRunningInstancesIds();
			for (Long runningInstanceId : runningProcessesIds) {
				ProcessInstance instance = this
						.getProcessInstance(runningInstanceId);

				if (instance != null) {
					String userEmail = (String) ((WorkflowProcessInstance) instance)
							.getVariable("userEmail");

					if (userEmail != null && userEmail.equals(userId)) {
						String procId = instance.getProcessId();

						if (procId != null) {
							Process proc = this.getProcess(procId);
							result.add(proc.getName());
						}
					}
				}
			}
		}

		return result;
	}

	public ProcessivaProcess getProcessivaProcess(String processId) {
		ProcessivaProcess result = null;

		for (ProcessivaProcess proc : processivaProcesses) {
			String procId = proc.getProcessId();

			if (procId != null && procId.equals(processId)) {
				return proc;
			}
		}

		return result;
	}

	public List<ProcessInstanceInfo> getRunningInstances(String processId) {
		return processInstanceInfoDao.getRunningInstances(processId);
	}
}
