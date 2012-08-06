<div class="span7 offset3 center">
	<c:if test="${not empty errors}">
    	<div class="alert alert-error">
        	<c:forEach items="${errors}" var="error">
            	${error.message}<br />
        	</c:forEach>
    	</div>
	</c:if>

	<form id="aceitar" action="${pageContext.request.contextPath}/teste/responder/pergunta" method="get"
		class="form-horizontal form-layout">
		<fieldset>
			<legend>
				<span><fmt:message key="teste.dados" />
				</span>
				<hr />
			</legend>
			
			<div class="control-group-min">
                <label class="control-label" for="input01"><fmt:message key="testeparticipar.titulo"/>*</label>
                <div class="controls">
                    <textarea readonly="readonly" name="textoIndroducao" id="teste_textoIndroducao" rows="5" class="span5">${testeSession.teste.tituloPublico}</textarea>
                </div>
            </div>
			
			<div class="control-group-min">
                <label class="control-label" for="input01"><fmt:message key="testeparticipar.introducao"/>*</label>
                <div class="controls">
                    <textarea readonly="readonly" name="textoIndroducao" id="teste_textoIndroducao" rows="5" class="span5">${testeSession.teste.textoIndroducao}</textarea>
                </div>
            </div>

			<div class="form-actions">
				<input type="submit" value="<fmt:message key="testeparticipar.iniciarteste" />" 
				name="salvar" title="<fmt:message key="testeparticipar.iniciarteste" />" 
				class="btn btn-primary" style="float: right; margin-right: 40px"/>
			</div>
			
		</fieldset>
	</form>
</div>