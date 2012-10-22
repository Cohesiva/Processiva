package com.cohesiva.processes.jbpm.processes;

import java.util.List;

import org.drools.definition.process.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.db.UserDao;

@Service
public class ProcessesContraintsService implements IProcessesConstraingsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private IProcessService processService;

	public boolean isProcessAllowed(String processId, String userId) {
		if (processId.equals("com.cohesiva.basket.subscribe")) {
			if (userDao.isSubscribingBasket(userId)
					|| isAlreadyStartedByUser(processId, userId)) {
				return false;
			}
		} else if (processId.equals("com.cohesiva.basket.unsubscribe")) {
			if (!userDao.isSubscribingBasket(userId)
					|| isAlreadyStartedByUser(processId, userId)) {
				return false;
			}
		} else if (processId.equals("com.cohesiva.basket.payment")) {
			if (!userDao.isSubscribingBasket(userId)
					|| isAlreadyStartedByUser(processId, userId)) {
				return false;
			}
		} else if (processId.equals("com.cohesiva.basket.balance.inquiry")) {
			if (!userDao.isSubscribingBasket(userId)) {
				return false;
			}
		}

		return true;
	}

	private boolean isAlreadyStartedByUser(String processId, String userId) {
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
