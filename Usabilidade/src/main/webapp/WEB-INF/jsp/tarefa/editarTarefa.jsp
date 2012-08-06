<%@include file="../leftmenus/default.jsp"%>

<div class="span9 container-right">
	<c:if test="${not empty errors}">
	<div class="alert alert-error">
		<c:forEach items="${errors}" var="error">
        ${error.message}<br />
		</c:forEach>
		</div>
	</c:if>

	<ul class="breadcrumb">
		<li><a href="${pageContext.request.contextPath}/usuario"> <fmt:message
					key="testes.meus" /> </a> <span class="divider">/</span></li>
		<li>
			<a
			href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2">
				<fmt:message key="testes.editar" />
				[<fmt:message key="testes.passo2" />] 
			</a> 
			<span class="divider">/</span>
		</li>

		<li class="active"><fmt:message key="tarefa.editar" />
		</li>
	</ul>

	<form class="form-horizontal form-layout" id="form_pergunta"
		method="post"
		action="${pageContext.request.contextPath}/teste/tarefa/atualizar">
		<fieldset>
			<legend>
				<span><fmt:message key="tarefa.editar" />
				</span>
				<p>
					${testeView.teste.titulo }
				</p>
				<hr />
			</legend>
			<input type="hidden" name="tarefa.id" value="${tarefa.id}" />
			<div class="control-group">
				<label class="control-label" for="input01"><fmt:message
						key="tarefa.titulo" />*</label>
				<div class="controls">
					<input type="text" value="${tarefa.nome }" name="tarefa.nome"
						class="span6" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="input01"><fmt:message
						key="tarefa.roteito" />*</label>
				<div class="controls">
					<textarea rows="10" cols="" name="tarefa.roteiro"
						class="span6"> ${tarefa.roteiro } </textarea>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="input01"><fmt:message
						key="tarefa.urlInicial" />*</label>
				<div class="controls">
					<input type="text" value="${tarefa.urlInicial }"
						name="tarefa.urlInicial" class="span6" />
				</div>
			</div>
			<input type="hidden" name=idTeste value=${testeView.teste.id }>

			<div class="form-actions">
            	<input type="submit" value="<fmt:message key="tarefa.editar" />" 
            	name="editarTarefa" title="<fmt:message key="tarefa.editar" />" 
            	class="btn btn-primary" style="float: right; margin-right: 60px"/>
        	</div>
		</fieldset>
	</form>
</div>