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
        <div id="loginDiv">
            <form id="loginForm"
                  action="${pageContext.request.contextPath}/conta" method="post">
                <label for="email">
                    <fmt:message key="email" />
                    * :             
                </label>
                <input type="text" name="email" class="required" id="email" value="${email}"/>             
                <label for="senha">
                    <fmt:message key="senha" />
                    *:             
                </label>
                <input type="password" name="senha" id="senha"/>     
                <input type="submit"
                       value=<fmt:message key="entrar" />
                       />  
            </form>
            <a href="${pageContext.request.contextPath}/usuario/recupera-senha">
                <fmt:message key="esqueceu.senha"/></a>
        </div>

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