<form  id="form_pergunta" method="post"
	action="${pageContext.request.contextPath}/teste/salvar/tarefa">
	<label for="tarefa.nome"><fmt:message key="tarefa.nome" />*:</label> <input
		type="text" value="${tarefa.nome }" name="tarefa.nome" /> <label
		for="tarefa.roteiro"><fmt:message key="tarefa.roteito" />*:</label>
	<textarea rows="" cols="" name="tarefa.roteiro">
${tarefa.roteiro }
</textarea>

	<label for="tarefa.urlInicial"><fmt:message
			key="tarefa.urlInicial" />*:</label> <input type="text"
		value="${tarefa.urlInicial }" name="tarefa.urlInicial" /> <input
		type="submit" value="<fmt:message key="tarefa.criar" />">
</form>

