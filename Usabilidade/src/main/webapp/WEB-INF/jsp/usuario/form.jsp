<c:if test="${not empty errors}">
    <c:forEach items="${errors}" var="error">
        ${error.category} - ${error.message}<br />
    </c:forEach>
</c:if>

<form id="editUsuario_Form" action="${pageContext.request.contextPath}/usuarios" method="post">

    <c:if test="${not empty usuario.id}">
        <input type="hidden" name="usuario.id" value="${usuario.id}"/>
        <input type="hidden" name="_method" value="put"/>
    </c:if>
    Nome:
    <input type="text" name="usuario.nome" value="${usuario.nome}" id="nome"/>
    Email:
    <input type="text" name="usuario.email" value="${usuario.email}"/>
    Telefone:
   				<input type="text" name="usuarios.telefones[${status.index}]" value="${telefones}" />
		
    <input type="text" name="usuario.telefones[0]" value="${usuario.telefones[0]}"/>
    Senha:
    <input type="password" name="usuario.senha" id="usuario.senha" value="${usuario.senha}"/>
    Confirma Senha:
    <input type="password" name="confirmaSenha" />
    <div class="actions">
        <button type="submit">send</button>
    </div>
</form>

<a href="${pageContext.request.contextPath}/login">Back</a>
