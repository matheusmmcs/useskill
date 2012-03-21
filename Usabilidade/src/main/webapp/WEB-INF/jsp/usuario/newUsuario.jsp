<c:if test="${not empty errors}">
    <c:forEach items="${errors}" var="error">
        ${error.message}<br />
    </c:forEach>
</c:if>

<form id="editUsuario_Form"
      action="${pageContext.request.contextPath}/usuarios" method="post">
    <table>
        <c:if test="${not empty usuario.id}">
            <input type="hidden" name="usuario.id" value="${usuario.id}" />
            <input type="hidden" name="_method" value="put" />
        </c:if>
        <tr>
            <td><fmt:message key="nome" />:</td>
            <td><input type="text" name="usuario.nome"
                       value="${usuario.nome}" id="nome" />
            </td>
        </tr>
        <tr>
            <td><fmt:message key="email" />:</td>
            <td><input type="text" name="usuario.email"
                       value="${usuario.email}" />
            </td>
        </tr>
        <tr class="linha_tel">
            <td><fmt:message key="telefone" />:</td>
            <td><input type="text" name="usuario.telefones[0]"
                       value="${usuario.telefones[0]}" />
            </td>
            <td><button type="button" class="inserir_tel">adicionar</button>
            </td>
        </tr>
        <tr class="linha_depois_tel">
            <td><fmt:message key="senha" />:</td>
            <td><input type="text" name="usuario.senha" id="usuario.senha"
                       value="${usuario.senha}" />
            </td>
        </tr>
        <tr>
            <td><fmt:message key="confirmasenha" />:</td>
            <td><input type="password" name="confirmaSenha" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit"
                       value="<fmt:message key="usuario.cadastro"/>" />
            </td>
        </tr>
    </table>
</form>
<a href="${pageContext.request.contextPath}">Back</a>

<script type="text/javascript">
    $(document)
    .ready(
    function() {
        var contador = 0;

        $('button.inserir_tel').click(function() {
            $('.linha_depois_tel').before(conteudo_inserir())
        })
        $('button.remover_tel').live("click", function() {
            $(this).parent().parent().remove()
        })

        function conteudo_inserir() {
            contador++;
            return '<tr><td><fmt:message key="telefone"/>:</td><td><input type="text" name="usuario.telefones['+contador+']" value="${usuario.telefones['+contador+']}" /></td><td><button type="button" class="remover_tel">remover</button></td></tr>'
        }
    });

    $("#editUsuario_Form").validate({
        rules : {
            "usuario.nome" : {
                required : true
            },
            "usuario.email" : {
                required : true,
                email : true
            },
            "usuario.senha" : {
                required : true,
                minLength : 6
            },
            "confirmaSenha" : {
                required : true,
                minLength : 6
            }
        }
    });
</script>