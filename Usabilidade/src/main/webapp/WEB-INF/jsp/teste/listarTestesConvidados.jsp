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
		<li class="active"><fmt:message key="testes.convites" />
		</li>
	</ul>
	
	<div class="btn-toolbar">
		<div class="btn-group pull-right">
			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/teste/criar"> <fmt:message
					key="testes.criar" /> </a>
		</div>
	
		<div class="btn-group pull-left">		
			<a class="btn"
				href="${pageContext.request.contextPath}/usuario">
				<fmt:message key="testes.mini.construcao"/>
			</a>
			<a class="btn"
				href="${pageContext.request.contextPath}/testes/liberados">
				<fmt:message key="testes.mini.liberados"/>
			</a>
			<a class="btn"
				href="${pageContext.request.contextPath}/testes/convidados">
				<fmt:message key="testes.mini.convites"/>
			</a>
		</div>
	</div>

	<!--<a href="${pageContext.request.contextPath}/files/chrome.crx" title="Download Plugin Google Chrome"><img src="${pageContext.request.contextPath}/img/banner.jpg" /></a>-->
	<a href="https://chrome.google.com/webstore/detail/useskill-for-google-chrom/ldobnfnompkepngljogagmhbghmiidgi" target="_blank" title="Download Plugin Google Chrome"><img src="${pageContext.request.contextPath}/img/banner.jpg" /></a>

	<div class="form-horizontal form-layout">
		<p style="line-height: 20px;padding: 0 20px;font-size: 16px;color: #829abe;margin: 10px 0px 5px;">
			<fmt:message key="testeparticipar.mensagemchrome" />
		</p>
		<fieldset>
			<legend>
				<hr />
				<span> <fmt:message key="testes.convites" /> </span>
				<hr />
			</legend>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th><fmt:message key="table.titulo" /></th>
						<th><fmt:message key="testes.criador" /></th>
						<th><fmt:message key="testes.tipoconvidado" /></th>
						<th style="width: 45px"><fmt:message key="table.acoes" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${testesConvidados}" var="convidadoVO">
						<tr>
							<td>${convidadoVO.teste.titulo}</td>
							<td>${convidadoVO.teste.usuarioCriador.nome}</td>
							<td><fmt:message key="testes.tipo.${convidadoVO.tipoConvidado}" />
							</td>
							<td class="centertd">
							<!-- <a class="btn btn-success"
								href="${pageContext.request.contextPath}/teste/participar/${convidadoVO.teste.id}/aceitar/"
								title="<fmt:message key="table.aceitar"/>"> <span
									class="icon-ok icon-white"></span> </a>--> 
								<a title="<fmt:message key="table.recusar"/>"
								class="btn btn-primary btn-modal" 
								data-href="${pageContext.request.contextPath}/teste/participar/negar/testeId/${convidadoVO.teste.id}" 
								data-acao="Remover" 
								data-toggle="modal" 
								href="#modalMessages"
								> <span
									class="icon-remove icon-white"></span> </a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</fieldset>
		<jsp:include page="../paginator.jsp" flush="true">
			<jsp:param name="link" value="${pageContext.request.contextPath}/testes/convidados/pag/"/>
		</jsp:include>
	</div>
</div>