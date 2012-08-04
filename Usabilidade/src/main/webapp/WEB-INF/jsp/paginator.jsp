
<%
	String link = request.getParameter("link");
%>

<center>
	<div class="pagination">
		<ul>
			<c:choose>
				<c:when test="${paginaAtual==1}">
					<li class="disabled"><a href="#"> << </a>
					</li>
				</c:when>
				<c:otherwise>
					<li><a href="<%out.print(link);%>${paginaAtual-1}"> << </a>
					</li>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${paginaAtual-3>0}">
					<li class="disabled"><a href="#">...</a>
					</li>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${paginaAtual-2>0}">
					<li><a href="<%out.print(link);%>${paginaAtual-2}">${paginaAtual-2}</a>
					</li>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${paginaAtual-1>0}">
					<li><a href="<%out.print(link);%>${paginaAtual-1}">${paginaAtual-1}</a>
					</li>
				</c:when>
			</c:choose>
			<li><a class="active" href="<%out.print(link);%>${paginaAtual}">${paginaAtual}</a>
			</li>
			<c:choose>
				<c:when test="${paginaAtual+1<=qtdPaginas}">
					<li><a href="<%out.print(link);%>${paginaAtual+1}">${paginaAtual+1}</a>
					</li>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${paginaAtual+2<=qtdPaginas}">
					<li><a href="<%out.print(link);%>${paginaAtual+2}">${paginaAtual+2}</a>
					</li>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${paginaAtual+3<=qtdPaginas}">
					<li class="disabled"><a href="#">...</a>
					</li>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${paginaAtual==qtdPaginas}">
					<li class="disabled"><a href="#"> >> </a>
					</li>
				</c:when>
				<c:otherwise>
					<li><a href="<%out.print(link);%>${paginaAtual+1}"> >> </a>
					</li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</center>