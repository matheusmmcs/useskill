<form action="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id }/editar/passo2"
	method="post">
	Titulo: <input type="text" name="titulo"
		value="${usuarioLogado.teste.titulo}" id="teste_titulo" /> 
	Titulo publico: <input
		type="text" name="tituloPublico"
		value="${usuarioLogado.teste.tituloPublico}" id="teste_tituloPublico" />
	Texto introdução:
	 <textarea name="textoIndroducao"
		value="${usuarioLogado.teste.textoIndroducao}"
		id="teste_textoIndroducao" cols="8" rows="5" >
${usuarioLogado.teste.textoIndroducao}
		
	</textarea>
<button type="submit">
		<fmt:message key="" />
	</button>
	
</form>