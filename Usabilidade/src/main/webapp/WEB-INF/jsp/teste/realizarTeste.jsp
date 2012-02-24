<%@page import="br.ufpi.util.WebCliente"%>
<%@page import="com.gargoylesoftware.htmlunit.html.HtmlPage"%>
<%@page import="com.gargoylesoftware.htmlunit.WebClient"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--<link type="text/css" rel="stylesheet" href="css/style.css"/>
        <link type="text/css" rel="stylesheet" href="css/jquery.contextMenu.css"/>-->
        <title>Protótipo</title>
    </head>
    <body>
        <%
            String url = request.getParameter("url");
            WebCliente webCliente = new WebCliente();
            out.print(webCliente.getPaginaCarregada(url));
        %>
    </body>
</html>