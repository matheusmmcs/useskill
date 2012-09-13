<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jscripts/libs/jqueryui/css/smoothness/jquery-ui-1.8.18.custom.css">
	
	<style>
		#USsaveOrdem{
			margin: 0 10px 7px 0;
		}
		.ui-state-green,.ui-state-blue{
			cursor: default;
		}
	</style>
</head>
<body>
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
		<li>
			<a href="${pageContext.request.contextPath}/testes/liberados"> 
				<fmt:message key="testes.liberado" />
			</a>
			<span class="divider">/</span>
		</li>
		<li class="active">Analise</li>
	</ul>

	<div class="form-horizontal form-layout">
		<fieldset>
			<legend>
				<span> Analise </span>
				<p>
					${testeView.teste.titulo }
				</p>
				<hr />
			</legend>
			
		
			<ul class="sortable">
				<c:if test="${not empty elementosTeste}">
					<c:forEach items="${elementosTeste}" var="el">
								<c:choose>
									<c:when test="${el.tipo == 'T'}">
							<li class="ui-state-blue">
								<div class="titulo">Tarefa - ${el.titulo }</div>
								<div class="acoes ">
										<a class="btn" href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/tarefa/${el.id}/analise" title="<fmt:message key="table.editar"/>">
											<span class="icon-pencil"></span>
										</a>
									
								</div>
								<input type="hidden" name="tipo" value="T"/>
								<input type="hidden" name="id" value="${el.id}"/>
							</li>
									</c:when>
									<c:otherwise>
									
							<li class="ui-state-green">
								<div class="titulo">Pergunta - ${el.titulo }</div>
								<div class="acoes ">
										<a class="btn" href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${el.id}/pergunta" title="<fmt:message key="table.editar"/>">
											<span class="icon-pencil"></span>
										</a>
								</div>
								<input type="hidden" name="tipo" value="P"/>
								<input type="hidden" name="id" value="${el.id}"/>
							</li>
									</c:otherwise>
								</c:choose>
					</c:forEach>
				</c:if>
			</ul>
		</fieldset>
	</div>
</div>

</body>