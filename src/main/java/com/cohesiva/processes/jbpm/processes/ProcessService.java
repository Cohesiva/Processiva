package com.cohesiva.processes.jbpm.processes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.definition.process.Process;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.db.jbpm.ProcessInstanceInfoDao;
import com.cohesiva.processes.jbpm.auth.IAuthorizationService;
import com.cohesiva.processes.jbpm.base.IJbpmBase;

@Service
public class ProcessService implements IProcessService {

	@Autowired
	IJbpmBase jbpmBase;

	@Autowired
	private IAuthorizationService authorizationService;

	@Autowired
	private IProcessesConstraingsService processesContraintsService;

	@Autowired
	private ProcessInstanceInfoDao processInstanceInfoDao;

	private Map<String, String> startInfoMap = new HashMap<String, String>();

	public ProcessService() {
		startInfoMap
				.put("com.cohesiva.basket.subscribe",
						"Zostaniesz powiadomiony mailowo, kiedy twoje zgłoszenie subskrypcji grupy Cohesiva Basket zostanie zatwierdzone.");
		startInfoMap
				.put("com.cohesiva.basket.unsubscribe",
						"Zglosiłeś chęć zakończenia subskrypcji grupy Cohesiva Basket. Sprawdź email, aby upewnić się, że zakończyłeś subskypcję.");
		startInfoMap
				.put("com.cohesiva.basket.payment",
						"Aby zakupić karnet, wpłać 50 PLN w sekretariacie Cohesiva.");
		startInfoMap
				.put("com.cohesiva.basket.balance.inquiry",
						"Informacje o stanie Twojego konta zostały wysłane na Twój adres email.");
	}

	public String getStartInfo(String procId) {
		return startInfoMap.get(procId);
	}

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

}
