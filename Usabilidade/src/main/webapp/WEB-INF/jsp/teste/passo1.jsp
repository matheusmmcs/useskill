<ul class="breadcrumb">
    <li>
        <a href="${pageContext.request.contextPath}/usuario">Início</a> <span class="divider">/</span>
    </li>
    <li class="active">Editar Teste - Passo 1</li>
</ul>

<c:if test="${not empty errors}">
    <div class="alert alert-error">
        <c:forEach items="${errors}" var="error">
            ${error.message}<br />
        </c:forEach>
    </div>
</c:if>

<div class="span10 offset1">
    <ul class="nav nav-tabs" style="margin: 0 auto; width: 97%">
        <li class="active"><a href="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id }/editar/passo1">Passo1</a></li>
        <li><a href="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id }/editar/passo2">Passo2</a></li>
        <li><a href="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id }/editar/passo3">Passo3</a></li>
        <li><a href="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id }/editar/passo4">Passo4</a></li>
    </ul>
    <form class="form-horizontal form-layout" id="teste_passo1" action="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id }/editar/passo2" method="post">
        <c:if test="${not empty usuario.id}">
            <input type="hidden" name="usuario.id" value="${usuario.id}" />
            <input type="hidden" name="_method" value="put" />
        </c:if>
        <fieldset>
            <legend>Passo 1</legend>
            <div class="control-group">
                <label class="control-label" style="margin-left: 100px;" for="input01"><fmt:message key="teste.titulo"/>*</label>
                <div class="controls">
                    <input type="text" name="titulo" value="${usuarioLogado.teste.titulo}" id="teste_titulo" class="input-xlarge" style="width: 400px; margin-left: 30px"/> 
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" style="margin-left: 100px;" for="input01"><fmt:message key="teste.publico"/>*</label>
                <div class="controls">
                    <input type="text" name="tituloPublico" value="${usuarioLogado.teste.tituloPublico}" id="teste_tituloPublico" class="input-xlarge" style="width: 400px; margin-left: 30px"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" style="margin-left: 100px;" for="input01"><fmt:message key="teste.textoIndroducao"/>*</label>
                <div class="controls">
                    <textarea name="textoIndroducao" value="${usuarioLogado.teste.textoIndroducao}" id="teste_textoIndroducao" cols="8" rows="5" class="input-xlarge" style="width: 400px; margin-left: 30px">${usuarioLogado.teste.textoIndroducao}</textarea>
                </div>
            </div>
            <div class="form-actions">
                <input type="submit" value="<fmt:message key="salvar.continuar" />" name="salvar" title="Próximo passo" class="btn btn-primary" style="float: right; width: 120px"/>
            </div>
        </fieldset>
    </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/bootstrap-tab.js"></script>

<script type="text/javascript">
    $("teste_passo1").validate({
        rules:{
            "titulo":{requerid:true},
            "tituloPublico":{requerid:true},
            "textoIntroducao":{requerid:true}
        }
    });
</script>