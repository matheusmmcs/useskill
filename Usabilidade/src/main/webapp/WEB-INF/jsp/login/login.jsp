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
        ${error }
        <form id="loginForm"
              action="${pageContext.request.contextPath}/conta" method="post">

            <fmt:message key="email" />
            * : <input type="text" name="email" class="required"/> <br />
            <fmt:message key="senha" />
            *: <input type="password" name="senha" /><br /> <input type="submit"
                                                                   value=<fmt:message key="entrar" /> />
        </form>
    </body>
</html>