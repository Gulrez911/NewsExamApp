<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ctet.data.*, java.text.*, java.util.*"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link rel="stylesheet" href="./resources/newDesing/css/style.css">
</head>
<style>
</style>

<body>
	<div class="auth-wrapper">
		<div class="d-none2">
			<div class="d-none">
				<img src="./resources/newDesing/image/login-v2-dark.svg" alt="">
			</div>
		</div>
		<div class="auth-bg">
			<div class="inner">
				<h1 class="card-title">Welcome to Vuexy! 👋</h1>
				<p class="card-text">Please sign-in to your account and start the adventure</p>

				<form name="testloginform" class="userform" method="post" modelAttribute="testUserData"
					action="publicTestAuthenticate">

					<form:hidden path="testUserData.testId" />
					<form:hidden path="testUserData.user.companyName" />
					<form:hidden path="testUserData.user.companyId" />

					<div class="form-group">
						<div class="label2">
							<label for="login-first-name" class="d-block">First Name</label>
						</div>
						<!--                         <input id="login-first-name" name="login-first-name" type="text" placeholder="First Name" -->
						<!--                             class="form-control"> -->

						<form:input path="testUserData.user.firstName" name="firstName" id="login-first-name" placeholder="First Name"
							required="true" class="form-control" />


					</div>
					<div class="form-group">
						<div class="label2">
							<label for="login-last-name" class="d-block">Last Name</label>
						</div>
<!-- 						<input id="login-email" name="login-last-name" type="text" placeholder="Last Name" class="form-control"> -->

						<form:input path="testUserData.user.lastName" name="lastName" id="login-last-name" placeholder="Last Name"
							required="true" class="form-control" />

					</div>
					<div class="form-group">
						<div class="label2">
							<label for="login-email" class="d-block" id="__BVID__50__BV_label_">Email</label>
						</div>
						<!--                         <input id="login-email" name="login-email" type="email" placeholder="john@example.com" -->
						<!--                             class="form-control"> -->

						<form:input type="email" path="testUserData.user.email" name="email" placeholder="Email" required="true"
							class="form-control" id="login-email" />


					</div>

					<div class="form-group">
						<div class="label2">
							<label for="login-password" class="d-block" id="__BVID__50__BV_label_">Password</label>
						</div>
<!-- 						<input id="login-email" name="login-password" type="text" placeholder="Password" class="form-control"> -->

<form:password path="testUserData.user.password" id="login-password" name="psw" placeholder="Password" required="true"
						class="form-control"  />
						
<%-- <form:password path="testUserData.user.password" id="login-password" name="psw" placeholder="Password" required="true" --%>
<%-- 						class="form-control" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" /> --%>

					</div>
					<div class="form-group">
						<div class="label2">
							<label for="login-re-password" class="d-block" id="__BVID__50__BV_label_">Re Password</label>
						</div>
<!-- 						<input id="login-email" name="login-re-password" type="text" placeholder="Re Password" class="form-control"> -->

	<form:password path="testUserData.user.rePassword" name="rePassword" id="login-re-password" placeholder="Re Password"
						required="true" class="form-control" />
						
<%-- 	<form:password path="testUserData.user.rePassword" name="rePassword" id="login-re-password" placeholder="Re Password" --%>
<%-- 						required="true" class="form-control" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" /> --%>
						
					</div>
					
					
						<form:hidden path="testUserData.testName" name="testName" id="testName" required="true" disabled="true"
						class="input100" />
						
							<form:hidden path="testUserData.user.companyName" name="companyName" id="companyName" disabled="true"
						class="input100" />

					<div class="btn-block">
						<button type="submit" class="btn btn-primary btn-block">Sign in</button>
					</div>
				</form>

				<div class="form-create">
					<p class="card-text text-center mt-2">
						<span>New on our platform? </span> <a href="#"> Create an account </a>
					</p>

				</div>

				<!-- <div class="divider"><div class="divider-text"> or </div></div> -->
				<p class="subtitle fancy">
					<span>or</span>
				</p>
				<div class="social">
					<div class="social_icon">
						<img src="./resources/newDesing/image/icons8-github-48.png" alt="">
					</div>
					<div class="social_icon">
						<img src="./resources/newDesing/image/icons8-facebook-48.png" alt="">
					</div>
					<div class="social_icon">
						<img src="./resources/newDesing/image/icons8-instagram-48.png" alt="">
					</div>
					<div class="social_icon">
						<img src="./resources/newDesing/image/icons8-twitter-48.png" alt="">
					</div>
					<div class="social_icon">
						<img src="./resources/newDesing/image/icons8-gmail-48.png" alt="">
					</div>

				</div>
			</div>




		</div>
	</div>
	</div>

</body>

</html>