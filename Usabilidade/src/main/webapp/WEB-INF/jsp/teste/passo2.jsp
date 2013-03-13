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
			
			<div class="alert fade in alert-success hide">
            	<button type="button" class="close">x</button>
            	Ordem Alterada com Sucesso!
          	</div>
			
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
					<c:forEach items="${elementosTeste}" var="ele">
						<c:set var="eleTipo" value="${ele.tipo.toString()}" />
								<c:choose>
									<c:when test="${eleTipo eq 'T'}">
							<li class="ui-state-blue">
								<span class="ui-azul ui-icon ui-icon-arrowthick-2-n-s"></span>
								<div class="titulo">Tarefa - ${ele.titulo } </div>
								<div class="acoes ">
										<a class="btn" href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${ele.id}/tarefa" title="<fmt:message key="table.editar"/>">
											<span class="icon-pencil"></span>
										</a>
										<a title="<fmt:message key="table.remover"/>" class="btn btn-primary btn-modal" data-href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/tarefa/${ele.id}/apagar" data-acao="Remover" data-toggle="modal" href="#modalMessages">
											<span class="icon-trash icon-white"></span>
										</a>
								</div>
								<input type="hidden" name="tipo" value="T"/>
								<input type="hidden" name="id" value="${ele.id}"/>
							</li>
									</c:when>
									<c:otherwise>
									
							<li class="ui-state-green">
								<span class="ui-green ui-icon ui-icon-arrowthick-2-n-s"></span>
								<div class="titulo">Pergunta - ${ele.titulo }</div>
								<div class="acoes ">
										<a class="btn" href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2/editar/${ele.id}/pergunta" title="<fmt:message key="table.editar"/>">
											<span class="icon-pencil"></span>
										</a>
										<a title="<fmt:message key="table.remover"/>" class="btn btn-primary btn-modal" data-href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/pergunta/${ele.id}/apagar" data-acao="Remover" data-toggle="modal" href="#modalMessages">
											<span class="icon-trash icon-white"></span>
										</a>
								</div>
								<input type="hidden" name="tipo" value="P"/>
								<input type="hidden" name="id" value="${ele.id}"/>
							</li>
									</c:otherwise>
								</c:choose>
					</c:forEach>
				</c:if>
			</ul>
			
			<div class="form-actions">
				<a id="USproxPasso"
				href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo3"
				title="<fmt:message key="testes.proximopasso" />"
				class="btn btn-primary pull-right"> <fmt:message
					key="testes.proximopasso" /> </a>
			</div>
		</fieldset>
	</div>
</div>
<script>
	(function($){
		var $sortable = $(".sortable").disableSelection().sortable({
			update: function(event, ui) {
				salvarOrdem();
			}
		});
		var $li = $sortable.children('li');
		function ElementosTeste(id, tipo){
			this.id = id;
			this.tipo = tipo;
		}
		var arrayElementos = new Array();

		$('#USproxPasso').click(function(e){
			$('#USsaveOrdem').trigger('click');			
		});
		
		function salvarOrdem(){
			arrayElementos = new Array();
			var posLi = $sortable.children('li');
			$.each(posLi, function(key, value) { 
				var elem = new ElementosTeste($(value).find('input[name="id"]').val(),$(value).find('input[name="tipo"]').val())
				arrayElementos.push(elem);
			});
			
			var listel = JSON.stringify(arrayElementos);
			var idt = "${testeView.teste.id}";
			$.ajax({
				url: "${pageContext.request.contextPath}/teste/ordenar",
				cache: false,
				type: "POST",
				async: false,
				data: {idTeste: idt, listaElementos: listel},
				success: function(dados){
					if(dados.string == "sucesso"){
						$(".alert").fadeIn(200);//.delay(1000).fadeOut(200)
					}
				},
				error: function(jqXHR, status, err){
					console.log(jqXHR);
				}
			});
		}
		
		$(".close").click(function(e){
			e.preventDefault();
			$(".alert").fadeOut(200);
		});
	})(jQuery);
</script>
</body>