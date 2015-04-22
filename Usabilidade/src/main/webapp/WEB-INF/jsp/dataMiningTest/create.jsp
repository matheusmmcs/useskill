<body> 
	<div class="container-padding-left">
	
		<ul class="breadcrumb">
			<li>
				<a href="${pageContext.request.contextPath}/"> 
					<fmt:message key="inicio" />
				</a>
				<span class="divider">/</span>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/datamining/testes/"> 
					<fmt:message key="datamining" /> - <fmt:message key="datamining.testes.list" />
				</a>
				<span class="divider">/</span>
			</li>
			<c:if test="${test.id != null}">
				<li>
					<a href="${pageContext.request.contextPath}/datamining/testes/${test.id}"> 
						<fmt:message key="datamining.testes.details" />
					</a>
					<span class="divider">/</span>
				</li>
			</c:if>
			
			<li class="active">${title}</li>
		</ul>
	    
	    <div class="span10 center">
			<c:if test="${not empty errors}">
				<div class="alert alert-error">
					<c:forEach items="${errors}" var="error">
		            	${error.message}<br />
					</c:forEach>
				</div>
			</c:if>
		
			<form action="${pageContext.request.contextPath}/datamining/testes/salvar" method="post" class="datamining-form form-layout">
				<fieldset>
					<legend>
						<span>${title}</span>
						<hr/>
					</legend>
					
					<input type="hidden" name="test.id" value="${test.id}" />
					
					<div class="control-group">
		            	<label class="control-label" for="testTitle"><fmt:message key="datamining.testes.title" />*</label>
		            	<div class="controls">
		                	<input type="text" value="${test.title}" name="test.title"  id="testTitle" class="input"/> 
		            	</div>
		        	</div>
		        	
		        	<div class="control-group">
		            	<label class="control-label" for="testClientAbbreviation"><fmt:message key="datamining.testes.abbrev" />*</label>
		            	<div class="controls">
		                	<input type="text" value="${test.clientAbbreviation}" name="test.clientAbbreviation"  id="testClientAbbreviation" class="input"/> 
		            	</div>
		        	</div>
		        	
		        	<div class="control-group">
		            	<label class="control-label" for="testUrlSystem"><fmt:message key="datamining.testes.url" />*</label>
		            	<div class="controls">
		                	<input type="text" value="${test.urlSystem}" name="test.urlSystem"  id="testUrlSystem" class="input"/> 
		            	</div>
		        	</div>
		
					<hr/>
					
		        	<div class="form-actions">
		            	<input type="submit" value="${title}" 
		            		name="criarTarefa" title="${title}" 
		            		class="btn btn-primary pull-right submit"/>
		            	<a href="#" onclick="window.history.go(-1); return false;" class="btn pull-right"><fmt:message key="datamining.cancel" /></a>
		        	</div>
				</fieldset>
			</form>
		</div>
	
    </div>
</body>

