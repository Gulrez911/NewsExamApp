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


<script>
	$(document).ready(function() {
		// 		$('[data-toggle="popover"]').popover();
	});
	/* When the user clicks on the button,
	 toggle between hiding and showing the dropdown content */
	function myFunction() {
		document.getElementById("myDropdown").classList.toggle("show");
	}

	function filterFunction() {
		var input, filter, ul, li, a, i;
		input = document.getElementById("myInput");
		filter = input.value.toUpperCase();
		div = document.getElementById("myDropdown");
		a = div.getElementsByTagName("a");
		for (i = 0; i < a.length; i++) {
			txtValue = a[i].textContent || a[i].innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				a[i].style.display = "";
			} else {
				a[i].style.display = "none";
			}
		}
	}
 	function myFunction() {
		document.getElementById("myDropdown").classList.toggle("show");
	}

	function filterFunction() {
		var input, filter, ul, li, a, i;
		input = document.getElementById("myInput");
		filter = input.value.toUpperCase();
		div = document.getElementById("myDropdown");
		a = div.getElementsByTagName("a");
		for (i = 0; i < a.length; i++) {
			txtValue = a[i].textContent || a[i].innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				a[i].style.display = "";
			} else {
				a[i].style.display = "none";
			}
		}
	}
</script>
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
				<li class="nav-item active" id="logo"><a href="showLearnerAdminDashboard"><img class="active" src="./eAssess/images/New B&amp;G copy.png" style="height: 53px; padding-top: 13px;"></img></a></li>
				<li class="nav-item"><a href="lmsModules">Manage Modules</a></li>
				<li class="nav-item active"><a class="nav-link" href="lmsQuestion_list">Question Bank</a></li>
				<li class="nav-item"><a class="nav-link " href="lmsTests">Test Bank</a></li>
				<li class="nav-item"><a class="nav-link " href="showAllResults">Results - Learners</a></li>
				<li class="nav-item"><a class="nav-link " href="showManualReviewResults">Review Tests</a></li>
				<li class="nav-item "><a class="nav-link " href="meetings">Meetings</a></li>
				<li class="nav-item"><a class="nav-link " href="signoff">Log Off</a></li>
			</ul>
		</nav>

		<div id="colorlib-services" class="container module-content">
			<div>
				<div class="row">
					<!-- 				<div class="col-2"></div> -->
					<div class="col-8" style="display: -webkit-inline-box;">
						<!-- 					<button class="btn btn-primary">Questions</button> -->
						<a href="addQuestion" class="btn btn-primary save"><i class="material-icons fa fa-plus-circle"></i> Questions</a> <span style="padding-left: 15px;"> <a href="javascript:showFileDialog();"
							id="uploadQuestionsLink" class="btn btn-primary save"><i class="material-icons fa fa-upload"></i> Import</a>
						</span> <span style="padding-left: 15px;"> <a href="verification" class="btn btn-primary save"><i class="material-icons fa fa-check"></i> Verify </a>
						</span>
						<div class="widget widget_search" style="padding-left: 15px;">
							<div class="search-form">
								<form action="searchQuestions" method="get">
									<input class="quest_search" type="text" placeholder="Search a question" name="searchText" value="${param.searchText}" id="searchText">
								</form>
							</div>
						</div>
					</div>
					<!-- 					<div class="col-2"> -->
					<!-- 					</div> -->
					<!-- 					<div class="col-2"> -->
					<!-- 					</div> -->
					<!-- 					<div class="col-2"> -->

					<!-- 					</div> -->
					<div class="col-md-4" style="float: right; padding-left: 27%; margin: 11px 0;" id="pagination">
						<c:if test="${showPreviousPage}">
							<a href="${callingMethod}?page=${previousPage}${queryParam}"><i class="fa fa-arrow-left"></i></a>
						</c:if>

						<c:if test="${selectedPage != null &&  selectedPage > 0}">
										                                    ${selectedPage} / ${totalNumberOfPages}
										                                </c:if>

						<c:if test="${showNextPage}">
							<a href="${callingMethod}?page=${nextPage}${queryParam}"><i class="fa fa-arrow-right"></i></a>
						</c:if>

					</div>
				</div>


			</div>

			<table class="table table-striped table-hover custom-table table-bordered" style="box-shadow: 0px 10px 35px -5px #a8a8e5;">
				<thead>
					<tr>
						<th><b>#</b></th>
						<th>Question</th>
						<th>Category</th>
						<th>Level</th>
						<th>Updated</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${qs}" var="question" varStatus="loop">
						<tr>
							<td>${loop.count}</td>
							<td><c:out value="${question.questionText}"></c:out></td>
							<td>${question.category}</td>
							<td><c:out value="${question.difficultyLevel.level}"></c:out></td>
							<td><c:out value="${question.updatedDate}"></c:out></td>
							<td style="text-align: center;"><a href="addQuestion?qid=${question.id}" style="margin-right: 10px;" title="Edit Test"><i class="fa fa-pencil" aria-hidden="true"></i> </a> <a
								onclick="deleteQ(${question.id})" href="#" title="Delete Test"><i class="fa fa-trash-o" aria-hidden="true"></i> </a></td>
						</tr>
					</c:forEach>
				</tbody>

			</table>
		</div>
	</div>

	<form id="fileFormQuestions" method="POST" enctype="multipart/form-data">
		<input type="file" name="fileQuestions" id="fileQuestions" style="display: none" />

	</form>
	<script>
		$('[data-toggle=offcanvas]').click(
				function() {
					$('.row-offcanvas').toggleClass('active');
					$('.collapse').toggleClass('in').toggleClass('hidden-xs')
							.toggleClass('visible-xs');
				});

		$('#search').on('click', function() {
			var text = document.getElementById("searchText").value;
			if (text.length != 0) {
				window.location = "searchQuestions?searchText=" + text;
			}
		});

		var isXlsx = function(name) {
			return name.match(/xlsx$/i)
		};

		$("#btnfile").click(function() {
			$("#uploadfile").click();
		});

		function showFileDialog() {

			$("#fileQuestions	").click();
		}

		$(document).ready(	function() {
					var file = $('[name="fileQuestions"]');
					var fileU = document.getElementById('fileQuestions');
					fileU.addEventListener("change",function() {
										if (fileU.files.length > 0) {
											var filename = $.trim(file.val());
											if (!(isXlsx(filename))) {
												alert("Hello! I am an alert box!!");

												notify('Error',
														'Please select an xlsx file to upload');
												return;
											}
											$.ajax({			xhr : function() {
																	var xhr = new window.XMLHttpRequest();
																	return xhr;
																},
																url : 'upload',
																type : "POST",
																data : new FormData(	document.getElementById("fileFormQuestions")),
																enctype : 'multipart/form-data',
																processData : false,
																contentType : false
															})
													.done(
															
															 
															function(
																	data) {
																sweetAlert("Congratulations","File Upload Successful","success");
//																	setTimeout(function(){}, 3000);																		
// 																location.reload();
															
															
															
															}
															
													
													)
													.fail(
															function(
																	jqXHR,
																	textStatus) {
																notify(
																		'Failure',
																		'File Upload Failed. Please contact Administrator');
															});
											document
													.getElementById('fileQuestions').value = null;
											return;
										}

									});

				});
 

		function notify(messageType, message) {
			var notification = 'Information';
			$(function() {
				new PNotify({
					title : notification,
					text : message,
					type : messageType,
					styling : 'bootstrap3',
					hide : true
				});
			});
		}

		function confirm(id) {
			(new PNotify({
				title : 'Confirmation Needed',
				text : 'Are you sure? Do you really want to delete this Q?',
				icon : 'glyphicon glyphicon-question-sign',
				hide : false,
				confirm : {
					confirm : true
				},
				buttons : {
					closer : false,
					sticker : false
				},
				history : {
					history : false
				}
			})).get().on('pnotify.confirm', function() {
				window.location = "lmsRemoveQuestionFromList?qid=" + id;
			}).on('pnotify.cancel', function() {

			});
		}

		function sweetAlert(msgtype,message,icon){
			  Swal.fire(
				       msgtype,
				       message,
				       icon
				    )
			}
		
		function deleteQ(id){
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
				 window.location = "removeQuestion?qid=" + id;
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

