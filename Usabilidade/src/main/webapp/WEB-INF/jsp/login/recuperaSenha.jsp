<div class="span7 offset3 center">
	<c:if test="${not empty errors}">
    	<div class="alert alert-error">
        	<c:forEach items="${errors}" var="error">
            	${error.message}<br />
        	</c:forEach>
    	</div>
	</c:if>

	<form method="post" action="${pageContext.request.contextPath}/usuario/recupera-senha-completa"
		id="recuperar_senha" class="form-horizontal form-layout">
		<c:if test="${not empty usuario.id}">
			<input type="hidden" name="usuario.id" value="${usuario.id}" />
			<input type="hidden" name="_method" value="put" />
		</c:if>
		<fieldset>
			<legend>
				<span><fmt:message key="usuario.novasenha" />
				</span>
				<hr />
			</legend>
			<div class="control-group-min">
				<label class="control-label" for="input01"><fmt:message
						key="login.email" />*</label>
				<div class="controls">
					<input type="text" name="email" id="email" value="${email}"
						class="span5" />
				</div>
			</div>


			<div class="form-actions">
				<input type="submit" value="<fmt:message key="usuario.gerarnovasenha"/>"
					name="enviar" title="<fmt:message key="usuario.gerarnovasenha"/>"
					class="btn btn-primary" style="float: right; margin-right: 40px" />
			</div>
		</fieldset>
	</form>
</div>
<script type="text/javascript">
    $("#recuperar_senha").validate({rules:{
            "email":{ required:true,
                email:true}
        }});
</script> 