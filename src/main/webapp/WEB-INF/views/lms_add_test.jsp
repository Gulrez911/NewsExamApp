<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ctet.data.*, java.text.*, java.util.*"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>eAssess</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="./resources/userprofile/images/E-assess_E.png" rel="shortcut icon">





<link href="./resources/userprofile/css/bootstrap4.css" rel="stylesheet">


<!-- <script src="./resources/userprofile/js/jquery.min.js"></script> -->
<!-- <script src="./resources/userprofile/js/popper.min.js"></script> -->
<!-- <script src="./resources/userprofile/js/bootstrap4.min.js"></script> -->
<link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.33.1/sweetalert2.css">
<!-- <link rel="stylesheet" href="./resources/userprofile/css/style.css"> -->
<link rel="stylesheet" href="./resources/userprofile/css/menuzord.css">
<link rel="stylesheet" href="./resources/userprofile/css/cardstyle.css">

<link href="./resources/assets/materialize/css/materialize.min2.css" rel="stylesheet" type="text/css" />



<!--     <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"> -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/css/bootstrap-select.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/js/bootstrap-select.min.js"></script>



<style>
body {
	font-family: medium-content-serif-font, Georgia, Cambria,
		"Times New Roman", Times, serif;
}
.dropdown-toggle::after {
    margin-top: -20px;
    margin-right: -18px;
    }
</style>



</head>
<body id="top" class="has-header-search">
	<div id="page">


		<nav class="navbar navbar-expand-sm" style="background: #1a1ab1; height: 65px;">
			<ul class="navbar-nav menuzord-menu">
							<li class="nav-item active" style="padding-right: 35px;"><a href="showLearnerAdminDashboard"><img class="active" src="./eAssess/images/New B&amp;G copy.png" style="height: 53px; padding-top: 13px;margin-bottom: 10px;"></img></a></li>
				<li class="nav-item"><a href="lmsModules">Manage Modules</a></li>
				<li class="nav-item "><a class="nav-link" href="lmsQuestion_list">Question Bank</a></li>
				<li class="nav-item active"><a class="nav-link " href="lmsTests">Test Bank</a></li>
				<li class="nav-item"><a class="nav-link " href="showAllResults">Results - Learners</a></li>
				<li class="nav-item"><a class="nav-link " href="showManualReviewResults">Review Tests</a></li>
				<li class="nav-item "><a class="nav-link " href="meetings">Meetings</a></li>
				<li class="nav-item"><a class="nav-link " href="signoff">Log Off</a></li>
			</ul>
		</nav>

		<section class="container module-content">
			<div class="row no-gutter">
				<div class="col-md-2 mb-20">
					<div class="col-md-12 col-sm-6 text-center">
						<div class="stepsitem active">
							<span>1</span>
							<div class="intro-header">
								<i class="fa fa-cog"></i>
								<h2 class="white-text">Set Your Test</h2>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-6 text-center">
						<div class="stepsitem" style="border-bottom: 1px solid #eef5fb;">
							<span>2</span>
							<div class="intro-header">
								<i class="fa fa-question-circle"></i>
								<h2 class="white-text">Add Questions</h2>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-6 text-center">
						<div class="stepsitem">
							<span>3</span>
							<div class="intro-header">
								<i class="fa fa-share-alt"></i>
								<h2 class="white-text">Share Test</h2>
							</div>
						</div>
					</div>
				</div>
				
				<div class="col-md-10 mb-20 addtestform">
					<form id="step1" method="POST" action="lmsSaveAndGoToStep2" modelattribute="test">

						<div class="col-md-12">
							<div class="row">
								<div class="col-md-6">
									<div class="row formfields">
										<div class="col-md-4">
											<label class="fieldtitle">Test Title</label>
										</div>
										<div class="col-md-8">
											<div class="input-field">
												<form:input path="test.testName" name="testName" id="testName" required="true" placeholder="Test Name" />
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-6">
								<div class="row formfields">
									<div class="col-md-4">
										<label class="fieldtitle">Duration (in mins.)</label>
									</div>
									<div class="col-md-4">
										<div class="input-field">
											<form:input path="test.testTimeInMinutes" name="testTimeInMinutes" id="testTimeInMinutes" value="" placeholder="Enter time in minutes"
												required="required" />

										</div>
									</div>
									<div class="col-md-4">
										<div class="input-field">
											<div class="select-wrapper">
<!-- 												<span class="select-dropdown " data-activates="select-options-929d5f36-9ec3-d673-a141-5fd2a86c6930">Minutes</span> -->
												<select
													class="initialized">
													<option selected="">Minutes</option>
												</select>
											</div>
										</div>
									</div>
								</div>
							</div>
							</div>
						</div>

						<div class="col-md-12">
						<div class="row">
							<div class="col-md-6">
								<div class="row formfields">
									<div class="col-md-4">
										<label class="fieldtitle">Passmark in Percentage</label>
									</div>
									<div class="col-md-8">
										<div class="input-field">
											<input id="passPercent" name="passPercent" type="text" value="60.0" required="true">
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row formfields">
									<div class="col-md-4">
										<label class="fieldtitle">No.of attempt </label>
									</div>
									<div class="col-md-8">
										<div class="input-field">
											<form:input path="test.noOfConfigurableAttempts" />
										</div>
									</div>
								</div>
							</div>
