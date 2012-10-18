<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String email = null;
	if (session.getAttribute("loggedEmail") != null) {
		email = session.getAttribute("loggedEmail").toString();

		pageContext.setAttribute("email", email);
	}
%>

<div class="container-fluid">
	<div class="row-fluid">

		<div class="span4">
			<div class="container">
				<div class="content">
					<c:if test="${not empty info}">
						<div class="sectionProcesses">
							<p>${info}</p>
						</div>

						<hr class="lineProcesses"/>
					</c:if>

					<div class="sectionProcesses">
						<p>
							<strong>Dostępne procesy:</strong>
						</p>

						<c:choose>
							<c:when test="${not empty processes}">
								<table class="tabProcesses">
									<tbody>
										<c:forEach var="process" items="${processes}">
											<tr>
												<td>${process.name}</td>
												<!-- <td><button id="startProcess"
														onclick="startProcess('${process.id}');"
														title="Start_process">Start process</button></td> -->
												<td>
													<button class="btn btn-info" id="startProcess"
														onclick="this.disabled=true; startProcess('${process.id}');"
														title="Start_process">
														<i class="icon-white icon-play"></i>Start
													</button>
												</td>

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
					</div>

					<hr class="lineProcesses"/>

					<div class="sectionProcesses">
						<p>
							<strong>Uruchomione procesy:</strong>
						</p>

						<c:choose>
							<c:when test="${not empty userInstances}">
								<table class="tabProcesses">
									<tbody>
										<c:forEach var="userInstance" items="${userInstances}">
											<tr>
												<td>- ${userInstance}</td>

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
					</div>
				</div>
			</div>
			<!-- /container -->
		</div>
		<!--/span -->

		<div class="span8">
			<div class="container">
				<div class="content"></div>
			</div>
			<!-- /container -->
		</div>
		<!--/span -->
	</div>
	<!--/row -->
	<hr />
</div>


