<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<title>Home</title>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link href='http://fonts.googleapis.com/css?family=Oswald:400,300,700'
	rel='stylesheet' type='text/css'>
<style>
body {
	font-family: 'Georgia', sherif;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<div class="container">

		<button onclick="addRow()" class="btn btn-primary">Add Row</button>
		<!-- 		<div id="row123"></div> -->
		<form:form id="frm" modelAttribute="student">
			<table id="tbl">

			</table>
		</form:form>
	</div>
</body>
<script>
	function addRow() {
		// 		alert("test");
		$("#frm").append(
				$("#tbl").append(
						"<tr>" + "<td>" + "<input  type='text'/onkeyup='change()' id='123'>" + "</td>"
								+ "<td>" + "<input type='text'/>" + "</td>"
								+ "<td>" + "<input type='text'/>" + "</td>"
								+ "<td>" + "<input type='text'/>" + "</td>"
								+ "</tr>")
		/* "<input type='text'/>" + "<input type='text'/>"
				+ "<input type='text'/>" */);
	}
	function change(){
// alert("test");
var x = $("#123").val();
console.log(x);
		}
</script>


</html>
