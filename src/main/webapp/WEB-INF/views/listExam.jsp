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
				<li class="nav-item active" id="logo"><a href="showLearnerAdminDashboard"><img class="active"
						src="./eAssess/images/New B&amp;G copy.png" style="height: 53px; padding-top: 13px;"></img></a></li>
				<li class="nav-item"><a href="lmsModules">Manage Modules</a></li>
				<li class="nav-item active"><a class="nav-link" href="lmsQuestion_list">Question Bank</a></li>
				<li class="nav-item"><a class="nav-link " href="lmsTests">Test Bank</a></li>
				<li class="nav-item"><a class="nav-link " href="showAllResults">Results - Learners</a></li>
				<li class="nav-item"><a class="nav-link " href="showManualReviewResults">Review Tests</a></li>
				<li class="nav-item "><a class="nav-link " href="meetings">Meetings</a></li>
				<li class="nav-item"><a class="nav-link " href="signoff">Log Off</a></li>
			</ul>
		</nav>

		<div class="container">
			<input class="form-control" id="myInput" type="text" placeholder="Search.."> <a href="createExam"><button
					type="button" class="btn btn-primary">Create Exam</button></a> <a href="createCategory"><button type="button"
					class="btn btn-primary">Create Category</button></a> <a href="listExam"><button type="button"
					class="btn btn-primary">List Exam</button></a> <a href="listCategory"><button type="button" class="btn btn-primary">List
					Category</button></a>


			<table class="table table-hover">
				<thead class="thead-dark">
					<tr>
						<th scope="col">No</th>
						<th scope="col">Exam Name</th>
						<th scope="col">Test List</th>
						<th scope="col">Update</th>
						<th scope="col">Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${exams}" var="exam" varStatus="loop">
						<tr>
							<td scope="row">${loop.index}</td>
							<td>${exam.examName}</td>
							<td><c:forEach items="${exam.tests2}" var="test" varStatus="loop2">
							
							${test.testName},
							&nbsp&nbsp
							</c:forEach></td>

							<td><a href="createExam?eid=${exam.id}"><button type="button" class="btn btn-primary">Update</button></a></td>

							<td><a href="#" onclick="deleteExam(${exam.id})"><button type="button" class="btn btn-primary">Delete</button></a></td>

							<%-- 						<td>${loop.index}</td> --%>
							<!-- 						<td>@mdo</td> -->
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>


		<!-- 		<div class="questiontablelist" style="overflow-x: scroll; height: 500px;"> -->
		<!-- 			<table class="table"> -->
		<!-- 				<thead> -->
		<!-- 					<tr> -->
		<!-- 						<th>No</th> -->
		<!-- 						<th>Exam Name</th> -->

		<!-- 						<th style="white-space: nowrap;">Category</th> -->
		<!-- 						<th>Difficulty Level</th> -->
		<!-- 						<th style="white-space: nowrap;">Updated On</th> -->

		<!-- 						<th style="white-space: nowrap;">Update</th> -->
		<!-- 						<th style="white-space: nowrap;">Delete</th> -->
		<!-- 					</tr> -->
		<!-- 				</thead> -->
		<!-- 				<tbody> -->
		<!-- 				<tbody> -->

		<%-- 					<c:forEach items="${qs}" var="question" varStatus="loop"> --%>
		<!-- 						<tr> -->

		<%-- 							<td>${loop.count}</td> --%>


		<%-- 							<td><c:out value="${question.questionText}"></c:out></td> --%>

		<%-- 							<td>${question.category}</td> --%>
		<%-- 							<td><c:out value="${question.difficultyLevel.level}"></c:out></td> --%>
		<%-- 							<td><c:out value="${question.updatedDate}"></c:out></td> --%>
		<%-- 							<td><a href="addQuestion?qid=${question.id}">Click </a></td> --%>
		<%-- 							<td><a href="javascript:confirm('${question.id}')">Click </a></td> --%>
		<!-- 						</tr> -->
		<%-- 					</c:forEach> --%>
		<!-- 				</tbody> -->

		<!-- 				</tbody> -->
		<!-- 			</table> -->
		<!-- 		</div> -->

	</div>


	<script type="text/javascript">


function sweetAlert(msgtype,message,icon){
	  Swal.fire(
		       msgtype,
		       message,
		       icon
		    )
	}

function deleteExam(id){
	 Swal.fire({
	  title: 'Are you sure?',
	  text: "You want to delete this Question ?",
	  icon: 'warning',
	  showCancelButton: true,
	  confirmButtonColor: '#3085d6',
	  cancelButtonColor: '#d33',
	  confirmButtonText: 'Yes, delete it!'
	}).then((result) => {
	  if (result.value) {
		 window.location = "deleteExam?eid=" + id;
	  }
	})
}

</script>

	<c:if test="${msgtype != null}">
		<script>
	var notification = 'Information';
	$(function() {
		    Swal.fire(
	       '${msgtype}',
	       '${message}',
	       '${icon}'
	    )
	});
</script>
	</c:if>



</body>
</html>

