<div id="topo">
	<div id="topo_centro">
		<a id="usabilitool" title="Usabilitool"
			href="${pageContext.request.contextPath}/"></a>
		<div id="topo_direito">
			<a href="${pageContext.request.contextPath}/logout" id="logout"
				class="btn btn-danger"><fmt:message key="logout" /></a> <span
				id="nome">${usuarioLogado.usuario.nome}</span>
		</div>
	</div>
	<div id="menu_topo">
		<div id="menu_topo_centro">
			<img alt="" src="${pageContext.request.contextPath}/img/select.png"
				class="imagem_selected" />
			<ul>
				<li><a href="${pageContext.request.contextPath}/usuario"><fmt:message
							key="inicio" /></a></li>
				<li><a href="${pageContext.request.contextPath}/teste/criar"><fmt:message
							key="crie.seu.teste" /></a></li>
				<li><a href="${pageContext.request.contextPath}/login"><fmt:message
							key="testes.Liberado" /></a></li>
				<li><a href="${pageContext.request.contextPath}usuarios/${usuarioLogado.usuario.id} }/edit"><fmt:message
							key="minha.conta" /></a>
				</li>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jscripts/menu.js"></script>