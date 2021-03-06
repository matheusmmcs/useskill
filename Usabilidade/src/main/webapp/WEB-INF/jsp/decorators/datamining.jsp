<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<!-- decorator = main -->
	<meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><decorator:title default="UseSkill" /></title>
	<link href="${pageContext.request.contextPath}/img/favicon.ico" rel="shortcut icon" type="image/x-icon" />

	<link rel="stylesheet/less" href="${pageContext.request.contextPath}/less/style2.less" />
	<link href="${pageContext.request.contextPath}/jscripts/libs/vis/vis.min.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/tablesort.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/jscripts/libs/angular/angular-chart/angular-chart.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/jscripts/libs/angular/datepicker/angular-datepicker.min.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/jscripts/libs/angular/loading/loading-bar.min.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/jscripts/libs/angular/angular-nvd3/nv.d3.css" type="text/css" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/jscripts/libs/less-1.3.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/jscripts/libs/modernizr-2.5.3.js"></script>
	
	<script src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-MML-AM_CHTML"></script>
	<script type="text/x-mathjax-config">
	  	MathJax.Hub.Config({
  			TeX: { equationNumbers: { autoNumber: "AMS" } },
			tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]}
		});
	</script>

	<decorator:head />
</head>
<body ng-app="useskill">

	<div long-loader class="hide longLoader" data-progressbar-label="Loading..."></div>
	<%@include file="topo.jsp"%>
	<div class="container">
		<div class="row container-row">
		
			<div class="useskill-datamining">
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/vis/vis.min.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-animate.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-resource.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-route.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-translate.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-translate-loader-url.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-tablesort.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-chart/angular-chart.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/ui-bootstrap/ui-bootstrap-tpls-0.14.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/datepicker/angular-datepicker.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-moment.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/loading/loading-bar.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-nvd3/d3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-nvd3/nv.d3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/angular/angular-nvd3/angular-nvd3.min.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/app/graph.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/app/app.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/bootstrap/bootstrap-popover.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/useskill.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/jquery.easing1.3.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/TweenMax.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jscripts/libs/elastic-progress.js"></script>
	
	
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
