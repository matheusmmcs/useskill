<head>
    <title>Usuario</title>
</head>
<body> 

    <div class="row show-grid">
        <div class="span2 offset10">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/usuarios/new">Cadastrar Usuário</a> 
        </div>
    </div>
    <div class="row show-grid">
        <div class="span12">
            <h1>Lista de Usuários</h1>
        </div>
        <div class="span12">
            <table class="table table-striped table-bordered table-condensed" style="background-color: white">
                <thead>
                    <tr>
                        <th>Nome do Usuário</th>
                        <th>Email do Usuário</th>
                        <th>Ver</th>
                        <th>Editar</th>
                        <th>Excluir</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${usuarioList}" var="usuario">
                        <tr>
                            <td>${usuario.nome}</td>
                            <td>${usuario.email}</td>
                            <td width="100"><a class="btn" href="${pageContext.request.contextPath}/usuarios/${usuario.id}" style="margin-left: 20px"><span class="icon-zoom-in"></span> Ver</a></td>
                            <td width="100"><a class="btn" href="${pageContext.request.contextPath}/usuarios/${usuario.id}/edit" style="margin-left: 12px"><span class="icon-pencil"></span> Editar</a></td>
                            <td width="100">
                                <form action="${pageContext.request.contextPath}/usuarios/${usuario.id}" method="post">
                                    <input type="hidden" name="_method" value="delete"/>
                                    <input type="submit" onclick="return confirm('Are you sure?')" value="Excluir" name="excluir" title="Excluir" class="btn icon-trash" style="margin-left: 18px"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>