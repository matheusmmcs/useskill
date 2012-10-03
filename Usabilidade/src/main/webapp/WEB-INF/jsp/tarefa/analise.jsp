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
					<fmt:message key="tarefa" /> - ${tarefa.nome }
					<br/>
					<fmt:message key="teste"/> - ${tarefa.teste.titulo }
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
								${convidados_EXPERT}
							</td>
							<td class="centertd">
								${convidados_realizaram_EXPERT}
							</td>
							<td class="centertd">
								${media_tempo_EXPERT}
							</td>
							<td class="centertd">
								${media_Acoes_EXPERT}
							</td>
							<td class="centertd">
								${total_fluxos_EXPERT}
							</td>
							<td class="centertd">
								${total_fluxos_media_tempo_EXPERT} / ${total_fluxos_media_acao_EXPERT}
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
								${convidados_USER}
							</td>
							<td class="centertd">
								${convidados_realizaram_USER}
							</td>
							<td class="centertd">
								${media_tempo_USER}
							</td>
							<td class="centertd">
								${media_Acoes_USER}
							</td>
							<td class="centertd">
								${total_fluxos_USER}
							</td>
							<td class="centertd">
								${total_fluxos_media_tempo_USER} / ${total_fluxos_media_acao_USER}
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