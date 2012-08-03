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
							<td><a class="btn"
								href="${pageContext.request.contextPath}/teste/${teste.id}/editar/passo1"
								title="<fmt:message key="table.editar"/>" data-topmenu="1"> <span
									class="icon-pencil"></span> </a> <a class="btn btn-primary"
								href="${pageContext.request.contextPath}/teste/${teste.id}/remover"
								title="<fmt:message key="table.remover"/>"> <span
									class="icon-trash icon-white"></span> </a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</fieldset>
		 <div class="pagination">
        <ul>      
        	<c:choose>
					<c:when test="${paginaAtual==1}">
						<li class="disabled"><a href="#"> << </a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/usuario/pag/${paginaAtual-1}"> << </a></li>
					</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${paginaAtual-2>0}">
					<li><a href="${pageContext.request.contextPath}/usuario/pag/${paginaAtual-2}">${paginaAtual-2}</a></li>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${paginaAtual-1>0}">
					<li><a href="${pageContext.request.contextPath}/usuario/pag/${paginaAtual-1}">${paginaAtual-1}</a></li>
				</c:when>
			</c:choose>
			<li><a class="active" href="${pageContext.request.contextPath}/usuario/pag/${paginaAtual}">${paginaAtual}</a></li>
			<c:choose>
					<c:when test="${paginaAtual+1<=qtdPaginas}">
						<li><a href="${pageContext.request.contextPath}/usuario/pag/${paginaAtual+1}">${paginaAtual+1}</a></li>
					</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${paginaAtual+2<=qtdPaginas}">
					<li><a href="${pageContext.request.contextPath}/usuario/pag/${paginaAtual+2}">${paginaAtual+2}</a></li>
				</c:when>
			</c:choose>
			<c:choose>
					<c:when test="${paginaAtual==qtdPaginas}">
						<li class="disabled"><a href="#"> >> </a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/usuario/pag/${paginaAtual+1}"> >> </a></li>
					</c:otherwise>
			</c:choose>
        </ul>
      </div>
</div>