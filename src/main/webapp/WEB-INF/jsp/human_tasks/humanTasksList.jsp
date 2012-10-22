<%--
  #%L
  Processiva Business Processes Platform
  %%
  Copyright (C) 2012 Cohesiva
  %%
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU Affero General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  
  You should have received a copy of the GNU Affero General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
  #L%
  --%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="Functions"%>

<%
	String email = null;
	if (session.getAttribute("loggedEmail") != null) {
		email = session.getAttribute("loggedEmail").toString();

		pageContext.setAttribute("email", email);
	}
%>

<jsp:useBean id="humanTaskFormMapper"
	type="com.cohesiva.processes.jbpm.service.humanTask.HumanTaskFormMapper"
	scope="request" />
<c:choose>
	<c:when test="${not empty tasksList}">
		<table class="tabProcesses">
			<tbody>
				<c:forEach var="task" items="${tasksList}">
					<tr>
						<td>${task.description}</td>
						<td>${task.status}</td>
						<c:if test="${task.status eq 'Ready'}">
							<c:choose>
								<c:when test="${f:isTaskImmediete(task.processId, task.name)}">
									<td>
										<button class="btn btn-info" id="doTask"
											onclick="window.location.href='/Jbpm_processes/do_task/${task.id}/${task.processId}/${task.name}/${task.processInstanceId}'">
											Wykonaj</button>
									</td>
								</c:when>
								<c:otherwise>
									<td>
										<button class="btn btn-info" id="claimTask"
											onclick="window.location.href='/Jbpm_processes/claim_task/${task.id}'">
											Przydziel do siebie</button>
									</td>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${task.status eq 'Reserved'}">
							<c:choose>
								<c:when test="${f:isTaskImmediete(task.processId, task.name)}">
									<td>
										<button class="btn btn-info" id="doTaskReserved"
											onclick="window.location.href='/Jbpm_processes/do_task/${task.id}/${task.processId}/${task.name}/${task.processInstanceId}'">
											<i class="icon-white icon-play"></i>Wykonaj
										</button>
									</td>
								</c:when>
								<c:otherwise>
									<td>
										<button class="btn btn-info" id="claimTask"
											onclick="window.location.href='/Jbpm_processes/start_task/${task.id}'">
											<i class="icon-white icon-play"></i>Rozpocznij
										</button>
									</td>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${task.status eq 'InProgress'}">
							<td>
								<button class="btn btn-info" id="claimTask"
									onclick="window.location.href='/Jbpm_processes/${f:getUrlMapping(task.processId, task.name)}/${task.id}/${task.processInstanceId}'">
									Zakończ</button>
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<div class="sectionProcessEmpty">
			<c:choose>
				<c:when test="${not empty email}">
					<p>Brak</p>
				</c:when>
				<c:otherwise>
					<p>Nie jesteś zalogowany</p>
				</c:otherwise>
			</c:choose>
		</div>
	</c:otherwise>
</c:choose>

