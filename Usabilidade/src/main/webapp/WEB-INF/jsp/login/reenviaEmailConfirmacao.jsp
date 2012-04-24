<fmt:message key="reenviar.email.confirmacao"/>
<form id="form_reenviar-confirmacao" action="${pageContext.request.contextPath}/usuario/reenviar/email" method="post">

    <label for="email">
        <fmt:message key="email"/>
    </label>
        <input type="hidden" name="email" value="${email}"/>
        <input type="submit" value="<fmt:message key="reenviar.email"/>"/>
</form>
<script type="text/javascript">
    $("#form_reenviar-confirmacao").validate({
        rules:{
            email:{
                requerid:true,
                email:true
            }
        }
        
    });
</script> 