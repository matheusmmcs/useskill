<body>
<c:if test="${not empty errors}">
            <div class="alert alert-error">
                <c:forEach items="${errors}" var="error">
                    ${error.message}<br />
                </c:forEach>
            </div>
        </c:if>
    <ul class="breadcrumb">
        <li class="active"><fmt:message key="inicio" /></li>
    </ul>
    <div class="row show-grid">
        <div class="span2 offset10">
            
        </div>
    </div>

    <div class="span10 offset1">
        <div class="form-horizontal form-layout">
            <div class="row show-grid">
                <div class="span4" style="margin: 10px 0px 0px 640px">
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/teste/criar" style="padding: 5px 20px; margin-left: 35px">Criar Teste</a>
                </div>
            </div>
            <fieldset style="width: 750px; margin: 0px auto">

                <div class="row show-grid" style="margin: 0px auto 0 auto">
                    <form class="form-horizontal" id="loginForm" action="${pageContext.request.contextPath}/conta" method="post">
                        <div class="span9">
                            <h1><fmt:message key="testes.construcao" /></h1>
                        </div>
                        <div class="span9">
                            <table class="table table-striped table-bordered table-condensed" style="background-color: white">
                                <thead>
                                    <tr>
                                        <th><fmt:message key="titulo" /></th>
                                        <th><fmt:message key="editar" /></th>
                                        <th><fmt:message key="remover" /></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${testesCriados}" var="teste">
                                        <tr>
                                            <td><a href="${pageContext.request.contextPath}/teste/${teste.id}/editar/passo1">${teste.titulo}</a></td>
                                            <td width="100"><a class="btn" href="${pageContext.request.contextPath}/teste/${teste.id}/editar/passo1" style="margin-left: 13px"><span class="icon-pencil"></span> <fmt:message key="editar" /></a></td>
                                            <td width="100"><a class="btn" href="${pageContext.request.contextPath}/teste/${teste.id}/remover" style="margin-left: 5px"><span class="icon-trash"></span> <fmt:message key="remover" /></a></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </form>
                </div>

                <div class="row show-grid" style="margin: 0px auto 0 auto">
                    <div class="form-horizontal" id="loginForm">
                        <div class="span9">
                            <h1>Convites de Testes</h1>
                        </div>
                        <div class="span9">
                            <table class="table table-striped table-bordered table-condensed" style="background-color: white">
                                <thead>
                                    <tr>
                                        <th><fmt:message key="titulo" /></th>
                                        <th><fmt:message key="criador" /></th>
                                        <th></th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${testesConvidados}" var="teste">
                                        <tr>
                                            <td>${teste.titulo}</td>
                                            <td width="200">${teste.usuarioCriador.nome}</td>
                                            <td width="100">
                                                <form action="${pageContext.request.contextPath}/teste/participar/aceitar" method="post">
                                                    <input type="hidden" value="${teste.id }" name="testeId"> 
                                                    <input class="btn btn-info" type="submit" value="<fmt:message key="participar"/>" style="margin-left: 20px">
                                                </form>
                                            </td>

                                            <td width="100">
                                                <form action="${pageContext.request.contextPath}/teste/participar/aceitar" method="post" style="margin-left: 8px">
                                                    <input type="hidden" value="${teste.id }" name="testeId"> 
                                                    <input class="btn" type="submit" value="<fmt:message key="nao.participar"/>">
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>


            </fieldset>
        </div>
    </div>
</body>