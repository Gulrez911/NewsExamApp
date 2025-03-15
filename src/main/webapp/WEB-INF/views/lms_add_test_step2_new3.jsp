<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ctet.data.*, java.text.*, java.util.*, com.ctet.web.dto.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Add Test</title>
<link href="./resources/userprofile/images/E-assess_E.png" rel="shortcut icon">

<link href="./resources/assets/flexSlider/flexslider.css" rel="stylesheet" type="text/css" />
<link href="./resources/Multi Select/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/css/shortcodes/shortcodes.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/materialize/css/materialize.min2.css" rel="stylesheet" type="text/css" />
<link href="./resources/css/pnotify.custom.min.css" rel="stylesheet" type="text/css">
<link href="./resources/assets/materialize/css/materialize.min2.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="./resources/userprofile/css/style.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="./resources/userprofile/css/bootstrap4.css" rel="stylesheet">
<link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.33.1/sweetalert2.css">
<link rel="stylesheet" href="./resources/userprofile/css/menuzord.css">
<link rel="stylesheet" href="./resources/userprofile/css/cardstyle.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>


<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<!-- <link rel="stylesheet" href="./resources/userprofile/css/bootstrap.css"> -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<style>
body {
	font-family: medium-content-serif-font, Georgia, Cambria,
		"Times New Roman", Times, serif;
}
</style>

