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
