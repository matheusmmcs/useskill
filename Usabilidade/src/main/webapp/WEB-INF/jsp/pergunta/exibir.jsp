<div class="span7 center pergunta" style="margin-top: 70px">
	<c:if test="${not empty errors}">
	<div class="alert alert-error">
		<c:forEach items="${errors}" var="error">
        ${error.message}<br />
		</c:forEach>
		</div>
	</c:if>
	

	<c:if test="${pergunta.tipoRespostaAlternativa!=true}">
		<form class="form-horizontal form-layout"
			action="${pageContext.request.contextPath}/teste/salvar/resposta/escrita"
			method="post">
			<fieldset>
				<legend>
					<span>
						<fmt:message key="questionario" />
					</span>
					<hr />
				</legend>
				
				<div class="control-group notop">
					<div><b><fmt:message key="testeparticipar.titulo" />:</b> ${pergunta.texto}</div>
				</div>

				<div class="control-group">
                	<div class="span6 center">
                		<textarea rows="6" cols="10" name="resposta" class="span6 center" style="resize:none"></textarea>                    	
                	</div>
            	</div>
				
				<div class="control-group">
					
				</div>
				
				<div class="form-actions">
					<input type="submit" value="<fmt:message key="responde.pergunta"/>" name="salvar" title="<fmt:message key="responde.pergunta"/>" class="btn btn-primary" style="float: right; width: 120px" />
				</div>
			</fieldset>
		</form>
	</c:if>

	<c:if test="${pergunta.tipoRespostaAlternativa==true}">
	<form class="form-horizontal form-layout" action="${pageContext.request.contextPath}/teste/salvar/resposta/alternativa" method="post">
		<fieldset>
			<legend>
					<span>
						<fmt:message key="questionario" />
					</span>
					<hr />
			</legend>

			<div class="control-group notop">
				<div><b><fmt:message key="testeparticipar.titulo" />:</b> ${pergunta.texto}</div>
			</div>

			<div class="control-group">
            	<div>
            	<c:forEach items="${pergunta.alternativas}" var="alternativa">
					<label class="radio">
						<input type="radio" name="alternativa.id" value="${alternativa.id}" />${alternativa.textoAlternativa}
					</label>
				</c:forEach>
            	</div>
          	</div>
			
			<div class="form-actions">
				<input type="submit" value="<fmt:message key="responde.pergunta"/>" name="salvar" title="<fmt:message key="responde.pergunta"/>" class="btn btn-primary" style="float: right; width: 120px" />
			</div>
		</fieldset>
		
	</form>
	</c:if>

</div>