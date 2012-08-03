<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<!-- decorator = main -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><decorator:title default="UseSkill" /></title>
<link href="${pageContext.request.contextPath}/img/favicon.ico" rel="shortcut icon" type="image/x-icon" />

<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/jquery.js"></script>


<link rel="stylesheet/less" href="${pageContext.request.contextPath}/less/style.less" />
<script src="${pageContext.request.contextPath}/jscripts/libs/less-1.3.0.min.js"></script>
<script src="${pageContext.request.contextPath}/jscripts/libs/modernizr-2.5.3.js"></script>
<decorator:head />
</head>
<body>
	<%@include file="topo.jsp"%>
	<div class="container">
		<div class="row container-row">
			<decorator:body />
		</div>
	</div>
	<footer>
		<hr/>
		<center>
			<div id="footer">
                <span><fmt:message key="footer.copyright"/></span>
        	</div>
    	</center>
	</footer>

	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/useskill.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/jquery.validate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/jquery.easing1.3.js"></script>
</body>
</html>
