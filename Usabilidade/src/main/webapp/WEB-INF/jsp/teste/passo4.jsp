<ul class="breadcrumb">
    <li>
        <a href="${pageContext.request.contextPath}/usuario"><fmt:message key="inicio" /></a> <span class="divider">/</span>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo1">Editar Teste - Passo 1</a> <span class="divider">/</span>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo2">Passo 2</a> <span class="divider">/</span>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo3">Passo 3</a> <span class="divider">/</span>
    </li>
    <li class="active">Passo 4</li>
</ul>

<c:if test="${not empty errors}">
    <div class="alert alert-error">
        <c:forEach items="${errors}" var="error">
            ${error.message}<br />
        </c:forEach>
    </div>
</c:if>

<div class="span10 offset1">
    <ul class="nav nav-tabs" style="margin: 0 auto; width: 97%">
        <li><a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo1">Passo1</a></li>
        <li><a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo2">Passo2</a></li>
        <li><a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo3">Passo3</a></li>
        <li class="active"><a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo4">Passo4</a></li>
    </ul>



    <form class="form-horizontal form-layout" id="teste_passo1" action="${pageContext.request.contextPath}/teste/liberar" method="post">
        <input type="hidden" value="${testeView.teste.id }" name="idTeste">

        <fieldset>
            <legend>Passo 4</legend>

            <div class="form-actions">
                <input type="submit" value="<fmt:message key="liberar.teste"/>" title="Liberar Teste" class="btn btn-primary btn-large" style="width: 200px; height: 50px; margin-left: 130px"/>
            </div>
        </fieldset>
    </form>
</div>