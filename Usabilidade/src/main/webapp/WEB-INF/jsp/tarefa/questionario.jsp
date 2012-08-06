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
					key="testes.meus" /> </a> <span class="divider">/</span>
		</li>
		<li>
			<a
			href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2">
				<fmt:message key="testes.editar" />
				[<fmt:message key="testes.passo2" />] 
			</a> 
			<span class="divider">/</span>
		</li>

		<li class="active">
			<fmt:message key="testes.perguntas.perguntas" />
			[<fmt:message key="testes.perguntas.tarefa" />${tarefa.id}]
		</li>
	</ul>

	<div class="btn-toolbar">
		<div class="pull-right">
			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/tarefa/${tarefa.id}/questionario/criar/pergunta">
				<fmt:message key="pergunta.inserir" /> </a>
		</div>
	</div>

	<div class="form-horizontal form-layout">
		<c:if test="${not empty usuario.id}">
			<input type="hidden" name="usuario.id" value="${usuario.id}" />
			<input type="hidden" name="_method" value="put" />
		</c:if>

		<fieldset>
			<legend>
				<span>
					<fmt:message key="testes.perguntas.perguntas" />
				</span>
				<p>
					${tarefa.nome}
				</p>
				<hr />
			</legend>
			<table class="table table-striped table-bordered table-condensed"
				style="background-color: white">
				<thead>
					<tr>
						<th><fmt:message key="pergunta.titulo" />
						</th>
						<th style="width: 85px"><fmt:message key="table.acoes" />
						</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty perguntas}">
						<c:forEach items="${perguntas}" var="pergunta">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${pergunta.id}/pergunta">${pergunta.titulo}</a>
								</td>
								
								<td class="centertd"><a class="btn"
									href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${pergunta.id}/pergunta">
										<span class="icon-pencil"></span> </a> 
									<a title="<fmt:message key="table.remover"/>"
									class="btn btn-primary btn-modal" 
									data-href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/apagar/tarefa/${tarefa.id}/pergunta/${pergunta.id}" 
									data-acao="Remover" 
									data-toggle="modal" 
									href="#modalMessages"
									> <span
										class="icon-trash icon-white"></span> </a>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</fieldset>
	</div>
</div>