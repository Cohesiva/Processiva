
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

import java.util.List;

import org.drools.definition.process.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.db.UserDao;
import com.cohesiva.processes.jbpm.service.processes.IProcessService;
import com.cohesiva.processes.jbpm.service.processes.IProcessesConstraintsService;

@Service
public class ProcessesContraintsService implements IProcessesConstraintsService {

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
