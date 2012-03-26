<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/contato/formee.js"></script>
<form class="formee" id="form_pergunta" method="post" action="${pageContext.request.contextPath}/teste/salvar/tarefa">
    <fieldset style="width: 600px;">
        <legend>Nova Tarefa</legend>
        <div class="grid-12-12">
            <label><fmt:message key="tarefa.nome" />: <em class="formee-req">*</em></label>
            <input type="text" value="${tarefa.nome }" name="tarefa.nome" /> 
        </div>
        <div class="grid-12-12">
            <label><fmt:message key="tarefa.roteito" />: <em class="formee-req">*</em></label>
            <textarea rows="" cols="" name="tarefa.roteiro"> ${tarefa.roteiro } </textarea>
        </div>
        <div class="grid-12-12">
            <label><fmt:message key="tarefa.urlInicial" />: <em class="formee-req">*</em></label>
            <input type="text" value="${tarefa.urlInicial }" name="tarefa.urlInicial" /> 
        </div>
        <div class="grid-8-12">

        </div>
        <div class="grid-4-12">
            <input type="submit" value="<fmt:message key="tarefa.criar" />" name="criar tarefa" title="Criar Tarefa" class="right"/>
        </div>
    </fieldset>
</form>  

