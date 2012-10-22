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

<div class="container-fluid">
	<div class="row-fluid">

		<div class="span4">
			<div class="container">
				<div class="content">
					<form method="post" action="/Jbpm_processes/unsubscribeBasket/confirm/handle_complete_task">
						<legend>Użytkownik: ${userEmail} chce zakonczyć subskrypcję grupy Cohesiva Basket.</legend>
						<input type="hidden" name="taskId" value="${taskId}">
						<button class="btn" type="submit" name="accept" value="Accept">Zezwól</button>
						<button class="btn" type="submit" name="decline" value="Decline">Odrzuć</button>
					</form>
				</div>
			</div>
		</div>

		<div class="span8">
			<div class="container">
				<div class="content"></div>
			</div>
		</div>

	</div>

	<hr />
</div>