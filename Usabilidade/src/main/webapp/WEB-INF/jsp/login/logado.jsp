<script type="text/javascript"
	src="${pageContext.request.contextPath}/jscripts/contato/formee.js"></script>
<form class="formee" id="loginForm"
	action="${pageContext.request.contextPath}/conta" method="post">
	<fieldset>
		<legend>Meus Testes</legend>

		<div class="grid-8-12">
			<fmt:message key="titulo" />
		</div>
		<div class="grid-2-12">
			<fmt:message key="editar" />
		</div>
		<div class="grid-2-12">
			<fmt:message key="remover" />
		</div>

		<c:forEach items="${testesCriados}" var="teste">
			<div class="grid-8-12 clear">
				<a
					href="${pageContext.request.contextPath}/teste/${teste.id}/editar/passo1">
					${teste.titulo} </a>
			</div>
			<div class="grid-2-12">
				<a
					href="${pageContext.request.contextPath}/teste/${teste.id}/editar/passo1">
					<fmt:message key="editar" />
				</a>
			</div>
			<div class="grid-2-12">
				<a
					href="${pageContext.request.contextPath}/teste/${teste.id}/remover">
					<fmt:message key="remover" />
				</a>
			</div>
		</c:forEach>
	</fieldset>
</form>


<form class="formee" id="loginForm"
	action="${pageContext.request.contextPath}/conta" method="post">
	<fieldset>
		<legend>Convites de Testes</legend>

		<div class="grid-5-12">
			<fmt:message key="titulo" />
		</div>
		<div class="grid-2-12">
			<fmt:message key="criador" />
		</div>
		<div class="grid-2-12">
			<fmt:message key="participar" />
		</div>
		<div class="grid-2-12">
			<fmt:message key="nao.participar" />
		</div>

		<c:forEach items="${testesConvidados}" var="teste">
			<div class="grid-5-12">${teste.tituloPublico}</div>
			<div class="grid-2-12">${teste.usuarioCriador.nome}</div>
				<div class="grid-2-12">
				<form action="${pageContext.request.contextPath}/teste/participar/aceitar" method="post">
					<input type="hidden" value="${teste.id }" name="testeId"> 
					<input type="submit"
						value="<fmt:message key="participar"/>">

				</form>
			</div>
			<div class="grid-2-12">
				<form action="${pageContext.request.contextPath}/teste/participar/aceitar" method="post">
					<input type="hidden" value="${teste.id }" name="testeId"> 
					<input type="submit"
						value="<fmt:message key="nao.participar"/>">

				</form>
			</div>
		</c:forEach>
	</fieldset>
</form>
