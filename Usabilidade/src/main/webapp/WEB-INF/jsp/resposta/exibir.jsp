${pergunta.texto}
<c:if test="${pergunta.tipoRespostaAlternativa!=true}">
	<form action="${pageContext.request.contextPath}/" method="post">
		<textarea rows="" cols="" name="resposta">
		
		</textarea>
		<input type="submit" value="<fmt:message key="responder.Pergunta"/>">
	</form>
</c:if>