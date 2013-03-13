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
		<li class="active">
			<fmt:message key="analise"/>
		</li>
	</ul>

	<div class="form-horizontal form-layout">
		<fieldset>
			<legend>
				<span>
					<fmt:message key="analise"/> - <fmt:message key="analise.list.tarefaseperguntas"/>
				</span>
				<p>
					<fmt:message key="teste"/> - ${testeView.teste.titulo }
				</p>
				<hr />
			</legend>
			
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th style="width: 100px"><fmt:message key="analise.tipo" /></th>
						<th><fmt:message key="analise.titulo.tarefaepergunta" /></th>						
						<th style="width: 105px"><fmt:message key="analise.detalhar" /></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty elementosTeste}">
					<c:forEach items="${elementosTeste}" var="el">
						<c:set var="elTipo" value="${el.tipo.toString()}" />
								<c:choose>
									<c:when test="${elTipo == 'T'}">
									<tr>
										<td>
											<fmt:message key="tarefa"/>
									</c:when>
									<c:otherwise>
									<tr>
										<td>
											<fmt:message key="pergunta"/>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								${el.titulo}
							</td>
							<td>
								<div class="acoes centertd">
								<c:choose>
									<c:when test="${elTipo == 'T'}">
									<a class="btn btn-primary" href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/tarefa/${el.id}/analise" title="<fmt:message key="table.editar"/>">
											<span class="icon-tasks icon-white"></span> <fmt:message key="usuarios"/>
										</a>

									</c:when>
									<c:otherwise>
									<a class="btn btn-primary" href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/pergunta/${el.id}/analise" title="<fmt:message key="table.editar"/>">
											<span class="icon-tasks icon-white"></span> <fmt:message key="usuarios"/>
										</a>

									</c:otherwise>
								</c:choose>
																		</div> 
							</td>
						</tr>
					</c:forEach>
				</c:if>
				</tbody>
			</table>
			
		</fieldset>
	</div>
</div>

</body>