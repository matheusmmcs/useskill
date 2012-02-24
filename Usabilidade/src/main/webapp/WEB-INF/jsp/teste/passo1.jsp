<form action="${pageContext.request.contextPath}/teste/editar/tarefas"
	method="post">
	<input type="text" value="${tarefa.nome }" name="tarefa.nome" />
	<textarea rows="10" cols="10" name="tarefa.roteiro"
		value="${tarefa.roteiro }"></textarea>
	<input type="submit">
</form>