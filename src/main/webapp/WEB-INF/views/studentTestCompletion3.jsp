<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ctet.data.*, java.text.*, java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MaukaChauka</title>
<link href="images/E-assess_E.png" rel="shortcut icon">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link href="css/font-awesome.css" rel="stylesheet" type="text/css">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/responsive.css" rel="stylesheet" type="text/css">
<link href="css/pnotify.custom.min.css" rel="stylesheet" type="text/css">

<link href="css/circle.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="scripts/FileSaver.js"></script>
<script type="text/javascript" src="scripts/dom-to-image.min.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="scripts/pnotify.custom.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- gul -->
<link href="user/css/chart.css" rel="stylesheet" type="text/css" />
<link href="user/css/result.css" rel="stylesheet" type="text/css" />
<script src="https://cdn.anychart.com/releases/8.10.0/js/anychart-core.min.js"></script>
<script src="https://cdn.anychart.com/releases/8.10.0/js/anychart-pie.min.js"></script>
<!-- gul -->
<script type="text/javascript">
	window.history.forward();
	function noBack() { window.history.forward(); }
</script>
<script>
window.location.hash="no-back-button";
window.location.hash="Again-No-back-button";//again because google chrome don't insert first hash into history
window.onhashchange=function(){window.location.hash="no-back-button";}
parent.closeFullScreen();

</script>
<style>
.completetest {
	/* 	float: left; */
	/* 	width: 80%; */
	
}

.testinformation {
	float: left;
	width: 100%;
	background-color: #e3efff;
	margin-top: 30px;
	margin-bottom: 30px;
	padding: 20px;
}

.testinformation ul {
	padding-left: 0px;
}

.testinformation li {
	font-size: 18px !important;
	padding-bottom: 10px;
	list-style: none;
}

.testinformation p {
	font-size: 18px;
}

.testinformation .table {
	background-color: #fff;
	padding: 15px !important;
	border-collapse: inherit;
	font-weight: bold;
}

.testinformation .table tbody tr td:first-child {
	color: rgb(0, 108, 255);
}

.testinformation .table td.green {
	color: #7ac142 !important;
}

.testinformation .table td.red {
	color: red;
}

.pie, .c100 .bar, .c100.p51 .fill, .c100.p52 .fill, .c100.p53 .fill,
	.c100.p54 .fill, .c100.p55 .fill, .c100.p56 .fill, .c100.p57 .fill,
	.c100.p58 .fill, .c100.p59 .fill, .c100.p60 .fill, .c100.p61 .fill,
	.c100.p62 .fill, .c100.p63 .fill, .c100.p64 .fill, .c100.p65 .fill,
	.c100.p66 .fill, .c100.p67 .fill, .c100.p68 .fill, .c100.p69 .fill,
	.c100.p70 .fill, .c100.p71 .fill, .c100.p72 .fill, .c100.p73 .fill,
	.c100.p74 .fill, .c100.p75 .fill, .c100.p76 .fill, .c100.p77 .fill,
	.c100.p78 .fill, .c100.p79 .fill, .c100.p80 .fill, .c100.p81 .fill,
	.c100.p82 .fill, .c100.p83 .fill, .c100.p84 .fill, .c100.p85 .fill,
	.c100.p86 .fill, .c100.p87 .fill, .c100.p88 .fill, .c100.p89 .fill,
	.c100.p90 .fill, .c100.p91 .fill, .c100.p92 .fill, .c100.p93 .fill,
	.c100.p94 .fill, .c100.p95 .fill, .c100.p96 .fill, .c100.p97 .fill,
	.c100.p98 .fill, .c100.p99 .fill, .c100.p100 .fill {
	border: 0.08em solid #7ac142;
}

.resultinformation {
	float: left;
	width: 100%;
}

.resultinformation h3 {
	background-color: rgb(0, 108, 255);
	padding: 10px;
	color: #fff;
	font-weight: bold;
	margin-bottom: 30px;
}

.progress {
	background-color: #cccccc;
}

.progress-bar {
	background-color: #7ac142;
}

