<%@include file="../leftmenus/default.jsp"%>

<div class="span9 container-right">
	<c:if test="${not empty errors}">
		<c:forEach items="${errors}" var="error">
        ${error.message}<br />
		</c:forEach>
	</c:if>
	
	<ul class="breadcrumb">
		<li>
        	<a href="${pageContext.request.contextPath}/usuario">
        		<fmt:message key="testes.meus" />
        	</a> 
        	<span class="divider">/</span>
    	</li>
    	<li class="active"><fmt:message key="testes.editar" />
			[<fmt:message key="testes.passo1" />]
		</li>
	</ul>

	<ul class="nav nav-tabs" style="margin: 0 auto; width: 97%">
        <li class="active"><a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo1"><fmt:message key="testes.passo1" /></a></li>
        <li><a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo2"><fmt:message key="testes.passo2" /></a></li>
        <li><a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo3"><fmt:message key="testes.passo3" /></a></li>
        <li><a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo4"><fmt:message key="testes.passo4" /></a></li>
    </ul>
	<form id="teste_passo1" action="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo2" method="post"
		class="form-horizontal form-layout">
		<c:if test="${not empty usuario.id}">
            <input type="hidden" name="usuario.id" value="${usuario.id}" />
            <input type="hidden" name="_method" value="put" />
        </c:if>
		<fieldset>
			<legend>
				<span><fmt:message key="testes.passo1" /></span>
				<hr/>
			</legend>
			
			<div class="control-group">
                <label class="control-label" for="input01"><fmt:message key="testes.passo1.titulo"/>*</label>
                <div class="controls">
                    <input type="text" name="titulo" value="${testeView.teste.titulo}" id="teste_titulo" class="span6"/> 
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="input01"><fmt:message key="testes.passo1.titulopublico"/>*</label>
                <div class="controls">
                    <input type="text" name="tituloPublico" value="${testeView.teste.tituloPublico}" id="teste_tituloPublico" class="span6"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="input01"><fmt:message key="testes.passo1.introducao"/>*</label>
                <div class="controls">
                    <textarea name="textoIndroducao" id="teste_textoIndroducao" cols="8" rows="5" class="span6">${testeView.teste.textoIndroducao}</textarea>
                </div>
            </div>

			<div class="form-actions">
				<input type="submit" value="<fmt:message key="testes.salvarecontinuar" />" 
				name="criarTeste" title="<fmt:message key="testes.proximopasso" />" 
				class="btn btn-primary" style="float: right; margin-right: 60px"/>
			</div>
		</fieldset>
	</form>
</div>