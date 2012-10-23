
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

package com.cohesiva.processes.jbpm.service.processes;

import java.util.List;
import java.util.Map;

import org.drools.definition.process.Process;
import org.drools.runtime.process.ProcessInstance;

/*
 * Service for managing access and view of processes for users
 */
public interface IProcessService {
	//public String getStartInfo(String procId);

	public ProcessInstance getProcessInstance(long processInstanceId);

	public Process getProcess(String processId);
	
	public List<Process> getAuthorizedProcesses(String userId);
	
	public List<String> getRunningInstancesNames(String userId);
	
	//public Map<String, Object> getStartProcessData(String processId);
}