.questionslist {
	float: left;
	width: 100%;
	border: 1px solid #dddddd;
	margin-bottom: 20px;
}

.questionslist .table {
	margin-bottom: 0;
}

.questionslist .table th {
	padding-top: 0;
	text-align: center;
}

.questionslist .table td {
	text-align: center;
}

.questionslist p {
	background-color: #006cff;
	padding: 10px;
	color: #fff;
	margin-bottom: 0;
	font-weight: bold;
}

.container {
	width: 1400px;
}

.m-b-35 {
	margin-bottom: 35px !important;
}

.wrapper, .row {
	height: fit-content;
	margin-left: 15px;
	margin-right: 15px;
}
</style>
</head>
<!------ Include the above in your HEAD tag ---------->
<body onload="noBack();">
 
		
		<%
	com.ctet.data.Test test = (com.ctet.data.Test) request.getSession().getAttribute("test");
	String testName = test.getTestName();
	request.setAttribute("test", test);
	%>
 
	   <div class="completetest" >

	<div class="container" id="dom">
                <div class="testinformation">
                    <div class="text-center">
                        <img src="./resources/images/process_complete.png">
                        <h1 style="color: #7ac142;text-align: center;margin-top: 0;font-weight: bold;">
						<%
							Integer noOfAttempts = (Integer) request.getSession().getAttribute("noOfAttempts");
							String att = "";
								if(noOfAttempts == null){
									noOfAttempts = 0;
									
								}
								else{
									noOfAttempts = noOfAttempts + 1;
								}
								
								if(noOfAttempts == 1){
// 									att =  "attempt";
// 									att = ${completionUTF.attempt};
									
								}
								else{
// 								att =  "attempts";
// 								att = ${completionUTF.attempt};
								}
						%>
<%-- 						${studentTestForm.firstName} ${studentTestForm.lastName} - You have completed the test</h1> --%>
						
							${studentTestForm.firstName} ${studentTestForm.lastName} - ${completionUTF.completeTest }</h1>
                    </div>
               
<!--<h3 style="text-align: center;">You completed the test in ${studentTestForm.noOfAttempts == null?1:(studentTestForm.noOfAttempts==0?1:(studentTestForm.noOfAttempts))}  ${(studentTestForm.noOfAttempts == null || studentTestForm.noOfAttempts == 0 || studentTestForm.noOfAttempts == 1)?"attempt":"attempts"} </h3>
				   <h3 style="text-align: center;" id="currtime">  </h3> -->
<%-- 				   <h3 style="text-align: center;"> ${completionUTF.completeTestIn } <%= noOfAttempts %> <%= att %> </h3> --%>
				     <h3 style="text-align: center;"> ${completionUTF.completeTestIn } <%= noOfAttempts %>  ${completionUTF.attempt} </h3>
				   <h3 style="text-align: center;" id="currtime">  </h3>
				   
				   
                    <h3 style="text-align: center;padding-bottom: 20px;">${completionUTF.sharedTest } - ${studentTestForm.testCreatedBy}</h3>
	
