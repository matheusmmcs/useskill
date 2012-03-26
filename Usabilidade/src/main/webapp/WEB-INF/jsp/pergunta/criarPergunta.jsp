<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/contato/formee.js"></script>
<form class="formee" action="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id}/editar/passo2/salvar/pergunta" method="POST">
    <fieldset style="width: 600px;">
        <legend>Nova Pergunta</legend>
        <c:if test="${not empty pergunta.id}">
            <input type="hidden" name="pergunta.id" value="${pergunta.id}" />
            <input type="hidden" name="_method" value="put"/>
        </c:if>
        <div class="grid-12-12">
            <label><fmt:message key="pergunta.titulo" />: <em class="formee-req">*</em></label>
            <input type="text" name="pergunta.titulo" value="${pergunta.titulo}" id="titulo" /> 
        </div>
        <div class="grid-12-12">
            <label><fmt:message key="pergunta.texto" />: <em class="formee-req">*</em></label>
            <textarea name="pergunta.texto" id="texto">${pergunta.texto}</textarea>
        </div>
        <div class="grid-12-12">
            <input type="radio" name="pergunta.tipoRespostaAlternativa" value="true" />
    <input type="radio" name="pergunta.tipoRespostaAlternativa" value="false"/>
        </div>
        <div class="grid-8-12">

        </div>
        <div class="grid-4-12">
            <input type="submit" name="pergunta.salvar" value="<fmt:message key="pergunta.salvar"/>" title="Criar Pergunta" class="right"/>
        </div>
    </fieldset>
</form> 