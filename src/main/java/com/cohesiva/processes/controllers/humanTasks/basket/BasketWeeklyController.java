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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cohesiva.processes.controllers.humanTasks.HumanTaskBaseController;
import com.cohesiva.processes.jbpm.service.processes.basket.IBasketProcessesService;

@Controller
public class BasketWeeklyController extends HumanTaskBaseController {

	@Autowired
	IBasketProcessesService basketProcessesService;

	@RequestMapping(value = "/basketWeeklyProcess/confirm/{taskId}/{processInstanceId}", method = RequestMethod.GET)
	protected ModelAndView completeTask(@PathVariable String taskId,
			@PathVariable long processInstanceId) {

		Map<String, Object> data = new HashMap<String, Object>();

		String viewPath;

		if (!basketProcessesService.isTooLateToSignUp()) {
			data.put("taskId", taskId);

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String today = dateFormat.format(new Date());
			data.put("date", today);

			viewPath = "jsp/human_tasks/forms/basket/confirmPresenceForm.jsp";
		} else {
			viewPath = "jsp/human_tasks/forms/basket/tooLate.jsp";
		}

		return new ModelAndView(viewPath, data);
	}

	@RequestMapping(value = "/basketWeekly/confirm/handle_complete_task", params = "accept", method = RequestMethod.POST)
	public String completeTaskAccept(@RequestParam("taskId") long taskId,
			HttpSession httpSession) {

		String email = (String) httpSession.getAttribute("loggedEmail");

		if (email != null) {
			List<String> groups = authorizationService.getUserGroups(email);

			humanTaskService.finishTask(taskId, email, groups, null);
		}

		return "redirect:/humanTasks";
	}

	@RequestMapping(value = "/basketWeeklyProcess/callBasketOff/{taskId}/{processInstanceId}", method = RequestMethod.GET)
	protected ModelAndView callBasketOff(@PathVariable String taskId,
			@PathVariable long processInstanceId) {

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("taskId", taskId);

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String today = dateFormat.format(new Date());
		data.put("date", today);

		String viewPath = "jsp/human_tasks/forms/basket/callBasketOffForm.jsp";

		return new ModelAndView(viewPath, data);
	}

	@RequestMapping(value = "/basketWeekly/confirmCallBasketOff/handle_complete_task", params = "accept", method = RequestMethod.POST)
	public String callBasketOffkAccept(@RequestParam("taskId") long taskId,
			@RequestParam("callOffReason") String callOfReason,
			HttpSession httpSession) {

		String email = (String) httpSession.getAttribute("loggedEmail");

		if (email != null) {
			List<String> groups = authorizationService.getUserGroups(email);

			Map<String, Object> results = new HashMap<String, Object>();

			results.put("callOffReason", callOfReason);

			humanTaskService.finishTask(taskId, email, groups, results);
		}

		return "redirect:/humanTasks";
	}
}