<!-- 	gul test -->
	

				<c:forEach var="section" items="${sectionInstanceDtos}">
					<c:forEach var="ques" varStatus="status" items="${section.questionInstanceDtos}">

						<div class="que2">

							<div class="que2text">Question -${ques.questionMapperInstance.questionMapper.question.questionText}</div>

							<div class="options">
								<%-- ${ques.questionMapperInstance.questionMapper.question.choice1}${ques.questionMapperInstance.questionMapper.question.choice2}${ques.questionMapperInstance.questionMapper.question.choice3} --%>
								<%-- 								${(ques.questionMapperInstance.userChoices)} --%>

								<c:choose>
									<c:when
										test="${(ques.questionMapperInstance.questionMapper.question.rightChoices=='Choice 1')&&(not empty ques.questionMapperInstance.userChoices)}">
										<div class="options2" style="background: green; color: white;">${ques.questionMapperInstance.questionMapper.question.choice1}</div>
									</c:when>

									<c:when
										test="${(ques.questionMapperInstance.userChoices !=  ques.questionMapperInstance.questionMapper.question.rightChoices )&&(ques.questionMapperInstance.userChoices =='Choice 1')}">
										<div class="options2" style="background: red; color: white;">${ques.questionMapperInstance.questionMapper.question.choice1}</div>
									</c:when>
									<c:otherwise>
										<div class="options2">${ques.questionMapperInstance.questionMapper.question.choice1}</div>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when
										test="${(ques.questionMapperInstance.questionMapper.question.rightChoices=='Choice 2')&&(not empty ques.questionMapperInstance.userChoices)}">
										<div class="options2" style="background: green; color: white;">${ques.questionMapperInstance.questionMapper.question.choice2}</div>
									</c:when>
									<c:when
										test="${(ques.questionMapperInstance.userChoices !=  ques.questionMapperInstance.questionMapper.question.rightChoices )&&(ques.questionMapperInstance.userChoices =='Choice 2')}">
										<div class="options2" style="background: red; color: white;">${ques.questionMapperInstance.questionMapper.question.choice2}</div>
									</c:when>
									<c:otherwise>
										<div class="options2">${ques.questionMapperInstance.questionMapper.question.choice1}</div>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when
										test="${(ques.questionMapperInstance.questionMapper.question.rightChoices=='Choice 3')&&(not empty ques.questionMapperInstance.userChoices)}">
										<div class="options2" style="background: green; color: white;">${ques.questionMapperInstance.questionMapper.question.choice3}</div>
									</c:when>
									<c:when
										test="${(ques.questionMapperInstance.userChoices !=  ques.questionMapperInstance.questionMapper.question.rightChoices )&&(ques.questionMapperInstance.userChoices =='Choice 3')}">
										<div class="options2" style="background: red; color: white;">${ques.questionMapperInstance.questionMapper.question.choice3}</div>
									</c:when>
									<c:otherwise>
										<div class="options2">${ques.questionMapperInstance.questionMapper.question.choice3}</div>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when
										test="${(ques.questionMapperInstance.questionMapper.question.rightChoices=='Choice 4')&&(ques.questionMapperInstance.userChoices!='')&&(not empty ques.questionMapperInstance.userChoices)}">
										<div class="options2" style="background: green; color: white;">${ques.questionMapperInstance.questionMapper.question.choice4}</div>
									</c:when>
									<c:when
										test="${(ques.questionMapperInstance.userChoices !=  ques.questionMapperInstance.questionMapper.question.rightChoices )&&(ques.questionMapperInstance.userChoices =='Choice 4')}">
										<div class="options2" style="background: red; color: white;">${ques.questionMapperInstance.questionMapper.question.choice4}</div>
									</c:when>
									<c:otherwise>
										<div class="options2">${ques.questionMapperInstance.questionMapper.question.choice4}</div>
									</c:otherwise>
								</c:choose>





							</div>



						</div>
						<!-- 				defult -->

						<!-- 						<div class="options"> -->
						<!-- 							<ul> -->
						<%-- 								<li>Question -${ques.questionMapperInstance.questionMapper.question.questionText}</li> --%>
						<%-- 								<li>Option 1 -${ques.questionMapperInstance.questionMapper.question.choice1}</li> --%>
						<%-- 								<li>Option 2 -${ques.questionMapperInstance.questionMapper.question.choice2}</li> --%>
						<%-- 								<li>Option 3-${ques.questionMapperInstance.questionMapper.question.choice3}</li> --%>
						<%-- 								<li>Option 4 -${ques.questionMapperInstance.questionMapper.question.choice4}</li> --%>

						<%-- 								<li>Your Choice -${ques.questionMapperInstance.userChoices}</li> --%>
						<%-- 								<li><b> Correct Choice -${ques.questionMapperInstance.questionMapper.question.rightChoices} </b></li> --%>
						<%-- 								<li style="${ques.questionMapperInstance.correct == true? "color:green":"color:red"}"><i --%>
						<%-- 									class="${ques.questionMapperInstance.correct == true? "fafa-check":"fafa-close"}"></i> --%>
						<%-- 									${ques.questionMapperInstance.correct == true? "Correct":"Not Correct"} </b></li> --%>
						<%-- 								<li><b> ${ques.questionMapperInstance.questionMapper.question.justification == null ?"NA":ques.questionMapperInstance.questionMapper.question.justification}</b></li> --%>

						<!-- 							</ul> -->

						<!-- 						</div> -->

					</c:forEach>
				</c:forEach>
