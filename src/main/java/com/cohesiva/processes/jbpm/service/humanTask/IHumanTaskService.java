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
