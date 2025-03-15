<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ctet.data.*, java.text.*, java.util.*, com.ctet.web.dto.*"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MaukaChauka</title>

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" />
<!-- 		<link href="assets/plugins/global/plugins.bundle.css" rel="stylesheet" type="text/css" /> -->
<link href="user/css/style.css" rel="stylesheet" type="text/css" />

<link href="user/css/login_popup.css" rel="stylesheet" type="text/css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>

<!-- <link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css"> -->



<!--End::Google Tag Manager -->
<style>
/*  #show2:checked ~ .modal {  */

/*    display: block; */
/*   } */

/* .modal { */
/*    display: none; */
/*   /* position: absolute; */
*
/
/*   left: 0px; */
/*   top: 0px; */
/*   z-index: 1; */
/* } */
</style>
</head>

<body>

	<div class="nav">
		<div class="web_icon">
			<img src="./resources/newDesing/image/pngegg.png" alt="">

		</div>
		<ul>

			<li>Home</li>
			<li>CTET</li>
			<li>KVS</li>
			<li>DSSSB</li>
			<li>B.Ed.</li>
			<li>D.El.Ed.</li>
			<li>HPTET</li>
			<li>MPTET</li>
			<li>UPTET</li>
			<li>BTET</li>
			<li>OTHERS</li>




		</ul>

		<div class="web_icon">



			<label for="show2" class="show-btn"> <img src="./resources/user/images/user.png" alt=""
				style="cursor: pointer;">
			</label>
		</div>

		<!-- 		   <label for="show2" class="show-btn">View Form</label> -->


	</div>




	<div class="side">

		<div class="logo"></div>


		<ul>
			<li><a href="#" class="active"><i class="fas fa-solid fa-home"></i><span class="links_name"> Home</span> </a></li>

			<li><a href="home44" class="active"> <i class="fas fa-folder" aria-hidden="true"></i> <span
					class="links_name">Test Series </span></a></li>

			<li><a href="#"> <i class="fas fa-folder" aria-hidden="true"></i> <span class="links_name">Quizes </span></a></li>

			<li><a href="#"> <i class="fas fa-folder" aria-hidden="true"></i> <span class="links_name"> Previous
						Year Papers </span></a></li>

			<li><a href="#"> <i class="fas fa-folder" aria-hidden="true"></i> <span class="links_name">Practice</span>
			</a></li>



			<li><a href="#"> <i class="fas fa-folder" aria-hidden="true"></i> <span class="links_name">Exams </span></a></li>

			<li><a href="#"> <i class="fas fa-folder" aria-hidden="true"></i> <span class="links_name">Doubts </span></a></li>


			<li><a href="#"><i class="fas fa-folder" aria-hidden="true"></i> <span class="links_name">GK &
						Current Affairs </span></a></li>


			<li><a href="#"><i class="fas fa-folder" aria-hidden="true"></i> <span class="links_name">KBC</span> </a></li>


		</ul>

	</div>
	<!-- 		<nav> -->
	<!-- 			<div class="sidebar-button"> -->
	<!-- 				<i class="fas fa-solid fa-bars"></i> <span class="dashboard">Dashboard</span> -->
	<!-- 			</div> -->

	<!-- 		</nav> -->


	<section>







		<div class="all">



			<div class="edu">
				<ul>
					<li>

						<h1>KNOW ABOUT CTET</h1>
						<p>CTET syllabus</p>
						<p>Exam Pattern</p>
						<p>CTET Strategy</p>
						<p>How to crack exam in a simple way</p>
					</li>

					<li>

						<h1>KNOW ABOUT D.El.Ed</h1>
						<p>D.EL.ED syllabus</p>
						<p>D.El.Ed. Admission</p>
						<p>D.El.Ed. Curriculum</p>
						<p>D.El.Ed. Exams and Scope</p>
					</li>
				</ul>

			</div>









			<div class="categories">
				<div class="categories1">
					<ul>
						<c:forEach items="${categories}" var="category" varStatus="loop">
							<li id="${category.id}"><a href="home44?id=${category.id}">${category.categoryName} </a></li>
						</c:forEach>



					</ul>
				</div>
				<div class="categories2">


					<ul>
						<c:forEach items="${exams}" var="exam" varStatus="loop">
							<li>
								<!-- <img src="v2_style/images/01 (1).png"> --> <!--                     <h2> Cloud Architecture &amp; Design</h2> -->

								<div class="exam_name">${exam.examName}</div>
								<p>
									<a href="examTest?examId=${exam.id}">View Test Series </a>
									<!--    <a href="#" id="rzp-button1" onclick="paymentProcess()" >Pay</a> -->
									<button id="rzp-button1" onclick="paymentProcess()">Pay</button>
									<!--    <a href="#"  onclick="test()" >Test</a> -->
								</p>

							</li>

						</c:forEach>
					</ul>



				</div>
			</div>

			<input type="checkbox" id="show2" style="display: none">


			<div id="demo-modal" class="modal">
				<div class="modal__content">
<label for="show2" class="modal__close">&times;</label>
					<div class="login">
						<h2>Login Form</h2>
					</div>
 
					<div class="slide-controls">
					
