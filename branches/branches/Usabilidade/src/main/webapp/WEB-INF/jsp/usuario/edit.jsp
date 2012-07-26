
<c:if test="${not empty errors}">
	<div class="alert alert-error">
		<c:forEach items="${errors}" var="error">
            ${error.message}<br />
		</c:forEach>
	</div>
</c:if>


<form action="${pageContext.request.contextPath}/usuarios" method="post">
	<c:if test="${not empty usuario.id}">
		<input type="hidden" name="usuario.id" value="${usuario.id}" />
		<input type="hidden" name="_method" value="put" />
	</c:if>
	<fieldset></fieldset>
</form>





<c:if test="${not empty errors}">
	<c:forEach items="${errors}" var="error">
        ${error.message}<br />
	</c:forEach>
</c:if>

<form id="editUsuario_Form"
	action="${pageContext.request.contextPath}/usuarios" method="post"
	class="form-horizontal form-layout w600">
	<c:if test="${not empty usuario.id}">
		<input type="hidden" name="usuario.id" value="${usuario.id}" />
		<input type="hidden" name="_method" value="put" />
	</c:if>
	<fieldset style="width: 600px;">
		<legend>
			<fmt:message key="editar.conta" />
		</legend>
		<div class="control-group">
			<label class="control-label" for="input01"><fmt:message
					key="nome" />*</label>
			<div class="controls">
				<input type="text" name="usuario.nome" value="${usuario.nome}"
					class="input-xlarge" id="nome" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="input01"><fmt:message
					key="email" />*</label>
			<div class="controls">
				<input type="text" name="usuario.email" value="${usuario.email}"
					class="input-xlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="input01"><fmt:message
					key="telefone" /></label>
			<div class="controls">
				<input type="text" name="usuario.telefones[0]"
					value="${usuario.telefones[0]}" class="input-xlarge" />
			</div>
		</div>

		<div class="form-actions">
			<input type="submit" value="<fmt:message key="editar"/>"
				name="enviar" title="Enviar" class="btn btn-primary"
				style="float: right" />
		</div>
	</fieldset>
</form>

<script type="text/javascript">
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
</script>