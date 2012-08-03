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
                                        <th><fmt:message key="convidar.usuarios" /></th>
                                        <th><fmt:message key="analise" /></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${testesLiberados}" var="teste">
                                        <tr>
                                            <td><a href="${pageContext.request.contextPath}/teste/${teste.id}/editar/passo1">${teste.titulo}</a></td>
                                            <td width="100"><a class="btn" href="${pageContext.request.contextPath}/teste/${teste.id}/convidar/usuarios" style="margin-left: 13px"></span> <fmt:message key="convidar.usuarios" /></a></td>
                                            <td width="100"><a class="btn" href="${pageContext.request.contextPath}/teste/${teste.id}/remover" style="margin-left: 5px"><fmt:message key="analise" /></a></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </form>
                </div>
            </fieldset>
        </div>
        <div class="pagination">
        <ul>      
        	<c:choose>
					<c:when test="${paginaAtual==1}">
						<li class="disabled"><a href="#"> << </a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/testes/liberados/pag/${paginaAtual-1}"> << </a></li>
					</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${paginaAtual-2>0}">
					<li><a href="${pageContext.request.contextPath}/testes/liberados/pag/${paginaAtual-2}">${paginaAtual-2}</a></li>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${paginaAtual-1>0}">
					<li><a href="${pageContext.request.contextPath}/testes/liberados/pag/${paginaAtual-1}">${paginaAtual-1}</a></li>
				</c:when>
			</c:choose>
			<li><a class="active" href="${pageContext.request.contextPath}/testes/liberados/pag/${paginaAtual}">${paginaAtual}</a></li>
			<c:choose>
					<c:when test="${paginaAtual+1<=qtdPaginas}">
						<li><a href="${pageContext.request.contextPath}/testes/liberados/pag/${paginaAtual+1}">${paginaAtual+1}</a></li>
					</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${paginaAtual+2<=qtdPaginas}">
					<li><a href="${pageContext.request.contextPath}/testes/liberados/pag/${paginaAtual+2}">${paginaAtual+2}</a></li>
				</c:when>
			</c:choose>
			<c:choose>
					<c:when test="${paginaAtual==qtdPaginas}">
						<li class="disabled"><a href="#"> >> </a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/testes/liberados/pag/${paginaAtual+1}"> >> </a></li>
					</c:otherwise>
			</c:choose>
          
        </ul>
      </div>
    </div>
</body>