<!-- 						<input type="radio" name="slide" id="login" checked> <input type="radio" name="slide" id="signup"> -->
						
<!-- 						<div class="slide-controls"> -->
						<label for="login" class="slide" onclick="login()"  id="login">Login</label> <label for="signup" class="signup" onclick="signup()" id="sign">Signup</label>
						
 

					


</div>
<!-- 	<input type="radio" name="slide" id="login" checked> <input type="radio" name="slide" id="signup"> -->
<!-- 						<input type="radio" name="slide" id="login" checked> <input type="radio" name="slide" id="signup"> -->

			<form role="form" class="login22">
					 
						
						<div class="form-group">
							<input type="text" class="form-control" id="usrname" placeholder="Enter Email">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" id="usrname" placeholder="Password">
						</div>
						 
						 
						
						
					 <div class="form-group">
						<button type="submit" class="btn btn-success btn-block">
							  Login
						</button>
						</div>
						
					</form>



					<form role="form" class="signup33">
						<div class="form-group">
							<input type="text" class="form-control" id="usrname" placeholder="Enter First Name">
						</div>
						<div class="form-group">
							<input type="text" class="form-control" id="usrname" placeholder="Enter Last Name">
						</div>
						
						<div class="form-group">
							<input type="text" class="form-control" id="usrname" placeholder="Enter Email">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" id="usrname" placeholder="Password">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" id="usrname" placeholder="Re Password">
						</div>
						 
						
						
					 <div class="form-group">
						<button type="submit" class="btn btn-success btn-block">
							  Login
						</button>
						</div>
						
					</form>
					
			</div>

					<!-- 					<a href="#" class="modal__close">&times;</a> -->
				</div>
			</div>



		</div>

	</section>

	<script type="text/javascript">
		// alert(${param.id});

		var idd = ${id};
		// background: deepskyblue;
		$('#' + idd).css("background", "deepskyblue");
	</script>

	<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
	<script>
		function test() {
			var razorpay_payment_id = "124854";
			$.ajax({
				type : 'post',
				url : 'sucessrazorpay',
				data : {
					razorpay_payment_id : razorpay_payment_id
				},
				success : function(result) {
					console.log(result);
					alert('Your order is successfully done');
				}
			});

		}

		function paymentProcess() {
			var options = {
				"key" : "rzp_live_wC9SMX349JoZ1n",
				"amount" : 200, // Example: 2000 paise = INR 20
				"name" : "MERCHANT name",
				"description" : "description",
				"image" : "img.webp",// COMPANY LOGO
				//                 "order_id": "order_9A33XWu170gUtm",
				//                 "handler": function (response) {
				//                     console.log(response);
				//                     // AFTER TRANSACTION IS COMPLETE YOU WILL GET THE RESPONSE HERE.
				//                 },

				"prefill" : {
					"name" : "Surkhab Farooqui", // pass customer name
					"email" : 'gulfarooqui1@gmail.com',// customer email
					"contact" : '+919833685778' //customer phone no.
				},
				"notes" : {
					"address" : "address" //customer address 
				},
				"theme" : {
					"color" : "#15b8f3" // screen color
				},

				"handler" : function(response) {
					var payment_id = response.razorpay_payment_id;
					console.log(response);
					console.log("Hi");
					$.ajax({
						type : 'post',
						url : 'sucessrazorpay',
						data : {
							payment_id : payment_id
						},
						success : function(result) {
							console.log(result);
							alert('Your order is successfully done');
							alert(response.razorpay_payment_id);

							alert(response.razorpay_order_id);
							alert(response.razorpay_signature)
						}
					});

				}

			//                 "handler": function (response){
			//                     var payment_id = response.razorpay_payment_id;
			//                     console.log(response);
			//                     console.log("Hi");
			//                     $.ajax({
			//                         type:'post',
			//                         url:'sucessrazorpay',
			//                         data:{payment_id:payment_id},
			//                         success:function(result){
			//                             console.log(result);
			//                             alert('Your order is successfully done');
			//                         }
			//                     });
			//                 }

			};

			var rzp1 = new Razorpay(options);
			rzp1.on('payment.failed', function(response) {
				alert(response.error.code);
				alert(response.error.description);
				alert(response.error.source);
				alert(response.error.step);
				alert(response.error.reason);
				alert(response.error.metadata.order_id);
				alert(response.error.metadata.payment_id);
			});

			document.getElementById('rzp-button1').onclick = function(e) {
				rzp1.open();
				e.preventDefault();
			}
			//             alert("success");
		}
	</script>
<script type="text/javascript">


function  login(){
// 	alert("sssd");
// 	$('#' + idd).css("display", "none");
	$('#login').css("background", "deepskyblue");
	$('#sign').css("background", "gainsboro");
	
	
	$('.signup33').css("display", "none");
	$('.login22').css("display", "block");
}


function  signup(){
// 	alert("sssssadfasd");
// background: gainsboro;
	$('#sign').css("background", "deepskyblue");
	$('#login').css("background", "gainsboro");

	
	$(".login22").css("display", "none");
	$('.signup33').css("display", "block");
}
 
 
 </script>

</body>

</html>