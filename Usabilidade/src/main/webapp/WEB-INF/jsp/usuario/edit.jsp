<c:if test="${not empty errors}">
    <c:forEach items="${errors}" var="error">
        ${error.message}<br />
    </c:forEach>
</c:if>

<form id="editUsuario_Form" action="${pageContext.request.contextPath}/usuarios" method="post" class="formee">
    <c:if test="${not empty usuario.id}">
        <input type="hidden" name="usuario.id" value="${usuario.id}" />
        <input type="hidden" name="_method" value="put" />
    </c:if>
    <fieldset style="width: 600px;">
        <div class="grid-12-12 clear">
            <label><fmt:message key="nome" />: <em class="formee-req">*</em></label>
            <input type="text" name="usuario.nome" value="${usuario.nome}" id="nome" />
        </div>
        <div class="grid-12-12 clear">
            <label><fmt:message key="email" />: <em class="formee-req">*</em></label>
            <input type="text" name="usuario.email" value="${usuario.email}" />
        </div>
        <div class="grid-6-12 clear">
            <label><fmt:message key="senha" />: <em class="formee-req">*</em></label>
            <input type="password" name="usuario.senha" id="usuario.senha" value="${usuario.senha}" />
        </div>
        <div class="grid-6-12">
            <label><fmt:message key="confirmasenha" />: <em class="formee-req">*</em></label>
            <input type="password" name="confirmaSenha" value=""/>
        </div>
        <div class="grid-6-12">
            <label><fmt:message key="telefone" />: </label>
            <input type="text" name="usuario.telefones[0]" value="${usuario.telefones[0]}" />
        </div>
        <div class="grid-6-12">
            <input type="submit" value="<fmt:message key="usuario.cadastro"/>" name="enviar" title="Enviar" class="right" style="margin-top: 30px"/>
        </div>
    </fieldset>
</form>
<a href="${pageContext.request.contextPath}">Back</a>
<script type="text/javascript">
    $("#editUsuario_Form").validate({
        rules : {
            "usuario.nome" : {
                required : true
            },
            "usuario.email" : {
                required : true,
                email : true
            },
            "usuario.senha" : {
                required : true,
                minLength : 6
            },
            "confirmaSenha" : {
                required : true,
                minLength : 6
            }
        }
    });
</script>