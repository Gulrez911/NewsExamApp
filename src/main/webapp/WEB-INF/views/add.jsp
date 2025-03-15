<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page isELIgnored="false"%>
<html>
<title>Home</title>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link href='http://fonts.googleapis.com/css?family=Oswald:400,300,700' rel='stylesheet' type='text/css'>
<link href="./resources/img/gear.png" rel="shortcut icon">
<link href="./resources/css/bootstrap.min.css" rel="stylesheet">
<script src="./resources/js/jquery.min.js" type="text/javascript"></script>
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> -->

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- <script src="https://code.jquery.com/jquery-1.12.4.js"></script> -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
body {
	font-family: 'Georgia', sherif;
}
</style>
</head>
<body>
	<div class="container">
		<button onclick="window.location.href='list'" class="btn btn-primary">Student List</button>

		<form:form action="${pageContext.request.contextPath}/logout" method="POST">

			<input type="submit" value="Logout" class="btn btn-primary"/>

		</form:form>
		${student.fname}
		<table>
			<form:form action="save" method="post" modelAttribute="employee" cssClass="form-horizontal">

				<div class="form-group">
					<label for="username" class="col-md-3 control-label">First Name</label>
					<div class="col-md-9">
						<form:input path="firstName" cssClass="form-control" id="username" />
					</div>
				</div>
				<div class="form-group">
					<label for="lastName" class="col-md-3 control-label">Last Name:</label>
					<div class="col-md-9">
						<form:input path="lastName" cssClass="form-control" />
					</div>
				</div>
				<div class="form-group">
					<label for="email" class="col-md-3 control-label">Email:</label>
					<div class="col-md-9">
						<form:input path="email" cssClass="form-control" />
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-md-3 control-label">Password:</label>
					<div class="col-md-9">
						<form:password path="password" cssClass="form-control"  id="password"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-offset-3 col-md-9">
						<form:button class="btn btn-primary">Submit</form:button>
					</div>
				</div>

			</form:form>

		</table>

	</div>
</body>
</html>
