<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>

    <body>
        <c:forEach var="error" items="${errors}">
            ${error.message}<br />
        </c:forEach>

        <script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/contato/formee.js"></script>
        <form class="formee" id="loginForm" action="${pageContext.request.contextPath}/conta" method="post">
            <c:if test="${not empty usuario.id}">
                <input type="hidden" name="usuario.id" value="${usuario.id}" />
                <input type="hidden" name="_method" value="put" />
            </c:if>
            <fieldset style="width: 600px;">
                <legend>Acessar Conta</legend>
                <div class="grid-12-12">
                    <label><fmt:message key="email" />: <em class="formee-req">*</em></label>
                    <input type="text" name="email" class="required" id="email" value="${email}"/>  
                </div>
                <div class="grid-7-12">
                    <label><fmt:message key="senha" />: <em class="formee-req">*</em></label>
                    <input type="password" name="senha" id="senha"/>    
                </div>

                    <div class="grid-5-12" style="margin-top: 25px;">
                    <input type="submit" value="<fmt:message key="entrar"/>" name="enviar" title="Enviar" class="right"/>
                    <a class="right" href="${pageContext.request.contextPath}/usuario/recupera-senha" style="margin: 17px 15px 0px 0px;"><fmt:message key="esqueceu.senha"/></a>
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