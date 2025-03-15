<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="materialize is a material design based mutipurpose responsive template">
<meta name="keywords" content="material design, card style, material template, portfolio, corporate, business, creative, agency">
<meta name="author" content="trendytheme.net">
<title>eAssess</title>

</head>

<body id="top" class="has-header-search">
	<!--header start-->
	<form:form id="loginForm" method="post" modelAttribute="user" action="authenticate">
		<form:input style="width:150px" type="email" path="email" name="email" id="username" cssClass="form-control" required="true"
			placeholder="User" />
		<form:password style="width:150px" path="password" name="password" id="password" cssClass="form-control" required="true"
			placeholder="Password" />

		<button style="width: 150px" type="submit">Login</button>

	</form:form>


	<!--header end-->

	<section class="banner-wrapper parallax-bg banner-12   dark-5 valign-wrapper height-650" data-stellar-background-ratio="0.5"></section>








	<!-- jQuery -->


</body>

</html>