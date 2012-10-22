package com.cohesiva.processes.jbpm.serviceImpl.processes;

import org.springframework.stereotype.Service;

import com.cohesiva.processes.jbpm.service.processes.IProcessesVariables;

@Service(value = "processesVariables")
public class ProcessesVariables implements IProcessesVariables {

	private String applicationUrl;

	public String getApplicationUrl() {
		return applicationUrl;
	}

	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}

	public String getEmailFooter() {
		String footer = "<br /><br /> <hr align=\"left\" width=\"600\"/> Cohesiva Process Engine: "
				+ this.getApplicationUrl();

		return footer;
	}
}
