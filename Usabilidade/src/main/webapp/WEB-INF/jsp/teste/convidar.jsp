
<c:if test="${not empty errors}">
    <div class="alert alert-error">
        <c:forEach items="${errors}" var="error">
            ${error.message}<br />
        </c:forEach>
    </div>
</c:if>

<div class="span10 offset1">
   
    <div class="form-horizontal form-layout">
        <fieldset style="width: 750px; margin: -12px auto 0 auto;">


            <form class="form-horizontal" id="loginForm" action="${pageContext.request.contextPath}/teste/convidar/usuario" method="post">
                <div class="span9" style="margin-top: 20px">
                    <h1><fmt:message key="convidar.usuarios" /></h1>
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
    </div>
</div>
