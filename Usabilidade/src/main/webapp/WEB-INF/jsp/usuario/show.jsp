<head>
    <title>Usuario [show]</title>
</head>
<body>

    <script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/contato/formee.js"></script>
    <div class="formee" id="loginForm" style="width: 600px; margin: 0 auto;">
        <fieldset>
            <legend>Usuário Selecionado</legend>
            <div class="row title">
                <div class="grid-12-12 clear">
                    <label>Nome do Usuário</label>
                    <input type="text" name="nome" value="${usuario.nome}" disabled/>
                </div>
                <div class="grid-12-12">
                    <label>Email do Usuário</label>
                    <input type="text" name="email" value="${usuario.email}" disabled/>
                </div>
                <div class="grid-10-12">

                </div>
                <div class="grid-2-12">
                    <a class="input" href="${pageContext.request.contextPath}/usuarios/${usuario.id}/edit">Editar</a>
                </div>
            </div>	
        </fieldset>
    </div>  
<a href="${pageContext.request.contextPath}/usuarios">Back</a>
</body>