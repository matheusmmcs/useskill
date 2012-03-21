<form action="${pageContext.request.contextPath}/teste/convidar/usuario" method="post">
	<table border="1">
		<thead>
			<tr>
				<th></th>
				<th><fmt:message key="usuario.nome" /></th>
			</tr>
		</thead>
		<tbody>

			<c:forEach items="${usuariosLivres}" var="usuario">
				<tr>
					<td><input type="checkbox" name="idUsuarios[]" value="${usuario.id }"></td>
					<td>${usuario.nome}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<input type="submit" value="<fmt:message key="usuarios.add"/>"/>
</form>
<h1>usuarios Convidados</h1>
<form action="${pageContext.request.contextPath}/teste/desconvidar/usuario" method="post">
	<table border="1">
		<thead>
			<tr>
				<th></th>
				<th><fmt:message key="usuario.nome" /></th>
			</tr>
		</thead>
		<tbody>

			<c:forEach items="${usuariosEscolhidos}" var="usuario">
				<tr>
					<td><input type="checkbox" name="idUsuarios[]" value="${usuario.id }"></td>
					<td>${usuario.nome}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<input type="submit" value="<fmt:message key="usuarios.add"/>"/>
</form>
