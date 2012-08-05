<%@include file="../leftmenus/default.jsp"%>

<div class="span9 container-right">
	<c:if test="${not empty errors}">
		<c:forEach items="${errors}" var="error">
        ${error.message}<br />
		</c:forEach>
	</c:if>

	<div class="form-horizontal form-layout">

			<form class="form-horizontal"
				action="${pageContext.request.contextPath}/teste/desconvidar/usuario"
				method="post">
				<table class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th style="width: 30px"></th>
							<th><fmt:message key="table.nome" /></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${convidados}" var="convidado">
								<tr>
									<td><input type="checkbox" name="idUsuarios[]"
										value="${convidado.usuario.id }"></td>
									<td>${convidado.usuario.nome}</td>
									<td>${convidado.tipoConvidado}</td>
								</tr>
							</c:forEach>
					</tbody>
				</table>
				<input type="hidden" name=idTeste value=${testeView.teste.id }>
				<input type="submit"
					value="<fmt:message key="testes.passo3.usuariosremove"/>"
					class="btn btn-danger" style="float: right; margin-right: 10px" />
			</form>
		</fieldset>
			<jsp:include page="../paginator.jsp" flush="true">
			<jsp:param name="link" value="${pageContext.request.contextPath}/teste/${testeView.teste.id}/usuarios/convidados/pag/"/>
		</jsp:include>
	</div>
</div>
	
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