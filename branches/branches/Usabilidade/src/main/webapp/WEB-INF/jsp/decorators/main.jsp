<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>

<!-- decorator = main -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
<decorator:title default="UseSkill" />
</title>
<link href="${pageContext.request.contextPath}/img/favicon.ico"
	rel="shortcut icon" type="image/x-icon" />

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/reset.css" />

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap-ex.css" />

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/menu.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css" />

<decorator:head />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jscripts/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jscripts/application.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jscripts/jquery.validate.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jscripts/jquery.easing1.3.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/jscripts/bootstrap.js" />
</head>
<body>
	<%@include file="topo.jsp"%>
	<div id="conteudo">
		<div id="conteudo_interno">
			<decorator:body />
		</div>
	</div>
</body>
</html>
