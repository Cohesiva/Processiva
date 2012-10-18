<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container-fluid">
	<div class="row-fluid">

		<div class="span4">
			<div class="container">
				<div class="content">
				
					
					
					<form method="post" class="form-horizontal"
						action="/Jbpm_processes/generateCV/handle_complete_task">
						<legend>Wybierz parametry CV</legend>

						 <div class="control-group">
						    <label class="control-label" for="heroEmail">Osoba</label>
						    <div class="controls">
						      	<select id="heroEmail" name="heroEmail">
						      		<c:forEach var="employeeEmail" items="${employees}">
										<option>${employeeEmail}</option>
									</c:forEach>
								</select>
						    </div>
						  </div>
			
						 <div class="control-group">
						    <label class="control-label" for="docForm">Format</label>
						    <div class="controls">
						    
						    	<c:forEach var="docFormat" items="${docFormats}">
									<label class="radio"> 
										<input type="radio" 
											name="format" id="docForm${docFormat}" value="${docFormat}">
										${docFormat}
									</label>
								</c:forEach>
								<!--  
							     <label class="radio"> 
									<input type="radio" 
										name="docForm" id="docFormPdf" value="pdf" checked>
									Pdf
								</label> 
								<label class="radio"> <input type="radio"
									name="docForm" id="docFormOdt" value="odt">
									Odt
								</label>
								-->
						    </div>
						  </div>
						  
						  <input type="hidden" name="taskId" value="${taskId}" />
						  
						 <div class="control-group">
						    <div class="controls">				      
						      <button class="btn primary" type="submit">Wygeneruj</button>
						    </div>
						  </div>					
					
					</form>
					
					<!--  
					<form method="post" class="form-inline"
						action="/Jbpm_processes/generateCV/handle_complete_task">
						<legend>Wybierz parametry CV</legend>

						<label class="radio"> 
							<input type="radio"
								name="docForm" id="docFormPdf" value="pdf" checked>
							Pdf
						</label> 
						<label class="radio"> <input type="radio"
							name="docForm" id="docFormOdt" value="odt">
							Odt
						</label>
						
						<select>
						  <option>asdsd@cohesiva.com</option>
						  <option>dgsgg@cohesiva.com</option>
						  <option>jgkuy@cohesiva.com</option>
						</select>
						
						<button class="btn primary" type="submit">Wygeneruj</button>
					</form>
					-->
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
