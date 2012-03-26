<head>
    <title>Usuario [index]</title>
    <style type="text/css">
        label{cursor: default}
        .title{font-weight: bold}
        .formee a.input{padding: 6px 6px}
        .grid-1-12, .grid-4-12, .grid-5-12{overflow: hidden} 
    </style>
</head>
<body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/contato/formee.js"></script>
    <div class="formee" id="loginForm">
        <fieldset>
            <legend>Lista de Usuários</legend>
            <div class="row title">
                <div class="grid-4-12 clear">
                    <label>Nome do Usuário</label>
                </div>
                <div class="grid-5-12">
                    <label>Email do Usuário</label>
                </div>
                <div class="grid-1-12">
                    <label style="margin-left: 7px;">Ver</label>
                </div>
                <div class="grid-1-12">
                    <label style="margin-left: 7px;">Editar</label>
                </div>
                <div class="grid-1-12">
                    <label>Excluir</label>
                </div>
            </div>
            <c:forEach items="${usuarioList}" var="usuario">
                <div class="row">
                    <div class="grid-4-12 clear">
                        <label>${usuario.nome}</label>
                    </div>
                    <div class="grid-5-12">
                        <label>${usuario.email}</label>
                    </div>
                    <div class="grid-1-12">
                        <a class="input" href="${pageContext.request.contextPath}/usuarios/${usuario.id}">Ver</a>
                    </div>
                    <div class="grid-1-12">
                        <a class="input" href="${pageContext.request.contextPath}/usuarios/${usuario.id}/edit">Editar</a>
                    </div>
                    <div class="grid-1-12">
                        <form action="${pageContext.request.contextPath}/usuarios/${usuario.id}" method="post">
                            <input type="hidden" name="_method" value="delete"/>
                            <input type="submit" onclick="return confirm('Are you sure?')" value="Excluir" name="excluir" title="Excluir" class="right" style="width: 50px; font-size: 10px; font-weight: normal; padding: 4px 1px; margin: 0px auto"/>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </fieldset>
    </div>  

    <br />
    <a href="${pageContext.request.contextPath}/usuarios/new">New Usuario</a> 
    <script type="text/javascript">
        (function ($) {               
            $(document).ready(function(){
                var rows = $('.row')
                var bool=true;
                var cont=0;
                rows.each(function(){
                    if(bool){
                        rows.eq(cont).css('background', '#cbd5dd')
                        bool=false
                    }else{
                        bool=true
                    }
                    cont++
                })
            })
        })(jQuery);
    </script>
</body>