<%@page import = "projeto.easi.WebCliente"%> 
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>

<%
    String url = request.getParameter("url");
    if(url!=null){
        out.print(WebCliente.loadPage(url));
    }
%>
