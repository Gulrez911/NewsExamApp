<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ctet.data.*, java.text.*, java.util.*,com.ctet.web.dto.*"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MaukaChauka</title>
<link rel="stylesheet" href="./resources/newDesing/css/student.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<spring:url value="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js" var="mainJs1" />
<script src="${mainJs1}"></script>
<spring:url value="/resources/scripts/custom.js" var="mainJs2" />
<script src="${mainJs2}"></script>
<spring:url value="/resources/scripts/pnotify.custom.min.js" var="mainJs3" />
<script src="${mainJs3}"></script>
<spring:url value="/resources/scripts/html2canvas.js" var="mainJs4" />
<script src="${mainJs4}"></script>
<spring:url value="/resources/scripts/src-min-noconflict/ace.js" var="mainJs5" />
<script src="${mainJs5}"></script>


<script type="text/javascript">
		var active = 'true';
		var studentNameTaken = localStorage.getItem('${studentTestForm.firstName}${studentTestForm.lastName}');
		var testNameTaken = localStorage.getItem('testName-${studentTestForm.firstName}${studentTestForm.lastName}');
		var tc= localStorage.getItem('timeCounter-${studentTestForm.firstName}${studentTestForm.lastName}');
			
			
			if(studentNameTaken == 'yes' && testNameTaken == '${studentTestForm.firstName}${studentTestForm.lastName}-${studentTestForm.testName}'  && tc != null){
			timeCounter=tc;
			}
			else{
				timeCounter = 0;
			}
			
			if(tc == null){
				timeCounter= 0;
			}
			
		function setTimeOnLoad(){
		timeProcess();
		}	
		
		function timeProcess(){
		timeCounter = parseInt(timeCounter) + 1;
		var end = new Date();
		var hours =  (${studentTestForm.duration}/60) % 60;
		var minutes = (${studentTestForm.duration}) % 60;
		var seconds = (${studentTestForm.duration} * 60) % 60;
		
		end.setMinutes(minutes);
		end.setHours(hours);
		end.setSeconds(seconds);
		
		var start = new Date();
		start.setMinutes((timeCounter/60) % 60);
		start.setHours((timeCounter/(60*60)) % 60);
		start.setSeconds(timeCounter % 60);
		
		var t = Date.parse(end) - Date.parse(start);
		seconds = Math.floor( (t/1000) % 60 );
		minutes = Math.floor( (t/1000/60) % 60 );
		hours = Math.floor( (t/(1000*60*60)) % 24 );
		
		 if (hours   < 10) {hours   = "0"+hours;}
		 
		  if (minutes < 10) {minutes = "0"+minutes;}
		  
		   if (seconds < 10) {seconds = "0"+seconds;}
		
		//document.getElementById("examTimer").innerHTML = hours+":"+minutes+":"+seconds;
		document.getElementById("hours").innerHTML = hours;
		document.getElementById("min").innerHTML = minutes;
		document.getElementById("sec").innerHTML = seconds;
			
		}
		 	
		var submitTest = 'false';
		
		function examTimer(){
			if(submitTest == 'true'){
				return;
			}
			timeProcess();
		
			if((${studentTestForm.duration} * 60) - timeCounter <= 3 ){
				notify('Info', 'Test Time exceeding shortly! Your test will be auto submitted');
			}		
				
			if( timeCounter >= (${studentTestForm.duration} * 60)  ){
			submitTest();
			}
		}
		
		function takeScreenShot(){
			if(active == 'false'){
				notify('Info', 'The exam window looks to be in the background. This is a non-compliance. We are recording it in our system. If number of non-'+

	'compliances exceed a threshold, the Test Admin may mark this attempt as void. Please beware! ');
				var datasend="user=${studentTestForm.emailId}\ntestName=${studentTestForm.testName}\ncompanyId=${studentTestForm.companyId}";
				$.ajax({
				    type: "POST",
				    url: "registerNonCompliance",
				    data: { 
					data:datasend
				    }
				}).done(function(fileName) {
					alert("done");

				}); 
			}
			else if(active == 'true'){
				this.window.focus();
				 html2canvas(document.querySelector("#screenShotId"), {
				logging: true,
				allowTaint: true
			    }).then(function(canvas) {
				var dataImage = canvas.toDataURL("image/png");
				$.ajax({
				    type: "POST",
				    url: "uploadScreenSnapShot?testName=${studentTestForm.testName}",
				    data: { 
					data:dataImage
				    }
				}).done(function(fileName) {
					alert("done");

				}); 
			    });
			}
		
		
		}
		
		function activeScreen(){
		active = 'true';
		//alert(' active ' +active);
		}
		
		function passiveScreen(){
		active = 'false';
		//alert(' passicve ' +active);
		}
		window.addEventListener('focus', activeScreen);
		window.addEventListener('blur', passiveScreen);

		
		var myVar = setInterval(examTimer, 1000);
		var myVar2 = setInterval(takeScreenShot, 45000);
		
		</script>

