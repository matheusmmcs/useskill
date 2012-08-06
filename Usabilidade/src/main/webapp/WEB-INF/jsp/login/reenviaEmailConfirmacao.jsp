<div class="span7 offset3 center">
	<c:if test="${not empty errors}">
    	<div class="alert alert-error">
        	<c:forEach items="${errors}" var="error">
            	${error.message}<br />
        	</c:forEach>
    	</div>
	</c:if>

	<form class="form-horizontal form-layout"
		action="${pageContext.request.contextPath}/usuario/reenviar/email"
		method="post">
		<fieldset>
			<legend>
				<span><fmt:message key="usuario.reenviar.email" />
				</span>
				<hr />
			</legend>
			
			<input type="hidden" name="email" value="${email}" /> 
			
			<div class="form-actions">
    			<input type="submit" value="<fmt:message key="usuario.reenviar.email"/>" title="<fmt:message key="usuario.reenviar.email"/>" 
    			class="btn btn-primary btn-large" style="width: 250px; height: 50px; margin-left: 130px;"/>
        	</div>
		</fieldset>
	</form>
</div>