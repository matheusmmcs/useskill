<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jscripts/libs/jqueryui/css/smoothness/jquery-ui-1.8.18.custom.css">
	<script src="${pageContext.request.contextPath}/jscripts/libs/jqueryui/js/jquery-ui-1.8.18.custom.min.js"></script>
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
		<li><a href="${pageContext.request.contextPath}/usuario"> <fmt:message
					key="testes.meus" /> </a> <span class="divider">/</span>
		</li>
		<li class="active"><fmt:message key="testes.editar" />
			[<fmt:message key="testes.passo2" />]
		</li>
	</ul>

	<div class="btn-toolbar">
		<div class="pull-right">
			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/criar/tarefa">
				<fmt:message key="tarefa.inserir"/></a> 
			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/criar/pergunta"> 
				<fmt:message key="pergunta.inserir"/></a>
		</div>
	</div>

	<ul class="nav nav-tabs" style="margin: 0 auto; width: 97%">
		<li><a
			href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo1"><fmt:message
					key="testes.passo1" /> </a></li>
		<li class="active"><a
			href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo2"><fmt:message
					key="testes.passo2" /> </a></li>
		<li><a
			href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo3"><fmt:message
					key="testes.passo3" /> </a></li>
		<li><a
			href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo4"><fmt:message
					key="testes.passo4" /> </a></li>
	</ul>
	<div class="form-horizontal form-layout">
		<fieldset>
			<legend>
				<span> <fmt:message key="testes.passo2" /> </span>
				<p>
					${testeView.teste.titulo }
				</p>
				<hr />
				<p>
					<fmt:message key="testes.passo2.info" />
				</p>
			</legend>
		
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th><fmt:message key="table.titulo" /></th>
						<th style="width: 85px"><fmt:message key="table.acoes" />
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty perguntas}">
						<c:forEach items="${perguntas}" var="pergunta">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${pergunta.id}/pergunta">
										${pergunta.titulo}</a>
								</td>
								<td class="centertd"><a class="btn"
									href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${pergunta.id}/pergunta"
									title="<fmt:message key="table.editar"/>"> <span
										class="icon-pencil"></span> </a> 
									<a title="<fmt:message key="table.remover"/>"
									class="btn btn-primary btn-modal" 
									data-href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/pergunta/${pergunta.id}/apagar" 
									data-acao="Remover" 
									data-toggle="modal" 
									href="#modalMessages"
									> <span
										class="icon-trash icon-white"></span> </a></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			
			<ul class="sortable">
				<c:if test="${not empty elementosTeste}">
					<c:forEach items="${elementosTeste}" var="el">
								<c:choose>
									<c:when test="${el.tipo == 'T'}">
							<li class="ui-state-blue">
								<span class="ui-azul ui-icon ui-icon-arrowthick-2-n-s"></span>
								<div class="titulo">Tarefa - ${el.titulo }</div>
								<div class="acoes ">
										<a class="btn" href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${el.id}/tarefa" title="<fmt:message key="table.editar"/>">
											<span class="icon-pencil"></span>
										</a>
										<a title="<fmt:message key="table.remover"/>" class="btn btn-primary btn-modal" data-href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/tarefa/${el.id}/apagar" data-acao="Remover" data-toggle="modal" href="#modalMessages">
											<span class="icon-trash icon-white"></span>
										</a>
								</div>
								<input type="hidden" name="tipo" value="T"/>
								<input type="hidden" name="id" value="1"/>
							</li>
									</c:when>
									<c:otherwise>
									
							<li class="ui-state-green">
								<span class="ui-green ui-icon ui-icon-arrowthick-2-n-s"></span>
								<div class="titulo">Pergunta - ${el.titulo }</div>
								<div class="acoes ">
										<a class="btn" href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${el.id}/pergunta" title="<fmt:message key="table.editar"/>">
											<span class="icon-pencil"></span>
										</a>
										<a title="<fmt:message key="table.remover"/>" class="btn btn-primary btn-modal" data-href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/pergunta/${el.id}/apagar" data-acao="Remover" data-toggle="modal" href="#modalMessages">
											<span class="icon-trash icon-white"></span>
										</a>
								</div>
								<input type="hidden" name="tipo" value="T"/>
								<input type="hidden" name="id" value="1"/>
							</li>
									</c:otherwise>
								</c:choose>
					</c:forEach>
				</c:if>
			</ul>
			
			<div class="form-actions">
				<a
				href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo3"
				title="<fmt:message key="testes.passo2.salvarordem" />"
				class="btn btn-primary pull-right"> <fmt:message
					key="testes.passo2.salvarordem" /> </a>
			</div>
		</fieldset>
	</div>
</div>
<script>
	(function($){
		var $sortable = $(".sortable").disableSelection().sortable();
		function ElementosTeste(id, tipo){
			this.id = id;
			this.tipo = tipo;
		}
		var arrayElementos = new Array();
	})(jQuery);
</script>
</body>