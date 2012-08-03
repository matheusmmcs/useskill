<ul class="breadcrumb">
	<li><a href="${pageContext.request.contextPath}/usuario"><fmt:message
				key="inicio" /></a> <span class="divider">/</span></li>
	<li><a
		href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo2">Editar
			Teste - Passo 2</a> <span class="divider">/</span></li>
	<li><a
		href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo2/${tarefa.id}/tarefa">
		<fmt:message key="editar" /> - ${tarefa.nome }</a> <span class="divider">/</span></li>
	<li class="active">${tarefa.nome }</li>
</ul>

<c:if test="${not empty errors}">
	<div class="alert alert-error">
		<c:forEach items="${errors}" var="error">
            ${error.message}<br />
		</c:forEach>
	</div>
</c:if>

<div class="span10 offset1">
	<ul class="nav nav-tabs" style="margin: 0 auto; width: 97%">
		<li><a
			href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo1">Passo1</a></li>
		<li class="active"><a
			href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo2">Passo2</a></li>
		<li><a
			href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo3">Passo3</a></li>
		<li><a
			href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo4">Passo4</a></li>
	</ul>
	<div class="form-horizontal form-layout">
		<c:if test="${not empty usuario.id}">
			<input type="hidden" name="usuario.id" value="${usuario.id}" />
			<input type="hidden" name="_method" value="put" />
		</c:if>
		<div class="row show-grid">
			<div class="span4" style="margin: 10px 0px 0px 520px">
				 <a	class="btn btn-primary"
					href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo2/editar/${tarefa.id}/tarefa/questionario/criar/pergunta"
					style="margin-left: 5px">Inserir Pergunta</a>
			</div>
		</div>
		<fieldset style="width: 750px; margin: -30px auto 0 auto">
			<legend>${tarefa.nome}</legend>

			<div class="form-horizontal" id="loginForm">
				<div class="span9" style="margin-top: 20px">
					<h1>Perguntas</h1>
				</div>
				<div class="span9">
					<table class="table table-striped table-bordered table-condensed"
						style="background-color: white">
						<thead>
							<tr>
								<th><fmt:message key="pergunta.titulo" /></th>
								<th><fmt:message key="editar" /></th>
								<th><fmt:message key="remover" /></th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${not empty perguntas}">
								<c:forEach items="${perguntas}" var="pergunta">
									<tr>
										<td><a
											href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${pergunta.id}/pergunta">${pergunta.titulo}</a>
										</td>
										<td width="100"><a class="btn"
											href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${pergunta.id}/pergunta"><span
												class="icon-pencil"></span> Editar</a></td>
										
										<td width="100">
											<form
												action="${pageContext.request.contextPath}/teste/apagar/tarefa/pergunta"
												method="post">
												<input class="btn" type="submit" value="Deletar"> <input
													type="hidden" name="testeId" value="${testeView.teste.id }">
												<input type="hidden" name="perguntaId"
													value="${pergunta.id}" />
												<input type="hidden" name="tarefaId"
													value="${tarefa.id}" />
											</form>
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
		</fieldset>
		<div class="form-actions">
			<form id="teste_passo1"
				action="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo2"
				method="get">
				<input type="submit" value="<fmt:message key="salvar.continuar" />"
					title="Próximo passo" class="btn btn-primary"
					style="float: right; width: 120px" />
			</form>
		</div>
	</div>
</div>