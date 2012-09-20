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
		<li class="active">
			<fmt:message key="analise.list.usuarios" />
		</li>
	</ul>

	<form class="form-horizontal form-layout"
		action="${pageContext.request.contextPath}/conta" method="post">
		<fieldset>
			<legend>
				<span>
					<fmt:message key="analise.list.usuarios" />
				</span>
				<p>
					<fmt:message key="tarefa" /> - ${tarefa.titulo }
					<br/>
					<fmt:message key="teste"/> - ${testeView.teste.titulo }
				</p>
				<hr />
			</legend>
		</fieldset>
		
		<fieldset style="margin-top: -15px;">
			<legend>
				<span>
					<fmt:message key="analise.dados.usuarios" />
				</span>
			</legend> 
			<p class="legend">
				<fmt:message key="analise.dados.usuarios.tipo"/><fmt:message key="testes.tipo.TESTER"/>
			</p>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<!-- <th rowspan="2" style="width: 65px"></th> -->
						<th colspan="2"><fmt:message key="usuarios"/></th>
    					<th colspan="4"><fmt:message key="fluxos"/></th>
  					</tr>
					<tr>
						<th style="width: 65px"><fmt:message key="analise.usuarios.convidados"/></th>
						<th style="width: 65px"><fmt:message key="analise.usuarios.realizaram"/></th>
						<th style="width: 65px"><fmt:message key="analise.fluxos.tempo"/></th>
						<th style="width: 65px"><fmt:message key="analise.fluxos.acoes"/></th>
						<th style="width: 65px"><fmt:message key="analise.fluxos.realizados"/></th>
						<th style="width: 65px"><fmt:message key="analise.fluxos.namedia"/></th>
					</tr>
				</thead>
				<tbody>
						<tr>
							<td class="centertd">
								12
							</td>
							<td class="centertd">
								10
							</td>
							<td class="centertd">
								80 - 120
							</td>
							<td class="centertd">
								18 - 32
							</td>
							<td class="centertd">
								15
							</td>
							<td class="centertd">
								13/11
							</td>
						</tr>
				</tbody>
			</table>
			
			<p class="legend">
				<fmt:message key="analise.dados.usuarios.tipo"/><fmt:message key="testes.tipo.USER"/>
			</p>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<!-- <th rowspan="2" style="width: 65px"></th> -->
						<th colspan="2"><fmt:message key="usuarios"/></th>
    					<th colspan="4"><fmt:message key="fluxos"/></th>
  					</tr>
					<tr>
						<th style="width: 65px"><fmt:message key="analise.usuarios.convidados"/></th>
						<th style="width: 65px"><fmt:message key="analise.usuarios.realizaram"/></th>
						<th style="width: 65px"><fmt:message key="analise.fluxos.tempo"/></th>
						<th style="width: 65px"><fmt:message key="analise.fluxos.acoes"/></th>
						<th style="width: 65px"><fmt:message key="analise.fluxos.realizados"/></th>
						<th style="width: 65px"><fmt:message key="analise.fluxos.namedia"/></th>
					</tr>
				</thead>
				<tbody>
						<tr>
							<td class="centertd">
								12
							</td>
							<td class="centertd">
								10
							</td>
							<td class="centertd">
								80 - 120
							</td>
							<td class="centertd">
								18 - 32
							</td>
							<td class="centertd">
								15
							</td>
							<td class="centertd">
								13/11
							</td>
						</tr>
				</tbody>
			</table>
		</fieldset>
		
		<br/>
		
		<fieldset>
			<legend>
				<span>
					<fmt:message key="analise.detalhamento.usuarios" />
				</span>
			</legend>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th><fmt:message key="analise.nome.usuario" /></th>
						<th style="width: 110px"><fmt:message key="analise.qtdfluxos" /></th>
						<th style="width: 45px"><fmt:message key="analise.tipo" /></th>
						<th style="width: 80px"><fmt:message key="analise.detalhar" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${fluxos}" var="fluxo">
						<tr>
							<td>
								${fluxo.nomeUsuario}
							</td>
							<td class="centertd">
								
							</td>
							<td class="centertd">
								${fluxo.tipoConvidado}
							</td>
							<td class="centertd">
								<a class="btn btn-success" title="<fmt:message key="testes.usuarios.convidados" />"
								href="${pageContext.request.contextPath}/teste/${testeId}/tarefa/${tarefaId}/usuario/${fluxo.idUsuario}/analise">
									<span class="icon-tasks icon-white"></span> <fmt:message key="analise.fluxos" />
								</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</fieldset>
		<jsp:include page="../paginator.jsp" flush="true">
			<jsp:param name="link" value="${pageContext.request.contextPath}/teste/${testeId}/tarefa/${tarefaId}/analise/pag/"/>
		</jsp:include>
	</form>
</div>