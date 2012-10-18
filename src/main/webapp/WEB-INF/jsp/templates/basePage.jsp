<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String email = null;
	String loginInfo = null;

	if (session.getAttribute("loggedEmail") != null) {
		email = session.getAttribute("loggedEmail").toString();

		loginInfo = email;
	} else {
		loginInfo = "Logowanie";
	}
%>

<!DOCTYPE html>

<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Processiva - Cohesiva Process Engine</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="" />

<link href="/Jbpm_processes/resources/css/bootstrap.css"
	rel="stylesheet" />
<link href="/Jbpm_processes/resources/css/admin.css" rel="stylesheet" />
<link href="/Jbpm_processes/resources/css/processes.css"
	rel="stylesheet" />
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}
/* Override some defaults */
/*
html,body {
	background-color: #eee;
}
*/
body {
	padding-top: 40px;
}

.container {
	width: 300px;
}

/* The white background content wrapper */
.container>.content { /*background-color: #fff;*/
	padding-top: 20px;
	padding-left: 27px;
	padding-bottom: 10px;
	margin: 0 -20px;
	/*-webkit-border-radius: 10px 10px 10px 10px;
	-moz-border-radius: 10px 10px 10px 10px;
	border-radius: 10px 10px 10px 10px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .15);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .15);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .15); */
}

.login-form {
	margin-left: 65px;
}

legend {
	margin-right: -50px;
	font-weight: bold;
	color: #404040;
}
</style>
<link href="/Jbpm_processes/resources/css/bootstrap-responsive.css"
	rel="stylesheet" />

<!-- Le fav and touch icons -->
<link rel="shortcut icon"
	href="/Jbpm_processes/resources/ico/favicon.ico" />
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="/Jbpm_processes/resources/ico/apple-touch-icon-144-precomposed.png" />
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="/Jbpm_processes/resources/ico/apple-touch-icon-114-precomposed.png" />
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="/Jbpm_processes/resources/ico/apple-touch-icon-72-precomposed.png" />
<link rel="apple-touch-icon-precomposed"
	href="/Jbpm_processes/resources/ico/apple-touch-icon-57-precomposed.png" />
</head>

<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a>

				<div class="nav-collapse">
					<ul class="nav">
						<!-- <li><a href="/Jbpm_processes/users">Users</a></li>  -->
						<li id="processesMenuItem"><a
							href="/Jbpm_processes/processes">Procesy</a></li>
						<li id="tasksMenuItem"><a href="/Jbpm_processes/humanTasks">Zadania</a></li>
					</ul>
				</div>

				<div class="btn-group pull-right">
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-user"></i> <c:out value='<%=loginInfo%>' /> <span
						class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="/Jbpm_processes/loginGoogle">Zaloguj</a></li>
						<li class="divider"></li>
						<li><a href="/Jbpm_processes/logout">Wyloguj</a></li>
					</ul>
				</div>

			</div>
		</div>
	</div>

	<div id="gb" class="gbes">
		<div id="gbw">
			<div id="gbq" class="gbes">
				<div id="gbq1" class="gbt gbes">
					<table id="gbqlt">
						<tbody>
							<tr>
								<td id="gbqlw" class="gbgt gbes"><a class="gbqla2"
									title="Cohesiva" href="http://cohesiva.com/pl/firma.html">
										<img id="gbqld" class="gbqldr" alt="Cohesiva"
										src="https://www.google.com/a/cohesiva.com/images/logo.gif?alpha=1&service=google_default">
								</a></td>
								<td id="gbqlw" class="gbgt gbes logoProcesses">Processiva -
									Cohesiva Process Engine</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div id="gbx1" class="gbes"></div>

		</div>
	</div>

	<c:if test="${not empty requestScope.errorMsg}">
		<div class="alert alert-error">
			<a class="close" data-dismiss="alert">×</a>
			<p>${requestScope.errorMsg}</p>
		</div>
	</c:if>

	<!--page specific content -->
	<div id="body">
		<jsp:include page="${requestScope.body}" />
	</div>

	<footer>
		<p>&copy; Cohesiva 2012</p>
	</footer>

	<!--/.fluid-container -->

	<!-- Le javascript ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="/Jbpm_processes/resources/js/jquery.js"></script>
	<script src="/Jbpm_processes/resources/js/bootstrap-transition.js"></script>
	<script src="/Jbpm_processes/resources/js/bootstrap-dropdown.js"></script>
	<script src="/Jbpm_processes/resources/js/bootstrap-collapse.js"></script>
	<script src="/Jbpm_processes/resources/js/bootstrap-alert.js"></script>
	<script src="/Jbpm_processes/resources/js/processes.js"></script>
	<script src="${requestScope.jsSource}"></script>
</body>
</html>