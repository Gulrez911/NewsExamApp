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
	<form action="uploadtest" enctype="multipart/form-data" method="post">
		<table>
			<tr>
				<td><input type="file" name="file"></td>
				<td><button type="submit">Submit</button></td>
			</tr>
		</table>
	</form>
	<span style="color: green; font-size: 14px;">${msg}</span>
	
	<button class="btn btn-primary" onclick="downloadFile()">Download</button>
</body>
<script>
function downloadFile(){
	console.log("test")
	window.location.href="downloadFile";
}
</script>
</html>