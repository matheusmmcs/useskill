<%@include file="../leftmenus/default.jsp"%>

<div class="span9 container-right">
	<c:if test="${not empty errors}">
		<c:forEach items="${errors}" var="error">
        ${error.message}<br />
		</c:forEach>
	</c:if>

	<form id="editUsuario_Form"
		action="${pageContext.request.contextPath}/usuarios" method="post"
		class="form-horizontal form-layout">
		<c:if test="${not empty usuario.id}">
			<input type="hidden" name="usuario.id" value="${usuario.id}" />
			<input type="hidden" name="_method" value="put" />
		</c:if>
		<fieldset>
			<legend>
				<span><fmt:message key="usuario.editardados" /></span>
				<hr/>
			</legend>
			<div class="control-group">
				<label class="control-label" for="input01"><fmt:message
						key="nome" />*</label>
				<div class="controls">
					<input type="text" name="usuario.nome" value="${usuario.nome}"
						class="span6" id="nome" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="input01"><fmt:message
						key="email" />*</label>
				<div class="controls">
					<input type="text" name="usuario.email" value="${usuario.email}"
						class="span6" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="input01"><fmt:message
						key="telefone" /> </label>
				<div class="controls">
					<input type="text" name="usuario.telefones[0]"
						value="${usuario.telefones[0]}" class="span6" />
				</div>
			</div>

			<div class="form-actions">
				<input type="submit" value="<fmt:message key="editar"/>"
					name="enviar" title="Enviar" class="btn btn-primary"
					style="float: right; margin-right: 60px" />
			</div>
		</fieldset>
	</form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/jquery.maskedinput-1.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/jquery.validate.js"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#editUsuario_Form").validate({
			rules : {
				"usuario.nome" : {
					required : true
				},
				"usuario.senha" : {
					required : true,
					minLength : 6
				}
			}
		});
	});
})(jQuery);
</script>