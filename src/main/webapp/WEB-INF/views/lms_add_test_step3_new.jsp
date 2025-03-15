<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ctet.data.*, java.text.*, java.util.*, com.ctet.web.dto.*"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Add Test</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="./resources/userprofile/images/E-assess_E.png" rel="shortcut icon">

<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" /> -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">


<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="./resources/assets/flexSlider/flexslider.css" rel="stylesheet" type="text/css" />
<link href="./resources/Multi Select/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="./resources/userprofile/css/style.css">
<link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,700" rel="stylesheet">
<link rel="stylesheet" href="./resources/userprofile/css/animate.css">
<link rel="stylesheet" href="./resources/userprofile/css/icomoon.css">
<link href="./resources/assets/css/shortcodes/shortcodes.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/materialize/css/materialize.min2.css" rel="stylesheet" type="text/css" />
<!-- <link href="./resources/css/pnotify.custom.min.css" rel="stylesheet" type="text/css"> -->
<!-- <link href="./resources/css/pnotify.custom.min.css" rel="stylesheet" type="text/css"> -->
<!-- <link rel="stylesheet" href="./resources/userprofile/css/bootstrap.css"> -->
<!-- <link rel="stylesheet" href="./resources/userprofile/css/style.css"> -->
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> -->
<!-- <script type="text/javascript" src="scripts/pnotify.custom.min.js"></script> -->

<!-- <link rel="stylsheete" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css"> -->
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker3.min.css"> -->
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"> -->
<!--   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
<!-- <script src="https://cdn.jsdelivr.net/momentjs/2.14.1/moment.min.js"></script> -->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script> -->
<!--   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script> -->


<link href="./resources/userprofile/css/bootstrap4.css" rel="stylesheet">
<script src="./resources/userprofile/js/jquery.min.js"></script>
<script src="./resources/userprofile/js/popper.min.js"></script>
<script src="./resources/userprofile/js/bootstrap4.min.js"></script>
<link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.css">
<link rel="stylesheet" href="https://monim67.github.io/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" type="text/css" media="all" />

<script src="./resources/userprofile/js/demo.js"></script>
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.min.js"></script>
<script type="text/javascript" src="https://monim67.github.io/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="https://monim67.github.io/bootstrap-datetimepicker/js/demo.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.33.1/sweetalert2.css">
<!-- <link rel="stylesheet" href="./resources/userprofile/css/style.css"> -->
<link rel="stylesheet" href="./resources/userprofile/css/menuzord.css">
<link rel="stylesheet" href="./resources/userprofile/css/cardstyle.css">


<style>
#classModal {
	
}

.modal-body {
	overflow-x: auto;
}

.js .animate-box {
	opacity: 100 !important;
}

.classes .classes-img {
	display: block;
	position: relative;
	height: 100px;
	background-size: unset;
}

body {
	font-family: medium-content-serif-font, Georgia, Cambria,
		"Times New Roman", Times, serif;
}

div.scrollmenu {
	background-color: #333;
	overflow: auto;
	white-space: nowrap;
}

.test {
	overflow: auto;
	white-space: nowrap;
}

#myDropdown {
	min-width: 387px !important;
	height: 300px !important;
	overflow: auto !important;
}

div.scrollmenu {
	background-color: #333;
	overflow: auto;
	white-space: nowrap;
}

.test {
	overflow: auto;
	white-space: nowrap;
}
</style>