</div>
						</div>
						<div class="col-md-12">
						<div class="row">
							<div class="col-md-6">
								<div class="row formfields">
									<div class="col-md-4">
										<label class="fieldtitle">Conducted by</label>
									</div>
									<div class="col-md-8">
										<div class="input-field">
											<form:input path="user.email" name="email" id="email" value="" disabled="true" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row formfields">
									<div class="col-md-4">
										<label class="fieldtitle">Test Type </label>
									</div>
									<div class="col-md-8">
										<div class="input-field">
											<select id="testType" name="testType">
												<option value="Java" selected="selected">Java</option>
												<option value="Microsoft technologies">Microsoft technologies</option>
												<option value="C/C++">C/C++</option>
												<option value="Python">Python</option>
												<option value="General Knowledge">General Knowledge</option>
												<option value="Composite Test">Composite Test</option>
											</select>
										</div>
									</div>
								</div>
							</div>
</div>
						</div>
						<div class="col-md-12">
						<div class="row">
							<div class="col-md-6">
								<div class="row formfields">
									<div class="col-md-4">
										<label class="fieldtitle">Email Domain for Test Givers </label>
									</div>
									<div class="col-md-8">
										<div class="input-field">
											<form:input path="test.domainEmailSupported" name="domainEmailSupported" value="" placeholder="Enter an email domain" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row formfields">
									<div class="col-md-8">
										<label class="fieldtitle">Displayed upon exam completion and email communications relating to the exam</label>
									</div>
									<div class="col-md-4">
										<form:checkbox path="test.showFinalScoreToParticipants" label="" />
									</div>
								</div>
							</div>
							</div>
						</div>
						<div class="col-md-12">
						<div class="row">
							<div class="col-md-6">
								<div class="row formfields">
									<div class="col-md-8">
										<label class="fieldtitle" for="sentToStudent1">Email a copy of the student's results to above contact</label>
									</div>
									<div class="col-md-4">
										<form:checkbox path="test.sentToStudent" label="" />

										<!-- 												showFinalScoreToParticipants -->
									</div>
								</div>
							</div>

							<!--  -->

							<div class="col-md-6">
								<div class="row formfields">
									<div class="col-md-8">
										<label class="fieldtitle">Share Recommendation Engine by Email</label>
									</div>
									<div class="col-md-4">
										<label> <form:checkbox path="test.sendRecommReport" id="sendRecommReport" label="" />
										</label>
									</div>
								</div>
							</div>
							</div>
						</div>
						<div class="col-md-12">
						<div class="row">
							<div class="col-md-6">
								<div class="row formfields">
									<div class="col-md-8">
										<label class="fieldtitle">Shuffle Questions in Test</label>
									</div>
									<div class="col-md-4">
										<div class="input-field">
											<form:checkbox path="test.randomQuestions" id="random" label="" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row formfields">
									<div class="col-md-4">
										<label class="fieldtitle">Proctor Settings</label>
									</div>
									<div class="col-md-8">
										<div class="input-field">
											<input type="checkbox" checked="" disabled=""> <label style="font-size: 12px;" for="sentToStudent1">Allow webcam proctoring </label>
											<input type="hidden" name="_sentToStudent" value="on">
										</div>
									</div>
								</div>
							</div>
							</div>
						</div>
						<div class="col-md-12">
						<div class="row">
							<div class="col-md-6">
								<div class="row formfields">
									<div class="col-md-12">
										<label class="fieldtitle" style="font-size: 14px;">Notification &amp; Messages</label> <label class="fieldtitle" style="font-size: 14px;">Exam
											Introduction Text</label> <label class="fieldtitle" style="font-size: 11.5px;">Users see this text just before they begin the exam</label>
									</div>
									<div class="col-md-12">
										<div class="input-field">
											<textarea id="intro" name="intro" class="materialize-textarea"></textarea>
											<label for="intro"></label>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row formfields">
									<div class="col-md-12">
										<label class="fieldtitle" style="font-size: 14px;">Text - Completion Success</label> <label class="fieldtitle" style="font-size: 14px;">Exam
											Introduction Text</label> <label class="fieldtitle" style="font-size: 11.5px;">Shown to the user if they pass the exam. A copy is also sent via
											email</label>
									</div>
									<div class="col-md-12">
										<div class="input-field">
											<textarea id="postTestCompletionText" name="postTestCompletionText" class="materialize-textarea"></textarea>
											<label for="intro"></label>
										</div>
									</div>
								</div>
							</div>
							</div>
						</div>
						<div class="col-md-12">
							<a type="button" class="btn btn-danger float-left" href="lmsTests"
								style="margin-right: 20px;"><i class="fa fa-arrow-circle-left" aria-hidden="true"></i> Back</a>
							 <button type="submit" name="submit"
								class="btn btn-primary float-right" id="next">
								NEXT <i class="fa fa-arrow-circle-o-right" aria-hidden="true"></i>
							</button>
						
							 
							  
						</div>
					</form>

				</div>
				
			</div>
			
			
		</section>







	</div>



	<!-- jQuery -->
<script>
		$(document).ready(function() {
// 			$('select').material_select();
			$('#example-multiple-selected').selectpicker();
// 			var d=document.getElementsByClassName('filter-option');
// 			$(".filter-option").html();

// 			$("div[class='filter-option']").attr('class', 'newClass');
// 			$("div[class='filter-option-inner']").css("text-align", "center");
			$("#testTimeInMinutes").val(60);
			
// 			console.log($("div[class='filter-option']").html());
		});
	</script>
	<script>
		$('#next').on('click', function() {
			//addteststep2
			document.getElementById("step1").submit();
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
		</script>

		<c:if test="${msgtype != null}">
			<script>
				var notification = 'Information';
				$(function() {
					new PNotify({
						title : notification,
						text : '${message}',
						type : '${msgtype}',
						styling : 'bootstrap3',
						hide : true
					});
				});
			</script>
		</c:if>
</body>
</html>
