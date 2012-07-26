<c:if test="${not empty errors}">
	<div class="alert alert-error">
		<c:forEach items="${errors}" var="error">
                    ${error.message}<br />
		</c:forEach>
	</div>
</c:if>

	<c:if test="${pergunta.tipoRespostaAlternativa!=true}">
		<form class="form-horizontal form-layout w600"
			action="${pageContext.request.contextPath}/teste/salvar/resposta/escrita"
			method="post">
			<fieldset>
				<legend><fmt:message key="questionario" /></legend>
				<div class="control-group">
					<label class="control-label" for="input01" style="width: 150px;">
						<fmt:message key="testeparticipar.titulo" />: </label>
					<div class="controls" style="padding-left: 20px;">${pergunta.texto}</div>
				</div>


				<div class="control-group">
                	<label class="control-label" for="input01"></label>
                	<div class="controls">
                		<textarea rows="10" cols="10" name="resposta" class="input-xmlarge"></textarea>                    	
                	</div>
            	</div>
				
				<div class="control-group">
					
				</div>
				
				<div class="form-actions">
					<input type="submit" value="<fmt:message key="responde.pergunta"/>"
						name="salvar" title="<fmt:message key="responde.pergunta"/>"
						class="btn btn-primary" style="float: right; width: 120px" />
				</div>
			</fieldset>
		</form>
	</c:if>
	
	
	<c:if test="${pergunta.tipoRespostaAlternativa==true}">
	<form class="form-horizontal form-layout w600"
		action="${pageContext.request.contextPath}/teste/salvar/resposta/alternativa"
		method="post">
		
		<fieldset>
			<legend><fmt:message key="questionario" /></legend>
			<div class="control-group">
				<label class="control-label" for="input01" style="width: 150px;">
					<fmt:message key="testeparticipar.titulo" />: </label>
				<div class="controls" style="padding-left: 20px;">${pergunta.texto}</div>
			</div>

			<div class="control-group">
            	<label class="control-label"></label>
            	<div class="controls">
            	
            	<c:forEach items="${pergunta.alternativas}" var="alternativa">
					<label class="radio">
						<input type="radio" name="alternativa.id" value="${alternativa.id}" />${alternativa.textoAlternativa}
					</label>
				</c:forEach>
            	
            	</div>
          	</div>

			
			<div class="form-actions">
				<input type="submit" value="<fmt:message key="responde.pergunta"/>"
					name="salvar" title="<fmt:message key="responde.pergunta"/>"
					class="btn btn-primary" style="float: right; width: 120px" />
			</div>
		</fieldset>
		
	</form>
	</c:if>
