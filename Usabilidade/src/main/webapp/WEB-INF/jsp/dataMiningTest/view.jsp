<body> 
	<div class="container-padding-left">
	
		<c:if test="${sucesso != null}">
			<div class="alert alert-success">
	            ${sucesso}<br />
			</div>
		</c:if>
		
		<c:if test="${not empty errors}">
			<div class="alert alert-error">
				<c:forEach items="${errors}" var="error">
	            	${error.message}<br />
				</c:forEach>
			</div>
		</c:if>
	
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
			<li class="active"><fmt:message key="datamining.testes.details" /></li>
		</ul>
	    
	    <div class="row show-grid">
		    <div class="span6 border-right">
		    	<h2>
	            	<fmt:message key="datamining.testes.details" />
	            </h2>
	            
	            <div>
		            <dl>
		            	<dt><fmt:message key="datamining.testes.title" />:</dt>
		            	<dd>${test.title}</dd>
		            </dl>
		            <dl>
		            	<dt><fmt:message key="datamining.testes.abbrev" />:</dt>
		            	<dd>${test.clientAbbreviation}</dd>
		            </dl>
		            <dl>
		            	<dt><fmt:message key="datamining.testes.url" />:</dt>
		            	<dd>${test.urlSystem}</dd>
		            </dl>
		            <div class="actions-bottom">
<%-- 		            	<a class="btn btn-danger pull-right" href="${pageContext.request.contextPath}/datamining/testes/excluir/${test.id}" title="<fmt:message key="datamining.delete" />"><span class="icon-white icon-trash"></span> <fmt:message key="datamining.delete" /></a> --%>
	        			<a class="btn pull-right" href="${pageContext.request.contextPath}/datamining/testes/editar/${test.id}" title="<fmt:message key="datamining.edit" />"><span class="icon-edit"></span> <fmt:message key="datamining.edit" /></a>
	        			<div class="clear-both"></div>
		            </div>
		            
	            </div>
		    </div>
	        <div class="span6">
	            <h2>
	            	<fmt:message key="datamining.testes.tasks" />	            
		            <a class="btn btn-primary pull-right" href="${pageContext.request.contextPath}/datamining/testes/${teste.id}/tarefas/criar"><fmt:message key="datamining.tasks.new" /></a> 
	            </h2>
	            <table class="table table-striped table-bordered table-condensed datamining-table" style="background-color: white">
	                <thead>
	                    <tr>
	                        <th width="80%"><fmt:message key="datamining.tasks.title" /></th>
	                        <th width="20%"><fmt:message key="datamining.details" /></th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <c:forEach items="${testesList}" var="teste">
	                        <tr>
	                            <td class="td-link"><a href="${pageContext.request.contextPath}/datamining/testes/${teste.id}">${teste.title}</a></td>
	                            <td class="text-center"><a class="btn btn-primary" href="${pageContext.request.contextPath}/datamining/testes/${teste.id}"><span class="icon-white icon-folder-open"></span> <fmt:message key="datamining.details" /></a></td>
	                        </tr>
	                    </c:forEach>
	                </tbody>
	            </table>
	        </div>
	    </div>
    </div>
</body>