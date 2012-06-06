${pergunta.texto}
<c:if test="${pergunta.tipoRespostaAlternativa!=true}">
	<form
		action="${pageContext.request.contextPath}/teste/salvar/resposta/escrita"
		method="post">
		<textarea rows="" cols="" name="resposta">
		
		</textarea>
		<input type="submit" value="<fmt:message key="responder.Pergunta"/>">
	</form>
</c:if>
<c:if test="${pergunta.tipoRespostaAlternativa==true}">
	<form action="${pageContext.request.contextPath}/teste/salvar/resposta/alternativa" method="post">
		<c:forEach items="${pergunta.alternativas}" var="alternativa">
		
                    ${alternativa.textoAlternativa}<br />
		</c:forEach>
		<input type="submit" value="<fmt:message key="respAonder.Pergunta"/>">
	</form>
</c:if>