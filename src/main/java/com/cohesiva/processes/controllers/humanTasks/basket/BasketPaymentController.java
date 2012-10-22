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
package com.cohesiva.processes.controllers.humanTasks.basket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.drools.runtime.process.ProcessInstance;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cohesiva.processes.controllers.humanTasks.HumanTaskBaseController;
import com.cohesiva.processes.jbpm.service.processes.basket.IBasketVariables;

@Controller
public class BasketPaymentController extends HumanTaskBaseController {
	
	@Autowired
	private IBasketVariables basketVariables;

	@RequestMapping(value = "/basketPaymentProcess/confirm/{taskId}/{processInstanceId}", method = RequestMethod.GET)
	protected ModelAndView completeTask(@PathVariable String taskId,
			@PathVariable long processInstanceId) {
		ProcessInstance procInstance = this
				.getProcessInstance(processInstanceId);

		String userEmail = null;

		if (procInstance != null) {
			userEmail = (String) ((WorkflowProcessInstance) procInstance)
					.getVariable("userEmail");
		}

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("userEmail", userEmail);
		data.put("taskId", taskId);
		data.put("carnetPrize", basketVariables.getCarnetPrize());

		String processId = procInstance.getProcessId();

		String viewPath = this.getViewPath(processId, taskId);

		return new ModelAndView(viewPath, data);
	}

	@RequestMapping(value = "/paymentBasket/confirm/handle_complete_task", params = "accept", method = RequestMethod.POST)
	public String completeTaskAccept(@RequestParam("taskId") long taskId,
			HttpSession httpSession) {

		String email = (String) httpSession.getAttribute("loggedEmail");

		if (email != null) {
			List<String> groups = authorizationService.getUserGroups(email);

			Map<String, Object> results = new HashMap<String, Object>();

			results.put("confirmedPayment", true);

			humanTaskService.finishTask(taskId, email, groups, results);
		}

		return "redirect:/humanTasks";
	}

	@RequestMapping(value = "/paymentBasket/confirm/handle_complete_task", params = "decline", method = RequestMethod.POST)
	public String completeTaskDecline(@RequestParam("taskId") long taskId,
			HttpSession httpSession) {

		String email = (String) httpSession.getAttribute("loggedEmail");

		if (email != null) {
			List<String> groups = authorizationService.getUserGroups(email);

			Map<String, Object> results = new HashMap<String, Object>();

			results.put("confirmedPayment", false);

			humanTaskService.finishTask(taskId, email, groups, results);
		}

		return "redirect:/humanTasks";
	}
}
