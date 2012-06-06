<div class="span6 offset3">
	<form class="form-horizontal form-layout" id="teste_passo1"
		action="${pageContext.request.contextPath}/teste/responder/pergunta"
		method="get">
		<fieldset>
			<legend>Dados do Teste</legend>
			<div class="control-group">
				<label class="control-label" for="input01" style="width: 150px;"> <fmt:message
						key="testeparticipar.titulo" />: </label>
				<div class="controls" style="padding-left: 20px;">${testeSession.teste.tituloPublico}</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="input01" style="width: 150px;"> <fmt:message
						key="testeparticipar.introducao" />: </label>
				<div class="controls" style="padding-left: 20px;">${testeSession.teste.textoIndroducao}</div>
			</div>
			<div class="form-actions">
				<input type="submit" value="<fmt:message key="testeparticipar.iniciarteste" />"
					name="salvar" title="Próximo passo" class="btn btn-primary"
					style="float: right; width: 120px" />
			</div>
		</fieldset>
	</form>
</div>