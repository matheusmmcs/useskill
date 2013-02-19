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
		<li>
			<a href="${pageContext.request.contextPath}/teste/${testeId}/analise"> 
				<fmt:message key="analise.tarefas" />
			</a>
			<span class="divider">/</span>     
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/teste/${testeId}/tarefa/${tarefaId}/analise"> 
				<fmt:message key="analise.list.usuarios.min" />
			</a>
			<span class="divider">/</span>     
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/teste/${testeId}/tarefa/${tarefaId}/usuario/${usuarioId}/analise"> 
				<fmt:message key="analise.list.fluxos.min" />
			</a>
			<span class="divider">/</span>     
		</li>
		<li class="active">
			<fmt:message key="analise.list.acoes" />
		</li>
	</ul>

	<form class="form-horizontal form-layout"
		action="${pageContext.request.contextPath}/conta" method="post">
		<fieldset>
			<legend>
				<span><fmt:message key="analise.list.acoes" />
				</span>
				<p>
					<fmt:message key="teste"/> - ${nomeTeste }
					<br/>
					<fmt:message key="tarefa" /> - ${nomeTarefa }
					<br/>
					<fmt:message key="usuario" /> - ${nomeUsuario }
					<br/>
					<fmt:message key="fluxo" /> - 1/3
				</p>
				<hr />
			</legend>

			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th style="width: 155px"><fmt:message key="acao" /></th>
						<th style="width: 155px"><fmt:message key="urldaacao" /></th>
						<th style="width: 155px"><fmt:message key="tempo" /></th>
						<th style="width: 155px"><fmt:message key="elemento" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${acoes}" var="acao">
						<tr>
							<td class="centertd">${acao.sActionType}</td>
							<td class="centertd">${acao.sUrl}</td>
							<td class="centertd">${acao.sTime}</td>
							<td class="centertd">${acao.sContent}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</fieldset>
	</form>
</div>