<style>
</style>

</head>
<body onload="setTimeOnLoad()">

	<form:form id="testForm" name="testForm" method="POST" modelAttribute="currentQuestion" enctype="multipart/form-data">
		<div class="menu">Test</div>
		<div class="w2">
			<div class="side">





				<ul>

					<c:forEach var="sectionInstance" varStatus="status" items="${sectionInstanceDtos}">
						<li ${sectionInstance.style} onclick="javascript:changeSection('${sectionInstance.section.sectionName}');"><a>${sectionInstance.section.sectionName}</a>
						</li>

					</c:forEach>


				</ul>
			</div>

			<div class="question">

				<div class="progress-section">

					<div class="progress-questions">
						<span class="progress-title">${noAnswered} of ${totalQuestions}</span>
<%-- 						<span class="progress-title">${noAnswered} of ${totalQuestions} answered</span> --%>
					</div>
					<!-- 				<div class="progress"> -->
					<!-- 					<div class="progress-bar brand-bg progress-dot six-sec-ease-in-out" role="progressbar" aria-valuenow="0%" -->
					<%-- 						aria-valuemin="0" aria-valuemax="100" style="width: ${percentage}%;"> --%>
					<!-- 						<span style="color: #0d0d0d">50%</span> -->
					<!-- 					</div> -->
					<!-- 				</div> -->

					<div class="progress">
						<div class="progress-bar brand-bg progress-dot six-sec-ease-in-out" style="width: ${percentage}%;">
							<p>${percentage}%</p>
						</div>
					</div>

					<div class="promo-btn">
						<div class="durationinfo">
							<span id="hours"></span><span id="colon">:</span><span id="min"></span><span id="colon">:</span><span id="sec"></span>


							<!-- 							<span class="time" id="timer" style="vertical-align: ;"><i id="hours"></i>H<i -->
							<!-- 									id="min"></i> Min<i id="sec"></i>  Sec</span> -->

						</div>
					</div>

				</div>




				<c:choose>
					<c:when
						test="${currentQuestion.questionMapperInstance.questionMapper.question.type=='MCQ' || currentQuestion.questionMapperInstance.questionMapper.question.type ==null}">

						<div class="question-number">${currentQuestion.position}.</div>


						<div class="que">
							<h4>${currentQuestion.questionMapperInstance.questionMapper.question.questionText}</h4>
							<div class="question-details">
								<ul class="options">


									<label for="one"> <form:radiobutton path="radioAnswer" id="one" value="one" class="radio1" /> <!--                                   <input type="radio" id="one" value="one" name="radioAnswer" class="radio1"> -->
										<li id="1" class="save-mcq-answer active"><span class="option">A</span><span class="question-text">

												<span>${currentQuestion.questionMapperInstance.questionMapper.question.choice1}</span>
										</span></li>
									</label>

									<label for="two"> <form:radiobutton path="radioAnswer" id="two" value="two" class="radio1" /> <!--                                 <input type="radio" id="two" value="two" name="radioAnswer" class="radio1" > -->
										<li id="2" class="save-mcq-answer " for="two"><span class="option">B</span><span class="question-text"><span>${currentQuestion.questionMapperInstance.questionMapper.question.choice2}</span></span>
									</li>
									</label>

									<label for="three"> <form:radiobutton path="radioAnswer" id="three" value="three" class="radio1" /> <!--                                 <input type="radio" id="two" value="two" name="radioAnswer" class="radio1" > -->
										<li id="2" class="save-mcq-answer " for="two"><span class="option">C</span><span class="question-text"><span>${currentQuestion.questionMapperInstance.questionMapper.question.choice3}</span></span>
									</li>
									</label>

									<label for="four"> <form:radiobutton path="radioAnswer" id="four" value="four" class="radio1" /> <!--                                 <input type="radio" id="two" value="two" name="radioAnswer" class="radio1" > -->
										<li id="2" class="save-mcq-answer " for="two"><span class="option">D</span><span class="question-text"><span>${currentQuestion.questionMapperInstance.questionMapper.question.choice4}</span></span>
									</li>
									</label>

									<label for="five"> <form:radiobutton path="radioAnswer" id="five" value="five" class="radio1" /> <!--                                 <input type="radio" id="two" value="two" name="radioAnswer" class="radio1" > -->
										<li id="2" class="save-mcq-answer " for="five"
										style="${currentQuestion.questionMapperInstance.questionMapper.question.choice5 == null || 
					
								currentQuestion.questionMapperInstance.questionMapper.question.choice5.trim().length() == 0? 'display: none;':'clear:left;font-size: 17px;'}">
											<span class="option">E</span><span class="question-text"><span>${currentQuestion.questionMapperInstance.questionMapper.question.choice5}</span></span>
									</li>
									</label>

									<label for="six"> <form:radiobutton path="radioAnswer" id="six" value="six" class="radio1" /> <!--                                 <input type="radio" id="two" value="two" name="radioAnswer" class="radio1" > -->
										<li id="2" class="save-mcq-answer " for="two"
										style="${currentQuestion.questionMapperInstance.questionMapper.question.choice6 == null || 
					
								currentQuestion.questionMapperInstance.questionMapper.question.choice6.trim().length() == 0? 'display: none;':'clear:left;font-size: 17px;'}">
											<span class="option">F</span><span class="question-text"><span>${currentQuestion.questionMapperInstance.questionMapper.question.choice6}</span></span>
									</li>
									</label>






								</ul>
							</div>
						</div>
					</c:when>
				</c:choose>











				<div class="user-actions clearfix">


					<c:choose>
						<c:when test="${currentSection.first==true}">
						</c:when>
						<c:otherwise>


							<a href="javascript:prev();" class="multiple_choice btn button1 disabled" id="back">Previous</a>



							<!-- 								<a class="back waves-effect waves-light btn submit-button mt-30" -->
							<!-- 									href="javascript:prev();" id="back">Back</a> -->
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${currentSection.last==true}">

							<a href="javascript:submitTestCheckNoAnswer();" class="list-full-module  multiple_choice btn button2" id="next">SUBMIT
								TEST</a>


							<!-- 								<a href="javascript:submitTestCheckNoAnswer();" -->
							<!-- 									class="next waves-effect waves-light btn submit-button mt-30" -->
							<!-- 									id="next">SUBMIT TEST</a> -->
						</c:when>
						<c:otherwise>

							<a href="javascript:next();" class="list-full-module  multiple_choice btn button2" id="next">Next</a>

							<!-- 								<a class="next waves-effect waves-light btn submit-button mt-30" -->
							<!-- 									href="javascript:next();" id="next">Next</a> -->
						</c:otherwise>
					</c:choose>

					<!-- 				<a href="javascript:void(0);" disabled="" class="  multiple_choice btn button1 disabled" data-question_id="112200" -->
					<!-- 					data-question_category="multiple_choice" data-token="" data-question_no="0">Previous</a> <a -->
					<!-- 					href="javascript:void(0);" class="list-full-module  multiple_choice btn button2" data-question_id="112200" -->
					<!-- 					data-question_category="multiple_choice" -->
					<!-- 					data-token="dHRFRkZoNFo5QmhmRzB3VnhuQmg3NFlqek5KNEQrbDhjSittWnJCZUVBcG1JVFVMZU15NjhPM1Q2cUFJSDZjbg==" -->
					<!-- 					data-question_no="2">Next</a> -->


				</div>
			</div>
		</div>
	</form:form>

	<script>
	$(function () {
                $(".addimage").on('click', function (e) {
                    e.preventDefault();
                    $("#addimage").trigger('click');
                });
               
            });
	
	 $('#addimage').change(function () {
                var file = $('#addimage')[0].files[0].name;
                $('.queimage').text('Document to upload: '+file);
				uploadProjectDocs();
            });

	function changeSection(sectionName){
		//window.location = 'changeSection?sectionName='+sectionName+"&timeCounter="+timeCounter;
		//localStorage.setItem('timeCounter', timeCounter);
		var tp = '${currentQuestion.questionMapperInstance.questionMapper.question.type}';
			if(tp == 'CODING'){
				confirm(sectionName);
			}
			else{
				window.location = 'changeSection?sectionName='+sectionName+"&timeCounter="+timeCounter;
				localStorage.setItem('timeCounter', timeCounter);
			}
		
	}
	
	function get_center_pos(width, top){
    // top is empty when creating a new notification and is set when recentering
		if (!top){
			top = 30;
			// this part is needed to avoid notification stacking on top of each other
			$('.ui-pnotify').each(function() { top += $(this).outerHeight() + 20; });
		}

		return {"top": top, "left": ($(window).width() / 2) - (width / 2)}
	}
	
	function confirm(sectionName) {
           (new PNotify({
		    title: 'Confirmation Needed',
		    text: 'For a coding Question if you change a section you may loose your changes. Either copy your code somewhere and then change section or use Next/Back to navigate',
		    icon: 'glyphicon glyphicon-question-sign',
		    hide: false,
		    confirm: {
			confirm: true
		    },
		    buttons: {
			closer: false,
			sticker: false
		    },
		    history: {
			history: false
		    },
			 before_open: function (PNotify) {
            PNotify.get().css(get_center_pos(PNotify.get().width()));
			}
		})).get().on('pnotify.confirm', function() {
		   window.location = 'changeSection?sectionName='+sectionName+"&timeCounter="+timeCounter;
			localStorage.setItem('timeCounter', timeCounter);
		}).on('pnotify.cancel', function() {
		   
		});
    }
	
	  
	
	function next(){
		//$('input[name="one"]').not(':checked').val(0);
// 		alert("..")
		var qType = '${currentQuestion.questionMapperInstance.questionMapper.question.type}';
		if(qType == 'MCQ'){
			var one = $( 'input[name="one"]:checked' ).val();
			var two = $( 'input[name="two"]:checked' ).val();
			var three = $( 'input[name="three"]:checked' ).val();
			var four = $( 'input[name="four"]:checked' ).val();
			var five = $( 'input[name="five"]:checked' ).val();
			var six = $( 'input[name="six"]:checked' ).val();
			count = 0;
			if(one != null){
				count ++;
			}
			
			if(two != null){
				count ++;
			}
			
			if(three != null){
				count ++;
			}
			
			if(four != null){
				count ++;
			}
			
			if(five != null){
				count ++;
			}
			if(six != null){
				count ++;
			}
			
			var noOfConfiguredChoices = '${currentQuestion.questionMapperInstance.questionMapper.question.rightChoices}'.split(',').length;
			var correctNo = ('${currentQuestion.questionMapperInstance.questionMapper.question.rightChoices}'.match(/-/g) || []).length  + 1;
			
			//console.log('${currentQuestion.questionMapperInstance.questionMapper.question.rightChoices}');
			//console.log('correct choices'+correctNo);
			//console.log('no of chosen choices'+count);
			if(count == correctNo){
				//notify('INFO', 'go ahead');
			}
			else{
				//do nothing
			//notify('INFO', 'No of Answers chosen are not equal to no of correct answers. Please select '+correctNo+' option(s)');
			//return;
			}
			
			
		}
		
	
	
	
//edt.value = editor.getSession().getValue();
	var linktext=document.getElementById('next').text;
		if(linktext == 'Finish Test'){
		submitTest();
		}
		else{
		document.testForm.action = "nextQuestion?questionId=${currentQuestion.questionMapperInstance.questionMapper.id}&timeCounter="+timeCounter;
		storeTimeLocal();
		document.testForm.submit();
		}
	}
	
	
	
	function prev(){
	var qType = '${currentQuestion.questionMapperInstance.questionMapper.question.type}';
	//var qType = '${currentQuestion.questionMapperInstance.questionMapper.question.type}';
		if(qType == 'MCQ'){
			var one = $( 'input[name="one"]:checked' ).val();
			var two = $( 'input[name="two"]:checked' ).val();
			var three = $( 'input[name="three"]:checked' ).val();
			var four = $( 'input[name="four"]:checked' ).val();
			var five = $( 'input[name="five"]:checked' ).val();
			var six = $( 'input[name="six"]:checked' ).val();
			count = 0;
			if(one != null){
				count ++;
			}
			
			if(two != null){
				count ++;
			}
			
			if(three != null){
				count ++;
			}
			
			if(four != null){
				count ++;
			}
			
			if(five != null){
				count ++;
			}
			if(six != null){
				count ++;
			}
			
			var correctNo = ('${currentQuestion.questionMapperInstance.questionMapper.question.rightChoices}'.match(/-/g) || []).length  + 1;
			

			console.log('${currentQuestion.questionMapperInstance.questionMapper.question.rightChoices}');
			console.log('correct choices'+correctNo);
			console.log('no of chosen choices'+count);
			if(count == correctNo){
				//notify('INFO', 'go ahead');
			}
			else{
				//do nothing cognizant
			//notify('INFO', 'No of Answers chosen are not equal to no of correct answers. Please select '+correctNo+' option(s)');
			//return;
			}
			
			
		}
	
	 
	document.testForm.action = "prevQuestion?questionId=${currentQuestion.questionMapperInstance.questionMapper.id}&timeCounter="+timeCounter;
	storeTimeLocal();
	document.testForm.submit();
	}
	
	function submitTest(){
	var qType = '${currentQuestion.questionMapperInstance.questionMapper.question.type}';
//	var qType = '${currentQuestion.questionMapperInstance.questionMapper.question.type}';
		if(qType == 'MCQ'){
			var one = $( 'input[name="one"]:checked' ).val();
			var two = $( 'input[name="two"]:checked' ).val();
			var three = $( 'input[name="three"]:checked' ).val();
			var four = $( 'input[name="four"]:checked' ).val();
			var five = $( 'input[name="five"]:checked' ).val();
			var six = $( 'input[name="six"]:checked' ).val();
			count = 0;
			if(one != null){
				count ++;
			}
			
			if(two != null){
				count ++;
			}
			
			if(three != null){
				count ++;
			}
			
			if(four != null){
				count ++;
			}
			
			if(five != null){
				count ++;
			}
			if(six != null){
				count ++;
			}
			
			var correctNo = ('${currentQuestion.questionMapperInstance.questionMapper.question.rightChoices}'.match(/-/g) || []).length  + 1;
			

			console.log('${currentQuestion.questionMapperInstance.questionMapper.question.rightChoices}');
			console.log('correct choices'+correctNo);
			console.log('no of chosen choices'+count);
			if(count == correctNo){
				//notify('INFO', 'go ahead');
			}
			else{
			//notify('INFO', 'No of Answers chosen are not equal to no of correct answers. Please select '+correctNo+' option(s)');
			//return;
			}
			
			
		}
	 
 
	document.testForm.action = "submitTest?questionId=${currentQuestion.questionMapperInstance.questionMapper.id}&timeCounter="+timeCounter;
	resetTimeLocal();
	document.testForm.submit();
	submitTest = 'true';
	notify('Info', 'The test is now submitted! Do not click the Submit button again incase you do not get a result screen fast because of a slow connection');
	}
	
	function storeTimeLocal(){
	localStorage.setItem('${studentTestForm.firstName}${studentTestForm.lastName}', 'yes');
	localStorage.setItem('testName-${studentTestForm.firstName}${studentTestForm.lastName}', '${studentTestForm.firstName}${studentTestForm.lastName}-'+

'${studentTestForm.testName}');
	localStorage.setItem('timeCounter-${studentTestForm.firstName}${studentTestForm.lastName}', timeCounter);
	}
	
	function resetTimeLocal(){
	localStorage.setItem('${studentTestForm.firstName}${studentTestForm.lastName}', 'no');
	localStorage.setItem('testName-${studentTestForm.firstName}${studentTestForm.lastName}', null);
	localStorage.setItem('timeCounter-${studentTestForm.firstName}${studentTestForm.lastName}', 0);
	}
	
	function submitTestCheckNoAnswer(){
	var uanswered = '${totalQuestions - (noAnswered+1)}';
	
	 submitTest();
	
// 		if(uanswered == '0'){
// 			submitTest();
// 		}
// 		else{
// 			(new PNotify({
// 		    title: 'Confirmation Needed',
// 		    text: 'Are you sure you want to submit the test? You still have unanswered Questions?',
// 		    icon: 'glyphicon glyphicon-question-sign',
// 		    hide: false,
// 		    confirm: {
// 			confirm: true
// 		    },
// 		    buttons: {
// 			closer: true,
// 			sticker: true
// 		    },
// 		    history: {
// 			history: false
// 		    }
// 		})).get().on('pnotify.confirm', function() {
// 		  // parent.closeFullScreen();	//uncomment it later
// 		   submitTest();
// 		}).on('pnotify.cancel', function() {
		   
// 		});
			
// 		}
		
	}
	
	function confirmWorkspace(qMapperInstanceId){
		(new PNotify({
		    title: 'Confirmation Needed',
		    text: 'Are you sure you have uploaded the project documentation. Your reviewer may take a dim view of your efforts if no documentation is found? If yes you can submit the workspace?',
		    icon: 'glyphicon glyphicon-question-sign',
		    hide: false,
		    confirm: {
			confirm: true
		    },
		    buttons: {
			closer: true,
			sticker: true
		    },
		    history: {
			history: false
		    }
		})).get().on('pnotify.confirm', function() {
		    submitFullStackCode(qMapperInstanceId);
		}).on('pnotify.cancel', function() {
		   
		});
	}
	
	function showAndNavigate(){
		(new PNotify({
		    title: 'About this',
		    text: 'Yes to this will download a Project Documentation template that you are expected to update and then upload. Kindly make sure the How-to use part is crystal clear for your reviewer to gro through and start your app. Do you want to proceed now?',
		    icon: 'glyphicon glyphicon-question-sign',
		    hide: false,
		    confirm: {
			confirm: true
		    },
		    buttons: {
			closer: false,
			sticker: false
		    },
		    history: {
			history: false
		    }
		})).get().on('pnotify.confirm', function() {
		    window.open('http://159.65.148.176/file-server/assessment/docs/Project_ReadMe.docx', '_blank');
		}).on('pnotify.cancel', function() {
		   
		});
	}
	
	
	function submitFullStackCode(qMapperInstanceId){
		
		
		
		var url = 'submitFullStackCode?qMapperInstanceId='+qMapperInstanceId;
	
	var data = {}; 
	
	
		$.ajax({
				type : 'GET',
				url : url,
				contentType: "application/json; charset=utf-8",
				data: data,
				success : function(data) {
					console.log("SUCCESS: ", data);
					notify('Information', data);
					
				},
				error : function(e) {
					console.log("ERROR: ", e);
					
				}
			});
	}
	
	function uploadProjectDocs(){
		qMapperInstanceId = '${currentQuestion.questionMapperInstance.id}';
		 var formData = new FormData();
		  var file = $('#addimage')[0].files[0];
		  console.log('1 file is '+file);
		  if(file != null){
			formData.append('addimage', file);
            console.log("form data " + formData);
            $.ajax({
                url : 'uploadProjectDocs?qMapperInstanceId='+qMapperInstanceId,
				enctype: 'multipart/form-data',
                data : formData,
                processData : false,
                contentType : false,
                type : 'POST',
                success : function(data) {
                     notify('Information','Your file has been uploaded. Check this link <br\>-<a href="'+data+'" >Click here</a> ');
                },
                error : function(err) {
                   notify('Information',err);
                }
            });
		  }
		  else{
			  notify('Information', 'Kindly upload the Project Documentations word file');
		  }
            
	}

	</script>
</body>
</html>
