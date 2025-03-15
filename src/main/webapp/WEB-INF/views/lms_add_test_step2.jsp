<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ctet.data.*, java.text.*, java.util.*"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Add Test</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="./resources/userprofile/images/E-assess_E.png" rel="shortcut icon" >

<!-- <link rel="stylesheet" href="./resources/userprofile/css/style.css"> -->
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

<body id="top" class="has-header-search">

	<!--header start-->
	<nav class="navbar navbar-expand-sm" style="background: #1a1ab1; height: 65px;">
			<ul class="navbar-nav menuzord-menu">
								<li class="nav-item active" id="logo"><a href="showLearnerAdminDashboard"><img class="active" src="./eAssess/images/New B&amp;G copy.png" style="height: 53px; padding-top: 13px;"></img></a></li>
				<li class="nav-item"><a href="lmsModules">Manage Modules</a></li>
				<li class="nav-item "><a class="nav-link" href="addQuestion">Question Bank</a></li>
				<li class="nav-item active"><a class="nav-link " href="lmsTests">Test Bank</a></li>
				<li class="nav-item"><a class="nav-link " href="showAllResults">Results - Learners</a></li>
				<li class="nav-item"><a class="nav-link " href="showManualReviewResults">Review Tests</a></li>
				<li class="nav-item "><a class="nav-link " href="meetings">Meetings</a></li>
				<li class="nav-item"><a class="nav-link " href="signoff">Log Off</a></li>
			</ul>
		</nav>
	<!--header end-->

	<!-- <div style="margin-bottom:50px;"></div> -->
<section>
		<div class="container module-content">
			<div class="row no-gutter">
				<div class="col-md-2 mb-20">
					<div class="col-md-12 col-sm-6 text-center">
						<div class="stepsitem">
							<span>1</span>
							<div class="intro-header">
								<i class="fa fa-cog"></i>
								<h2 class="white-text">Set Your Test</h2>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-6 text-center">
						<div class="stepsitem active">
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


				<div class="addteststeps2 col-md-10">
					<div class="col-md-12">
						<div class="step2sections">
							<label style="font-size: 20px; color: #333;">Sections</label>



							<c:forEach items="${test.sectionDtos}" var="section">
								<div class="quesectiondiv">
									<div class="quesection">${section.sectionName}- ${section.noOfQuestions} <a
											href="javascript:removeSection('${section.sectionName}');" id="delete"><i
											class="fa fa-trash"></i></a> <a id='update'
											href="javascript:highlight('${section.sectionName}');"><i class='fa fa-edit'></i></a>
									</div>
								</div>
							</c:forEach>
							<label class="addquestion"><span>+</span> Add Section </label>
						</div>
						 
					</div>
					<div class="col-md-12">
							<a class="btn btn-danger" href="lmsGobackStep1Test"
								style="margin-right: 20px;"><i class="fa fa-arrow-circle-left" aria-hidden="true"></i> Back</a>
								<a class="btn btn-danger" href="lmsTests"
								style="margin-right: 20px;"><i class="fa fa-arrow-circle-left" aria-hidden="true"></i> Cancel</a>
							 <div class="pull-right">
							 <a class="btn btn-primary" id="next" href="lmsTests">
								SAVE <i class="fa fa-arrow-circle-o-right" aria-hidden="true"></i>
							</a>
							</div>
						</div>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript">
		$(document).on('click', '.addquestion', function() {
			window.location = "lmsAddNewSection";
		});
		function removeSection(sectionName) {
		 	 Swal.fire({
				  title: 'Confirmation Needed',
				  text: "Are you sure you want to delete the section"+ sectionName,
				  icon: 'warning',
				  showCancelButton: true,
				  confirmButtonColor: '#3085d6',
				  cancelButtonColor: '#d33',
				  confirmButtonText: 'Yes, delete it!'
				}).then((result) => {
				  if (result.value) {
					  window.location = "lmsRemoveSection?sectionName="	+ sectionName;
				  }
				})
	}
			
// 			(new PNotify({
// 				title : 'Confirmation Needed',
// 				text : 'Are you sure you want to delete the section '
// 						+ sectionName,
// 				icon : 'glyphicon glyphicon-question-sign',
// 				hide : false,
// 				confirm : {
// 					confirm : true
// 				},
// 				buttons : {
// 					closer : false,
// 					sticker : false
// 				},
// 				history : {
// 					history : false
// 				}
// 			})).get().on(
// 					'pnotify.confirm',
// 					function() {
// 						window.location = "lmsRemoveSection?sectionName="
// 								+ sectionName;
// 					}).on('pnotify.cancel', function() {

// 			});
		function saveSection() {
			var name = document.getElementById('sectionTopic').value;
			var txtFValue = document.getElementById('txtF').value;
			if (name.trim().length == 0) {
				notify('Information',
						'Please enter a meaningful name for your section before saving. ');

			} else {
				window.location = 'saveSection?sectionTopic=' + name
						+ '&percentage=' + txtFValue;

			}

		}

		function addQ(qid, sectionName) {

			//window.location = "addQuestionToSection?sectionName="+sectionName+"&questionId="+qid;
			var url = "addQuestionToSectionAjax?sectionName=" + sectionName
					+ "&questionId=" + qid;
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
			var url = "removeQuestionToSectionAjax?sectionName=" + sectionName
					+ "&questionId=" + qid;
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
			//$.ajax({url: "showSectionsQuestions", success: function(result){
			//		notify("Success", "All Questions Selected so far..");
			//	    }});
			window.location = "showSectionsQuestions";
		}

		function highlight(sectionName) {
			window.location = "lmsGoToSection?sectionName=" + sectionName;
		}

		

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

		$('#search').on('click', function() {
			var text = document.getElementById("searchText").value;
			if (text.length != 0) {
				window.location = "searchQs?searchText=" + text;
			}
		});

		$(function() {
			$(".addimage").on('click', function(e) {
				e.preventDefault();
				$("#addimage").trigger('click');
			});
			$(".addaudio").on('click', function(e) {
				e.preventDefault();
				$("#addaudio").trigger('click');
			});
			$(".addvideo").on('click', function(e) {
				e.preventDefault();
				$("#addvideo").trigger('click');
			});
		});

		$('#addimage').change(function() {
			var file = $('#addimage')[0].files[0].name;
			$('.queimage').text('Image: ' + file);
		});
		$('#addaudio').change(function() {
			var file = $('#addaudio')[0].files[0].name;
			$('.queaudio').text('Audio: ' + file);
		});
		$('#addvideo').change(function() {
			var file = $('#addvideo')[0].files[0].name;
			$('.quevideo').text('Video: ' + file);
		});
 
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
</html>