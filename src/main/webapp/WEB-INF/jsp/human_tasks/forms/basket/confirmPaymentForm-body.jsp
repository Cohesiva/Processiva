<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="container-fluid">
	<div class="row-fluid">

		<div class="span4">
			<div class="container">
				<div class="content">
					<form method="post" action="/Jbpm_processes/paymentBasket/confirm/handle_complete_task">
						<legend>Użytkownik: ${userEmail} kupuje karnet Cohesiva Basket w cenie 50 PLN.</legend>
						<span class="help-block">Ważność karnetu to 2 miesiące. Jeśli użytkownik ma niewykorzystane środki z poprzedniego karnetu, 
								ich ważność zostaje przedłużona o 2 miesiące.</span>
						<input type="hidden" name="taskId" value="${taskId}"/>
						<button class="btn" type="submit" name="accept" value="Accept">Zatwierdź</button>
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