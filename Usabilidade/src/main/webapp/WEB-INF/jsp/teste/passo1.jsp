<form id="teste_passo1" action="${pageContext.request.contextPath}/teste/${usuarioLogado.teste.id }/editar/passo2"
      method="post">
    <label for="titulo">
        <fmt:message key="teste.titulo"/>*:
    </label>
    <input type="text" name="titulo"
           value="${usuarioLogado.teste.titulo}" id="teste_titulo" /> 
    <label for="titulo">
        <fmt:message key="teste.publico"/>*:
    </label> <input
        type="text" name="tituloPublico"
        value="${usuarioLogado.teste.tituloPublico}" id="teste_tituloPublico" />
    <label for="textoIndroducao">
        <fmt:message key="teste.textoIndroducao"/>*:
    </label>
    <textarea name="textoIndroducao"
              value="${usuarioLogado.teste.textoIndroducao}"
              id="teste_textoIndroducao" cols="8" rows="5" >
        ${usuarioLogado.teste.textoIndroducao}

    </textarea>
        <input type="submit" value="
    <fmt:message key="salvar.continuar" />"
    />

</form>
<script type="text/javascript">
    $("teste_passo1").validadte({
        rules:{
            "titulo":{requerid:true},
            "tituloPublico":{requerid:true},
            "textoIntroducao":{requerid:true}
        }
    });
</script>