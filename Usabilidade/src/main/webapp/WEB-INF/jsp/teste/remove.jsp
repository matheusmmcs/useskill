<form action="${pageContext.request.contextPath}/teste/remove" method="post" id="form_delete">
    <input type="hidden" name="_method" value="delete"/>
    <input type="password" name="senha" value="${senha}" id="senha" />
    <input type="submit" value="Confirmar"/>
</form>
<script type="text/javascript">
    $("#form_delete").validate({
        
        rules:{"senha":{
                required:true
            }}}
);
</script>