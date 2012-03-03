
<form id="recuperar_senha" method="post" action="${pageContext.request.contextPath}/usuario/recupera-senha-completa">
    <label for="email"> <fmt:message key="email">
        </fmt:message>
        <input type="text" name="email"/>
        <input type="submit" value=<fmt:message key="nova.senha" /> />
</form>
<script type="text/javascript">
    $("#recuperar_senha").validate({rules:{
            "email":{ required:true,
                email:true}
        }});
</script>