<div class="span3 container-left">
	<div class="well sidebar-nav">
		<div>
			<ul class="nav nav-list">
				<li class="nav-header">
				<a class="nav-show">
				<fmt:message key="leftmenu.minhaconta"/>
				<b class="caret"></b>
				</a></li>
			</ul>
			<ul class="nav nav-list nav-links" style="display: none;">
				<li>
					<a href="${pageContext.request.contextPath}/usuarios/${usuarioLogado.usuario.id}/edit">
					<fmt:message key="usuario.editardados"/></a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/usuarios/alterarsenha">
					<fmt:message key="topo.alterarsenha"/></a>
				</li>
<!-- 				<li> -->
<%-- 					<a href="${pageContext.request.contextPath}/testes/convidados"> --%>
<%-- 					<fmt:message key="usuario.convidar"/></a> --%>
<!-- 				</li> -->
			</ul>
		</div>
		
		<div>
			<ul class="nav nav-list">
				<li class="divider"></li>
				<li class="nav-header"><a class="nav-show"><fmt:message key="leftmenu.testes"/>
				<b class="caret"></b>
				</a></li>
			</ul>
			<ul class="nav nav-list nav-links" style="display: none;">
				<li><a href="${pageContext.request.contextPath}/teste/criar"><fmt:message key="testes.criar"/></a>
				</li>
				<li><a href="${pageContext.request.contextPath}/usuario"><fmt:message key="testes.construcao"/></a>
				</li>
				<li><a href="${pageContext.request.contextPath}/testes/liberados"><fmt:message key="testes.liberado"/></a>
				</li>
				<li><a href="${pageContext.request.contextPath}/testes/convidados"><fmt:message key="testes.convites"/></a>
				</li>
			</ul>
		</div>
	</div>
</div>