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
		<li class="active"><fmt:message key="testes.meus" /></li>
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

	<div class="form-horizontal form-layout">
		<fieldset>
			<legend>
				<span> <fmt:message key="testes.construcao" /> </span>
				<hr />
			</legend>

			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th><fmt:message key="table.titulo" />
						</th>
						<th style="width: 85px"><fmt:message key="table.acoes" />
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${testesCriados}" var="teste">
						<tr>
							<td><a data-topmenu="1"
								href="${pageContext.request.contextPath}/teste/${teste.id}/editar/passo1">${teste.titulo}</a>
							</td>
							<td class="centertd"><a class="btn"
								href="${pageContext.request.contextPath}/teste/${teste.id}/editar/passo1"
								title="<fmt:message key="table.editar"/>" data-topmenu="1"> <span
									class="icon-pencil"></span> </a> 
								<a title="<fmt:message key="table.remover"/>"
								class="btn btn-primary btn-modal" 
								data-href="${pageContext.request.contextPath}/teste/${teste.id}/remover" 
								data-acao="Remover" 
								data-toggle="modal" 
								href="#modalMessages"
								> <span
									class="icon-trash icon-white"></span> </a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</fieldset>
		
		<jsp:include page="../paginator.jsp" flush="true">
			<jsp:param name="link" value="${pageContext.request.contextPath}/usuario/pag/"/>
		</jsp:include>
	</div>
</div>