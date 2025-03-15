<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ page import="com.assessment.data.*, java.text.*, java.util.*"%> --%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>eAssess</title>
<link href="./resources/userprofile/images/E-assess_E.png" rel="shortcut icon">

<link href="./resources/userprofile/css/bootstrap4.css" rel="stylesheet">


<script src="./resources/userprofile/js/jquery.min.js"></script>
<script src="./resources/userprofile/js/popper.min.js"></script>
<script src="./resources/userprofile/js/bootstrap4.min.js"></script>
<link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.33.1/sweetalert2.css">
<!-- <link rel="stylesheet" href="./resources/userprofile/css/style.css"> -->
<link rel="stylesheet" href="./resources/userprofile/css/menuzord.css">
<link rel="stylesheet" href="./resources/userprofile/css/cardstyle.css">

<style>
body {
	font-family: medium-content-serif-font, Georgia, Cambria,
		"Times New Roman", Times, serif;
}
</style>
</head>
<body>



	<div id="page">

		<nav class="navbar navbar-expand-sm" style="background: #1a1ab1; height: 65px;">
			<ul class="navbar-nav menuzord-menu">
				<li class="nav-item active" id="logo"><a href="showLearnerAdminDashboard"><img class="active" src="./eAssess/images/New B&amp;G copy.png"
						style="height: 53px; padding-top: 13px;"></img></a></li>
				<li class="nav-item"><a href="lmsModules">Manage Modules</a></li>
				<li class="nav-item active"><a class="nav-link" href="lmsQuestion_list">Question Bank</a></li>
				<li class="nav-item"><a class="nav-link " href="lmsTests">Test Bank</a></li>
				<li class="nav-item"><a class="nav-link " href="showAllResults">Results - Learners</a></li>
				<li class="nav-item"><a class="nav-link " href="showManualReviewResults">Review Tests</a></li>
				<li class="nav-item "><a class="nav-link " href="meetings">Meetings</a></li>
				<li class="nav-item"><a class="nav-link " href="signoff">Log Off</a></li>
			</ul>
		</nav>


	</div>

</body>
</html>

