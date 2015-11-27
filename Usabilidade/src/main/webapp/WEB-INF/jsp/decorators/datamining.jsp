<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<!-- decorator = main -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><decorator:title default="UseSkill" /></title>
	<link href="${pageContext.request.contextPath}/img/favicon.ico" rel="shortcut icon" type="image/x-icon" />

	<link rel="stylesheet/less" href="${pageContext.request.contextPath}/less/style.less" />
	<link href="${pageContext.request.contextPath}/plugin/vis/vis.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/tablesort.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/jscripts/libs/angular/angular-chart/angular-chart.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/jscripts/libs/angular/datepicker/angular-datepicker.min.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/jscripts/libs/less-1.3.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/jscripts/libs/modernizr-2.5.3.js"></script>
	
	<decorator:head />
</head>
<body>
	
	<%@include file="topo.jsp"%>
	<div class="container">
		<div class="row container-row">
		
			<div ng-app="useskill" class="useskill-datamining">
				<div ng-view></div>
				<%-- <decorator:body /> --%>
			</div>

		</div>
	</div>
	<footer>
	<hr />
	<center>
		<div id="footer">
			<span><fmt:message key="footer.copyright" />
			</span>
		</div>
	</center>
	</footer>


	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-1.3.15.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/json2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/chart/Chart.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/bootstrap/bootstrap.min.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-resource.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-route.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-translate.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-translate-loader-url.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-tablesort.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-chart/angular-chart.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/ui-bootstrap/ui-bootstrap-tpls-0.13.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/datepicker/angular-datepicker.min.js"></script>
	<script src="//cdn.jsdelivr.net/angular.moment/0.6.2/angular-moment.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/ngprogress.min.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/app/app.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/bootstrap/bootstrap-popover.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/useskill.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/jquery.easing1.3.js"></script>
	
	<!--
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/useskill/capt.mining.useskill.nojquery.js"></script>
	<script>
		(function($){
			$(document).ready(function(){
				useskill_capt_onthefly({
					url: "http://127.0.0.1:3000/actions/create",
					waitdomready: false,
					sendactions: true,
					debug: true,
				
					onthefly: true,
					capturehashchange: true,
					jheat: false,
					plugin: false,
				
					client: "Test",
					version: 1,
					username: "Usuario",
					role: "Role"
				});
			});
		})(jQuery);
	</script>
	-->
</body>
</html>
