package com.cohesiva.processes.jbpm.service.processes.hr;

import java.util.List;

/*
 * Service for processes connected with HR
 */
public interface IHRServices {
	public List<String> getEmployeesList();
	
	public List<String> getCVFormats();
}
