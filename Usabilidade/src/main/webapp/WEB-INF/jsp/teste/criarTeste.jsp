<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/contato/formee.js"></script>
<form class="formee" id="teste_Form" action="${pageContext.request.contextPath}/teste/salvar" method="post">
    <c:if test="${not empty usuario.id}">
        <input type="hidden" name="usuario.id" value="${usuario.id}" />
        <input type="hidden" name="_method" value="put" />
    </c:if>
    <fieldset style="width: 600px;">
        <legend>Novo Teste</legend>
        <div class="grid-9-12">
            <label><fmt:message key="teste.titulo"/>: <em class="formee-req">*</em></label>
            <input type="text" name="titulo" value="${usuarioLogado.teste.titulo}" id="teste_titulo" /> 
        </div>
        <div class="grid-3-12">
            <input type="submit" value="<fmt:message key="botao.criar.teste" />" name="criar teste" title="Criar Teste" class="right" style="margin-top: 28px;"/>
        </div>
    </fieldset>
</form>  