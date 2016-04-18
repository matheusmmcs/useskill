

<div class="span12">
	<c:if test="${not empty errors}">
		<div class="alert alert-error">
			<c:forEach items="${errors}" var="error">
        		${error.message}<br />
			</c:forEach>
		</div>
	</c:if>

	<ul class="breadcrumb">
		<li class="active"><fmt:message key="inicio" /></li>
	</ul>

	<div class="us-slider-choose">
		<a class="us-slider-choose-element" href="${pageContext.request.contextPath}/usuario">
			<h2 class="us-slider-choose-element-title"><fmt:message key="us.chooser.control" /></h2>
			<ul class="us-slider-choose-element-ul">
				<li><fmt:message key="us.chooser.control.q1" /></li>
				<li><fmt:message key="us.chooser.control.q2" /></li>
				<li><fmt:message key="us.chooser.control.q3" /></li>
			</ul>
			<img src="${pageContext.request.contextPath}/img/home/img1.jpg" class="us-slider-choose-img" />
		</a>
		<a class="us-slider-choose-element" href="${pageContext.request.contextPath}/datamining">
			<h2 class="us-slider-choose-element-title"><fmt:message key="us.chooser.onthefly" /></h2>
			<ul class="us-slider-choose-element-ul">
				<li><fmt:message key="us.chooser.onthefly.q1" /></li>
				<li><fmt:message key="us.chooser.onthefly.q2" /></li>
				<li><fmt:message key="us.chooser.onthefly.q3" /></li>
			</ul>
			<img src="${pageContext.request.contextPath}/img/home/img2.jpg" class="us-slider-choose-img" />
		</a>
	</div>

</div>