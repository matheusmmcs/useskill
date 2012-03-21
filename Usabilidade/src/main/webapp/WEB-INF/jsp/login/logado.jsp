<table border="1">
    <thead>
        <tr>
            <th><fmt:message key="titulo"/></th>
            <th><fmt:message key="editar"/></th>
            <th><fmt:message key="remover"/></th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${testesCriados}" var="teste" > 
            <tr class="linhaTabela1">
                <td>
                    <a href="${pageContext.request.contextPath}/teste/${teste.id}/editar/passo1">
                        ${teste.titulo}                    </a>
                </td>
                <td><a href="${pageContext.request.contextPath}/teste/${teste.id}/editar/passo1">
                        <fmt:message key="editar"/>
                    </a></td>
                <td>
                    <a href="${pageContext.request.contextPath}/teste/${teste.id}/remover">
                        <fmt:message key="remover"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<table >
    <thead>
        <tr>
            <th><fmt:message key="titulo"/></th>
            <th><fmt:message key="criador"/></th>
            <th><fmt:message key="participar"/></th>
            <th><fmt:message key="nao.participar"/></th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${testesConvidados}" var="teste" > 
            <tr class="linhaTabela1">
                <td>
                   
                        ${teste.tituloPublico}                    
                </td>
                <td>
                  ${teste.usuarioCriador.nome}      
                 </td>
                 <td>
                  ${teste.usuarioCriador.nome}      
                 </td>
                 <td>
                 <form action="" method="post">
                 <input type="hidden" value="${teste.id }" name="testeId">
                 <input type="hidden" value="${usuarioLogado.usuario.id }" name="usuarioId">
                 <input type="submit" value="<fmt:message key="nao.participar"/>">
                 
                 </form>
                     
                 </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
