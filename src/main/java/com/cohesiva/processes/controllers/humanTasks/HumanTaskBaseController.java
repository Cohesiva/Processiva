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
package com.cohesiva.processes.controllers.humanTasks;

import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.process.ProcessInstance;
import org.jbpm.task.service.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.cohesiva.processes.jbpm.service.base.IJbpmBase;
import com.cohesiva.processes.jbpm.service.humanTask.HumanTaskFormMapper;
import com.cohesiva.processes.jbpm.service.humanTask.IHumanTaskService;
import com.cohesiva.processes.jbpm.serviceImpl.auth.AuthorizationService;
import com.cohesiva.processes.jbpm.serviceImpl.processes.ProcessService;

public abstract class HumanTaskBaseController {

	@Autowired
	protected IJbpmBase jbpmBase;

	@Autowired
	protected IHumanTaskService humanTaskService;

	@Autowired
	protected HumanTaskFormMapper humanTaskFormMapper;

	@Autowired
	protected AuthorizationService authorizationService;
	
	@Autowired
	protected ProcessService processService;

	protected ProcessInstance getProcessInstance(long processInstanceId) {
		return processService.getProcessInstance(processInstanceId);
	}

	protected String getViewPath(String processId, String taskId) {
		String taskName = humanTaskService.getTaskName(new Long(taskId));

		return humanTaskFormMapper.getViewMapping(processId, taskName);
	}
	
	@ExceptionHandler(PermissionDeniedException.class)
	public ModelAndView handlePermDeniadException() {
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("errorMsg", "Wystąpił błąd w wykonywaniu zadania. Nie masz praw do tego zadania, lub żadana akcja jest niezgodna ze statusem zadania.");
		
		return new ModelAndView("jsp/error/error.jsp", data);
	}
}
