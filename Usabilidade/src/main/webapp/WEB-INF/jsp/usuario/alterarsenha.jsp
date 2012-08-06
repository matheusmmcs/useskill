<div class="span7 offset3 center">
	<c:if test="${not empty errors}">
    	<div class="alert alert-error">
        	<c:forEach items="${errors}" var="error">
            	${error.message}<br />
        	</c:forEach>
    	</div>
	</c:if>
	<c:if test="${not empty sucesso}">
    	<div class="alert alert-success">
        	<fmt:message key="${sucesso}" />
    	</div>
	</c:if>

	<form method="post" action="${pageContext.request.contextPath}/usuario/alterarSenha"
		id="recuperar_senha" class="form-horizontal form-layout">
		<c:if test="${not empty usuario.id}">
			<input type="hidden" name="usuario.id" value="${usuario.id}" />
			<input type="hidden" name="_method" value="put" />
		</c:if>
		<fieldset>
			<legend>
				<span><fmt:message key="login.alterarsenha" />
				</span>
				<hr />
			</legend>
			
			<div class="control-group">
				<label class="control-label" for="input01"><fmt:message
						key="login.senhaantiga" />*</label>
				<div class="controls">
					<input type="password" name="senhaAntiga" id="senhaAntiga" value="${senhaAntiga}"
						class="span4" />
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="input01"><fmt:message
						key="login.novasenha" />*</label>
				<div class="controls">
					<input type="password" name="senha" id="senha" value="${senha}"
						class="span4" />
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="input01"><fmt:message
						key="login.confirmanovasenha" />*</label>
				<div class="controls">
					<input type="password" name="confirmacaoSenha" id="confirmacaoSenha" value="${confirmacaoSenha}"
						class="span4" />
				</div>
			</div>

			<div class="form-actions">
				<input type="submit" value="<fmt:message key="login.alterarsenha"/>"
					name="enviar" title="<fmt:message key="login.alterarsenha"/>"
					class="btn btn-primary" style="float: right; margin-right: 60px" />
			</div>
		</fieldset>
	</form>
</div>