<!-- 	end -->
			<c:if test="${showResults}">
					<div class="row">

                        <div class="resultinformation">
                            <h3><i class="fa fa-bar-chart-o"></i> ${completionUTF.result }</h3>
                        </div>
                        <div class="col-md-5 text-center">
                            <img style="width: 250px;" src="./resources/images/result.png">
                        </div>
                        <div class="col-md-4">
                            <ul>
                                <li><b>${completionUTF.totalQuestions }</b> : ${TOTAL_QUESTIONS}</li>
                                <li><b>${completionUTF.totalMarks }</b> : ${TOTAL_MARKS}</li>
                                <li><b>${completionUTF.passPercentage }</b> : ${PASS_PERCENTAGE}</li>
                                <li><b>${completionUTF.resultPercentage }</b> : ${RESULT_PERCENTAGE}</li>
                                <li><b>${completionUTF.status }</b> : ${STATUS}</li>
								<c:if test="${confidencePercent != null}">
								<li><b>${completionUTF.overAllPercentage}</b> : ${confidencePercent}
								</li>
								</c:if>
                            </ul>
                        </div>
                        <div class="col-md-3">
                            <div class="c100 p${RESULT_PERCENTAGE_WITHOUT_FRACTION}">
                                <span>${TOTAL_MARKS}/${TOTAL_QUESTIONS}</span>
                                <div class="slice">
                                    <div class="bar"></div>
                                    <div class="fill"></div>
                                </div>
                            </div>
                        </div>
                    </div>
				
					<!--<div class="row">
						<h3 style="font-weight: bold;background-color: rgb(0,108,255);padding: 10px;color: #fff;margin-bottom: 0;">Results</h3>
						<table class="table" style="border: 1px solid #dddddd;">
                        <thead>
                            <tr>
                                <th>Section Name</th>
                                <th>Percentage Got</th>
                            </tr>
                        </thead>
						<tbody>
                        ${rows}
						</tbody>
						</table>
					</div> -->
			
					<div class="row">
					<c:if test="${showTraits}">			
						<h3 style="font-weight: bold;background-color: rgb(0,108,255);padding: 10px;color: #fff;margin-bottom: 0;">${completionUTF.learnerTraits}</h3>
						<c:forEach var="trait"  items="${traits}" >
						<div>
						
					
							<div>
								<ul type="circle" class="table">
									<li style="color:#3b6cff"> ${trait.trait}</li>
									<li> ${trait.description} </li>
									
									
								</ul>
                            
							</div>
							
						
						</div>
					</c:forEach>
				
					</c:if>
					</div>
					
			
			<c:if test="${codingAssignments}">		
			<div class="m-b-35">
			<h3 style="font-weight: bold;background-color: rgb(0,108,255);padding: 10px;color: #fff;margin-bottom: 0;">Coding Assignemnts Result Summary</h3>
			</c:if>		
					<c:forEach var="ins" items="${codingInstances}"> 
					<div class="questionslist">
                        <p>
                           ${ins.questionMapper.question.questionText}
                        </p>
						<h4 style="font-weight: bold;background-color: rgb(141, 51, 255 );padding: 2px;color: #fff;margin-bottom: 0;">Pass Threshold % -  ${ins.questionMapper.question.passPercentforCodingQAsPerWeightedScore}</h4>
						<h4 style="font-weight: bold;background-color: rgb(141, 51, 255 );padding: 2px;color: #fff;margin-bottom: 0;">Weighted  Score -  ${ins.codingScore}% </h4>
						<h4 style="font-weight: bold;background-color: rgb(141, 51, 255 );padding: 2px;color: #fff;margin-bottom: 0;">Status -  ${ins.codingScore >= ins.questionMapper.question.passPercentforCodingQAsPerWeightedScore ? "Pass":"Fail"} </h4>

                        <table class="table">
                            <thead>
                                <tr>
                                    <!--<th>Compilation Errors</th>
                                    <th>Runtime Errors</th>
                                    <th>Basic Test Case 1</th>
                                    <th>* Basic Test Case 2</th>
                                    <th>Test Case (Minimal Value)</th>
                                    <th>Test Case (High Value)</th>
                                    <th>Test Case (Invalid Data)</th> -->
									<th>Syntax Knowledge</th>
                                    <!-- <th>Basic Coding Ability</th> -->
                                    <th>Basic Code Integrity (Weight -  ${ins.questionMapper.question.weightInputPositive} )</th>
                                    <th>Basic Validations (Weight -  ${ins.questionMapper.question.weightInputNegative} )</th>
                                    <th>Withstand Extreme Low Inputs (Weight -  ${ins.questionMapper.question.weightExtremeMinimalValue} )</th>
                                    <th>Withstand Extreme High Inputs (Weight -  ${ins.questionMapper.question.weightExtremePositiveValue} )</th>
                                    <th>Production Grade Code (Weight -  ${ins.questionMapper.question.weightInvalidDataValue} )</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="${ins.codeCompilationErrors == true? "color:red":"color:green"}">${ins.codeCompilationErrors == true?"${completionUTF.yes}":"${completionUTF.no}"}</td>
                                    <!--<td class="${ins.codeRunTimeErrors == true? "color:red":"color:green"}">${ins.codeRunTimeErrors == true?"YES":"NO"}</td> -->
                                    <td class="${ins.testCaseInputPositive == true? "color:green":"color:red"}">${ins.testCaseInputPositive == true?"${completionUTF.yes}":"${completionUTF.no}"}</td>
                                    <td class="${ins.testCaseInputNegative == true? "color:green":"color:red"}">${ins.testCaseInputNegative == true?"${completionUTF.yes}":"${completionUTF.no}"}</td>
                                    <td class="${ins.testCaseMinimalValue == true? "color:green":"color:red"}">${ins.testCaseMinimalValue == true?"${completionUTF.yes}":"${completionUTF.no}"}</td>
                                    <td class="${ins.testCaseMaximumValue == true? "color:green":"color:red"}">${ins.testCaseMaximumValue == true?"${completionUTF.yes}":"${completionUTF.no}"}</td>
                                    <td class="${ins.testCaseInvalidData == true? "color:green":"color:red"}">${ins.testCaseInvalidData == true?"${completionUTF.yes}":"${completionUTF.no}"}</td>
                                </tr>
                            </tbody>
                        </table>
						                    </div>
					
					</c:forEach>
					
					<c:if test="${codingAssignments}">	
					
					</div>
					</c:if>		
		  
				<c:if test="${justification}">	
				<div class="m-b-35">
				<h3 style="font-weight: bold;background-color: rgb(0,108,255);padding: 10px;color: #fff;margin-bottom: 0;">Answer Summary</h3>
		 
				<c:forEach var="section"  items="${sectionInstanceDtos}" >
					<div class="questionslist">
						
						<%
						int count = 1;
						%>	
                        <c:forEach var="ques" varStatus="status" items="${section.questionInstanceDtos}" >
							<p>
							 ${section.section.sectionName} -   <%= count %>. ${ques.questionMapperInstance.questionMapper.question.questionText} 
							</p>
							<div class="options">
								<ul>
									<li> Your Choice -${ques.questionMapperInstance.userChoices} </li>
									<li><b> Correct Choice -${ques.questionMapperInstance.questionMapper.question.rightChoices} </b> </li>
									<li style="${ques.questionMapperInstance.correct == true? "color:green":"color:red"}"> <i class="${ques.questionMapperInstance.correct == true? "fa fa-check":"fa fa-close"}"></i> ${ques.questionMapperInstance.correct == true? "Correct":"Not Correct"} </b></li>
									<li><b> ${ques.questionMapperInstance.questionMapper.question.justification == null ?"NA":ques.questionMapperInstance.questionMapper.question.justification}</b></li>
									
								</ul>
                            
							</div>
							<%
								count ++;
							%>
						</c:forEach>
					</div>
				</c:forEach>
				</div>
				</c:if>
						
				</div>
            </div>
			
			
       
				
				
		
	   
	
	
	
		</c:if>
		
			<div class="container">
				<div class="page-header" style="background-color:#DAA300;color:#fff">
				     
			  </div>
			</div>	
		
	</div>
	
   
	   
			<div class="container">
				<div style="text-align: center !important">
                  <input type="button" value="Download" onclick="javascript:download()" class="btn btn-primary" style="background-color:#006cff !important;"/>
                </div>
			</div>
  




		<script>
		
		var d = new Date();
	//var d = dt.toString('dd-MMM-yyyy hh:mm');
	var date_format_str =  (d.getDate().toString().length==2?d.getDate().toString():"0"+d.getDate().toString())+"-"+((d.getMonth()+1).toString().length==2?(d.getMonth()+1).toString():"0"+(d.getMonth()+1).toString())+"-"+d.getFullYear().toString()+" "+(d.getHours().toString().length==2?d.getHours().toString():"0"+d.getHours().toString())+":"+((parseInt(d.getMinutes()/5)*5).toString().length==2?(parseInt(d.getMinutes()/5)*5).toString():"0"+(parseInt(d.getMinutes()/5)*5).toString());
	
	
	
	
	var disp = '<b>Test  - <%=testName%> ${completionUTF.completeByYouAt} ';
	disp += date_format_str +'</b>';
	document.getElementById("currtime").innerHTML =disp ;	
	
		function download(){
			domtoimage.toBlob(document.getElementById('dom'))
			.then(function (blob) {
				//var FileSaver = require('file-saver');
				//window.saveAs(blob, 'image.png');
				console.log('blob is '+blob);
				saveAs(blob, "image.png");
			});
		}
		
		$("a").each(function(i, el) {
		  const $el = $(el);
		  $el.click(function(e) {
			const href = $el.attr("href");
			// Find an element that has href as an ID
			// Scroll to that element
			$("html, body").scrollTop($("#" + href).offset().top);
			// Prevent default action, i.e., don't follow the hyperlink
			// to a new location
			e.preventDefault();
		  });
		});
		</script>

		<!-- donut -->

		<script>

