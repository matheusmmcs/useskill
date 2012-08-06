<div class="span6 offset3">
	<form class="form-horizontal form-layout" id="form_reenviar-confirmacao"
		action="${pageContext.request.contextPath}/usuario/reenviar/email"
		method="post">
		<fieldset>
			<legend><fmt:message key="reenviar.email.confirmacao" /></legend>
			<input type="hidden" name="email" value="${email}" /> 
			<div class="form-actions">
				<input type="submit" value="<fmt:message key="reenviar.email"/>" class="btn btn-primary"
					style="float: right; width: 200px;"/>
			</div>
		</fieldset>
	</form>
</div>

<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#form_reenviar-confirmacao").validate({
			rules : {
				email : {
					requerid : true,
					email : true
				}
			}
		});
	});
})(jQuery);
</script>