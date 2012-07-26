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
    <li class="active">Passo 3</li>
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
        <li class="active"><a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo3">Passo3</a></li>
        <li><a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo4">Passo4</a></li>
    </ul>

    <div class="form-horizontal form-layout">
        <fieldset style="width: 750px; margin: -12px auto 0 auto;">
            <legend>Passo 3</legend>


            <form class="form-horizontal" id="loginForm" action="${pageContext.request.contextPath}/teste/convidar/usuario" method="post">
                <div class="span9" style="margin-top: 20px">
                    <h1>Lista de Usuários</h1>
                </div>
                <div class="span9">
                    <table class="table table-striped table-bordered table-condensed" style="background-color: white">
                        <thead>
                            <tr>
                                <th></th>
                                <th><fmt:message key="usuario.nome" /></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${usuariosLivres}" var="usuario">
                                <tr>
                                    <td width="30"><input type="checkbox" name="idUsuarios[]" value="${usuario.id }"></td>
                                    <td>${usuario.nome}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <input type="hidden" name=idTeste value=${testeView.teste.id }>
                <input type="submit" value="<fmt:message key="usuarios.add"/>" class="btn btn-primary" style="float: right;"/>
            </form>
        </fieldset> 

        <fieldset style="width: 750px; margin: -12px auto 0 auto">
            <form class="form-horizontal" id="loginForm" action="${pageContext.request.contextPath}/teste/desconvidar/usuario" method="post">
                <div class="span9" style="margin-top: 20px">
                    <h1>Usuários Convidados</h1>
                </div>
                <div class="span9">
                    <table class="table table-striped table-bordered table-condensed" style="background-color: white">
                        <thead>
                            <tr>
                                <th></th>
                                <th><fmt:message key="usuario.nome" /></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${usuariosEscolhidos}" var="usuario">
                                <tr>
                                    <td width="30"><input type="checkbox" name="idUsuarios[]" value="${usuario.id }"></td>
                                    <td>${usuario.nome}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                 <input type="hidden" name=idTeste value=${testeView.teste.id }>
<%--                 <input type="submit" value="<fmt:message key="usuarios.add"/>" class="btn btn-primary" style="float: right;"/> --%>
                <input type="submit" value="<fmt:message key="usuarios.remove"/>" class="btn btn-danger" style="float: right; margin-right: 10px"/>
            </form>
        </fieldset>
    </div>
</div>
