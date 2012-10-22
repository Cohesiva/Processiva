package com.cohesiva.processes.jbpm.service.processes;

import java.util.List;
import java.util.Map;

import org.drools.definition.process.Process;
import org.drools.runtime.process.ProcessInstance;

/*
 * Service for managing jbpm processes
 */
public interface IProcessService {
	public String getStartInfo(String procId);

	public ProcessInstance getProcessInstance(long processInstanceId);

	public Process getProcess(String processId);
	
	public List<Process> getAuthorizedProcesses(String userId);
	
	public List<String> getRunningInstancesNames(String userId);
	
	public Map<String, Object> getStartProcessData(String processId);
}
