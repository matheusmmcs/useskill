<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/wysiwig/bootstrap/libs/css/prettify.css"></link>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/wysiwig/bootstrap/src/bootstrap-wysihtml5.css"></link>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/wysiwig/bootstrap/useskill-editor.css"></link>
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
					key="testes.meus" /> </a> <span class="divider">/</span></li>
		<li>
			<a
			href="${pageContext.request.contextPath}/teste/${testeView.teste.id}/editar/passo2">
				<fmt:message key="testes.editar" />
				[<fmt:message key="testes.passo2" />] 
			</a> 
			<span class="divider">/</span>
		</li>

		<li class="active"><fmt:message key="tarefa.editar" />
		</li>
	</ul>

	<form class="form-horizontal form-layout" id="form_pergunta"
		method="post"
		action="${pageContext.request.contextPath}/teste/tarefa/atualizar">
		<fieldset>
			<legend>
				<span><fmt:message key="tarefa.editar" />
				</span>
				<p>
					${testeView.teste.titulo }
				</p>
				<hr />
			</legend>
			
			<input type="hidden" name="tarefa.id" value="${tarefa.id}" />
			
			<div class="control-group">
				<label class="control-label" for="input01"><fmt:message
						key="tarefa.titulo" />*</label>
				<div class="controls">
					<input type="text" value="${tarefa.nome }" name="tarefa.nome"
						class="span6" />
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="input01"><fmt:message
						key="tarefa.urlInicial" />*</label>
				<div class="controls">
					<input type="text" value="${tarefa.urlInicial }"
						name="tarefa.urlInicial" class="span6" />
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="input01"><fmt:message
						key="tarefa.roteito" />*</label>
				<div class="controls">
					<textarea rows="10" cols="" name="tarefa.roteiro"
						class="span6 textarea"> ${tarefa.roteiro } </textarea>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="input01">
					<fmt:message key="tarefa.valores.dinamicos" />*
				</label>
				<div class="controls">
	                <input id="variavel" class="span2" type="text" style="margin-right: 20px;" placeholder="<fmt:message key="tarefa.variavel" />"/>
	                :
	                <input id="valor" class="span3"  type="text" style="margin-left: 20px;" placeholder="<fmt:message key="tarefa.valores.separados" />"/>	                
	                <a id="addVariavel" class="btn" style="margin-left: 10px;" href="#">
						<i class="icon-plus"></i> 
					</a> 
        	    </div>
			</div>
			
			<div class="control-group">
				<label class="control-label"></label>
				<div class="controls">
					<div class="span6" style="margin-left: 0;">
						<table class="table table-bordered" id="tabela_valores_dinamicos" style="padding-top: 0;">
							<thead>
								<tr>
									<th width="33%"><fmt:message key="tarefa.variavel" /></th>
									<th width="60%"><fmt:message key="tarefa.valores" /></th>
									<th width="7%"><fmt:message key="tarefa.acoes" /></th>
								</tr>
							</thead>
							<tbody count-tr="${fn:length(tarefa.variaveisRoteiro)}">
								
								<c:forEach items="${tarefa.variaveisRoteiro}" var="v">
									<tr id="${fn:toUpperCase(v.variavel)}">
										<td>${v.variavel}</td>
										<td>
											<c:forEach items="${v.valores}" var="valor">
												${valor.valor}<br/>
											</c:forEach>
										</td>
										<td>
											<a class="btn btn-danger valor-remove" href="#">
												<i class="icon-remove icon-white"></i>
											</a> 
											<input type="hidden" name="variaveis[]" value="${v.variavel}:<c:forEach items="${v.valores}" var="valor">${valor.valor};</c:forEach>" />
										</td>
									</tr>
								</c:forEach>
								
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			
			<input type="hidden" name=idTeste value=${testeView.teste.id }>

			<div class="form-actions">
            	<input type="submit" value="<fmt:message key="tarefa.editar" />" 
            	name="editarTarefa" title="<fmt:message key="tarefa.editar" />" 
            	class="btn btn-primary" style="float: right; margin-right: 60px"/>
        	</div>
		</fieldset>
	</form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/variaveis_roteiro.js"></script>

<script src="${pageContext.request.contextPath}/plugin/wysiwig/bootstrap/libs/js/wysihtml5-0.3.0.js"></script>
<script src="${pageContext.request.contextPath}/plugin/wysiwig/bootstrap/libs/js/prettify.js"></script>
<script src="${pageContext.request.contextPath}/plugin/wysiwig/bootstrap/src/bootstrap-wysihtml5.js"></script>
<script src="${pageContext.request.contextPath}/plugin/wysiwig/bootstrap/src/locales/bootstrap-wysihtml5.pt-BR.js"></script>

<script>
	var $texts = $('.textarea');
	$texts.wysihtml5({
		"font-styles": true,
		"emphasis": true,
		"lists": true,
		"html": true,
		"link": true,
		"image": true,
		"color": true,
		stylesheets: ["${pageContext.request.contextPath}/plugin/wysiwig/bootstrap/libs/css/wysiwyg-color.css"],
		locale: "pt-BR"
	});
	$texts.siblings(".wysihtml5-toolbar").css("width", $texts.css("width"));
	$(prettyPrint);
</script>

</body>