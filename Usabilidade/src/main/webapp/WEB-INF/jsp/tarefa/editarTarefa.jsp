<ul class="breadcrumb">
    <li>
        <a href="${pageContext.request.contextPath}/usuario">Início</a> <span class="divider">/</span>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo1">Editar Teste - Passo 1</a> <span class="divider">/</span>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/teste/${testeView.teste.id }/editar/passo2">Passo 2</a> <span class="divider">/</span>
    </li>
    <li class="active">Editar Tarefa</li>
</ul>

<c:if test="${not empty errors}">
    <div class="alert alert-error">
        <c:forEach items="${errors}" var="error">
            ${error.message}<br />
        </c:forEach>
    </div>
</c:if>

<form class="form-horizontal form-layout w600" id="form_pergunta" method="post" action="${pageContext.request.contextPath}/teste/tarefa/atualizar">
    <fieldset>
        <legend>Editar Tarefa</legend>
        <input type="hidden" name="tarefa.id" value="${tarefa.id}" />
        <div class="control-group">
            <label class="control-label" for="input01"><fmt:message key="tarefa.nome" />*</label>
            <div class="controls">
                <input type="text" value="${tarefa.nome }" name="tarefa.nome" class="input-xmlarge"/> 
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="input01"><fmt:message key="tarefa.roteito" />*</label>
            <div class="controls">
                <textarea rows="10" cols="" name="tarefa.roteiro" class="input-xmlarge"> ${tarefa.roteiro } </textarea>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="input01"><fmt:message key="tarefa.urlInicial" />*</label>
            <div class="controls">
                <input type="text" value="${tarefa.urlInicial }" name="tarefa.urlInicial" class="input-xmlarge"/> 
            </div>
        </div>
	<input type="hidden" name=idTeste value=${testeView.teste.id }>

        <div class="form-actions">
            <input type="submit" value="<fmt:message key="tarefa.editar" />" name="criar tarefa" title="Criar Tarefa" class="btn btn-primary" style="float: right"/>
        </div>
    </fieldset>
</form>