
<div class="span12 container-right">

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
	
	<c:if test="${not empty errors}">
		<div class="alert alert-error">
			<c:forEach items="${errors}" var="error">
        		${error.message}<br />
			</c:forEach>
		</div>
	</c:if>
	
	<!-- 	INIT GRAPH -->
	
<!-- 	<div class="form-layout"> -->
<!-- 		<fieldset> -->
<!-- 			<legend> -->
<!-- 				<span> -->
<%-- 					<fmt:message key="analise.grafo.acoes" /> --%>
<!-- 				</span> -->
<!-- 				<p> -->
<%-- 					<fmt:message key="analise.grafo.view" /> <fmt:message key="tarefa" /> - ${tarefa.nome } --%>
<!-- 				</p> -->
<!-- 				<hr/> -->
<!-- 			</legend> -->
<!-- 		</fieldset> -->
<!-- 	</div> -->
	
	
<!-- 	<div id="useskill-network" style="width: 99%; height: 350px; margin: 0 auto; background-color: #fafafa; border: 2px solid lightgray; margin-bottom: 20px;"> -->
<!-- 	</div> -->
	
<!-- 	<ul class="horizontal" style="margin-bottom: 20px; margin-left: 0"> -->
<%-- 		<li><b><fmt:message key="analise.grafo.legenda" /></b></li> --%>
<%-- 		<li><span class="badge badge-inverse"><fmt:message key="analise.grafo.legenda.fixo" /></span></li> --%>
<%-- 		<li><span class="badge badge-info"><fmt:message key="analise.grafo.legenda.obrigatoria" /></span></li> --%>
<%-- 		<li><span class="badge badge-success"><fmt:message key="analise.grafo.legenda.melhor" /></span></li> --%>
<%-- 		<li><span class="badge badge-warning"><fmt:message key="analise.grafo.legenda.normal" /></span></li> --%>
<%-- 		<li><span class="badge badge-important"><fmt:message key="analise.grafo.legenda.desconhecia" /></span></li> --%>
<!-- 	</ul> -->
	
	
<!-- 	END GRAPH -->

<!-- 	SCRIPTS DO VISJS -->
<%-- 	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/vis/vis.js"></script> --%>
<%-- 	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/app/graph/models.js"></script> --%>
<!-- 	http://visjs.org/examples/network/25_physics_configuration.html -->
<!-- 	http://visjs.org/docs/network.html#Nodes -->
	
	<form class="form-horizontal form-layout"
		action="${pageContext.request.contextPath}/conta" method="post">
		
		
		<fieldset>
			<legend>
				<span>
					<fmt:message key="analise.dados.usuarios" />
				</span>
			</legend> 
			<p class="legend">
				<fmt:message key="analise.dados.usuarios.tipo"/><fmt:message key="testes.tipo.TESTER"/>
			</p>
			<table class="table blue-table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<!-- <th rowspan="2" style="width: 65px"></th> -->
						<th colspan="2"><fmt:message key="usuarios"/></th>
    					<th colspan="4"><fmt:message key="fluxos"/></th>
  					</tr>
					<tr>
						<th class="sub-th"><fmt:message key="analise.usuarios.convidados"/></th>
						<th class="sub-th"><fmt:message key="analise.usuarios.realizaram"/></th>
						<th class="sub-th"><fmt:message key="analise.fluxos.tempo"/></th>
						<th class="sub-th"><fmt:message key="analise.fluxos.acoes"/></th>
						<th class="sub-th"><fmt:message key="analise.fluxos.realizados"/></th>
						<th class="sub-th"><fmt:message key="analise.fluxos.namedia"/></th>
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
			<table class="table blue-table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<!-- <th rowspan="2" style="width: 65px"></th> -->
						<th colspan="2"><fmt:message key="usuarios"/></th>
    					<th colspan="4"><fmt:message key="fluxos"/></th>
  					</tr>
					<tr>
						<th class="sub-th"><fmt:message key="analise.usuarios.convidados"/></th>
						<th class="sub-th"><fmt:message key="analise.usuarios.realizaram"/></th>
						<th class="sub-th"><fmt:message key="analise.fluxos.tempo"/></th>
						<th class="sub-th"><fmt:message key="analise.fluxos.acoes"/></th>
						<th class="sub-th"><fmt:message key="analise.fluxos.realizados"/></th>
						<th class="sub-th"><fmt:message key="analise.fluxos.namedia"/></th>
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
			<table class="table blue-table table-striped table-bordered table-condensed">
				<colgroup>
					<col span="1" style="width: 60%;">
			       	<col span="1" style="width: 15%;">
			       	<col span="1" style="width: 10%;">
			       	<col span="1" style="width: 15%;">
			    </colgroup>
				<thead>
					<tr>
						<th><fmt:message key="analise.nome.usuario" /></th>
						<th><fmt:message key="analise.prioridade" /></th>
						<th><fmt:message key="analise.tipo" /></th>
						<th><fmt:message key="analise.detalhar" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listaPrioridade}" var="resultado">
						<tr>
							<td>
								${resultado.fluxo.usuario.nome}
							</td>
							<td class="centertd">
								${resultado.prioridade}
							</td>
							<td class="centertd">
								${resultado.fluxo.tipoConvidado}
							</td>
							<td class="centertd">
								<a class="btn btn-success" title="<fmt:message key="testes.usuarios.convidados" />"
								href="${pageContext.request.contextPath}/teste/${testeId}/tarefa/${tarefaId}/usuario/${resultado.fluxo.usuario.id}/analise">
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