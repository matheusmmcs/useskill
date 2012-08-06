<div class="span7 offset3 center">
	<c:if test="${not empty errors}">
		<div class="alert alert-error">
			<c:forEach items="${errors}" var="error">
            	${error.message}<br />
			</c:forEach>
		</div>
	</c:if>

	<form action="${pageContext.request.contextPath}/usuarios"
		method="post" id="newUserForm" class="form-horizontal form-layout">
		<c:if test="${not empty usuario.id}">
			<input type="hidden" name="usuario.id" value="${usuario.id}" />
			<input type="hidden" name="_method" value="put" />
		</c:if>
		<fieldset>
			<legend>
				<span><fmt:message key="usuario.cadastrodeusuario" /> </span>
				<hr />
			</legend>
			<div class="control-group-medium">
				<label class="control-label" for="input01"><fmt:message
						key="nome" />*</label>
				<div class="controls">
					<input type="text" name="usuario.nome" value="${usuario.nome}"
						class="span5" id="nome" />
				</div>
			</div>
			<div class="control-group-medium">
				<label class="control-label" for="input01"><fmt:message
						key="email" />*</label>
				<div class="controls">
					<input type="text" name="usuario.email" value="${usuario.email}"
						class="span5" />
				</div>
			</div>
			<div class="control-group-medium">
				<label class="control-label" for="input01"><fmt:message
						key="senha" />*</label>
				<div class="controls">
					<input type="password" name="usuario.senha" id="usuario.senha"
						class="span5" />
				</div>
			</div>
			<div class="control-group-medium">
				<label class="control-label" for="input01"><fmt:message
						key="confirmasenha" />*</label>
				<div class="controls">
					<input type="password" name="confirmaSenha" value="" class="span5" />
				</div>
			</div>
			<div class="control-group-medium">
				<label class="control-label" for="input01"><fmt:message
						key="telefone" />
				</label>
				<div class="controls">
					<input type="text" name="usuario.telefones[0]"
						value="${usuario.telefones[0]}" class="span5" />
				</div>
			</div>

			<div class="form-actions">
				<input type="submit" value="<fmt:message key="usuario.cadastro"/>"
					name="enviar" title="<fmt:message key="usuario.cadastro"/>"
					class="btn btn-primary" style="float: right;" />
			</div>

		</fieldset>
	</form>
</div>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#newUserForm").validate({
            rules : {
                "usuario.nome" : {
                    required : true
                },
                "usuario.email" : {
                    required : true,
                    email : true
                },
                "usuario.senha" : {
                    required : true,
                    minLength : 6
                },
                "confirmaSenha" : {
                    required : true,
                    minLength : 6
                }
            }
        });
	});
})(jQuery);
</script>