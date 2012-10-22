<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="container-fluid">
	<div class="row-fluid">

		<div class="span4">
			<div class="container">
				<div class="content">
					<form method="post" action="/Jbpm_processes/basketWeekly/confirm/handle_complete_task">
						<legend>Czy będziesz dzisiaj ${date} na zajęciach koszykówki?</legend>
						<input type="hidden" name="taskId" value="${taskId}"/>
						<input type="hidden" name="processInstanceId" value="${processInstanceId}"/>
						<button class="btn" type="submit" name="accept" value="Accept">Tak</button>
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