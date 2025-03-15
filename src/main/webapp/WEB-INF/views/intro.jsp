
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ctet.data.*, java.text.*, java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>E-Assess</title>
<spring:url value="/resources/assets/img/ico/favicon.png" var="c1" />

<link href="${c1}" rel="shortcut icon" />
<spring:url value="https://fonts.googleapis.com/css?family=Raleway:400,300,500,700,900" var="c2" />

<link href="${c2}" rel="stylesheet" type="text/css" />
<!-- Material Icons CSS -->
<spring:url value="/resources/assets/fonts/iconfont/material-icons.css" var="c3" />

<link href="${c3}" rel="stylesheet" type="text/css" />
<!-- FontAwesome CSS -->
<spring:url value="/resources/assets/fonts/font-awesome/css/font-awesome.min.css" var="c4" />

<link href="${c4}" rel="stylesheet" type="text/css" />
<!-- materialize -->
<spring:url value="/resources/assets/materialize/css/materialize.min.css" var="c9" />

<link href="${c9}" rel="stylesheet" type="text/css" />

<!-- Bootstrap -->
<spring:url value="/resources/assets/bootstrap/css/bootstrap.min.css" var="c10" />

<link href="${c10}" rel="stylesheet" type="text/css" />
<!-- shortcodes -->
<spring:url value="/resources/assets/css/shortcodes/shortcodes.css" var="c11" />

<link href="${c11}" rel="stylesheet" type="text/css" />
<!-- shortcodes -->
<spring:url value="/resources/assets/css/shortcodes/login.css" var="c21" />

<link href="${c21}" rel="stylesheet" type="text/css" />
<!-- Style CSS -->
<spring:url value="/resources/assets/style.css" var="c12" />

<link href="${c12}" rel="stylesheet" type="text/css" />
<style>
.starttestinfo {
	background-color: #FFFFFF;
	box-shadow: 0px 0px 9px 1px rgba(0, 0, 0, 0.16);
	float: right;
	width: 100%;
	padding: 15px;
}

.starttestinfo .testname {
	float: left;
	width: 100%;
	text-align: center;
	word-wrap: break-word
}

.starttestinfo .testname h3 {
	color: #001021;
	font-size: 20px;
	font-weight: 700;
	line-height: 34px;
	margin-top: 10px;
}

.starttestinfo .totalque {
	float: left;
	width: 100%;
	text-align: center;
}

.starttestinfo .totalque span {
	padding: 0 10px;
}

.starttestinfo .publishdate {
	float: left;
	width: 100%;
	text-align: center;
	padding-top: 30px;
}

.starttestinfo .publishdate span {
	float: left;
	width: 50%;
}

.starttestinfo .startbutton {
	float: left;
	width: 100%;
	text-align: center;
	padding-top: 40px;
}

.starttestinfo .startbutton a {
	background-color: #03A9F4;
	padding: 10px;
	float: left;
	width: 100%;
	text-align: center;
	color: #FFFFFF;
	font-size: 20px;
	font-weight: 400;
	line-height: 24px;
	border-radius: 5px;
	text-decoration: none;
}

.starttestinfo .publishdate {
	float: left;
	width: 100%;
	text-align: center;
	padding-top: 30px;
}

.starttestinfo .publishdate span {
	float: left;
	width: 50%;
}

.starttestinfo .publishdate.foot span {
	float: left;
	width: 100%;
}

.header .userheader {
	float: right;
}

.header .userheader .userinfo {
	float: right;
	text-align: right;
}

.header .userheader .userinfo h4 {
	float: left;
	color: #4A4A4A;
	font-size: 16px;
	font-weight: 400;
	line-height: 19px;
	text-align: right;
	padding-right: 15px;
	margin-bottom: 0;
}

.header .userheader .userinfo span {
	color: #B2B2B2;
	float: right;
	width: 100%;
}

