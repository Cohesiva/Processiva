package com.cohesiva.processes.jbpm.humanTask;

import java.util.List;
import java.util.Map;

import org.jbpm.task.query.TaskSummary;

public interface IHumanTaskService {

	public List<TaskSummary> getUsersTasks(String userId, List<String> groups);
	
	public void claimTask(long taskId, List<String> groups, String userId);
	
	public void startTask(long taskId, String userId);
	
	public boolean finishTask(long taskId, String userId, List<String> groups, Map<String, Object> data);
	
	public String getTaskName(long taskId);
}
