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

package com.cohesiva.processes.jbpm.serviceImpl.shedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.jbpm.service.base.IJbpmBase;
import com.cohesiva.processes.jbpm.service.processes.IProcessStarterService;
import com.cohesiva.processes.jbpm.service.shedules.IEventStarter;

@Service(value = "eventStarter")
public class EventStarter implements IEventStarter {

	@Autowired
	protected IJbpmBase jbpmBase;

	@Autowired
	private IProcessStarterService processStarterService;

	public void startProcess(String processId) {
		processStarterService.startProcess(processId, "SCHEDULER");
	}

	public void signal(String signal, String processId) {
		jbpmBase.signalEvent(signal, processId);
	}
}
