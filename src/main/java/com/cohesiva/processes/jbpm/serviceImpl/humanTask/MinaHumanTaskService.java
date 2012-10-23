
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

package com.cohesiva.processes.jbpm.serviceImpl.humanTask;

import java.util.List;
import java.util.Map;

import org.drools.SystemEventListenerFactory;
import org.jbpm.process.workitem.wsht.MinaHTWorkItemHandler;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.mina.MinaTaskClientConnector;
import org.jbpm.task.service.mina.MinaTaskClientHandler;
import org.jbpm.task.service.responsehandlers.BlockingGetTaskResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskSummaryResponseHandler;
import org.jbpm.task.utils.ContentMarshallerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.jbpm.service.humanTask.IHumanTaskService;
import com.cohesiva.processes.jbpm.serviceImpl.base.JbpmBase;

@Service
public class MinaHumanTaskService implements IHumanTaskService {

	private final static String HOST = "127.0.0.1";
	private final static int PORT = 9123;

	@Autowired
	private JbpmBase jbpmBase;

	private TaskClient connect() {
		TaskClient client = new TaskClient(new MinaTaskClientConnector(
				"mina client", new MinaTaskClientHandler(
						SystemEventListenerFactory.getSystemEventListener())));

		client.connect(HOST, PORT);

		return client;
	}

	private void disconnect(TaskClient client) {
		try {
			client.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ContentData getContentData(Map<String, Object> map) {
		MinaHTWorkItemHandler workItemHandler = jbpmBase.getHumanTaskHandler();

		ContentData contentData = null;

		if (map != null) {
			contentData = ContentMarshallerHelper.marshal(map,
					workItemHandler.getMarshallerContext(), null);
		}

		return contentData;
	}

	public List<TaskSummary> getUsersTasks(String userId, List<String> groups) {
		TaskClient client = connect();

		BlockingTaskSummaryResponseHandler taskSummaryResponseHandler = new BlockingTaskSummaryResponseHandler();

		if (groups != null) {
			client.getTasksAssignedAsPotentialOwner(userId, groups, "en-UK",
					taskSummaryResponseHandler);

		} else {
			client.getTasksAssignedAsPotentialOwner(userId, "en-UK",
					taskSummaryResponseHandler);
		}

		taskSummaryResponseHandler.waitTillDone(200);

		List<TaskSummary> tasks = taskSummaryResponseHandler.getResults();

		disconnect(client);

		return tasks;
	}

	public void claimTask(long taskId, List<String> groups, String userId) {
		TaskClient client = connect();

		BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();

		if (groups != null) {
			client.claim(taskId, userId, groups, responseHandler);
		} else {
			client.claim(taskId, userId, responseHandler);
		}

		responseHandler.waitTillDone(200);

		disconnect(client);
	}

	public void startTask(long taskId, String userId) {
		TaskClient client = connect();

		BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();

		client.start(taskId, userId, responseHandler);

		responseHandler.waitTillDone(200);

		disconnect(client);
	}

	public boolean finishTask(long taskId, String userId, List<String> groups,
			Map<String, Object> data) {
		boolean finished = false;

		TaskClient client = connect();

		BlockingTaskSummaryResponseHandler taskSummaryResponseHandler = new BlockingTaskSummaryResponseHandler();

		if (groups != null) {
			client.getTasksAssignedAsPotentialOwner(userId, groups, "en-UK",
					taskSummaryResponseHandler);
		} else {
			client.getTasksAssignedAsPotentialOwner(userId, "en-UK",
					taskSummaryResponseHandler);
		}

		List<TaskSummary> tasks = taskSummaryResponseHandler.getResults();

		if (tasks.size() > 0) {

			for (TaskSummary task : tasks) {
				if (task.getId() == taskId) {

					String status = task.getStatus().toString();

					if (status.equals("Ready")) {
						this.claimTask(taskId, groups, userId);
						this.startTask(taskId, userId);
					} else if (status.equals("Reserved")) {
						this.startTask(taskId, userId);
					}

					BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();

					client.complete(taskId, userId, this.getContentData(data),
							responseHandler);

					responseHandler.waitTillDone(1000);

					boolean isDone = responseHandler.isDone();

					if (isDone) {
						finished = true;
					} else {
						finished = false;
					}

					break;
				}
			}
		}

		disconnect(client);

		return finished;
	}

	public String getTaskName(long taskId) {

		Task task = this.getTask(taskId);

		if (task.getNames() != null) {
			return task.getNames().get(0).getText();
		}

		return null;
	}

	private Task getTask(long taskId) {
		TaskClient client = connect();

		BlockingGetTaskResponseHandler responseHandler = new BlockingGetTaskResponseHandler();

		client.getTask(taskId, responseHandler);

		Task task = responseHandler.getTask();

		responseHandler.waitTillDone(200);

		disconnect(client);

		return task;
	}
}
