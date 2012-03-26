<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/contato/formee.js"></script>
<form class="formee" id="teste_passo1" action="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id }/editar/passo2" method="post">
    <fieldset style="width: 600px;">
        <legend>Passo 1</legend>
        <div class="grid-12-12">
            <label><fmt:message key="teste.titulo"/>: <em class="formee-req">*</em></label>
            <input type="text" name="titulo" value="${usuarioLogado.teste.titulo}" id="teste_titulo" /> 
        </div>
        <div class="grid-12-12">
            <label><fmt:message key="teste.publico"/>: <em class="formee-req">*</em></label>
            <input type="text" name="tituloPublico" value="${usuarioLogado.teste.tituloPublico}" id="teste_tituloPublico" />
        </div>
        <div class="grid-12-12">
            <label><fmt:message key="teste.textoIndroducao"/>: <em class="formee-req">*</em></label>
            <textarea name="textoIndroducao" value="${usuarioLogado.teste.textoIndroducao}" id="teste_textoIndroducao" cols="8" rows="5">${usuarioLogado.teste.textoIndroducao}</textarea>
        </div>
        <div class="grid-8-12">

        </div>
        <div class="grid-4-12">
            <input type="submit" value="<fmt:message key="salvar.continuar" />" name="salvar" title="Próximo passo" class="right"/>
        </div>
    </fieldset>
</form>  


<script type="text/javascript">
    $("teste_passo1").validate({
        rules:{
            "titulo":{requerid:true},
            "tituloPublico":{requerid:true},
            "textoIntroducao":{requerid:true}
        }
    });
</script>