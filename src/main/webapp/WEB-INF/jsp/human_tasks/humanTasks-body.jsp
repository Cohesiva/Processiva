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


