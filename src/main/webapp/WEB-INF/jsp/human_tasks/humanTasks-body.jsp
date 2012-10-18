<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container-fluid">
	<div class="row-fluid">

		<div class="span4">
			<div class="container">
				<div class="content">

					<div class="sectionProcesses">
						<p>
							<strong>Przydzielone Tobie zadania:</strong>
						</p>

						<div id="humanTasksList">
							<jsp:include page="/WEB-INF/jsp/human_tasks/humanTasksList.jsp" />
						</div>
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


