<ul class="breadcrumb">
    <li>
        <a href="${pageContext.request.contextPath}/usuario">Início</a> <span class="divider">/</span>
    </li>
    <li class="active">Inserir Teste</li>
</ul>

<c:if test="${not empty errors}">
    <div class="alert alert-error">
        <c:forEach items="${errors}" var="error">
            ${error.message}<br />
        </c:forEach>
    </div>
</c:if>


<form class="form-horizontal form-layout w600" id="teste_Form" action="${pageContext.request.contextPath}/teste/salvar" method="post">
    <c:if test="${not empty usuario.id}">
        <input type="hidden" name="usuario.id" value="${usuario.id}" />
        <input type="hidden" name="_method" value="put" />
    </c:if>
    <fieldset>
        <legend>Criar Teste</legend>
        <div class="control-group">
            <label class="control-label" for="input01"><fmt:message key="teste.titulo"/>*</label>
            <div class="controls">
                <input type="text" name="titulo" value="${usuarioLogado.teste.titulo}" id="teste_titulo" class="input-xlarge"/> 
            </div>
        </div>

        <div class="form-actions">
            <input type="submit" value="<fmt:message key="botao.criar.teste" />" name="criar teste" title="Criar Teste" class="btn btn-primary" style="float: right; width: 120px"/>
        </div>
    </fieldset>
</form>