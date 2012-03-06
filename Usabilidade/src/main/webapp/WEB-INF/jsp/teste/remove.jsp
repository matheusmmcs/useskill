<c:if test="${not empty errors}">
    <c:forEach items="${errors}" var="error">
        ${error.message}<br />
    </c:forEach>
</c:if>

<form action="${pageContext.request.contextPath}/teste/removed" method="post" id="form_delete">
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