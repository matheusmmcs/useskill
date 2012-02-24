<form id="teste_Form"
	action="${pageContext.request.contextPath}/teste/editar" method="post">

	Titulo: <input type="text" name="titulo"
		value="${usuarioLogado.teste.titulo}" id="teste_titulo" /> <input
		type="text" name="tituloPublico"
		value="${usuarioLogado.teste.tituloPublico}" id="teste_tituloPublico" />
	<textarea name="textoIndroducao"
		value="${usuarioLogado.teste.textoIndroducao}"
		id="teste_textoIndroducao" cols="8" rows="5" />
		
	</textarea>
	<button type="submit">
		<fmt:message key="" />
	</button>
</form>