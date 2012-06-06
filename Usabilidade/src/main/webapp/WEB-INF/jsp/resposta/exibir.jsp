 <c:if test="${not empty errors}">
            <div class="alert alert-error">
                <c:forEach items="${errors}" var="error">
                    ${error.message}<br />
                </c:forEach>
            </div>
        </c:if>
${pergunta.texto}
<c:if test="${pergunta.tipoRespostaAlternativa!=true}">
	<form
		action="${pageContext.request.contextPath}/teste/salvar/resposta/escrita"
		method="post">
		<textarea rows="" cols="" name="resposta">
		
		</textarea>
		<input type="submit" value="<fmt:message key="responde.pergunta"/>">
	</form>
</c:if>

<c:if test="${pergunta.tipoRespostaAlternativa==true}">
	<form action="${pageContext.request.contextPath}/teste/salvar/resposta/alternativa" method="post">
		<c:forEach items="${pergunta.alternativas}" var="alternativa">
		               <input type="radio" name="alternativa.id" value="${alternativa.id}" />
		                    ${alternativa.textoAlternativa}<br>
		</c:forEach>
		<input type="submit" value="<fmt:message key="responde.pergunta"/>">
	</form>
</c:if>