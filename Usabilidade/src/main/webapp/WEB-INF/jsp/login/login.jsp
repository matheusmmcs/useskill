<div class="span7 offset3 center">
	<c:if test="${not empty errors}">
    	<div class="alert alert-error">
        	<c:forEach items="${errors}" var="error">
            	${error.message}<br />
        	</c:forEach>
    	</div>
	</c:if>

	<form action="${pageContext.request.contextPath}/conta" method="post"
		id="loginForm" class="form-horizontal form-layout">
		<c:if test="${not empty usuario.id}">
			<input type="hidden" name="usuario.id" value="${usuario.id}" />
			<input type="hidden" name="_method" value="put" />
		</c:if>
		<fieldset>
			<legend>
				<span><fmt:message key="usuario.acessarconta" />
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
			<div class="control-group-min">
				<label class="control-label" for="input01"><fmt:message
						key="login.senha" />*</label>
				<div class="controls">
					<input type="password" name="senha" id="senha" class="span5" />
				</div>
			</div>


			<div class="form-actions">
				<input type="submit" value="<fmt:message key="login.entrar"/>"
					name="enviar" title="<fmt:message key="login.entrar"/>"
					class="btn btn-primary" style="float: right; margin-right: 40px" /> <a
					href="${pageContext.request.contextPath}/usuario/recupera-senha"
					class="btn" style="float: right; margin-right: 10px"><fmt:message
						key="login.esqueceusenha" />
				</a>
			</div>
		</fieldset>
	</form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/jquery.validate.js"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#loginForm").validate({
			rules : {
				email : {
					required : true,
					email : true
				},
				senha : {
					required : true
				}
			},
			errorElement : "div"
		});
	});
})(jQuery);
</script>