<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>

    <body>
        <c:if test="${not empty errors}">
            <div class="alert alert-error">
                <c:forEach items="${errors}" var="error">
                    ${error.message}<br />
                </c:forEach>
            </div>
        </c:if>


        <form class="form-horizontal form-layout w600" action="${pageContext.request.contextPath}/conta" method="post" id="loginForm">
            <c:if test="${not empty usuario.id}">
                <input type="hidden" name="usuario.id" value="${usuario.id}" />
                <input type="hidden" name="_method" value="put" />
            </c:if>
            <fieldset>
                <legend>Acessar Conta</legend>
                <div class="control-group">
                    <label class="control-label" for="input01"><fmt:message key="email" />*</label>
                    <div class="controls">
                        <input type="text" name="email" id="email" value="${email}" class="input-xlarge"/>  
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="input01"><fmt:message key="senha" />*</label>
                    <div class="controls">
                        <input type="password" name="senha" id="senha" class="input-xlarge"/>    
                    </div>
                </div>
                

                <div class="form-actions">
                    <input type="submit" value="<fmt:message key="entrar"/>" name="enviar" title="Enviar" class="btn btn-primary" style="float: right; width: 120px"/>
                    <a href="${pageContext.request.contextPath}/usuario/recupera-senha" class="btn" style="float: right; margin-right: 10px"><fmt:message key="esqueceu.senha"/></a>
                </div>
            </fieldset>
        </form>

        <script type="text/javascript">
            $("#loginForm").validate({
                rules:{
                    email:{
                        required:true,
                        email:true
                    },
                    senha:{
                        required:true                        
                    }
                },
                errorElement: "div"});
        </script>
    </body>
</html>