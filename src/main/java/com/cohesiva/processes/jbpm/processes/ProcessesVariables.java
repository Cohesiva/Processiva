package com.cohesiva.processes.jbpm.processes;

import org.springframework.stereotype.Service;

@Service
public class ProcessesVariables implements IProcessesVariables {

	public String getApplicationUrl() {
		return "http://processiva.cohesiva.com:4080/Jbpm_processes";
	}

	public String getCohesivaBasketSubject() {
		return "Cohesiva Basket";
	}

	public String getCohesivaBasketFromEmail() {
		return "cohesiva.basket@cohesiva.com";
	}

	public String getEmailFooter() {
		String footer = "<br /><br /> <hr align=\"left\" width=\"600\"/> Cohesiva Process Engine: "
				+ this.getApplicationUrl();

		return footer;
	}
	
	public int getBasketFinalHour() {
		return 15;
	}

}