.logo img {
	width: 200px;
}
</style>
</head>
<body id="top" class="has-header-search">
	<!--header start-->
	<header id="header" class="header tt-nav nav-border-bottom sticky" style="height: auto;">
		<div class="header-sticky light-header ">
			<div class="container">
				<div class="col-md-12">
					<div class="col-md-6">
						<div class="logo">
							<a href="javascript:void(0);"><img class="retina" src="<%=request.getContextPath()%>/resources/assets/images/Logo.png" alt=""></a>
						</div>
					</div>
					<div class="col-md-6">
						<div class="userheader mt-15">
							<div class="userinfo">

								<h4>
									Welcome ${studentTestForm.userName}<span>${studentTestForm.emailId}</span>
								</h4>

								<img src="<%=request.getContextPath()%>/resources/images/userimg.png">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	<!--header end-->

	<section class="feature-presentation padding-top-50 padding-bottom-20">
		<div class="container">

			<div class="text-center mb-40">
				<!-- 								<h2 class="section-title text-bold">Important Instructions</h2> -->
				<!-- 								<p class="section-sub" </p> -->
			</div>
			<div class="row">
				<div class="col-md-4 col-sm-6">
					<div class="featured-item feature-icon icon-hover icon-hover-brand right-icon mt-50 mb-80">
						<div class="icon radius-4">

							<img style="width: 60% !important; margin-top: 11px" src="<%=request.getContextPath()%>/resources/assets/images/serviceicon1.png">
						</div>
						<div class="desc">
							<h2>
								<b>Web Proctored Tests</b>
							</h2>
							<p>Secured tests with capabilities to detect potential attempts of a candidate to move away from test screen.</p>
						</div>
					</div>
					<div class="featured-item feature-icon icon-hover icon-hover-brand right-icon mb-80">
						<div class="icon radius-4">

							<img style="width: 60% !important; margin-top: 11px" src="<%=request.getContextPath()%>/resources/assets/images/serviceicon2.png">
						</div>
						<div class="desc">
							<h2>
								<b>Multiple Coding Plateform</b>
							</h2>
							<p>Support coding tests on Java/C/C++/Python/Perl/Javascript and host of other languages</p>
						</div>
					</div>
				</div>
				<form:form id="studentIntro" method="POST" action="studentJourney" modelAttribute="studentTestForm">

					<div class="col-md-4">
						<div class="starttestinfo">
							<div class="testname">
								<img src="<%=request.getContextPath()%>/resources/images/testimage.png">
								<div class="example">
									<h3>${studentTestForm.testName}</h3>
								</div>
							</div>
							<div class="totalque">
								<span>Total Questions &nbsp;&nbsp; ${studentTestForm.totalQuestions}</span> <span>Duration &nbsp;&nbsp; ${studentTestForm.duration} minutes</span>
							</div>
							<div class="publishdate">
								<span>Published on <br> ${studentTestForm.formattedPublishedDate}
								</span> <span>To be completed by <br> Within 1 session
								</span>
							</div>
							<div class="startbutton">
								<a href="javascript:start()">START TEST</a>
							</div>
							<div class="publishdate foot">
								<span><i class="fa fa-user"></i>&nbsp;System Administrator</span> <span><i class="fa fa-envelope"></i>&nbsp;admin@e-assess.com</span>
							</div>
						</div>
					</div>
				</form:form>
				<div class="col-md-4 col-sm-6">
					<div class="featured-item feature-icon icon-hover icon-hover-brand mt-50 mb-80">
						<div class="icon radius-4">

							<img style="width: 60% !important; margin-top: 11px" src="<%=request.getContextPath()%>/resources/assets/images/serviceicon3.png">
						</div>
						<div class="desc">
							<h2>
								<b>Full Stack Support</b>
							</h2>
							<p>Support online test for a Full Stack Applilaction with embedded Web server and Database support</p>
						</div>
					</div>
					<div class="featured-item feature-icon icon-hover icon-hover-brand mb-80">
						<div class="icon radius-4">

							<img style="width: 60% !important; margin-top: 11px" src="<%=request.getContextPath()%>/resources/assets/images/serviceicon4.png">
						</div>
						<div class="desc">
							<h2>
								<b>Comprehensive Profiling by Recomm Engine</b>
							</h2>
							<p>Reports a Candidate's performance on a very granular level specifying the strengths of an individuals in specific and sub-specific technology areas</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<footer class="footer footer-four">
		<div class="secondary-footer brand-bg darken-2 text-center">
			<div class="container">
				<p style="margin-bottom: 0;">Copyrigh © 2019 E-Assess. All Rights Reserved – Privacy Policy For enterprise solutions</p>
			</div>
		</div>
	</footer>
	<!-- jQuery -->



	<spring:url value="/resources/assets/js/jquery-2.1.3.min.js" var="mainJs1" />
	<script src="${mainJs1}"></script>
	<spring:url value="/resources/assets/bootstrap/js/bootstrap.min.js" var="mainJs2" />
	<script src="${mainJs2}"></script>
	<spring:url value="/resources/assets/materialize/js/materialize.min.js" var="mainJs3" />
	<script src="${mainJs3}"></script>
	<spring:url value="/resources/assets/js/menuzord.js" var="mainJs4" />
	<script src="${mainJs4}"></script>
	<spring:url value="/resources/assets/js/bootstrap-tabcollapse.min.js" var="mainJs5" />
	<script src="${mainJs5}"></script>
	<spring:url value="/resources/assets/js/jquery.easing.min.js" var="mainJs6" />
	<script src="${mainJs6}"></script>
	<spring:url value="/resources/assets/js/jquery.sticky.min.js" var="mainJs7" />
	<script src="${mainJs7}"></script>
	<spring:url value="/resources/assets/js/smoothscroll.min.js" var="mainJs8" />
	<script src="${mainJs8}"></script>
	<spring:url value="/resources/assets/js/jquery.stellar.min.js" var="mainJs9" />
	<script src="${mainJs9}"></script>
	<spring:url value="/resources/assets/js/jquery.inview.min.js" var="mainJs10" />
	<script src="${mainJs10}"></script>
	<spring:url value="/resources/assets/owl.carousel/owl.carousel.min.js" var="mainJs11" />
	<script src="${mainJs11}"></script>
	<spring:url value="/resources/assets/flexSlider/jquery.flexslider-min.js" var="mainJs12" />
	<script src="${mainJs12}"></script>
	<spring:url value="/resources/assets/magnific-popup/jquery.magnific-popup.min.js" var="mainJs13" />
	<script src="${mainJs13}"></script>
	<spring:url value="https://maps.googleapis.com/maps/api/js" var="mainJs14" />
	<script src="${mainJs14}"></script>
	<spring:url value="/resources/assets/js/scripts.js" var="mainJs15" />
	<script src="${mainJs15}"></script>
	<spring:url value="/resources/assets/scripts/custom.js" var="mainJs16" />
	<script src="${mainJs16}"></script>
	<spring:url value="/resources/assets/scripts/pnotify.custom.min.js" var="mainJs17" />
	<script src="${mainJs17}"></script>
	<script type="text/javascript">
		function start() {

			document.getElementById("studentIntro").submit();
		}
	</script>

</body>
</html>
