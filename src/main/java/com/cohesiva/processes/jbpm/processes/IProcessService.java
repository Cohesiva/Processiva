package com.cohesiva.processes.jbpm.processes;

import java.util.List;

import org.drools.definition.process.Process;
import org.drools.runtime.process.ProcessInstance;

public interface IProcessService {
	public String getStartInfo(String procId);

	public ProcessInstance getProcessInstance(long processInstanceId);

	public Process getProcess(String processId);
	
	public List<Process> getAuthorizedProcesses(String userId);
	
	public List<String> getRunningInstancesNames(String userId);
}
