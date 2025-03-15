
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
 <link href="./user/css/intro.css" rel="stylesheet" type="text/css" />
 <style>
        
    </style>
</head>
<body id="top" class="has-header-search">
	<!--header start-->
	
	<nav>
        <div class="logo">
            <span>MaukaChauka</span>
        </div>
        <div class="profile">
            <span>
                Welcome, ${studentTestForm.userName}
            </span>
<!--             <img src="./resources/user/images/gulrez.jpg"  alt="gulrez"/> -->
        </div>
    </nav>
    
     <div class="container">
        <!-- container start -->

				<form:form id="studentIntro" method="POST" action="studentJourney" modelAttribute="studentTestForm">
        <div class="test_start">
            <div class="test_info">
                <div class="test_name">
                    <span>
                       Test Name : ${studentTestForm.testName}
                    </span>
                </div>
                <div class="test_details">
                    <span>
                        Total Question : ${studentTestForm.totalQuestions}
                    </span>
                    <span>
                        Duration : ${studentTestForm.duration} minutes
                    </span>
                </div>
                <div class="public">
                    <span>
                        Published on
                        ${studentTestForm.formattedPublishedDate}
                    </span>
                </div>

            </div>

            <div class="action">
            <a href="javascript:start()" class="btn_start">START TEST</a>
<!--                 <button class="btn_start">Start Test</button> -->
            </div>
            <div class="admin_info">
                <span> admin@maukachauka.com </span>
            </div>
        </div>
        	</form:form>
        <!-- container end -->
    </div>
	 
	<script type="text/javascript">
		function start() {

			document.getElementById("studentIntro").submit();
		}
	</script>

</body>
</html>
