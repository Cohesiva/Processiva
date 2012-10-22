package com.cohesiva.processes.jbpm.service.humanTask;

import java.util.List;
import java.util.Map;

import org.jbpm.task.query.TaskSummary;

/*
 * Service for communicating with human task server
 */
public interface IHumanTaskService {

	public List<TaskSummary> getUsersTasks(String userId, List<String> groups);
	
	public void claimTask(long taskId, List<String> groups, String userId);
	
	public void startTask(long taskId, String userId);
	
	public boolean finishTask(long taskId, String userId, List<String> groups, Map<String, Object> data);
	
	public String getTaskName(long taskId);
}
