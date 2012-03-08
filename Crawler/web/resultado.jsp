<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.gargoylesoftware.htmlunit.html.HtmlSubmitInput"%>
<%@page import="com.gargoylesoftware.htmlunit.html.HtmlTextInput"%>
<%@page import="com.gargoylesoftware.htmlunit.html.HtmlForm"%>
<%@page import="com.gargoylesoftware.htmlunit.html.HtmlPage"%>
<%@page import="com.gargoylesoftware.htmlunit.WebClient"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Busca Google</title>
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
    </head>
    <body>
        <div id="info">
            <div id="titulo">Informações:</div>
            <br/><span class="1"></span>
            <br/><span class="2"></span>
            <br/>Clicou em: 
            <br/><span class="3"></span>
            <br/><span class="4"></span>
            <input type="button" name="relatorio" id="relatorio" value="Gerar Relatório" class="botao" title="Teste" />
            <input type="button" name="ajaxbutton" id="ajaxbutton" value="AJAX" class="botao" />
        </div>
        
        <%
        String busca = request.getParameter("google");
        WebClient webClient = new WebClient();

        HtmlPage page1 = webClient.getPage("http://www.google.com.br");
        //HtmlForm form = page1.getFormByName("f");
        //HtmlSubmitInput button = form.getInputByName("btnG");
        //HtmlTextInput textInput = form.getInputByName("q");
        //textInput.setValueAttribute(busca + " torres");

        //page1 = button.click();
        out.print(page1.asXml());
         
        %>

        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
        <script type="text/javascript">
           $(this).mousemove(function(e){
                var pagecord = "("+e.pageX+","+e.pageY+")"
           });
           $(this).click(function(e){
                var id = $(e.target).attr("id");
                var classe = $(e.target).attr("class");
                var nome = $(e.target).attr("name");
                var valor = $(e.target).attr("value")
                var pagecord = "("+e.pageX+","+e.pageY+")"

                alert("Atr: "+e.target.tagName+" - ID: "+id+" - Classe: "+classe+" - Name: "+nome+"\nValor: "+valor+"\nPosicao: "+pagecord)
           });
        </script>
    </body>
    
</html>
