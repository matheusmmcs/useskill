<form action="${pageContext.request.contextPath}/teste/liberar" method="post">
<input type="hidden" value="${usuarioLogado.teste.id }" name="idTeste">
<input type="submit" value="<fmt:message key="liberar.teste"/>" >
</form>