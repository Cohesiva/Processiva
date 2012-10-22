package com.cohesiva.processes.controllers.humanTasks.hr;

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
import com.cohesiva.processes.jbpm.service.processes.hr.IHRServices;

@Controller
public class ConfigureCVController extends HumanTaskBaseController {
	
	@Autowired
	IHRServices hrServices;

	@RequestMapping(value = "/generateCVProcess/configureCV/{taskId}/{processInstanceId}", method = RequestMethod.GET)
	protected ModelAndView completeTask(@PathVariable String taskId,
			@PathVariable long processInstanceId) {

		String viewPath = "jsp/human_tasks/forms/hr/configureCVForm.jsp";
		
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("taskId", taskId);
		data.put("employees", hrServices.getEmployeesList());
		data.put("docFormats", hrServices.getCVFormats());

		return new ModelAndView(viewPath, data);
	}

	@RequestMapping(value = "/generateCV/handle_complete_task", method = RequestMethod.POST)
	public String completeTaskAccept(@RequestParam("taskId") long taskId,
			@RequestParam("format") String format,
			@RequestParam("heroEmail") String heroEmail, HttpSession httpSession) {

		String email = (String) httpSession.getAttribute("loggedEmail");

		if (email != null) {
			List<String> groups = authorizationService.getUserGroups(email);

			Map<String, Object> results = new HashMap<String, Object>();

			results.put("documentType", format);
			results.put("heroEmail", heroEmail);

			humanTaskService.finishTask(taskId, email, groups, results);
		}

		return "redirect:/humanTasks";
	}
}
