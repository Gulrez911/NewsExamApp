<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html>
<title>Home</title>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link href='http://fonts.googleapis.com/css?family=Oswald:400,300,700' rel='stylesheet' type='text/css'>
<style>
body {
	font-family: 'Georgia', sherif;
}
</style>
</head>
<body>
	<div class="container">
		<button class="btn btn-primary" onclick="window.location.href='add'">Add New</button>
		<button class="btn btn-primary" onclick="window.location.href='test'">Test</button>
		<table class="table table-sm table-dark">
			<th>First Name</th>
			<th>Last Name</th>
			<th>Password</th>
			<th>Action</th>
			<c:forEach items="${list}" var="std">
				<tr>
					<td>${std.firstName}</td>
					<td>${std.lastName}</td>
					<td>${std.password}</td>
					<td>
						<%-- <a href="delete?id=${std.id}" class="btn btn-primary">Delete</a> --%> <a href="delete?id=${std.id}"
						onclick="return confirm('Are you sure want  to delete?')" class="btn btn-primary">Delete</a> &nbsp&nbsp<a
						href="update?id=${std.id}" class="btn btn-danger">Update</a>
					</td>
				</tr>
			</c:forEach>

		</table>

	</div>
</body>
</html>
