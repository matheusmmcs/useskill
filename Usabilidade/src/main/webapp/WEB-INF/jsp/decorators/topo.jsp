<div id="topo">
	<div id="topo_centro">
		<a id="logotipe" title="Usabilitool"
			href="${pageContext.request.contextPath}/"></a>
		<c:if test="${usuarioLogado.usuario!=null}">


			<div id="topo_direito">
				<div class="btn-group pull-right">
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<fmt:message key="usuario.minhaconta" /> <span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/usuarios/${usuarioLogado.usuario.id}/edit">
							<fmt:message key="usuario.meusdados" />
						</a></li>
						<li><a href="#">Alterar Senha</a></li>
						<li class="divider"></li>
						<li><a href="${pageContext.request.contextPath}/logout">
							<fmt:message key="logout" /> 
						</a></li>
					</ul>
					<a id="btn-logout" class="btn btn-primary" href="${pageContext.request.contextPath}/logout"> <i
						class="icon-off icon-white"></i> </a>
				</div>

				<span id="nome"><fmt:message key="topo.ola"/>${usuarioLogado.usuario.nome}</span>
			</div>
		</c:if>
	</div>
	<div id="menu_topo">
		<div id="menu_topo_centro">
			<img alt="" src="${pageContext.request.contextPath}/img/select.png"
				class="imagem_selected" />
			<ul>
				<!--  em comum -->
				<li><a id="topmenu0" href="${pageContext.request.contextPath}" data-topmenu="4">
				<fmt:message key="inicio" /></a></li>
				<c:choose>
					<c:when test="${usuarioLogado.usuario!=null}">
						<!--  se logado -->
						<li><a id="topmenu1" href="${pageContext.request.contextPath}/usuario">
						<fmt:message key="testes.meus" /></a></li>
						<li><a id="topmenu2" href="${pageContext.request.contextPath}/teste/criar">
						<fmt:message key="testes.criar" /></a></li>
						<li><a id="topmenu3" href="${pageContext.request.contextPath}/usuarios/${usuarioLogado.usuario.id}/edit">
						<fmt:message key="usuario.meusdados" /></a>
						</li>
					</c:when>
					<c:otherwise>
						<!--  se nao logado -->
						<li><a id="topmenu4" href="${pageContext.request.contextPath}/usuarios/new">
						<fmt:message key="usuario.cadastro" /> </a></li>
						<li><a id="topmenu5" href="${pageContext.request.contextPath}/login">
						<fmt:message key="acessar.conta" /> </a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/topMenu.js"></script>