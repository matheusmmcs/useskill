<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<!-- decorator = teste -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/webclient/loadaction.css" />

	
<!--  FANCYBOX -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/fancybox/jquery.fancybox.css" media="screen" />

<decorator:head />
</head>
<body>
	<%@include file="headerscriptUser.jsp"%>
	<div id="webclient-conteudo">
		<decorator:body />
	</div>
</body>
</html>