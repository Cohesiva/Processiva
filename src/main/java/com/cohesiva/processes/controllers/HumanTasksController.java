package com.cohesiva.processes.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cohesiva.processes.jbpm.service.humanTask.HumanTaskFormMapper;
import com.cohesiva.processes.jbpm.service.humanTask.IHumanTaskService;
import com.cohesiva.processes.jbpm.serviceImpl.auth.AuthorizationService;

@Controller
public class HumanTasksController {

	@Autowired
	private IHumanTaskService humanTaskService;

	@Autowired
	private HumanTaskFormMapper humanTaskFormMapper;

	@Autowired
	private AuthorizationService authorizationService;

	@RequestMapping(value = "/humanTasks", method = RequestMethod.GET)
	public ModelAndView showHumanTasks(HttpSession httpSession) {

		String email = (String) httpSession.getAttribute("loggedEmail");

		List<TaskSummary> tasksList = null;

		if (email != null) {
			List<String> groups = authorizationService.getUserGroups(email);

			tasksList = humanTaskService.getUsersTasks(email, groups);
		}

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("tasksList", tasksList);
		modelMap.put("humanTaskFormMapper", humanTaskFormMapper);

		return new ModelAndView("jsp/human_tasks/humanTasks.jsp", modelMap);
	}

	@RequestMapping(value = "/start_task/{taskId}", method = RequestMethod.GET)
	public ModelAndView startTask(@PathVariable long taskId,
			HttpSession httpSession) {

		String email = (String) httpSession.getAttribute("loggedEmail");

		if (email != null) {
			humanTaskService.startTask(taskId, email);
		}

		return showHumanTasks(httpSession);
	}

	@RequestMapping(value = "/claim_task/{taskId}", method = RequestMethod.GET)
	public ModelAndView claimTask(@PathVariable long taskId,
			HttpSession httpSession) {

		String email = (String) httpSession.getAttribute("loggedEmail");

		if (email != null) {
			List<String> groups = authorizationService.getUserGroups(email);

			humanTaskService.claimTask(taskId, groups, email);
		}

		return showHumanTasks(httpSession);
	}

	@RequestMapping(value = "/do_task/{taskId}/{processId}/{taskName}/{processInstanceId}", method = RequestMethod.GET)
	public String doTask(@PathVariable long taskId,
			@PathVariable String processId, @PathVariable String taskName,
			@PathVariable String processInstanceId, HttpSession httpSession) {

		String page = humanTaskFormMapper.getUrlMapping(processId, taskName)
				+ "/" + taskId + "/" + processInstanceId;

		return "redirect:" + page;
	}

	@ExceptionHandler(PermissionDeniedException.class)
	public ModelAndView handlePermDeniadException() {
		Map<String, Object> data = new HashMap<String, Object>();

		data.put(
				"errorMsg",
				"Wystąpił błąd w wykonywaniu zadania. Nie masz praw do tego zadania, lub żadana akcja jest niezgodna ze statusem zadania.");

		return new ModelAndView("jsp/error/error.jsp", data);
	}
}
