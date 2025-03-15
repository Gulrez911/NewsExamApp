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


<script src="./resources/userprofile/js/jquery.min.js"></script>
<script src="./resources/userprofile/js/popper.min.js"></script>
<script src="./resources/userprofile/js/bootstrap4.min.js"></script>
<link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.33.1/sweetalert2.css">
<!-- <link rel="stylesheet" href="./resources/userprofile/css/style.css"> -->
<link rel="stylesheet" href="./resources/userprofile/css/menuzord.css">
<link rel="stylesheet" href="./resources/userprofile/css/cardstyle.css">

 
<link rel="stylesheet" href="./resources/userprofile/css/dataTables.bootstrap4.css">
<script src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.21/js/dataTables.bootstrap4.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.3.0/lodash.js"></script>

<style>
body {
	font-family: medium-content-serif-font, Georgia, Cambria,
		"Times New Roman", Times, serif;
}
</style>



</head>
<body id="top" class="has-header-search">
	<div id="page">


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

		<div id="colorlib-services" class="container module-content">
			<div>
				<div class="row">
					<div class="col-2">
						<!-- 						<a href="lmsAddQuestion" class="btn btn-primary save"><i class="material-icons fa fa-plus-circle"></i> Questions</a> -->
					</div>
				</div>


			</div>

			<table id="tbl" class="table table-striped table-hover custom-table table-bordered" style="box-shadow: 0px 10px 35px -5px #a8a8e5;">
				<thead>
					<tr>
						<th><b>#</b></th>
						<th class="title">Test Title</th>
						<th style="width: 18%;">Category</th>
						<th style="width: 12%;">Time In Mins</th>
						<th style="width: 7%;">Pass %</th>
						<th style="width: 11%;">Created By</th>
						<th style="width: 11%;">Last Update</th>
						<!-- <th>Expire Test</th>
												<th>Update Test</th>
												<th>Duplicate Test</th> -->
						<th style="width: 8%;">Actions</th>
					</tr>
				</thead>

			</table>
		</div>







		<!-- Duplicate Test Popup -->
		<div id="modalcopy" class="modal fade modalcopy" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">


					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Duplicate Test</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">
						<div class="col-md-12" style="padding-bottom: 20px">
							<div class="row formfields">
								<div class="col-md-4">
									<label class="fieldtitle meeting-label">Existing Test Name</label>
								</div>
								<div class="col-md-8">
									<div class="input-field">
										<input id="existing_name" value="Sample Test " type="text" disabled />
									</div>
								</div>
							</div>
						</div>

						<div class="col-md-12" style="padding-bottom: 20px">
							<div class="row formfields">
								<div class="col-md-4">
									<label class="fieldtitle meeting-label">New Test Name</label>
								</div>
								<div class="col-md-8">
									<div class="input-field">
										<input id="newTest" type="text" required />
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-12" style="padding-bottom: 20px">
							<div class="row formfields">
								<div class="col-md-4">
									<label class="fieldtitle meeting-label">Qualifier 1</label>
								</div>
								<div class="col-md-8">
									<div class="input-field">
										<input id="newQual1" type="text" required />
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-12" style="padding-bottom: 20px">
							<div class="row formfields">
								<div class="col-md-4">
									<label class="fieldtitle meeting-label">Qualifier 2</label>
								</div>
								<div class="col-md-8">
									<div class="input-field">
										<input id="newQual2" type="text" />
									</div>
								</div>
							</div>
						</div>

					</div>

					<!-- Modal footer -->
					<div class="modal-footer" style="padding-right: 20%;">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary" onClick="javascript:dup()">Duplicate Test</button>
					</div>


				</div>
			</div>
		</div>
	</div>


	<!-- Share Test Popup -->
	<div id="modalshare" class="modal fade modalcopy" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Share Test</h4>
				</div>
				<div class="modal-body">

					<div role="tabpanel">
						<!-- Nav tabs -->
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation" class="active"><a href="#uploadTab" aria-controls="uploadTab" role="tab" data-toggle="tab">Single Share</a></li>
							<li role="presentation"><a href="#browseTab" aria-controls="browseTab" role="tab" data-toggle="tab">Bulk Share</a></li>
						</ul>
						<!-- Tab panes -->
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active" id="uploadTab">

								<form method="GET" action="sharePublicTest" enctype="multipart/form-data">
									<div>
										<label>Existing test name</label> <input id="existing_name1" type="text" readonly="readonly" name="existing_name1" />
									</div>
									<div>
										<label>First Name</label> <input id="firstName" type="text" name="firstName" />
									</div>
									<div>
										<label>Last name</label> <input id="lastName" type="text" name="lastName" /></a>
									</div>
									<div>
										<label>Email Id</label> <input id="userEmail" type="text" name="userEmail" />
									</div>
									<div>
										<label>Public Test URL</label> <input id="publicTestUrl" type="text" readonly="readonly" />
									</div>
									<div>
										<label>Expire Date</label> <input id="txt2Date" name="expId" />
									</div>

									<input type="hidden" name="testId" id="testId" value="" />
									<div class="buttons text-center" style="padding-top: 20px;">
										<input class="waves-effect waves-light btn" type="button" value="Copy in your Clipboard" onClick="javascript:copyUrlInClipBoard()" /> <input
											class="waves-effect waves-light btn" type="submit" value="Share" /> <input class="waves-effect waves-light btn" type="button" value="Close"
											data-dismiss="modal" />
									</div>
								</form>

							</div>
							<div role="tabpanel" class="tab-pane" id="browseTab">

								<form method="POST" action="shareBulkPublicTest" enctype="multipart/form-data">
									<div>
										<label>Existing test name</label> <input id="existing_name2" type="text" readonly="readonly" name="existing_name1" />
									</div>
									<div>
										<input id="publicTestUrl2" type="hidden" readonly="readonly" />
									</div>
									<div>
										<label>Bulk Users</label><input type="file" name="file">
									</div>
									<div>
										<label>Expire Date</label> <input id="txt2Date2" name="expId" />
									</div>

									<input type="hidden" name="testId" id="testId2" value="" />
									<div class="buttons text-center" style="padding-top: 20px;">
										<input class="waves-effect waves-light btn" type="button" value="Copy in your Clipboard" onClick="javascript:copyUrlInClipBoard()" /> <input
											class="waves-effect waves-light btn" type="submit" value="Share" /> <input class="waves-effect waves-light btn" type="button" value="Close"
											data-dismiss="modal" />
									</div>
								</form>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<!-- jQuery -->
	<script>
			$(document)
					.ready(
							function() {
								var data = ${listTests};
								console.log(data);
								var x = 0;
								var table = $('#tbl')
										.DataTable(
												{
													"lengthMenu" : [
														[  -1, 10, 25, 50 ],
														[  "All", 10, 25, 50 ] ],

													"aaData" : data,
													"aoColumns" : [
															{
																"mData" : "srNo"
															},
															{
																"mData" : "testName"
															},
															{
																"mData" : "category"
															},
															{
																"mData" : "testTimeInMinutes"
															},
															{
																"mData" : "passPercent"
															},
															{
																"mData" : "cDate"
															},
															{
																"mData" : "uDate"
															},

															{
																"mData" : function(
																		data,
																		type,
																		dataToSet) {
																	data = "<a onClick='deleteTest("
																			+ data.id
																			+ ");' href='#' style='margin-right: 10px;' title='Delete Test'><i class='fa fa-trash' aria-hidden='true'></i> </a><a href='lmsUpdateTest?testId="
																			+ data.id
																			+ " ' title='Edit Test' style='margin-right: 10px;' title='Delete Test'><i class='fa fa-pencil' aria-hidden='true'></i> </a><a href='javascript:void(0);' class='testname' title='Duplicate Test'  data-toggle='modal' onClick='javascript:duplicateOpen(   \""
																			+ data.testName
																			+ "\"   )'><i class='fa fa-copy'></i></a>";
																	return data;
																}
															} ],
												// 	        "dom": '<"toolbar">frtip',

												});

								// 		$("#tbl_filter").remove();
								$("#tbl_length")
										.prepend(
												'<a href="lmsAddtest" class="btn btn-primary" style="margin-right: 63px"><i class="material-icons fa fa-plus-circle"></i> Tests</a>');
								// 	    $("#tbl_length label").html($("#tbl_length label").html().replace('Show ', ''));
								// 	    $("#tbl_length label").html($("#tbl_length label").html().replace(' entries', ''));
								// 	    $("#tbl_filter label").html($("#tbl_filter label").html().replace('Search:', ''));

								// 	    $("#tbl_filter label").html("<input type='search' class='form-control form-control-sm' placeholder='Search Test' aria-controls='tbl'>");

								// $('#tbl_filter label').each(function() {
								//     var text = $(this).text();
								//     $(this).text(text.replace('Search:', '')); 
								// });

								// $('#tbl_filter label:contains("Search:")').text('Hello world'); 

								$('#tbl_filter').find("input[type=search]")
										.each(
												function(ev) {
													if (!$(this).val()) {
														$(this).attr(
																"placeholder",
																"Search Test");
													}
												});

								// 	    $("#tbl_filter:has(input)").attr("placeholder", "Search Test");;
								//   		var newDiv = document.createElement('div');
								//   		element.appendChild(newDiv);
								//   		 var uploaderDiv = elements[0];
								//   		elements[0].insertAfter(newDiv, uploaderDiv.children[0]);
							});

			// 	$(document).load(function () {

			// 		var element =document.getElementById("tbl_wrapper");
			// 	  	  var btn = document.createElement("DIV");
			// 	  		alert(btn);
			// 			element.appendChild(btn);
			// 	});

			function dup() {
				var existing_name = document.getElementById("existing_name").value;
				var newTest = document.getElementById("newTest").value;
				var newQual1 = document.getElementById("newQual1").value;
				var newQual2 = document.getElementById("newQual2").value;
				if (newTest == '' || newTest == null) {
					notify('Info', 'Enter a name for the new Test');
				} else if (newQual1 == '' || newQual1 == null) {
					notify('Info', 'Enter a Qualifier name for the new Test');
				} else {
					window.location = "lmsDuplicateTest?existing_name="
							+ existing_name + "&newTest=" + newTest
							+ "&newQual1=" + newQual1 + "&newQual2=" + newQual2;
				}

			}

			function duplicateOpen(testName) {
				//             alert();
				//             var name = $(this).attr('data-name');
				console.log('here ' + testName);
				//             console.log(tenantId);
				document.getElementById("existing_name").value = testName;
				$('#modalcopy').modal('show');
				$('#modalshare').modal('hide');
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
				//       		var uemail = document.getElementById("uemail").value;
				var existing_name1 = document.getElementById("existing_name1").value;
				var firstName = document.getElementById("firstName").value;
				var lastName = document.getElementById("lastName").value;
				var userEmail = document.getElementById("userEmail").value;
				var testId = document.getElementById("testId").value;
				var expId = document.getElementById("txt2Date").value;
				if (firstName == '' || firstName == null) {
					notify('Info', 'First Name can not be blank');
				} else if (lastName == '' || lastName == null) {
					notify('Info', 'Last Name can not be blank');
				} else if (userEmail == '' || userEmail == null) {
					notify('Info', 'Email can not be blank');
				} else if (!validateEmail(userEmail)) {
					notify('Info', 'Enter a valid email');
				} else {
					window.location = "sharePublicTest?existing_name1="
							+ existing_name1 + "&firstName=" + firstName
							+ "&lastName=" + lastName + "&userEmail="
							+ userEmail + "&testId=" + testId + "&expId="
							+ expId;
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
				(new PNotify(
						{
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

// 			function notify(messageType, message) {
// 				var notification = 'Information';
// 				$(function() {
// 					new PNotify({
// 						title : notification,
// 						text : message,
// 						type : messageType,
// 						styling : 'bootstrap3',
// 						hide : true
// 					});
// 				});
// 			}

			function test() {
				var searchTest = document.getElementById("searchText").value;
				console.log(searchTest);
			}
		
			function deleteTest(id){
		 	 Swal.fire({
			  title: 'Are you sure?',
			  text: "You want to delete this Test ?",
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes, delete it!'
			}).then((result) => {
			  if (result.value) {
				 window.location = "lmsRetireTest?testId=" + id;
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
