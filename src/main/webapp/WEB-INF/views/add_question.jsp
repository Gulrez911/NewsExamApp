<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Add Question</title>
<link href="./resources/userprofile/images/E-assess_E.png" rel="shortcut icon" >
<link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,700" rel="stylesheet">
<!-- <link rel="stylesheet" href="./resources/assets/style2.css"> -->

<link href="./resources/assets/flexSlider/flexslider.css" rel="stylesheet" type="text/css" />
<link href="./resources/Multi Select/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/css/shortcodes/shortcodes.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/materialize/css/materialize.min2.css" rel="stylesheet" type="text/css" />
<link href="./resources/css/pnotify.custom.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="./resources/userprofile/css/bootstrap.css">
<link rel="stylesheet" href="./resources/userprofile/css/style.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylsheete" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="./resources/userprofile/css/animate.css">
<link rel="stylesheet" href="./resources/userprofile/css/icomoon.css">

<link href="./resources/userprofile/css/bootstrap4.css" rel="stylesheet">


<link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.33.1/sweetalert2.css">
<link rel="stylesheet" href="./resources/userprofile/css/menuzord.css">
<link rel="stylesheet" href="./resources/userprofile/css/cardstyle.css">

<link href="./resources/assets/materialize/css/materialize.min2.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/css/bootstrap-select.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/js/bootstrap-select.min.js"></script>

<style>
body {
	font-family: medium-content-serif-font, Georgia, Cambria,
		"Times New Roman", Times, serif;
}
</style>
<script>
	function goback() {
		window.location = "lmsQuestion_list";
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
</head>
<body id="top" class="has-header-search">
 
	<!--header start-->
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

	<!--header end-->

	<section>
		<div class="container module-content">

			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<div class="mb-30">
						<h2 class="section-title" style="text-align: center;">${question_label}</h2>
					</div>
					<form name="questionForm" method="post" modelAttribute="question" action="saveQuestion" enctype="multipart/form-data">

						<div class="input-field">
							<form:textarea path="question.questionText" required="true" class="materialize-textarea" />
							<form:hidden path="question.id" />
							<form:hidden path="question.companyId" />
									<form:hidden path="question.companyName" />
							<label for="questionText">Question</label>
						</div>


						<div id="mcqDiv" class="formfield">
							<div class="row">
								<div class="col-md-12 mt-20 mb-20" style="padding-top: 15px;padding-bottom: 15px">
									<div class="col-md-1" style="font-weight: 700; color: black;">Options</div>
									<div class="col-md-8"></div>
									<div class="col-md-4 float-right" style="font-weight: 700; color: black;    padding-left: 45px;">Correct Choice</div>
								</div>
								<div class="col-md-12">
									<div class="col-md-1" style="color: black;">A</div>
									<div class="col-md-8">
										<form:input path="question.choice1" name="choice1" id="choice1" required="true" />
									</div>
									<div class="col-md-3 text-center">
										<form:checkbox path="question.one" id="one1" class="filled-in" label="" />
										<!-- 																					<input type="checkbox" id="one1" name="one" value="true" class="filled-in" />  -->
										<!-- 																				<label for="one1"></label> -->
									</div>
								</div>
								<div class="col-md-12">
									<div class="col-md-1" style="color: black;">B</div>
									<div class="col-md-8">
										<form:input path="question.choice2" name="choice2" id="choice2" required="true" />
									</div>
									<div class="col-md-3 text-center">
										<form:checkbox path="question.two" id="two1" label="" />
										<!-- 																				<input type="checkbox" id="two1" name="two" value="true" class="filled-in" /> -->
										<!-- 																				 <label -->
										<!-- 																						for="two1"></label> -->
									</div>
								</div>
								<div class="col-md-12">
									<div class="col-md-1" style="color: black;">C</div>
									<div class="col-md-8">
										<form:input path="question.choice3" name="choice3" id="choice3" />
									</div>
									<div class="col-md-3 text-center">
										<form:checkbox path="question.three" label="" id="three1" />
										<!-- 																				<input type="checkbox" id="three1" name="three" value="true" class="filled-in" /> -->
										<!-- 																				<label for="three1"></label> -->
									</div>
								</div>
								<div class="col-md-12">
									<div class="col-md-1" style="color: black;">D</div>
									<div class="col-md-8">
										<form:input path="question.choice4" name="choice4" id="choice4" />
									</div>
									<div class="col-md-3 text-center">
										<form:checkbox path="question.four" id="four1" label="" />
										<!-- 																				<input type="checkbox" id="four1" name="four" value="true" class="filled-in" /> -->
										<!-- 																				<label for="four1"></label> -->
									</div>
								</div>
								<div class="col-md-12">
									<div class="col-md-1" style="color: black;">E</div>
									<div class="col-md-8">
										<form:input path="question.choice5" name="choice5" id="choice5" />
									</div>
									<div class="col-md-3 text-center">
										<form:checkbox path="question.five" id="five1" label="" />
										<!-- 																				<input type="checkbox" id="five1" name="five" value="true" class="filled-in" /> -->
										<!-- 																				<label for="five1"></label> -->
									</div>
								</div>
								<div class="col-md-12">
									<div class="col-md-1" style="color: black;">F</div>
									<div class="col-md-8">
										<form:input path="question.choice6" name="choice6" id="choice6" />
									</div>
									<div class="col-md-3 text-center">
										<form:checkbox path="question.six" id="six1" label="" />
										<!-- 																				<input type="checkbox" id="six1" name="six" value="true" class="filled-in" /> <label -->
										<!-- 																						for="six1"></label> -->
									</div>
								</div>
							</div>
						</div>


				 
						<!--End Fill in the blanks  -->
						<!-- Start Match the following -->
				 
						<!--  End match the following-->
						<!-- start full stack -->
						 
						<!--  end full stack-->
						 

						<div class="row">
							<div class="col-md-12 mt-20 mb-20" style="padding-bottom: 15px">
								<div class="col-md-12" style="font-weight: 700; color: black;">Choose Categories for Question</div>
							</div>
							<div class="col-md-12">
								<div class="col-md-1" style="color: black;">A</div>
								<div class="col-md-11">
									<form:input path="question.qualifier1" name="qualifier1" id="qualifier1" required="true" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="col-md-1" style="color: black;">B</div>
								<div class="col-md-11">
									<form:input path="question.qualifier2" name="qualifier2" id="qualifier2" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="col-md-1" style="color: black;">C</div>
								<div class="col-md-11">
									<form:input path="question.qualifier3" name="qualifier3" id="qualifier3" />
								</div>
							</div>
						</div>


						<div class="input-field">
							<form:textarea path="question.instructionsIfAny" id="output" class="materialize-textarea" />
							<label for="output">Instructions, if any</label>
						</div>


						<button type="button" class="btn btn-danger float-left"
							onclick="goback()" style="margin-right: 20px;">
							<i class="fa fa-arrow-circle-left" aria-hidden="true"></i> Back
						</button>
						<button type="submit" name="submit"
							class="btn btn-success float-right">
							<i class="fa fa-floppy-o" aria-hidden="true"></i> Save
						</button>
						<div class="mb-30"></div>
					</form>
				</div>
				<div class="col-md-3"></div>
			</div>
		</div>
	</section>

 
 
 
</body>

</html>