</head>
<body id="top" class="has-header-search">

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
									<div class="quesection">${section.sectionName}-
										${section.noOfQuestions} <a href="javascript:removeSection('${section.sectionName}');" id="delete"><i class="fa fa-trash"></i></a> <a id='update'
											href="javascript:highlight('${section.sectionName}');"><i class='fa fa-edit'></i></a>
									</div>
								</div>
							</c:forEach>
							<label class="addquestion"><span>+</span> Add Section </label>
						</div>

					</div>
					<div class="col-md-12 quesectiondiv">
						<a class="btn btn-danger" href="lmsGobackStep1Test" style="margin-right: 20px;"><i class="fa fa-arrow-circle-left" aria-hidden="true"></i> Back</a> <a class="btn btn-danger" href="lmsTests"
							style="margin-right: 20px;"><i class="fa fa-arrow-circle-left" aria-hidden="true"></i> Cancel</a>
						<div class="pull-right">
							<a class="btn btn-primary" id="next" href="lmsTests"> SAVE <i class="fa fa-arrow-circle-o-right" aria-hidden="true"></i>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>



	<footer class="footer footer-four">
		<div class="secondary-footer brand-bg darken-2 text-center">
			<div class="container">
				<ul>
					<li><a href="javascript:notify('Information', 'We will release the feature pretty soon! Please wait for our next release');">Dashboard</a></li>
					<li><a href="question_list">Question Bank</a></li>
					<li class="active"><a href="testlist">Tests</a></li>
					<li><a href="skills">Skills</a></li>
					<li><a href="showReports">Results</a></li>
					<li><a href="codingSessions">Code Analysis Reports</a></li>
					<li><a href="showSkillTags">Skill based Reports</a></li>
					<li><a href="showProfileParams">Recomm Setting</a></li>
					<li><a href="listUsers">Users</a></li>
				</ul>
			</div>
		</div>
	</footer>



	<!-- Duplicate Test Popup -->
	<div id="modalsection" class="modal fade modalsection" role="dialog" style="padding-right: 329px;">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content" style="display: inline-block; width: 900px;">
				<div class="modal-header">
					<h4 class="modal-title">Add Section</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<div class="col-md-12 mb-20">
						<a href="showParentCategories" class="btn btn-default">Show All Parent Categories</a> <a href="javascript:showSelected();" id="showSelected" class="btn btn-success"> Show Selected</a>
						<!--  <a
														href="http://beforesubmit.com/qe-assess/addtest_step2.html#"
														class="btn waves-effect waves-light green">Show Selected</a>  -->
						<a href="lmsRemoveAllQuestions" class="btn btn-danger">Clear All</a>
						<!-- 														<a href="javascript:saveSection();" class="btn waves-effect waves-light">Save Section</a> -->
						<a data-dismiss="modal" class="btn btn-danger">Cancel</a> <a href="javascript:saveSection();" id="savesection" class="btn btn-primary">Save</a>
					</div>

					<div class="col-md-12 mb-20" style="padding-top: 10px">
						<div class="row" style="padding-left: 13px">

							<div class="col-md-3" style="padding: 0;">
								<div class="widget widget_search">
									<input type="text" class="form-control" id="sectionTopic" value="${sectionDto.sectionName}" placeholder="Enter Section Name">
								</div>
							</div>

							<div class="col-md-2" style="text-align: center; margin-top: 10px;">
								<div class="widget widget_search">

									<div class="search-form">
										Count: <span id="count">${sectionDto.noOfQuestions}</span>
									</div>
								</div>
							</div>

							<div class="col-md-4">
								<div class="widget widget_search">

									<div class="search-form">
										<form action="lmsSearchQs" method="get">
											<input type="text" placeholder="Search a question" name="searchText" id="searchText">
											<button type="submit" id="search">
												<i class="fa fa-search"></i>
											</button>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-9">
						<label style="font-size: 16px; color: #202020; font-weight: normal;" class="active">Percent of Qs to be asked - </label> <input type="number" onkeypress="return check(event, value)"
							oninput="checkLength()" id="txtF" placeholder="Enter percentage" style="width: 15%;" value="${sectionDto.percentQuestionsAsked}">
					</div>
				</div>

				<div class="col-md-12">
					<div class="table-responsive ">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Question</th>
									<th>Category</th>
									<th>Level</th>
									<th>Points</th>
									<th>Select</th>
									<th>Select All Qs in Category</th>
									<th>Remove All Qs in Category</th>
								</tr>
							</thead>

							<tbody>
								<%
								int count = 0;
								%>
								<c:forEach items="${qs}" var="ques">
									<tr id="${ques.id}" style="background-color: ${ques.selected? '#33FFF9':'transparent'}">


										<td><c:out value="${ques.questionText}"></c:out></td>

										<td>${ques.testCategory}</td>
										<td><c:out value="${ques.difficultyLevel.level}"></c:out></td>

										<td><input type="text" placeholder="Marks, if Correct" value="1  " disabled style="width: 100%;"></td>

										<td id="${ques.id}-add" style="${ques.selected? 'display: none;':''}"><a href="javascript:addQ('${ques.id}', 'Core Java');">Click to Add</a></td>
										<td id="${ques.id}-remove" style="${ques.selected? '':'display: none;'}"><a href="javascript:removeQ('${ques.id}', 'Core Java');">Click to Remove</a></td>
										<%-- 												<td><a href="${addQtoCategory}${ques.testCategoryToAddQs}">Add All</a></td> --%>
										<td>${ques.testCategoryToAddQs}</td>
										<td>${ques.testCategoryToRemoveQs}</td>
									</tr>
									<%
									count++;
									%>
								</c:forEach>
							</tbody>
						</table>
					</div>




				</div>
			</div>
		</div>
	</div>



	<!-- jQuery -->


	<script type="text/javascript">
		$(document).on('click', '.addquestion', function() {
			window.location = "lmsAddNewSection";

		});
		$('#search').on('click', function() {
			var text = document.getElementById("searchText").value;
			if (text.length != 0) {
				window.location = "searchQuestions2?searchText=" + text;
			}
		});

		$(window).on('load', function() {
			$('#modalsection').modal('show');
		});

		function highlight(sectionName) {
			window.location = "lmsGoToSection?sectionName=" + sectionName;
		}

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

		function saveSection() {
			var name = document.getElementById('sectionTopic').value;
			var txtFValue = document.getElementById('txtF').value;
			if (name.trim().length == 0) {
				notify('Information',
						'Please enter a meaningful name for your section before saving. ');

			} else {

				window.location = 'lmsSaveSection?sectionTopic=' + name
						+ '&percentage=' + txtFValue;

			}

		}
		function addQ(qid, sectionName) {

			var url = "lmsAddQuestionToSection?sectionName=" + sectionName
					+ "&questionId=" + qid;
			console.log('here url ' + url);
			$.ajax({
				url : url,
				success : function(data) {
					console.log("SUCCESS: ", data);
// 					alert("...."+qid);
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
// 					$("#count").text(data);
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
// 					$("#count").text(data);
					//document.getElementById("no-"+sectionName).innerHTML = data;

				},
				error : function(e) {
					console.log("ERROR: ", e);

				}
			});
		}

		function showSelected() {
			window.location = "lmsShowSectionsQuestions";
		}
		dta = "";
		load = function() {
			$
					.ajax({
						url : 'lmsShowSectionsQuestions',
						type : 'GET',
						success : function(response) {

							console.log("hello");
							console.log("test", response.qs[0].questionText);
							console.log("test", response.qs[0].testCategory);
							console.log("test", response.qs[0].questionText);
							console.log("test", response.qs[0].questionText);
							console.log("test", response.qs[0].questionText);
							data = response.qs;

							for (i = 0; i < response.qs.length; i++) {
								console.log(data[i].questionText);
								$("#table1")
										.append(
												"<tr class='tr'> <td> "
														+ response.data[i].questionText
														+ " </td>  <td> <a href='#' onclick= edit("
														+ i
														+ ");> Edit </a>  </td> </td> <td> <a href='#' onclick='delete_("
														+ response.data[i].questionText
														+ ");'> Delete </a>  </td> </tr>");

							}
						}
					});
		};

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
 
	</script>
	<div class="hiddendiv common"></div>
</body>
</html>