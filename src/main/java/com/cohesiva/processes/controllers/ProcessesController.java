package com.cohesiva.processes.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cohesiva.processes.jbpm.service.base.IJbpmBase;
import com.cohesiva.processes.jbpm.service.processes.IProcessService;

@Controller
public class ProcessesController {

	@Autowired
	private IJbpmBase jbpmBase;

	@Autowired
	private IProcessService processService;

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

		StatefulKnowledgeSession session = jbpmBase.getSession();

		Map<String, Object> params = processService.getStartProcessData(processId);
		params.put("userEmail", email);

		session.startProcess(processId, params);
		
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("processes", processService.getAuthorizedProcesses(email));
		data.put("userInstances",
				processService.getRunningInstancesNames(email));

		String startInfo = processService.getStartInfo(processId);
		data.put("info", startInfo);

		return new ModelAndView("jsp/processes/processes-body.jsp", data);
	}
}
