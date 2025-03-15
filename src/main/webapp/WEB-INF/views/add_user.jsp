<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form name="userForm" method="post" modelAttribute="usr"
		action="saveUser">
		<div class="formfield">
			<label>First Name</label>

			<form:input path="usr.firstName" name="firstName" id="firstName"
				required="true" />
		</div>

		<div class="formfield">
			<label>Last Name</label>

			<form:input path="usr.lastName" name="lastName" id="lastName"
				required="true" />
		</div>

		<div class="formfield">
			<label>Email</label>

			<form:input path="usr.email" name="email" id="email" required="true" />
		</div>

		<div class="formfield">
			<label>Password</label>

			<form:password path="usr.password" name="password" id="password"
				required="true" />
		</div>

		<div class="formfield">
			<label>Mobile</label>

			<form:input path="usr.mobileNumber" name="mobileNumber"
				id="mobileNumber" />
		</div>


		 
		<div class="formfield savebtn">
			<input class="save" type="submit" value="Save User">
			<!--   <input type="submit" value="Save and add another"> -->
			<input type="button" value="Cancel" onClick="goback()">
		</div>

	</form>


</body>
</html>