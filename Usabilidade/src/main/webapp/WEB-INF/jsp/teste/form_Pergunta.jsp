<form action="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id}/editar/passo2/salvar/pergunta" method="POST">
     <c:if test="${not empty usuario.id}">
    <input type="hidden" name="pergunta.id" value="${pergunta.id}"/>
    </c:if>
    <input type="text" name="pergunta.titulo" value="${pergunta.titulo}" id="titulo"/>
    <textarea name="pergunta.texto" value="${pergunta.texto}" id="texto">
    </textarea>
</form>