anychart.onDocumentReady(function () {
  
  // add data
  var data = anychart.data.set([
    ['Spotify', 34],
    ['Apple Music', 21],
    ['Amazon Music', 15],
    ['Tencent apps', 11],
    ['YouTube Music', 6],
    ['Others', 13]
  ]);
  
  // create a pie chart with the data
  var chart = anychart.pie(data);
  
  // set the chart radius making a donut chart
  chart.innerRadius('55%')

  // create a color palette
  var palette = anychart.palettes.distinctColors();
 
  // set the colors according to the brands
  palette.items([
    { color: '#1dd05d' },
    { color: '#000000' },
    { color: '#00a3da' },
    { color: '#156ef2' },
    { color: '#f60000' },
    { color: '#96a6a6' }
  ]);

  // apply the donut chart color palette
  chart.palette(palette);
  
  // set the position of labels
  chart.labels().format('{%x} — {%y}%').fontSize(16);
  
  // disable the legend
  chart.legend(false);
  
  // format the donut chart tooltip
  chart.tooltip().format('Market share: {%PercentValue}%');

  // create a standalone label
  var label = anychart.standalones.label();

  // configure the label settings
  label
    .useHtml(true)
    .text(
      '<span style = "color: #313136; font-size:20px;">Global Market Share of <br/> Music Streaming Apps</span>' +
      '<br/><br/></br><span style="color:#444857; font-size: 14px;"><i>Spotify and Apple Music have more <br/>than 50% of the total market share</i></span>'
    )
    .position('center')
    .anchor('center')
    .hAlign('center')
    .vAlign('middle');
  
  // set the label as the center content
  chart.center().content(label);
  
  // set container id for the chart
  chart.container('container');
  
  // initiate chart drawing
  chart.draw();

});

    </script>
</body>
</html>