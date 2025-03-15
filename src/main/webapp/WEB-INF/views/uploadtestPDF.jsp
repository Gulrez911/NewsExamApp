<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Upload Page</title><link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css"><!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<h2>Hello World!</h2>
	 
	<span style="color: green; font-size: 14px;">${msg}</span>
	<a href="downloadQuestion?loc=${pdfImage}" class="btn btn-primary">Download</a>
<%-- 	<button class="btn btn-primary" onclick="downloadFile(${pdfImage})">Download</button> --%>
</body>
<script>
function downloadFile(loc){
	console.log("test")
	window.location.href="downloadFile?"+loc;
}
</script>
</html>