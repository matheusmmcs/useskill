<head>
    <title>Usuario [show]</title>
</head>
<body>

<c:if test="${not empty errors}">
    <div class="alert alert-error">
        <c:forEach items="${errors}" var="error">
            ${error.message}<br />
        </c:forEach>
    </div>
</c:if>

    
    
<form class="form-horizontal form-layout w600">
    <c:if test="${not empty usuario.id}">
        <input type="hidden" name="usuario.id" value="${usuario.id}" />
        <input type="hidden" name="_method" value="put" />
    </c:if>
    <fieldset>
        <legend>Usuário Selecionado</legend>
        <div class="control-group">
            <label class="control-label" for="input01"><fmt:message key="nome" />*</label>
            <div class="controls">
                <input type="text" name="nome" value="${usuario.nome}" disabled class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="input01"><fmt:message key="email" />*</label>
            <div class="controls">
                <input type="text" name="email" value="${usuario.email}" disabled class="input-xlarge"/>
            </div>
        </div>
        

        <div class="form-actions">
            <a href="${pageContext.request.contextPath}/usuarios/${usuario.id}/edit" class="btn btn-primary" style="float: right">Editar</a>
        </div>
    </fieldset>
</form>
     
</body>