<c:if test="${not empty tarefas}"><c:forEach items="${tarefas}" var="tarefa">
        ${tarefa.titulo}
    </c:forEach>
</c:if>
<c:if test="${not empty perguntas}"><c:forEach items="${perguntas}" var="pergunta">
        <div id="pergunta_exibe">
            
            
        </div>
        <br>
    </c:forEach>
</c:if>
<a href="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id}/editar/passo2/criar/tarefa">Inserir Tarefa</a>
<a href="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id}/editar/passo2/criar/pergunta">Inserir Pergunta</a>
