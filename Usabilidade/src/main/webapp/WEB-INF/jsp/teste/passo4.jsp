<%@include file="../leftmenus/default.jsp"%>

<div class="span9 container-right">
	<c:if test="${not empty errors}">
		<c:forEach items="${errors}" var="error">
        ${error.message}<br />
		</c:forEach>
	</c:if>

	<ul class="breadcrumb">
		<li><a href="${pageContext.request.contextPath}/usuario"> <fmt:message
					key="testes.meus" /> </a> <span class="divider">/</span>
		</li>
		<li class="active"><fmt:message key="testes.editar" />
			[<fmt:message key="testes.passo4" />]
		</li>
	</ul>
	
    <ul class="nav nav-tabs" style="margin: 0 auto; width: 97%">
        <li><a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo1"><fmt:message key="testes.passo1" /></a></li>
        <li><a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo2"><fmt:message key="testes.passo2" /></a></li>
        <li><a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo3"><fmt:message key="testes.passo3" /></a></li>
        <li class="active"><a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo4"><fmt:message key="testes.passo4" /></a></li>
    </ul>



    <form class="form-horizontal form-layout" 
    action="${pageContext.request.contextPath}/teste/liberar" method="post">
        <input type="hidden" value="${testeView.teste.id }" name="idTeste">
        <fieldset>
			<legend>
				<span><fmt:message key="testes.passo4" /></span>
				<hr/>
			</legend>

            <div class="form-actions">
                <input type="submit" value="<fmt:message key="liberar.teste"/>" title="Liberar Teste" class="btn btn-primary btn-large" style="width: 200px; height: 50px; margin-left: 230px;"/>
            </div>
        </fieldset>
    </form>
</div>
