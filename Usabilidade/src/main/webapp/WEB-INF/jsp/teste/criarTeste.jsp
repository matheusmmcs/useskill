<form id="teste_Form"
	action="${pageContext.request.contextPath}/teste/salvar" method="post">

	Titulo: <input type="text" name="titulo"
		value="${usuarioLogado.teste.titulo}" id="teste_titulo" /> 
	<button type="submit">
		<fmt:message key="" />
	</button>
</form>