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
<!-- <link href="user/css/style.css" rel="stylesheet" type="text/css" /> -->
<!-- <link href="./user/css/style_2.css" rel="stylesheet" type="text/css" /> -->
<!-- <link href="./resources/user/css/style_2.css" rel="stylesheet" type="text/css" /> -->
<link href="./resources/user/css/login_popup.css" rel="stylesheet" type="text/css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- <link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css"> -->

<link href="./user/css/style_3.css" rel="stylesheet" type="text/css" />
<link href='https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css' rel='stylesheet'>

<!-- bootstrap 5 -->

<!--  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"> -->
<!--   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script> -->
<!--End::Google Tag Manager -->
<style>
.search2 {
	/* position: absolute; */
	/* top: 50%;
        left: 50%; */
	margin-top: 10%;
	margin-left: 50%;
	/* width: 350px; */
	display: flex;
	align-items: center;
	background: #efefef;
	border-radius: 20px;
	padding: 0 15px;
	/* transform: translate(-50%, -50%); */
	border-style: solid;
	border-width: 5px 20px;
}

.search2 input {
	width: 100%;
	background: transparent;
	padding: 10px;
	outline: none;
	border: none;
}

/* //// */
nav .search-box2 {
	position: relative;
	height: 50px;
	/* max-width: 550px; */
	/* width: 100%; */
	margin: 0 20px;
}

nav .search-box2 input {
	height: 100%;
	/* width: 100%; */
	outline: none;
	background: #F5F6FA;
	border: 2px solid #EFEEF1;
	border-radius: 6px;
	font-size: 18px;
	padding: 0 15px;
}

nav .search-box2 .bx-search {
	position: absolute;
	height: 40px;
	width: 40px;
	background: #2697FF;
	right: 5px;
	top: 50%;
	transform: translateY(-50%);
	border-radius: 4px;
	line-height: 40px;
	text-align: center;
	color: #fff;
	font-size: 22px;
	/* transition: all 0.4 ease; */
}

.categories .categories1 ul {
	border-radius: 10px;
	list-style: none;
	/* 	padding: 60px 15px; */
	background: white !important;
	width: 180px;
}

.categories .categories1 ul li {
	padding: 20px;
	font-size: 16px;
}

.categories1 ul li:hover {
	background: deepskyblue;
}

.all {
	margin: 0px 100px;
}

.categories1 {
	padding: 30px 15px;
}

.categories2 {
	padding: 30px 15px;
}

.all .categories {
	display: flex;
	margin-left: 110px;
	margin-right: 110px;
	justify-content: flex-start;
	/*     width: 100%; */
}

.categories .categories1 ul  li a {
	text-decoration: none;
	color: black;
	font-size: 18px;
}

.all .categories2 ul li p {
	text-align: center;

	/* 	   background: deepskyblue; */
}

.all .categories2 ul li a:hover {
	background: chartreuse;
	color: black;
}

.all .categories2 ul li a {
	font-weight: bolder;
	border-radius: 5px;
	background: bisque;
	font-size: 18px;
	text-decoration: none;
	color: white;
	display: inline-block;
	color: #2033F4;
	vertical-align: middle;
	user-select: none;
	border: 1px solid transparent;
	padding: 10px 18px;
	font-size: 16px;
}

.exam_name {
	text-align: left;
	padding: 20px;
	font-size: 20px;
}

