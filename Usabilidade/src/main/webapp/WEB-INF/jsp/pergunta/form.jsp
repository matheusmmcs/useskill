<form
    action="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id}/editar/passo2/salvar/pergunta"
    method="POST">
    <c:if test="${not empty pergunta.id}">
        <input type="hidden" name="pergunta.id" value="${pergunta.id}" />
    </c:if>
    <label for="pergunta.titulo"> <fmt:message
            key="pergunta.titulo" />
    </label> <input type="text" name="pergunta.titulo" value="${pergunta.titulo}"
                    id="titulo" /> <label for="pergunta.texto"> <fmt:message
            key="pergunta.texto" />
    </label>
    <textarea name="pergunta.texto" id="texto">
        ${pergunta.texto}
    </textarea>
    <input type="radio" name="pergunta.tipoRespostaAlternativa"
           value="true"
           />
    <input type="radio" name="pergunta.tipoRespostaAlternativa" value="false"/>
    <input type="submit" name="pergunta.salvar"
           value="<fmt:message key="pergunta.salvar"/>" />
</form>