<div id="topo">
    <div id="topo_centro">
        <a id="usabilitool" title="Usabilitool" href="#"></a>
    </div>
    <div id="topo_direito">
        <a href="${pageContext.request.contextPath}/logout">
           <fmt:message key="logout"/>
    </a>
</div>
<div id="menu_topo">
    <div id="menu_topo_centro">
        <img alt="" src="${pageContext.request.contextPath}/img/select.png" class="imagem_selected"/>
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/usuario"><fmt:message key="inicio"/></a>
            </li>
           <!-- <li>
              <a href="${pageContext.request.contextPath}/usuarios/new">Cadastre-se</a> 
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/login">Acessar Conta</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/usuarios">Usuários</a>
            </li> !-->
        </ul>
    </div>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/menu.js"></script>