<%@include file="../leftmenus/default.jsp"%>

<div class="span9 container-right">
	<c:if test="${not empty errors}">
		<div class="alert alert-error">
			<c:forEach items="${errors}" var="error">
        		${error.message}<br />
			</c:forEach>
		</div>
	</c:if>

	<ul class="breadcrumb">
		<li>
			<a href="${pageContext.request.contextPath}/usuario"> 
				<fmt:message key="testes.meus" />
			</a>
			<span class="divider">/</span>
		</li>
		<li class="active"><fmt:message key="testes.liberado" /></li>
	</ul>
	
	<div class="btn-toolbar">
		<div class="btn-group pull-right">
			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/teste/criar"> <fmt:message
					key="testes.criar" /> </a>
		</div>
	
		<div class="btn-group pull-left">		
			<a class="btn"
				href="${pageContext.request.contextPath}/usuario">
				<fmt:message key="testes.mini.construcao"/>
			</a>
			<a class="btn"
				href="${pageContext.request.contextPath}/testes/liberados">
				<fmt:message key="testes.mini.liberados"/>
			</a>
			<a class="btn"
				href="${pageContext.request.contextPath}/testes/convidados">
				<fmt:message key="testes.mini.convites"/>
			</a>
		</div>
	</div>

	<form class="form-horizontal form-layout"
		action="${pageContext.request.contextPath}/conta" method="post">
		<fieldset>
			<legend>
				<span><fmt:message key="testes.liberado" />
				</span>
				<hr />
			</legend>

			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th style="width: 155px">Tipo da ação</th>
						<th style="width: 155px">Url</th>
						<th style="width: 155px">tempo realizacao</th>
						<th style="width: 155px">local</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${acoes}" var="acao">
						<tr>
							<td>${acao.sActionType}</td>
							<td>${acao.sUrl}</td>
							<td>${acao.sTime}</td>
							<td>${acao.sContent}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</fieldset>
		<jsp:include page="../paginator.jsp" flush="true">
			<jsp:param name="link" value="${pageContext.request.contextPath}/testes/liberados/pag/"/>
		</jsp:include>
	</form>
</div>