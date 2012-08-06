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
				<span><fmt:message key="teste.concluido" />
				</span>
				<hr />
			</legend>
		</fieldset>
	</div>
</div>