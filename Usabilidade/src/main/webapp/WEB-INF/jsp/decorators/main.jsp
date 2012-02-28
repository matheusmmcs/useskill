<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title><decorator:title default="Vraptor Scaffold"/></title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/menu.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
        <decorator:head/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/application.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easing1.3.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/menu.js"></script>
    </head>
    <body>
        <div id="topo">
            <div id="topo_centro">
                <a id="usabilitool" title="Usabilitool" href="#"></a>
            </div>
            <div id="menu_topo">
                <div id="menu_topo_centro">
                    <img alt="" src="img/select.png" class="imagem_selected"/>
                    <ul>
                        <li>
                            <a href="#" class="menu_selected">Início</a>
                        </li>
                        <li>
                            <a href="#">Início</a>
                        </li>
                        <li>
                            <a href="#">Inícioasdasdasd</a>
                        </li>
                        <li>
                            <a href="#">Início</a>
                        </li>
                        <li>
                            <a href="#">Início</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div id="conteudo">
            <div id="conteudo_interno">
                <decorator:body/>
            </div>
        </div>
    </body>
</html>
