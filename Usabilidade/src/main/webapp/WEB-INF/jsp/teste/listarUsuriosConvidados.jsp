<%@include file="../leftmenus/default.jsp"%>

<div class="span9 container-right">
	<c:if test="${not empty errors}">
		<c:forEach items="${errors}" var="error">
        ${error.message}<br />
		</c:forEach>
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
		<li class="active"><fmt:message key="testes.usuarios.convidados" /></li>
	</ul>
	
	<div class="btn-toolbar">
		<div class="btn-group pull-right">
			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/convidar/usuarios">
					<fmt:message key="testes.convidarusuarios" />
				</a>
		</div>
	</div>

	<div class="form-horizontal form-layout">
		<fieldset>
			<legend>
				<span>
					<fmt:message key="testes.usuarios.convidados" />
				</span>
				<p>
					${testeView.teste.titulo }
				</p>
				<hr />
			</legend>
			<form class="form-horizontal"
				action="${pageContext.request.contextPath}/teste/desconvidar/usuario"
				method="post">
				<table class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<!-- <th style="width: 30px"></th> -->
							<th><fmt:message key="table.nome" /></th>
							<th style="width: 70px"><fmt:message key="testes.tipoconvidado" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${convidados}" var="convidado">
								<tr>
									<%-- <td class="centertd"><input type="checkbox" name="idUsuarios[]"
										value="${convidado.usuario.id }"></td> --%>
									<td>${convidado.usuario.nome}</td>
									<td class="centertd"><fmt:message key="testes.tipo.${convidado.tipoConvidado}" /></td>
								</tr>
							</c:forEach>
					</tbody>
				</table>
				<input type="hidden" name=idTeste value=${testeView.teste.id }>
				<%-- <input type="submit"
					value="<fmt:message key="testes.passo3.usuariosremove"/>"
					class="btn btn-danger" style="float: right; margin-right: 10px" /> --%>
			</form>
		</fieldset>
		<jsp:include page="../paginator.jsp" flush="true">
			<jsp:param name="link" value="${pageContext.request.contextPath}/teste/${testeView.teste.id}/usuarios/convidados/pag/"/>
		</jsp:include>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/jquery.validate.js"></script>
<script>
	(function($){
		$(document).ready(function(){
			var $formListaUsuarios = $('form#listaUsuarios'), $tipoConvidado = $('input#tipoConvidado');
			
			$('#inputUser').click(function(e){
				e.preventDefault();
				$tipoConvidado.val(true);
				$formListaUsuarios.submit();
			});
			
			$('#inputTester').click(function(e){
				e.preventDefault();
				$tipoConvidado.val(false);
				$formListaUsuarios.submit();
			});
		});
	})(jQuery);
</script>