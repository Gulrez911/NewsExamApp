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

<link href="./user/css/style_4.css" rel="stylesheet" type="text/css" />
<link href="./resources/user/css/login_popup.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" />
<link href='https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css' rel='stylesheet'>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link href="./user/css/gul_popup.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />

<style>
.modal__wrapper {
	position: fixed;
	left: 0;
	top: 0;
	background: rgba(0, 0, 0, 0.6);
	height: 100%;
	width: 100%;
	display: flex;
	align-items: center;
	justify-content: center;
	opacity: 0;
	pointer-events: none;
	transition: all .3s ease-in-out;
}

.modal__container {
	background: #fff;
	width: 560px;
	max-width: 90%;
	padding: 20px;
	border-radius: 4px;
	position: relative;
	transform: translateY(-100%);
	transition: all .3s ease-in-out;
}

h2 {
	margin-bottom: 10px;
}

/* p {
			line-height: 1.6;
			margin-bottom: 20px;
		} */
.action {
	display: flex;
	align-items: center;
	justify-content: flex-end;
}

.modal__container button.close {
	position: absolute;
	right: 10px;
	top: 10px;
	font-size: 32px;
	background: none;
	border: none;
	outline: none;
	cursor: pointer;
}

.modal__container button.close:hover {
	color: #6B46C1;
}

.active {
	opacity: 1;
	pointer-events: auto;
}

.modal__wrapper.active .modal__container {
	transform: translateY(0px);
}
</style>

</head>

<body>

	<div class="app">




		<div class="sidebar">

			<!-- 		<div class="logo"></div> -->

			<div class="logo">
				<a href="#" class="simple-text logo-mini"> MC </a> <a href="#" class="simple-text logo-normal"> MaukaChauka </a>
			</div>


			<ul>
				<li><a href="." class="active"><i class="fa fa-solid fa-home"></i><span class="links_name"> Home</span> </a></li>

				<li><a href="."> <i class="fa fa-folder" aria-hidden="true"></i> <span class="links_name">Test
							Series </span></a></li>

				<li><a href="#"> <i class="fa fa-folder" aria-hidden="true"></i> <span class="links_name">Quizes </span></a></li>

				<li><a href="#"> <i class="fa fa-folder" aria-hidden="true"></i> <span class="links_name"> Previous
							Year Papers </span></a></li>

				<li><a href="#"> <i class="fa fa-folder" aria-hidden="true"></i> <span class="links_name">Practice</span>
				</a></li>



				<li><a href="#"> <i class="fa fa-folder" aria-hidden="true"></i> <span class="links_name">Exams </span></a></li>

				<li><a href="#"> <i class="fa fa-folder" aria-hidden="true"></i> <span class="links_name">Doubts </span></a></li>


				<li><a href="#"><i class="fa fa-folder" aria-hidden="true"></i> <span class="links_name">GK &
							Current Affairs </span></a></li>


				<li><a href="#"><i class="fa fa-folder" aria-hidden="true"></i> <span class="links_name">KBC</span> </a></li>


			</ul>

		</div>

		<section class="home-section">
			<nav>
				<div class="sidebar-button">
					<!-- <i class='bx bx-menu sidebarBtn'></i> -->
					<span class="dashboard">MaukaChauka</span>
				</div>
				<div class="wt">

					<div class="profile-photo">


						<label for="show2" class="show-btn"> ${user} </label>

						<div class="ww">
							<div class="profile-photo-show">

								<div class="menu-item px-5">
									<div class="menu-item2">
										<a href="javascript:logout()" class="menu-link px-5">Sign Out</a>
									</div>
									<div class="menu-item2">
										<a href="reset" class="menu-link px-5">Forget Password</a>
									</div>
									<div class="menu-item2">
										<a href="javascript:logout()" class="menu-link px-5">Sign Out</a>
									</div>
									<div class="menu-item2">
										<a href="javascript:logout()" class="menu-link px-5">Sign Out</a>
									</div>
									<div class="menu-item2">
										<a href="javascript:logout()" class="menu-link px-5">Sign Out</a>
									</div>
								</div>

							</div>
						</div>




					</div>

					<div class="menu-toggle">
						<div class="hamburger">
							<span></span>
						</div>
					</div>

				</div>
			</nav>

			<main class="content">


				<div class="forget">

					<form name="userloginform" class="login22" method="post"  action="forgetPassword"
						id="userloginform">

						<div class="form-group">
							<input type="email" class="form-control" id="username" placeholder="Enter Email" required="required" />
						</div>
						 
						<div class="form-group form-group2">
							<input type="search" value="Login" class="btn btn-success btn-block" onclick="sendOtp()"
								style="text-align: center; cursor: pointer;">
						</div>

					</form>
				</div>



			</main>


		</section>

		<!-- end -->
		<!-- 		Popup -->
		<div id="popup1" class="overlay">
			<div class="popup">
				<div class="close_box">
					<span class="close" id="close">&times;</span>
				</div>

				<div class="gul-success" id="gul-icon">
					<span><i class="fa-solid fa-check icon"></i></span>
				</div>
				<div class="gul-success2" id="gul-icon2">
					<span><i class="swal2-x-mark-line-left"></i></span> <span><i class="swal2-x-mark-line-right"></i></span>
				</div>
				<div class="gul-success3" id="gul-icon3">
					<span><i class="fas fa-exclamation icon2"></i></span>
				</div>
				<div class="gul-success4" id="gul-icon4">
					<span><i class="icon3">i</i></span>
				</div>

				<h2 class="title" id="gul-title"></h2>
				<div class="gul-content" id="gul-con"></div>
				<button class="button3" id="close_button">Ok</button>
			</div>
		</div>
		<!-- 		Popup end -->
	</div>

	<script src="./user/js/app.js" type="text/javascript"></script>
	<script src="./user/js/gul_popup.js"></script>
	<script src="./user/js/profile.js"></script>


	<script>
		const menu_toggle = document.querySelector('.menu-toggle');
		const sidebar = document.querySelector('.sidebar');
		const homesection2 = document.querySelector('.home-section');

		menu_toggle.addEventListener('click', () => {
			menu_toggle.classList.toggle('is-active');
			sidebar.classList.toggle('is-active');
			homesection2.classList.toggle('is-active2');
		});
		
		const home = document.querySelector('.content');
		const sidebar3 = document.querySelector('.sidebar');
		const menutoggle = document.querySelector('.menu-toggle');
		// const homesection = document.querySelector('.home-section');
		home.addEventListener('click', function () {
			menutoggle.classList.remove('is-active');
			sidebar3.classList.remove('is-active');
			homesection2.classList.remove('is-active2');
		});
	</script>

	<script>
	
	function sendOtp() {
	 	var username	= $("#username").val();
		 	
	 		$.get("sendOtp?email=" + username  	, function(data, status) {
// 	 				alert(data.user44);
				if (data.usercheck2 === undefined) {
					modalOpen('Opps?',data.notfound,'error');
				} else {
					modalOpen('Great!','Your password has been sent to your mail.','success');
					 
				}
			});
		 	
			 
	 
	}
 
 
	</script>
</body>

</html>