<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ctet.data.*, java.text.*, java.util.*, com.ctet.web.dto.*"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>IIHT</title>

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" />
<!-- 		<link href="assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" /> -->
<link href="user/css/style.css" rel="stylesheet" type="text/css" />

<!-- <link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css"> -->

<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>

<!--End::Google Tag Manager -->
</head>

<body>
	<div class="side">

		<div class="logo"></div>


		<ul>
			<li><a href="#" class="active"><i class="fas fa-solid fa-home"></i><span class="links_name"> Home</span> </a></li>

			<li><a href="home44" class="active"> <i class="fas fa-folder" aria-hidden="true"></i> <span class="links_name">Test
						Series </span></a></li>

			<li><a href="#"> <i class="fas fa-folder" aria-hidden="true"></i> <span class="links_name">Quizes </span></a></li>

			<li><a href="#"> <i class="fas fa-folder" aria-hidden="true"></i> <span class="links_name"> Previous
						Year Papers </span></a></li>

			<li><a href="#"> <i class="fas fa-folder" aria-hidden="true"></i> <span class="links_name">Practice</span>
			</a></li>



			<li><a href="#"> <i class="fas fa-folder" aria-hidden="true"></i> <span class="links_name">Exams </span></a></li>

			<li><a href="#"> <i class="fas fa-folder" aria-hidden="true"></i> <span class="links_name">Doubts </span></a></li>


			<li><a href="#"><i class="fas fa-folder" aria-hidden="true"></i> <span class="links_name">GK &
						Current Affairs </span></a></li>


			<li><a href="#"><i class="fas fa-folder" aria-hidden="true"></i> <span class="links_name">KBC</span> </a></li>


		</ul>

	</div>
		<nav>
			<div class="sidebar-button">
				<i class="fas fa-solid fa-bars"></i> <span class="dashboard">Dashboard</span>
			</div>

		</nav>

	<section>
		<div class="all">
			<div class="categories">
				<!-- 				<div class="categories1"> -->
				<!-- 					<ul> -->
				<%-- 						<c:forEach items="${categories}" var="category" varStatus="loop"> --%>
				<%-- 							<li><a href="home44?id=${category.id}">${category.categoryName} </a></li> --%>
				<%-- 						</c:forEach> --%>



				<!-- 					</ul> -->
				<!-- 				</div> -->
				<div class="categories2">
					<ul>
						<c:forEach items="${tests}" var="test" varStatus="loop">
							<li><a href="publicTest?testId=${test.id}&companyId=${test.companyName}" target="_blank">${test.testName}</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</section>


</body>

</html>