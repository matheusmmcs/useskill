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
		<li>
        	<a href="${pageContext.request.contextPath}/usuario">
        		<fmt:message key="testes.meus" />
        	</a> 
        	<span class="divider">/</span>
    	</li>
    	<li class="active"><fmt:message key="testes.criar"/></li>
	</ul>

	<form action="${pageContext.request.contextPath}/teste/salvar" method="post"
		class="form-horizontal form-layout">
		<c:if test="${not empty usuario.id}">
        	<input type="hidden" name="usuario.id" value="${usuario.id}" />
        	<input type="hidden" name="_method" value="put" />
    	</c:if>
		<fieldset>
			<legend>
				<span><fmt:message key="testes.criar"/></span>
				<hr/>
			</legend>
			<div class="control-group">
            	<label class="control-label" for="input01"><fmt:message key="testes.titulo"/>*</label>
            	<div class="controls">
                	<input type="text" name="titulo" value="${testeView.teste.titulo}" id="teste_titulo" class="span6"/> 
            	</div>
        	</div>

			<div class="form-actions">
				<input type="submit" value="<fmt:message key="testes.criar" />" 
				name="criarTeste" title="<fmt:message key="testes.criar" />" 
				class="btn btn-primary" style="float: right; margin-right: 60px"/>
			</div>
		</fieldset>
	</form>
</div>
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