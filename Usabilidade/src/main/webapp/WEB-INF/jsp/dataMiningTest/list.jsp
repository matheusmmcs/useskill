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
			<li class="active"><fmt:message key="datamining" /> - <fmt:message key="datamining.testes.list" /></li>
		</ul>
	    
	    <div class="row show-grid">
	        <div class="span12">
	            <h2>
	            	<fmt:message key="datamining.testes.list" />	            
		            <a class="btn btn-primary pull-right" href="${pageContext.request.contextPath}/datamining/testes/criar"><fmt:message key="datamining.testes.new" /></a> 
	            </h2>
	            
	        </div>
	        <div class="span12">
	            <table class="table table-striped table-bordered table-condensed datamining-table" style="background-color: white">
	                <thead>
	                    <tr>
	                        <th width="55%"><fmt:message key="datamining.testes.title" /></th>
	                        <th width="30%"><fmt:message key="datamining.testes.abbrev" /></th>
	                        <th width="15%"><fmt:message key="datamining.details" /></th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <c:forEach items="${testesList}" var="teste">
	                        <tr>
	                            <td class="td-link"><a href="${pageContext.request.contextPath}/datamining/testes/${teste.id}">${teste.title}</a></td>
	                            <td>${teste.clientAbbreviation}</td>
	                            <td class="text-center"><a class="btn btn-primary" href="${pageContext.request.contextPath}/datamining/testes/${teste.id}"><span class="icon-white icon-folder-open"></span> <fmt:message key="datamining.details" /></a></td>
	                        </tr>
	                    </c:forEach>
	                </tbody>
	            </table>
	        </div>
	    </div>
    </div>
</body>