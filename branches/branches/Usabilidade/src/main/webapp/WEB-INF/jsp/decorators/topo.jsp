<div id="topo">
	<div id="topo_centro">
		<a id="usabilitool" title="Usabilitool"
			href="${pageContext.request.contextPath}/"></a>
		<c:if test="${usuarioLogado.usuario!=null}">
			<div id="topo_direito">

				<a href="${pageContext.request.contextPath}/logout" id="logout"
					class="btn btn-danger"><fmt:message key="logout" /> </a> <span
					id="nome">${usuarioLogado.usuario.nome}</span>
			</div>
		</c:if>
	</div>
	<div id="menu_topo">
		<div id="menu_topo_centro">
			<img alt="" src="${pageContext.request.contextPath}/img/select.png"
				class="imagem_selected" />
			<ul>
			<!--  em comum -->
			<li><a href="${pageContext.request.contextPath}"><fmt:message
							key="inicio" /> </a>
				</li>
				<c:choose>
					<c:when test="${usuarioLogado.usuario!=null}">
					<!--  se logado -->
					<li><a href="${pageContext.request.contextPath}/usuario"><fmt:message
									key="testes.meus" /> </a>
						</li>
						<li><a href="${pageContext.request.contextPath}/teste/criar"><fmt:message
									key="crie.seu.teste" /> </a>
						</li>
						<li><a
							href="${pageContext.request.contextPath}/usuarios/${usuarioLogado.usuario.id}/edit"><fmt:message
									key="meus.dados" /> </a></li>
					</c:when>
					<c:otherwise>
					<!--  se nao logado -->
				<li><a href="${pageContext.request.contextPath}/usuarios/new"><fmt:message
							key="usuario.cadastro" /> </a>
				</li>
				<li><a href="${pageContext.request.contextPath}/login"><fmt:message
							key="acessar.conta" /> </a>
				</li>
					</c:otherwise>
				</c:choose>

			</ul>
		</div>
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jscripts/menu.js"></script>