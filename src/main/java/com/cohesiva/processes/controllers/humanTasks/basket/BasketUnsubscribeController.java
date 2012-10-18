package com.cohesiva.processes.controllers.humanTasks.basket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.drools.runtime.process.ProcessInstance;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cohesiva.processes.controllers.humanTasks.HumanTaskBaseController;

@Controller
public class BasketUnsubscribeController extends HumanTaskBaseController {

	@RequestMapping(value = "/basketUnsubProcess/confirm/{taskId}/{processInstanceId}", method = RequestMethod.GET)
	protected ModelAndView completeTask(@PathVariable String taskId,
			@PathVariable long processInstanceId) {
		ProcessInstance procInstance = this
				.getProcessInstance(processInstanceId);

		String userEmail = null;

		if (procInstance != null) {
			userEmail = (String) ((WorkflowProcessInstance) procInstance)
					.getVariable("userEmail");
		}

		Map<String, String> data = new HashMap<String, String>();

		data.put("userEmail", userEmail);
		data.put("taskId", taskId);

		String processId = procInstance.getProcessId();

		String viewPath = this.getViewPath(processId, taskId);

		return new ModelAndView(viewPath, data);
	}

	@RequestMapping(value = "/unsubscribeBasket/confirm/handle_complete_task", params = "accept", method = RequestMethod.POST)
	public String completeTaskAccept(@RequestParam("taskId") long taskId,
			HttpSession httpSession) {

		String email = (String) httpSession.getAttribute("loggedEmail");

		if (email != null) {
			List<String> groups = authorizationService.getUserGroups(email);

			Map<String, Object> results = new HashMap<String, Object>();

			results.put("removeSub", true);

			humanTaskService.finishTask(taskId, email, groups, results);
		}

		return "redirect:/humanTasks";
	}

	@RequestMapping(value = "/unsubscribeBasket/confirm/handle_complete_task", params = "decline", method = RequestMethod.POST)
	public String completeTaskDecline(@RequestParam("taskId") long taskId,
			HttpSession httpSession) {

		String email = (String) httpSession.getAttribute("loggedEmail");

		if (email != null) {
			List<String> groups = authorizationService.getUserGroups(email);

			Map<String, Object> results = new HashMap<String, Object>();

			results.put("removeSub", false);

			humanTaskService.finishTask(taskId, email, groups, results);
		}

		return "redirect:/humanTasks";
	}
}
