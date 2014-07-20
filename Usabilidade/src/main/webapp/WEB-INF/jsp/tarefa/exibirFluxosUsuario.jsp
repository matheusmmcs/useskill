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
			<a href="${pageContext.request.contextPath}/teste/${testeId}/tarefa/${tarefa.id}/analise"> 
				<fmt:message key="analise.list.usuarios.min" />
			</a>
			<span class="divider">/</span>     
		</li>
		<li class="active">
			<fmt:message key="analise.list.fluxos" />
		</li>
	</ul>

	<form class="form-horizontal form-layout"
		action="${pageContext.request.contextPath}/conta" method="post">
		<fieldset>
			<legend>
				<span>
					<fmt:message key="analise.list.fluxos" />
				</span>
				<p>
					<fmt:message key="teste"/> - ${tarefa.teste.titulo }
					<br/>
					<fmt:message key="tarefa" /> - ${tarefa.nome }
					<br/>
					<fmt:message key="usuario" /> - ${nomeUsuario }
				</p>
				<hr />
			</legend>
			<table class="table blue-table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th><fmt:message key="analise.datarealizacao"/></th>
						<th><fmt:message key="analise.duracao"/></th>
						<th><fmt:message key="analise.qtdAcoes"/></th>
						<th><fmt:message key="analise.fluxo.media"/></th>
						<th style="width: 105px"><fmt:message key="analise.detalhar"/></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${fluxos}" var="fluxo">
						<tr>
							<td class="centertd">
								<fmt:formatDate value="${fluxo.dataRealizacao}" type="date" pattern="dd/MM/yyyy"/> 
							</td>
							<td class="centertd">
								${fluxo.tempoRealizacao/1000} <fmt:message key="seg"/>
							</td>
							<td class="centertd">
								${fluxo.quantidadeAcoes}
							</td>
							<td class="centertd">
								${fluxo.mediaTempo } / ${fluxo.mediaAcoes }
							</td>
							<td class="centertd">
								<a class="btn btn-success" title="<fmt:message key="testes.usuarios.convidados" />"
								href="${pageContext.request.contextPath}/teste/${testeId}/tarefa/${tarefa.id}/usuario/${usuarioId}/fluxo/${fluxo.fluxoId}/analise">
									<span class="icon-tasks icon-white"></span> <fmt:message key="analise.acoes" />
								</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</fieldset>
	</form>
</div>