<head>
	<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/animatedtablesorter/style.css" type="text/css" />  -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/wysiwig/bootstrap/libs/css/wysiwyg-color.css" type="text/css" />
	<style>
		#roteiro-text{
			font-size: 12px !important;
			line-height: 16px;
			text-align: justify;
			padding: 5px;
			margin: 3px 15px 15px;
			color: #333;
			border: 1px solid #ddd;
		}
	</style>
</head>
<body>

<div class="span12 container-right">
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

	<div class="form-horizontal form-layout">
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
				<span>
					<fmt:message key="roteiro" />
				</span>
				<div id="roteiro-text">
					${tarefaRoteiro}
				</div>
				<hr />
			</legend>
				<div class="row-fluid">
				
				<p class="legend"><fmt:message key="analise.acoes.obrigatorias" /> - ${acoesObrigatorias.size()}</p>
				<table class="table blue-table">
					<colgroup>
						<col span="1" style="width: 10%;">
				       <col span="1" style="width: 15%;">
				       <col span="1" style="width: 25%;">
				       <col span="1" style="width: 50%;">
				    </colgroup>
					<thead>
						<tr>
							<th class=""><fmt:message key="tempo" /></th>
							<th class=""><fmt:message key="acao" /></th>
							<th class=""><fmt:message key="elemento" /></th>
							<th class=""><fmt:message key="urldaacao" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${acoesObrigatorias}" var="acao">
							<tr class="action-path 
								<c:choose>
									<c:when test="${acao.acaoEspecialista}">right-path</c:when>
								    <c:otherwise>wrong-path</c:otherwise> 
								</c:choose>"
								 data-toggle="popover" title="<fmt:message key="analise.acao.detalhes" />" 
								 data-content='ActionType = ${acao.sActionType} </br> Time = ${acao.sTime} </br> XPath = ${acao.sXPath} </br> Element Tag = ${acao.sTag} [${acao.sTagIndex}] </br> Position = [${acao.sPosX}, ${acao.sPosY}] </br> URL = ${acao.sUrl} </br> Content = ${acao.shortContent()} '
								 >
								<td class="centertd">${acao.sTime}</td>
								<td class="centertd">${acao.sActionType}</td>
								<td class="centertd long">${acao.getDescricaoElemento()}</td>
								<td class="centertd long">${acao.sUrl}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				
				<p class="legend"><fmt:message key="analise.acoes.usuario" /> - ${acoes.size()}</p>
				<table class="table blue-table">
					<colgroup>
						<col span="1" style="width: 10%;">
				       <col span="1" style="width: 15%;">
				       <col span="1" style="width: 25%;">
				       <col span="1" style="width: 50%;">
				    </colgroup>
					<thead>
						<tr>
							<th class=""><fmt:message key="tempo" /></th>
							<th class=""><fmt:message key="acao" /></th>
							<th class=""><fmt:message key="elemento" /></th>
							<th class=""><fmt:message key="urldaacao" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${acoes}" var="acao">
							<tr class="action-path 
								<c:choose>
									<c:when test="${acao.obrigatorio}">right-path</c:when>
								    <c:otherwise> 
								        <c:choose>
								            <c:when test="${acao.melhorCaminho}">best-path</c:when>
								            <c:otherwise> 
								                <c:choose>
										            <c:when test="${not acao.acaoEspecialista}">wrong-path</c:when> 
										            <c:otherwise></c:otherwise>
										        </c:choose>
								            </c:otherwise>
								        </c:choose>
								    </c:otherwise> 
								</c:choose>"
								 data-toggle="popover" title="<fmt:message key="analise.acao.detalhes" />" 
								 data-content='ActionType = ${acao.sActionType} </br> Time = ${acao.sTime} </br> XPath = ${acao.sXPath} </br> Element Tag = ${acao.sTag} [${acao.sTagIndex}] </br> Position = [${acao.sPosX}, ${acao.sPosY}] </br> URL = ${acao.sUrl} </br> Content = ${acao.shortContent()} '
								 >
								<td class="centertd">${acao.sTime}</td>
								<td class="centertd">${acao.sActionType}</td>
								<td class="centertd long">${acao.getDescricaoElemento()}</td>
								<td class="centertd long">${acao.sUrl}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
					
				</div>			
		</fieldset>
	</div>
	
</div>

<script>
	(function($){
		$(document).ready(function(){
			$("[data-toggle=popover]").popover({
			    html: true,
			    trigger: 'hover',
			    placement: get_popover_placement,
			    animation: true
			});
			
			function get_popover_placement(pop, dom_el) {
				var docHeight = (document.height !== undefined) ? document.height : document.body.offsetHeight;
				var top_pos = $(dom_el).offset().top;
				console.log(docHeight - top_pos)
				if (docHeight - top_pos < 300) return 'top';
				return 'bottom';
		    }
		});
	})(jQuery);
</script>
</body>