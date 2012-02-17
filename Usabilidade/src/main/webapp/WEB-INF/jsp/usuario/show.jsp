<head>
	<title>Usuario [show]</title>
</head>
<body>
	<p>
		<b>Usuario nome:</b>
		${usuario.nome}
	</p>
	<p>
		<b>Usuario email:</b>
		${usuario.email}
	</p>
	<p>
		<b>Usuario senha:</b>
		${usuario.senha}
	</p>

	<a href="${pageContext.request.contextPath}/usuarios/${usuario.id}/edit">Edit</a>
	<a href="${pageContext.request.contextPath}/usuarios">Back</a>
</body>