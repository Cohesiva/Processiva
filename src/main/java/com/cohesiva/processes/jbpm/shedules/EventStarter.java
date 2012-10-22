package com.cohesiva.processes.jbpm.shedules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.persistence.processinstance.ProcessInstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.db.UserDao;
import com.cohesiva.processes.db.jbpm.ProcessInstanceInfoDao;
import com.cohesiva.processes.jbpm.base.IJbpmBase;
import com.cohesiva.processes.jbpm.processes.IProcessesVariables;

/*
 * This service contains methods which are triggered on schedule and are used for starting processes 
 * and triggering events in processes.
 */
@Service(value = "eventStarter")
public class EventStarter {

	@Autowired
	protected IJbpmBase jbpmBase;

	@Autowired
	protected UserDao userDao;

	@Autowired
	private ProcessInstanceInfoDao processInstanceInfoDao;

	@Autowired
	private IProcessesVariables processVariables;

	public void startBasketWeeklyProcess() {
		StatefulKnowledgeSession ksession = jbpmBase.getSession();

		Map<String, Object> params = new HashMap<String, Object>();

		String emailFooter = processVariables.getEmailFooter();
		params.put("emailFooter", emailFooter);
		
		String answerLink = "<a href\""+processVariables.getApplicationUrl()+"\">tutaj</a>";
		
		params.put("answerLink", answerLink);

		ksession.startProcess("com.cohesiva.basket.weekly", params);
	}

	public void startProcess(String processId) {
		StatefulKnowledgeSession ksession = jbpmBase.getSession();
		
		ksession.startProcess("processId");
	}
	
	private void signal(String signal, String processId) {
		StatefulKnowledgeSession ksession = jbpmBase.getSession();

		List<ProcessInstanceInfo> runningInstances = processInstanceInfoDao
				.getRunningInstances(processId);

		for (ProcessInstanceInfo instance : runningInstances) {
			ksession.signalEvent(signal, null, instance.getId());
		}
	}
}