.all .categories2 ul li {
	display: inline-grid;
	/* 	 display: flex; */
	padding: 1rem;
	list-style: none;
	height: 195px;
	width: 231px;
	background: linear-gradient(to bottom, #1f72ee, #798ded, #a8aaed, #cdcaec, #ececec);
	box-shadow: rgb(0 0 0/ 20%) 0px 4px 8px 0px;
	border-radius: 5px;
	padding: 30px 15px;
	/* 	margin-top: 2%; */
}

.all .categories2 ul {
	display: flex;
	flex-wrap: wrap;
	gap: 12px;
}
</style>
<body>
	<nav>
		<div class="sidebar-button">
			<i class='bx bx-menu sidebarBtn'></i> <span class="dashboard">Dashboard</span>
		</div>
	 

		<!-- 									<label for="show2" class="show-btn"><div class="profile-details3"> -->
		<!-- 					Sign In -->
		<!-- 				</div></label> -->
		<!-- <div class="search-box">
			<input type="text" placeholder="Search...">
			<i class='bx bx-search'></i>
		</div> -->

		<!-- 		<div class="search-box2"> -->
		<!-- 			<input type="text" placeholder="Search..."> <i class='bx bx-search'></i> -->
		<!-- 		</div> -->

		<!-- <div class="search2">
			<input type="text">
			<i class="fa fa-search"></i>
		</div> -->

		<!-- <div class="search-box">
			<input type="text" placeholder="Type to search..">
			<div class="search-icon">
				<i class="fas fa-search"></i>
			</div>
			<div class="cancel-icon">
				<i class="fas fa-times"></i>
			</div>
			<div class="search-data">
			</div>
		</div> -->

		<!-- 				<div class="profile-details2"> -->
		<!-- 					<img src="images/profile.jpg" alt=""> -->
		<!-- 					<span class="text-muted">Hi,</span> -->
		<!-- 					<span class="admin_name">Gulrez</span> -->
		<!-- 					<i class='bx bx-chevron-down'></i> -->
		<!-- 				</div> -->

		<!-- 				 <div class="profile-details" onclick="test2()"> -->
		<!-- 					<a href="#" class="simple-text logo-mini admin_name" style="text-decoration: none;">  -->
		<!-- 					test -->
		<!-- 					</a> -->
		<!-- 					<span class="admin_name">Sign In</span> -->
		<!-- 				</div>  -->



		<label for="show2" class="show-btn"><div class="profile-details">
				<!-- 					<a href="#" class="simple-text logo-mini admin_name" style="text-decoration: none;">  -->
				Sign In
				<!-- 					</a> -->
				<!-- 					<span class="admin_name">Sign In</span> -->
			</div></label>
	</nav>
	<div class="sidebar2"  >
	<div class="sidebar">
		<div class="logo-details">
			<i class='bx bxl-c-plus-plus'></i> <span class="logo_name">MaukaChauka</span>
		</div>
		<ul class="nav-links">
			<li><a href="#" class="active"> <i class='bx bx-grid-alt'></i> <span class="links_name">Home</span>
			</a></li>
			<li><a href="#"> <i class='bx bx-box'></i> <span class="links_name">Test</span>
			</a></li>
			<li><a href="#"> <i class='bx bx-list-ul'></i> <span class="links_name">Quizes</span>
			</a></li>
			<li><a href="#"> <i class='bx bx-pie-chart-alt-2'></i> <span class="links_name">Previous Year Papers</span>
			</a></li>
			<li><a href="#"> <i class='bx bx-coin-stack'></i> <span class="links_name">Practice</span>
			</a></li>
			<li><a href="#"> <i class='bx bx-book-alt'></i> <span class="links_name">Exams</span>
			</a></li>
			<li><a href="#"> <i class='bx bx-user'></i> <span class="links_name">Doubts</span>
			</a></li>
			<li><a href="#"> <i class='bx bx-message'></i> <span class="links_name">GK & Current Affairs </span>
			</a></li>
			<li><a href="#"> <i class='bx bx-heart'></i> <span class="links_name">KBC</span>
			</a></li>
			<!-- <li>
          <a href="#">
            <i class='bx bx-cog' ></i>
            <span class="links_name">Setting</span>
          </a>
        </li> -->
			<li class="log_out"><a href="#"> <i class='bx bx-log-out'></i> <span class="links_name">Log out</span>
			</a></li>
		</ul>
	</div>
	<section class="home-section">


<!-- 		<!-- old --> 
		<div class="home-content">
			<div class="overview-boxes">
				<div class="box">
					<div class="right-side">

						<h1>KNOW ABOUT CTET</h1>
						<p>CTET syllabus</p>
						<p>Exam Pattern</p>
						<p>CTET Strategy</p>
						<p>How to crack exam in a simple way</p>




					</div>
				</div>

				<div class="box">
					<div class="right-side">

						<h1>KNOW ABOUT D.El.Ed</h1>
						<p>D.EL.ED syllabus</p>
						<p>D.El.Ed. Admission</p>
						<p>D.El.Ed. Curriculum</p>
						<p>D.El.Ed. Exams and Scope</p>



					</div>
				</div>


			</div>
			<div class="all">
				<div class="overview-boxes">
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
										<img src="v2_style/images/01 (1).png">                     <h2> Cloud Architecture &amp; Design</h2>

										<div class="exam_name">${exam.examName}</div>
										<p>
											<a href="examTest?examId=${exam.id}">View Test Series </a>
										</p>

									</li>

								</c:forEach>
							</ul>



						</div>
					</div>
				</div>
			</div>
		</div>




	</section>
</div>


	<input type="checkbox" id="show2" style="display: none">


	<div id="demo-modal" class="modal">
		<div class="modal__content">
			<label for="show2" class="modal__close">&times;</label>
			<div class="login">
				<h2 class="form2">Login Form</h2>
			</div>

			<div class="slide-controls">

				<!-- 						<input type="radio" name="slide" id="login" checked> <input type="radio" name="slide" id="signup"> -->

				<!-- 						<div class="slide-controls"> -->
				<label for="login" class="slide" onclick="login()" id="login">Login</label> <label for="signup" class="signup"
					onclick="signup()" id="sign">Signup</label>






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
					<button type="submit" class="btn btn-success btn-block">Login</button>
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
					<button type="submit" class="btn btn-success btn-block">Login</button>
				</div>

			</form>

		</div>

		<!-- 					<a href="#" class="modal__close">&times;</a> -->
		<!-- 			</div> -->
	</div>

	<script>
		let sidebar = document.querySelector(".sidebar");
		let sidebarBtn = document.querySelector(".sidebarBtn");
		sidebarBtn.onclick = function() {
			sidebar.classList.toggle("active");
			//   if(sidebar.classList.contains("active")){
			//   sidebarBtn.classList.replace("bx-menu" ,"bx-menu-alt-right");
			// }else
			//   sidebarBtn.classList.replace("bx-menu-alt-right", "bx-menu");
		}
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
		function login() {
			// 	alert("sssd");
			// 	$('#' + idd).css("display", "none");
			$('#login').css("background", "deepskyblue");
			$('#sign').css("background", "gainsboro");

			$('.signup33').css("display", "none");
			$('.login22').css("display", "block");
			$('.form2').text("Login Form");
		}

		function signup() {
			// 	alert("sssssadfasd");
			// background: gainsboro;
			$('#sign').css("background", "deepskyblue");
			$('#login').css("background", "gainsboro");

			$(".login22").css("display", "none");
			$('.signup33').css("display", "block");
			$('.form2').text("Signup Form");
		}

		function test2() {
			// 			if(document.getElementById('show2').checked) {
			// 			alert("sss");
			// 			    $(".modal").css("display", "flex");
			// 			} 
			// 			else {
			// 			    $("#txtAge").hide();
			// 			}
		}
	</script>
</body>

</html>