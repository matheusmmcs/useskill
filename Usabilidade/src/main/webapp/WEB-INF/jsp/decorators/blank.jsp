<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<!-- decorator = blank -->
<title><decorator:title default="UseSkill"/></title>
<link href="${pageContext.request.contextPath}/img/favicon.ico" rel="shortcut icon" type="image/x-icon" />

<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/jquery.js"></script>


<link rel="stylesheet/less" href="${pageContext.request.contextPath}/less/style.less" />
<script src="${pageContext.request.contextPath}/jscripts/libs/less-1.3.0.min.js"></script>
<script src="${pageContext.request.contextPath}/jscripts/libs/modernizr-2.5.3.js"></script>
<decorator:head />
</head>
<body>
	<decorator:body />
</body>
</html>