</head>
<body id="top" class="has-header-search" style="padding-bottom: 3%;">

	<!--header start-->
	<nav class="navbar navbar-expand-sm" style="background: #1a1ab1; height: 65px;">
		<ul class="navbar-nav menuzord-menu">
			<li class="nav-item active" id="logo"><a href="showLearnerAdminDashboard"><img class="active" src="./eAssess/images/New B&amp;G copy.png" style="height: 53px; padding-top: 13px;"></img></a></li>
			<li class="nav-item"><a href="lmsModules">Manage Modules</a></li>
			<li class="nav-item "><a class="nav-link" href="lmsQuestion_list">Question Bank</a></li>
			<li class="nav-item active"><a class="nav-link " href="lmsTests">Test Bank</a></li>
			<li class="nav-item"><a class="nav-link " href="showAllResults">Results - Learners</a></li>
			<li class="nav-item"><a class="nav-link " href="showManualReviewResults">Review Tests</a></li>
			<li class="nav-item "><a class="nav-link " href="meetings">Meetings</a></li>
			<li class="nav-item"><a class="nav-link " href="signoff">Log Off</a></li>
		</ul>
	</nav>
	<!--header end-->


	<section class="container module-content">

		<div class="container">
			<div class="row no-gutter">
				<div class="col-md-2 mb-20">
					<div class="col-md-12 col-sm-6 text-center">
						<div class="stepsitem" style="border-bottom: 1px solid #eef5fb;">
							<span>1</span>
							<div class="intro-header">
								<i class="fa fa-cog"></i>
								<h2 class="white-text">Set Your Test</h2>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-6 text-center">
						<div class="stepsitem">
							<span>2</span>
							<div class="intro-header">
								<i class="fa fa-question-circle"></i>
								<h2 class="white-text">Add Questions</h2>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-6 text-center">
						<div class="stepsitem active">
							<span>3</span>
							<div class="intro-header">
								<i class="fa fa-share-alt"></i>
								<h2 class="white-text">Share Test</h2>
							</div>
						</div>
					</div>
				</div>


				<div class="addteststeps2 col-md-10">

					<div class="col-md-12">

						<%-- 						<form:form method="POST" action="sharePublicTest" --%>
						<%-- 							modelAttribute="tests"> --%>
						<!-- 							<div class="col-md-12"> -->
						<!-- 								<div class="col-md-6"> -->
						<!-- 									<div class="row formfields"> -->
						<!-- 										<div class="col-md-4"></div> -->
						<!-- 										<div class="col-md-8"> -->
						<!-- 											<div class="input-field"> -->
						<!-- 												<input type="text" name="testId" id="testId" -->
						<%-- 													value="${tests.id}" hidden="true"> --%>
						<%-- 												<form:hidden path="test.id"  /> --%>
						<!-- 											</div> -->
						<!-- 										</div> -->
						<!-- 									</div> -->
						<!-- 								</div> -->
						<!-- 							</div> -->

						<!-- 							<div class="col-md-12"> -->
						<!-- 								<div class="col-md-6"> -->
						<!-- 									<div class="row formfields"> -->
						<!-- 										<div class="col-md-4"> -->
						<!-- 											<label class="fieldtitle">Existing test name</label> -->
						<!-- 										</div> -->
						<!-- 										<div class="col-md-8"> -->
						<!-- 											<div class="input-field"> -->
						<%-- 												<input id="existing_name1" value="${tests.testName}" --%>
						<!-- 													type="text" readonly="readonly" /> -->
						<!-- 											</div> -->
						<!-- 										</div> -->
						<!-- 									</div> -->
						<!-- 								</div> -->
						<!-- 							</div> -->
						<!-- 							<div class="col-md-12"> -->
						<!-- 								<div class="col-md-6"> -->
						<!-- 									<div class="row formfields"> -->
						<!-- 										<div class="col-md-4"> -->
						<!-- 											<label class="fieldtitle">First Name</label> -->
						<!-- 										</div> -->
						<!-- 										<div class="col-md-8"> -->
						<!-- 											<div class="input-field"> -->
						<!-- 												<input id="firstName" type="text" /> -->
						<!-- 											</div> -->
						<!-- 										</div> -->
						<!-- 									</div> -->
						<!-- 								</div> -->
						<!-- 							</div> -->
						<!-- 							<div class="col-md-12"> -->
						<!-- 								<div class="col-md-6"> -->
						<!-- 									<div class="row formfields"> -->
						<!-- 										<div class="col-md-4"> -->
						<!-- 											<label class="fieldtitle">Last name</label> -->
						<!-- 										</div> -->
						<!-- 										<div class="col-md-8"> -->
						<!-- 											<div class="input-field"> -->
						<!-- 												<input id="lastName" type="text" /> -->
						<!-- 											</div> -->
						<!-- 										</div> -->
						<!-- 									</div> -->
						<!-- 								</div> -->
						<!-- 							</div> -->
						<!-- 							<div class="col-md-12"> -->
						<!-- 								<div class="col-md-6"> -->
						<!-- 									<div class="row formfields"> -->
						<!-- 										<div class="col-md-4"> -->
						<!-- 											<label class="fieldtitle">Email Id</label> -->
						<!-- 										</div> -->
						<!-- 										<div class="col-md-8"> -->
						<!-- 											<div class="input-field"> -->
						<!-- 												<input id="userEmail" type="text" /> -->
						<!-- 											</div> -->
						<!-- 										</div> -->
						<!-- 									</div> -->
						<!-- 								</div> -->
						<!-- 							</div> -->
						<!-- 							<div class="col-md-12"> -->
						<!-- 								<div class="col-md-6"> -->
						<!-- 									<div class="row formfields"> -->
						<!-- 										<div class="col-md-4"> -->
						<!-- 											<label class="fieldtitle">Public Test URL</label> -->
						<!-- 										</div> -->
						<!-- 										<div class="col-md-8"> -->
						<!-- 											<div class="input-field"> -->
						<!-- 												<input id="publicTestUrl" type="text" -->
						<%-- 													value="${tests.publicUrl}" readonly="readonly" /> --%>
						<!-- 											</div> -->
						<!-- 										</div> -->
						<!-- 									</div> -->
						<!-- 								</div> -->
						<!-- 							</div> -->
						<!-- 							<div class="col-md-12"> -->

						<!-- 								<input type="button" value="Share" -->
						<!-- 									onClick="javascript:shareTest()" -->
						<!-- 									class="btn btn-primary popupbtn" /> -->
						<!-- 																								<button class="btn btn-primary popupbtn"  onClick="javascript:shareTest()" >Share</button> -->
						<!-- 							</div> -->
						<%-- 						</form:form> --%>



						<!--  Search User start here-->
						<div class="col-md-4">
							<div class="widget widget_search">

								<div class="search-form">
									<form action="lmsSearchUsers" method="get">
										<input type="text" placeholder="Search a User" name="searchText" id="searchText">
										<button type="submit" id="search"></button>
									</form>
								</div>
							</div>
						</div>
						<div class="col-md-8">
							<a class="btn btn-primary" href="lmsShowUsers" style="height: 42px; line-height: 21px">Show All Users</a> <a class="btn btn-success" href="lmsShowSelectedUsers"
								style="height: 42px; line-height: 21px">Show Selected</a> <a class="btn btn-danger" href="lmsRemoveAllUsers" style="height: 42px; line-height: 21px">Clear All</a>
						</div>
						<!--  Search User End Here-->
						<div class="col-md-4" style="margin-top: 3%;"></div>
						<div class="col-md-12">
							<!-- 														<h2>Add Candidates</h2> -->
							<div class="table-responsive" style="height: 200px">
								<table class="table  ">
									<thead>
										<tr>
											<th>Name</th>
											<th>Email</th>
											<th>Department</th>
											<th>Group</th>
											<th>Grade</th>
										</tr>
									</thead>

									<tbody>

										<c:forEach items="${users}" var="us">
											<tr bgcolor="${us.selected? '#33FFF9':'transparent'}">


												<td>${us.firstName}${us.lastName}</td>

												<td>${us.email}</td>
												<td>${us.department}</td>

												<td>${us.groupOfUser}</td>

												<td style="${us.selected? 'display: none;':''}"><a href="javascript:addU('${us.id}');">Click to Add</a></td>
												<td style="${us.selected? '':'display: none;'}"><a href="javascript:removeU('${us.id}');">Click to Remove</a></td>
											</tr>

										</c:forEach>
									</tbody>


								</table>
							</div>

						</div>
						<!-- add start and end date options -->
						<div class="col-md-4" style="margin-top: 3%;"></div>
						<div class="col-md-12">
							<div class="col-md-4">
								<div class="row formfields">
									<label class="fieldtitle">Link Start Date and Time</label>
									<div class="widget widget_search">
										<div class="search-form">
											<div class=' date' id='id_000'>
												<input type="text" id="startDate" style="margin-left: 0px; width: 106%;">
											</div>
										</div>
									</div>

									<!-- 									<div class="input-field"> -->
									<!-- 										<div class='input-group date' id='id_0'> -->
									<!-- 											<input type='text' class="quest_search input-group-addon" id="startDate" style="margin-left: 0px" /> -->
									<!-- 										</div> -->
									<!-- 									</div> -->
									<!-- 									<div class='input-group date' id='datetimepicker1'> -->

									<!-- 										<input type='text' class="form-control" id="startDate" -->
									<!-- 											style="height: 16px; width: 82%; border: 1px solid #202020; border-bottom: 1px solid #202020; border-right: none;" /> <span -->
									<!-- 											class="input-group-addon" style="border: 1px solid #202020"> <span class="glyphicon glyphicon-calendar"></span> -->
									<!-- 										</span> -->
									<!-- 									</div> -->
								</div>
							</div>
							<div class="col-md-4">
								<div class="row formfields">
									<label class="fieldtitle">Link End Date and Time</label>
									<div class="widget widget_search">
										<div class="search-form">
											<div class=' date' id='id_00'>
												<input type="text" id="endDate" style="margin-left: 0px; width: 106%;">
											</div>
										</div>
									</div>




									<!-- 									<div class='input-group date' id='datetimepicker2'> -->
									<!-- 										<input type='text' class="form-control" id="endDate" -->
									<!-- 											style="height: 16px; width: 82%; border: 1px solid #202020; border-bottom: 1px solid #202020; border-right: none;" /> <span -->
									<!-- 											class="input-group-addon" style="border: 1px solid #202020"> <span class="glyphicon glyphicon-calendar"></span> -->
									<!-- 										</span> -->
									<!-- 									</div> -->
								</div>
							</div>
						</div>

						<!-- end date options -->
						<div class="col-md-12">
							<h2 class="txt">PreView</h2>
							<div class="panel-group feature-accordion brand-accordion icon angle-icon" id="accordion-one">
								<c:forEach var="section" items="${test.sectionDtos}">
									<%
									int counter = 1;
									%>
									<div class="panel panel-default">
										<div class="panel-heading">
											<h3 class="panel-title">
												<a class="collapsed" data-toggle="collapse" data-parent="#accordion-one" href="#${section.sectionId}" aria-expanded="false">${section.sectionName}</a>
											</h3>
										</div>

										<%
										int count = 1;
										%>
										<div id="${section.sectionId}" class="panel-collapse collapse" aria-expanded="false" style="height: 0px;">

											<c:forEach var="ques" varStatus="status" items="${section.questions}">
												<div class="panel-body qus">
													<h3>
														<span><%=count%></span>&nbsp;&nbsp;${ques.questionText}
													</h3>
													<h4 style="${ques.choice1 == null || ques.choice1.length() == 0? 'display: none;':''}">Choice 1: &nbsp;&nbsp; ${ques.choice1}</h4>
													<h4 style="${ques.choice2 == null || ques.choice2.length() == 0? 'display: none;':''}">Choice 2: &nbsp;&nbsp; ${ques.choice2}</h4>
													<h4 style="${ques.choice3 == null || ques.choice3.length() == 0? 'display: none;':''}">Choice 3: &nbsp;&nbsp; ${ques.choice3}</h4>
													<h4 style="${ques.choice4 == null || ques.choice4.trim().length() == 0? 'display: none;':''}">Choice 4: &nbsp;&nbsp; ${ques.choice4}</h4>
													<h4 style="${ques.choice5 == null || ques.choice5.trim().length() == 0? 'display: none;':''}">Choice 5: &nbsp;&nbsp; ${ques.choice5}</h4>
													<h4 style="${ques.choice6 == null || ques.choice6.length() == 0? 'display: none;':''}">Choice 6: &nbsp;&nbsp; ${ques.choice6}</h4>
												</div>

												<%
												count++;
												%>
											</c:forEach>

										</div>
									</div>
									<%
									counter++;
									%>
								</c:forEach>

							</div>

						</div>
						<div class="col-md-12">
							<a class="btn btn-danger" href="lmsAddteststep2" style="margin-right: 20px;"><i class="fa fa-arrow-circle-left" aria-hidden="true"></i> Back</a>

							<div class="pull-right">
								<a class="btn btn-primary" id="next" href="javascript:shareTests()"> NEXT <i class="fa fa-arrow-circle-o-right" aria-hidden="true"></i>
								</a>
							</div>


						</div>
					</div>
				</div>


			</div>

		</div>

	</section>
	<!-- Share Test Popup -->
	<div id="modalshare" class="modal fade modalcopy" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">Ã—</button>
					<h4 class="modal-title">Share Test</h4>
				</div>
				<div class="modal-body">
					<form>
						<label>Existing test name</label> <input id="existing_name1" type="text"> <label>First Name</label> <input id="firstName" type="text"> <label>Last name</label> <input
							id="lastName" type="text"> <label>Email Id</label> <input id="userEmail" type="text"> <label>Public Test URL</label> <input id="publicTestUrl" type="text"> <input
							type="hidden" name="testId" id="testId" value="">
						<div class="buttons text-center" style="padding-top: 20px;">
							<i class="waves-effect waves-light btn waves-input-wrapper" style=""><input class="waves-button-input" type="button" value="Copy in your Clipboard" onclick="javascript:copyUrlInClipBoard()"></i>
							<!-- <i
								class="waves-effect waves-light btn waves-input-wrapper"
								style=""><input class="waves-button-input" type="button"
								value="Share" onclick="javascript:shareTest()"></i> -->
							<a href="javascript:shareTest();"> Share</a> <i class="waves-effect waves-light btn waves-input-wrapper" style=""><input class="waves-button-input" type="button" value="Close"
								data-dismiss="modal"></i>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- jQuery -->
	<!-- <script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js'></script> -->
	<!-- <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script> -->
	<!-- <script src='https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.min.js'></script> -->
	<!-- <script src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/3.1.3/js/bootstrap-datetimepicker.min.js'></script> -->

	<script>
		$(function() {

			$('#datetimepicker1').datetimepicker({
				format : 'DD/MM/YYYY hh:mm a'
			//minDate:new Date()

			});

			$('#datetimepicker2').datetimepicker({
				format : 'DD/MM/YYYY hh:mm a'

			});
		});
		// Add section
		//                                        var counter = 1;
		//                                        $(document).on('click', '.addquestion', function () {
		//                                            counter++;
		//                                            $(".quesectiondiv").append("<div class='quesection'><h4>Section " + counter + "</h4></div>");
		//                                        });
		$(document).on('click', '#update', function() {
			$('#modalsection').modal('show');
		});
		// 			var counter = 1;
		// 			$(document)
		// 					.on(
		// 							'click',
		// 							'.addSecstion',
		// 							function() {
		// 								$('#modalsection').modal('hide');
		// 								counter++;
		// 								$(".quesectiondiv")
		// 										.append(
		// 												"<div class='quesection'>Section "
		// 														+ counter
		// 														+ "<a id='delete'><i class='fa fa-trash'></i><a id='update'><i class='fa fa-edit'></i></a></a></div>");
		// 							});
	</script>

	<script>
		var point = false;
		var count = 0;
		function check(e, value) {
			//Check Charater
			debugger;
			if (count == 3)
				return false;
			var unicode = e.charCode ? e.charCode : e.keyCode;
			if (unicode == 46 && point == true)
				return false;
			if (unicode == 46 && point == false) {
				point = true;
			}
			if (unicode != 8)
				if ((unicode<48||unicode>57) && unicode != 46)
					return false;
			if (point == true)
				count++;
		}
		function checkLength() {
			var fieldVal = document.getElementById('txtF').value;
			//Suppose u want 3 number of character
			if (fieldVal <= 100) {
				return true;
			} else {
				var str = document.getElementById('txtF').value;
				str = str.substring(0, str.length - 1);
				document.getElementById('txtF').value = str;
			}
		}
	</script>

	<script type="text/javascript">
		function saveSection() {
			var name = document.getElementById('sectionTopic').value;
			var txtFValue = document.getElementById('txtF').value;
			if (name.trim().length == 0) {
				notify('Information', 'Please enter a meaningful name for your section before saving. ');
			} else {
				// 					window.location='addNewSection';
				window.location = 'saveSection?sectionTopic=' + name + '&percentage=' + txtFValue;
			}
		}
		function addQ(qid, sectionName) {
			//window.location = "addQuestionToSection?sectionName="+sectionName+"&questionId="+qid;
			var url = "addQuestionToSectionAjax?sectionName=" + sectionName + "&questionId=" + qid;
			console.log('here url ' + url);
			$.ajax({
				url : url,
				success : function(data) {
					console.log("SUCCESS: ", data);
					var tr = document.getElementById(qid);
					var tds = tr.getElementsByTagName("td");
					var tdadd = document.getElementById(qid + "-add");
					var tdremove = document.getElementById(qid + "-remove");
					console.log(tr);
					console.log(tds);
					console.log(tdadd);
					tr.style.backgroundColor = '#33FFF9';
					tdadd.style.display = "none";
					tdremove.style.display = "";
					//document.getElementById("no-"+sectionName).innerHTML = data;
				},
				error : function(e) {
					console.log("ERROR: ", e);
				}
			});
		}
		function removeQ(qid, sectionName) {
			//window.location = "removeQuestionToSection?sectionName="+sectionName+"&questionId="+qid;
			var url = "removeQuestionToSectionAjax?sectionName=" + sectionName + "&questionId=" + qid;
			console.log('here url ' + url);
			$.ajax({
				url : url,
				success : function(data) {
					console.log("SUCCESS: ", data);
					var tr = document.getElementById(qid);
					var tds = tr.getElementsByTagName("td");
					var tdadd = document.getElementById(qid + "-add");
					var tdremove = document.getElementById(qid + "-remove");
					console.log(tr);
					console.log(tds);
					console.log(tdadd);
					tr.style.backgroundColor = 'transparent';
					tdadd.style.display = "";
					tdremove.style.display = "none";
					//document.getElementById("no-"+sectionName).innerHTML = data;
				},
				error : function(e) {
					console.log("ERROR: ", e);
				}
			});
		}
		function showSelected() {
			$(".table-responsive").hide();
			$(".selectedQ").show();
			load();
		}
		// 			function showSelected() {
		// 				window.location = "showSectionsQuestions";
		// 			}
		dta = "";
		load = function() {
			$.ajax({
				url : 'lmsShowSectionsQuestions',
				type : 'GET',
				success : function(response) {
					alert("test");
					console.log("hello");
					console.log("test", response.qs[0].questionText);
					console.log("test", response.qs[0].testCategory);
					console.log("test", response.qs[0].questionText);
					console.log("test", response.qs[0].questionText);
					console.log("test", response.qs[0].questionText);
					data = response.qs;
					for (i = 0; i < response.qs.length; i++) {
						console.log(data[i].questionText);
						$("#table1").append(
								"<tr class='tr'> <td> " + response.data[i].questionText + " </td>  <td> <a href='#' onclick= edit(" + i
										+ ");> Edit </a>  </td> </td> <td> <a href='#' onclick='delete_(" + response.data[i].questionText
										+ ");'> Delete </a>  </td> </tr>");
					}
				}
			});
		};
	</script>
	<div class="hiddendiv common"></div>
	<footer class="footer footer-four"> </footer>
	<script type="text/javascript">
		function shareTests() {
			console.log('in sharetests');
			var startDate = document.getElementById("startDate").value;
			console.log('in sharetests ' + startDate);
			var endDate = document.getElementById("endDate").value;
			if (startDate == null || startDate == '') {
				sweetAlert('Information', 'Start Date and Time not present for the Test Link to be shared with the Candidate(s)', 'warning');
				return;
			}

			if (endDate == null || endDate == '') {
				sweetAlert('Information', 'End Date and Time not present for the Test Link to be shared with the Candidate(s)', 'warning');
				return;
			}
			startDate = encodeURI(startDate);
			endDate = encodeURI(endDate);
			window.location = "lmsShareTestWithUsers?startDate=" + startDate + "&endDate=" + endDate;
		}

		$('#search').on('click', function() {
			var text = document.getElementById("searchText").value;
			if (text.length != 0) {
				window.location = "searchUsers?searchText=" + text;
			}
		});
		function addU(uid) {
			window.location = "lmsAddUserToTest?userId=" + uid;

		}
		function removeU(uid) {
			window.location = "lmsRemoveUserToTest?userId=" + uid;
		}
		function showSelected() {
			window.location = "lmsShowSelectedUsers";
		}
		function shareOpen(testName, testPublicUrl, testId) {
			var name = $(this).attr('data-name');
			console.log('here ' + testName);
			document.getElementById("existing_name1").value = testName;
			document.getElementById("publicTestUrl").value = testPublicUrl;
			document.getElementById("testId").value = testId;
			$('#modalcopy').modal('hide');
			$('#modalshare').modal('show');
		}
		function copyUrlInClipBoard() {
			el = document.createElement('textarea');
			el.value = document.getElementById("publicTestUrl").value;
			document.body.appendChild(el);
			el.select();
			document.execCommand('copy');
			document.body.removeChild(el);
			//$('#modalshare').modal('hide');
		}
		function copyUrlClose() {
			$('#modalshare').modal('hide');
		}
		function shareTest() {
			var existing_name1 = document.getElementById("existing_name1").value;
			var firstName = document.getElementById("firstName").value;
			var lastName = document.getElementById("lastName").value;
			var userEmail = document.getElementById("userEmail").value;
			var testId = document.getElementById("testId").value;
			if (firstName == '' || firstName == null) {
				notify('Info', 'First Name can not be blank');
			} else if (lastName == '' || lastName == null) {
				notify('Info', 'Last Name can not be blank');
			} else if (userEmail == '' || userEmail == null) {
				notify('Info', 'Email can not be blank');
			} else if (!validateEmail(userEmail)) {
				notify('Info', 'Enter a valid email');
			} else {
				window.location = "sharePublicTest?existing_name1=" + existing_name1 + "&firstName=" + firstName + "&lastName=" + lastName
						+ "&userEmail=" + userEmail + "&testId=" + testId;
			}
		}
		function validateEmail(email) {
			var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			return re.test(email);
		}
		$('#search').on('click', function() {
			var text = document.getElementById("searchText").value;
			if (text.length != 0) {
				window.location = "searchTests?searchText=" + text;
			}
		});
		function confirm(id) {
			(new PNotify({
				title : 'Confirmation Needed',
				text : 'Are you sure? Students having the link to this exam may no longer be able to take the exam',
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
				window.location = "retireTest?testId=" + id;
			}).on('pnotify.cancel', function() {
			});
		}

		function sweetAlert(msgtype, message, icon) {
			Swal.fire(msgtype, message, icon)
		}
	</script>
	<c:if test="${msgtype != null}">
		<script>
			var notification = 'Information';
			$(function() {
				Swal.fire('${msgtype}', '${message}', '${icon}')
			});
		</script>
	</c:if>


</body>
</html>