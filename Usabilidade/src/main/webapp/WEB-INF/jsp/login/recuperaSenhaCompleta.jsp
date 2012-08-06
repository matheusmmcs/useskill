<div class="span7 offset3 center">
	<c:if test="${not empty errors}">
    	<div class="alert alert-error">
        	<c:forEach items="${errors}" var="error">
            	${error.message}<br />
        	</c:forEach>
    	</div>
	</c:if>

	<div class="form-horizontal form-layout">
		<fieldset>
			<legend>
				<span><fmt:message key="usuario.senha.redefinida"/>
				</span>
				<hr />
			</legend>
			<div class="form-actions">
            	<a class="btn btn-primary btn-large" style="width: 200px; height: 50px; margin-left: 230px;" href="${pageContext.request.contextPath}/login">
    				<fmt:message key="login"/>
    			</a>
        	</div>
		</fieldset>
	</div>
</div>