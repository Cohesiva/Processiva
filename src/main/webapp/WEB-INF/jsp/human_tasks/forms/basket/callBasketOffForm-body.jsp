<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="container-fluid">
	<div class="row-fluid">

		<div class="span4">
			<div class="container">
				<div class="content">
					<form method="post"
						action="/Jbpm_processes/basketWeekly/confirmCallBasketOff/handle_complete_task">
						<legend>Odwołaj zajęcia koszykówki w dniu ${date}.</legend>

						<label>Przyczyna odwołania zajęć: <input type="text"
							name="callOffReason" placeholder="Podaj przyczynę…">
						</label> 
						
						<input type="hidden" name="taskId" value="${taskId}" />

						<button class="btn primary" type="submit" name="accept"
							value="Accept">Odwołaj</button>
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
>
