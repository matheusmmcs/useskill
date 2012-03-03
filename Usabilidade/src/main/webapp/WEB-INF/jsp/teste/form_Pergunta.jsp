<form action="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id}/editar/passo2/salvar/pergunta" method="POST">
    <c:if test="${not empty pergunta.id}">
        <input type="hidden" name="pergunta.id" value="${pergunta.id}"/>
    </c:if>
        <label for="pergunta.titulo">
            <fmt:message key="pergunta.titulo"/>
        </label>
    <input type="text" name="pergunta.titulo" value="${pergunta.titulo}" id="titulo"/>
      <label for="pergunta.texto">
            <fmt:message key="pergunta.texto"/>
        </label>
            <textarea name="pergunta.texto" value="${pergunta.texto}" id="texto">
    </textarea>
    <input type="checkbox" name="pergunta.tipoRespostaAlternativa" value="${pergunta.tipoRespostaAlternativa}" />
</form>