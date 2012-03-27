<h1>Tarefas</h1>
<table>
	<thead>
		<tr>
			<td><fmt:message key="tarefa.nome" /></td>
			<td><fmt:message key="tarefa.fluxoIdeal.criado" /></td>
			<td><fmt:message key="editar" /></td>
			<td><fmt:message key="remover" /></td>

		</tr>
	</thead>
	<c:if test="${not empty tarefas}">
		<c:forEach items="${tarefas}" var="tarefa">
			<tr>
				<td><a
					href="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id}/editar/passo2/editar/${tarefa.id}/tarefa">
						${tarefa.nome}</a></td>

				<td><c:if test="${tarefa.fluxoIdealPreenchido == false}">
						<form action="${pageContext.request.contextPath}/tarefa/visualizar" method="post">
							<input type="hidden" value="${tarefa.id }" name="idTarefa">
							<input type="submit" value="<fmt:message key="gravar" />">
						</form>
					</c:if> <c:if test="${tarefa.fluxoIdealPreenchido == true}">
			Editar Fluxo Ideal</c:if>
				<td><a
					href="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id}/editar/passo2/editar/${tarefa.id}/tarefa">
						<fmt:message key="editar" />
				</a></td>
				<td>
					<form
						action="${pageContext.request.contextPath}/teste/removed/tarefa"
						method="post">
						<input type="hidden" value="${tarefa.id }" name="idTarefa">
						<input type="submit" value="<fmt:message key="remover"/>"
							class="ancora">
					</form>
				</td>

			</tr>
		</c:forEach>
	</c:if>
</table>
<h1>Perguntas</h1>
<c:if test="${not empty perguntas}">
	<c:forEach items="${perguntas}" var="pergunta">
		<div id="pergunta_exibe">
			<a
				href="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id}/editar/passo2/editar/${pergunta.id}/pergunta">${pergunta.titulo}</a>
			<a
				href="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id}/editar/passo2/editar/${pergunta.id}/pergunta">Editar</a>
			<form
				action="${pageContext.request.contextPath}/teste/duplicar/pergunta"
				method="post">
				<input type="submit" value="Duplicar"> <input type="hidden"
					name="testeId" value="${usuarioLogado.teste.id }"> <input
					type="hidden" name="perguntaId" value="${pergunta.id}" />
			</form>
			<form
				action="${pageContext.request.contextPath}/teste/apagar/pergunta"
				method="post">
				<input type="submit" value="Deletar"> <input type="hidden"
					name="testeId" value="${usuarioLogado.teste.id }"> <input
					type="hidden" name="perguntaId" value="${pergunta.id}" />
			</form>
		</div>
		<br>
	</c:forEach>
</c:if>
<a
	href="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id}/editar/passo2/criar/tarefa">Inserir
	Tarefa</a>
<a
	href="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id}/editar/passo2/criar/pergunta">Inserir
	Pergunta</a>
