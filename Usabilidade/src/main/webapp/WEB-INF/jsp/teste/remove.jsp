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
					key="testes.meus" /> </a> <span class="divider">/</span>
		</li>
    	<li class="active"><fmt:message key="testes.removerteste"/></li>
	</ul>

	<form action="${pageContext.request.contextPath}/teste/removed" method="post" id="form_delete"
		class="form-horizontal form-layout">
		<c:if test="${not empty usuario.id}">
        	<input type="hidden" name="usuario.id" value="${usuario.id}" />
        	<input type="hidden" name="_method" value="put" />
    	</c:if>
    	<input type="hidden" name=idTeste value=${testeView.teste.id }>
    	<input type="hidden" name="_method" value="delete"/>
		<fieldset>
			<legend>
				<span><fmt:message key="testes.removerteste"/></span>
				<hr/>
			</legend>
			<div class="control-group">
            	<label class="control-label" for="input01"><fmt:message key="login.senha"/>*</label>
            	<div class="controls">
                	<input type="password" name="senha" value="${senha}" id="senha" class="span6"/> 
            	</div>
        	</div>

			<div class="form-actions">
				<input type="submit" value="<fmt:message key="testes.remover" />" 
				name="removerTeste" title="<fmt:message key="testes.remover" />" 
				class="btn btn-primary" style="float: right; margin-right: 60px"/>
			</div>
		</fieldset>
	</form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/jquery.validate.js"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#form_delete").validate({ 
	        rules:{
	        	"senha":{
	                required:true
	            }
	    	}
	    });
	});
})(jQuery);
</script>