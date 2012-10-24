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
package com.cohesiva.processes.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cohesiva.processes.jbpm.service.processes.IProcessService;
import com.cohesiva.processes.jbpm.service.processes.IProcessStarterService;

@Controller
public class ProcessesController {

	@Autowired
	private IProcessService processService;

	@Autowired
	private IProcessStarterService processStarterService;
	

	@RequestMapping(value = "/processes")
	public ModelAndView showProcesses(HttpServletRequest request,
			HttpSession httpSession) {

		String email = (String) httpSession.getAttribute("loggedEmail");

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("processes", processService.getAuthorizedProcesses(email));

		data.put("userInstances",
				processService.getRunningInstancesNames(email));

		return new ModelAndView("jsp/processes/processes.jsp", data);
	}

	@RequestMapping(value = "/start_process", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView startProcess(@RequestParam String processId,
			HttpSession httpSession) {

		String email = (String) httpSession.getAttribute("loggedEmail");

		processStarterService.startProcess(processId, email);

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("processes", processService.getAuthorizedProcesses(email));
		data.put("userInstances",
				processService.getRunningInstancesNames(email));

		String startInfo = processStarterService.getStartInfo(processId);
		data.put("info", startInfo);

		return new ModelAndView("jsp/processes/processes-body.jsp", data);
	}
}
