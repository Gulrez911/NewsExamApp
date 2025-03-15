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
<link href="./user/css/social.css" rel="stylesheet" type="text/css" />

<!-- gmail sign in -->
<script src="https://apis.google.com/js/api:client.js"></script>
<script>
  var googleUser = {};
  var startApp = function() {
    gapi.load('auth2', function(){
      // Retrieve the singleton for the GoogleAuth library and set up the client.
      auth2 = gapi.auth2.init({
        client_id: '1031150543105-cqeanuk2t9cfn5sveu27mqr5u1n7com4.apps.googleusercontent.com',
        cookiepolicy: 'single_host_origin',
        // Request scopes in addition to 'profile' and 'email'
        //scope: 'additional_scope'
      });
      attachSignin(document.getElementById('customBtn'));
    });
  };

  function attachSignin(element) {
    console.log(element.id);
    auth2.attachClickHandler(element, {},
        function(googleUser) {
    	var profile = googleUser.getBasicProfile()
    	googleSignAuth(profile);
 
        }, function(error) {
          alert(JSON.stringify(error, undefined, 2));
        });
  }
  
  function signOut() {
	    var auth2 = gapi.auth2.getAuthInstance();
	    auth2.signOut().then(function () {
	        alert("You have been signed out successfully");
 
	    });
	}
  </script>

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

/* google login */
#customBtn {
	display: block;
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

					<!-- 								<button class="button" id="open5" -->
					<!-- 				    onclick="modalOpen('Are you sure?','You wont be able to revert this! Test','success')">Let me Pop up3</button> -->


					<!-- <div class="search-box2">
					<input type="text" placeholder="Search..." spellcheck="false" data-ms-editor="true"> <i
						class="bx bx-search"></i>
				</div> -->
					<!-- <label for="show2" class="show-btn">
					<div class="profile-details">
						Sign In
					</div>
				</label> -->

					<!-- 				 <div class="btn__purple"  onclick="logout()">Log Out</div>  -->

					<div class="profile-photo">


						<label for="show2" class="show-btn"> ${user} </label>

						<div class="ww">
							<div class="profile-photo-show">
								<div class="menu-item px-5">
									<div class="menu-item2">
										<a href="javascript:logout()" class="menu-link px-5">Sign Out</a>
									</div>
								</div>
							</div>
						</div>
						<!-- <button class="btn__purple" id="trigger">Login</button> -->
						<!-- <button class="btn__purple" id="trigger">Login</button> -->

						<div class="menu-toggle">
							<div class="hamburger">
								<span></span>
							</div>
						</div>
						<!-- <div class="sidebar-button">
			<i class='bx bx-menu sidebarBtn'></i>
				</div> -->
					</div>
			</nav>

			<main class="content">

				<div class="categories">
					<div class="categories2">
						<ul>
							<c:forEach items="${tests}" var="test" varStatus="loop">

								<li><a href="publicTestAuthenticate3?testId=${test.id}&testName=${test.testName}"
									onclick="return yes_js_login(this);">${test.testName}</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>

			</main>


		</section>

		<!-- new modal -->
		<div id="demo-modal" class="modal modal__wrapper">
			<div class="modal__content">
				<label for="show2" class="modal__close close">&times;</label>
				<div class="login">
					<h2 class="form2">Login Form</h2>
				</div>

				<div class="slide-controls">


					<label for="login" class="slide" onclick="login()" id="login">Login</label> <label for="signup" class="signup"
						onclick="signup()" id="sign">Sign Up</label>






				</div>

				<form name="userloginform" class="login22" method="post" modelAttribute="user2" action="auth" id="userloginform">

					<div class="form-group">
						<form:input path="user2.email" name="email" id="username" required="true" class="form-control" />
					</div>
					<div class="form-group">
						<form:password path="user2.password" name="password" id="login_password" required="true" class="form-control" />
					</div>




					<div class="form-group">
						<input type="text" value="Login" class="btn btn-success btn-block" onclick="loginUser()"
							style="text-align: center; cursor: pointer;">

						<!-- 												<input  type="submit" class="btn btn-success btn-block" onclick="loginUser()">Login</button> -->
					</div>

					<div class="rete">
						<a href="reset" class="menu-link px-5">Forget Password</a>
					</div>

					<div class="social_login2">

						<div id="gSignInWrapper">
							<div id="customBtn" class="customGPlusSignIn">
								<div class="icon google">
									<i class="fab fa-google"></i>
								</div>
							</div>
						</div>
						<div class="icon facebook" onclick="notWorking()">
							<i class="fab fa-facebook-f"></i>
						</div>
						<div class="icon twitter" onclick="notWorking()">
							<i class="fab fa-twitter"></i>
						</div>
						<div class="icon github" onclick="notWorking()">
							<i class="fab fa-github"></i>
						</div>

					</div>
				</form>


				<form name="userForm" method="post" modelAttribute="usr" action="saveUser" role="form" class="signup33">
					<%-- 				<form role="form" class="signup33"> --%>
					<div class="form-group">
						<form:input path="usr.firstName" name="firstName" id="firstName" required="true" class="form-control"
							placeholder="First Name" />
						<!-- 						<input type="text" class="form-control" id="FirstName" placeholder="Enter First Name"> -->
					</div>
					<div class="form-group">
						<form:input path="usr.lastName" name="lastName" id="lastName" required="true" class="form-control"
							placeholder="Last Name" />
						<!-- 						<input type="text" class="form-control" id="LastName" placeholder="Enter Last Name"> -->
					</div>

					<div class="form-group">
						<form:input path="usr.email" name="email" id="email" required="true" class="form-control" placeholder="Email" />
						<!-- 						<input type="text" class="form-control" id="Email" placeholder="Enter Email"> -->
					</div>
					<div class="form-group">
						<form:password path="usr.password" name="password" id="password" required="true" class="form-control" />
						<!-- 						<input type="password" class="form-control" id="Password2" placeholder="Password"> -->
					</div>
					<div class="form-group">
						<input type="password" class="form-control" id="Password3" placeholder="Re Password">
					</div>

					<div class="form-group">
						<form:input path="usr.mobileNumber" name="mobileNumber" id="mobileNumber" class="form-control" />
					</div>




					<div class="form-group">
						<input class="btn btn-success btn-block" type="submit" value="Save User">
						<!-- 						<button type="submit" class="btn btn-success btn-block">Login</button> -->
					</div>

				</form>

			</div>


		</div>
		<!-- end -->
		<!-- start -->
		<form style="display: none" action="googleSignAuth2" method="POST" id="form">
			<input type="hidden" id="var1" name="name" value="" /> <input type="hidden" id="var2" name="email" value="" /> <input
				type="hidden" id="var3" name="imageUrl" value="" />
		</form>
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
				<div class="gul-content" id="gul-con">Thank to pop me out of that button, but now i'm done so you can close
					this window. Lorem ipsum dolor sit amet consec ecusandae sint sit minus.</div>
				<button class="button3" id="close_button">Ok</button>
			</div>
		</div>
		<!-- 		Popup end -->
	</div>
	<script src="./user/js/app.js" type="text/javascript"></script>
	<script src="./user/js/gul_popup.js"></script>
	<script src="./user/js/profile.js"></script>
	<script type="text/javascript">
	function login() {
		// 	alert("sssd");
		// 	$('#' + idd).css("display", "none");
		$('#login').css("background", "#6B46C1");
		$('#login').css("color", "white");
		$('#sign').css("background", "gainsboro");
		$('#sign').css("color", "#6B46C1");

		$('.signup33').css("display", "none");
		$('.login22').css("display", "block");
		$('.form2').text("Login");
	}

	function signup() {
		// 	alert("sssssadfasd");
		// background: gainsboro;
		$('#sign').css("color", "white");
		$('#sign').css("background", "#6B46C1");
		$('#login').css("background", "gainsboro");
		$('#login').css("color", "#6B46C1");

		$(".login22").css("display", "none");
		$('.signup33').css("display", "block");
		$('.form2').text("Sign Up");
		$('.btn-block').css("color", "white");
		
	}

	</script>

	<script>
		const menu_toggle = document.querySelector('.menu-toggle');
		const sidebar = document.querySelector('.sidebar');
		const homesection2 = document.querySelector('.home-section');

		menu_toggle.addEventListener('click', () => {
			menu_toggle.classList.toggle('is-active');
			sidebar.classList.toggle('is-active');
			homesection2.classList.toggle('is-active2');
		});
	</script>

	<script>
		const home = document.querySelector('.content');
		const sidebar3 = document.querySelector('.sidebar');
		const menutoggle = document.querySelector('.menu-toggle');
		// const homesection = document.querySelector('.home-section');
		home.addEventListener('click', function () {
			// console.log("Hello world!");
			// $(".sidebar").css("left", "-300px");
			// sidebar3.classList.toggle('is-active');
			// sidebar3.classList.toggle('is-active');
			// homesection.classList.add('is-active2');
			menutoggle.classList.remove('is-active');
			sidebar3.classList.remove('is-active');
			homesection2.classList.remove('is-active2');
		});
		
        yes_js_login = function(obj) {
//         	alert(event.target.href);
//  var contentPanelId = $(event.target);
//  console.log(event.currentTarget.id);
//  console.log(this.id);
   	var href=    obj.getAttribute("href");
//         	alert(href);
	 		$.get("sessionCheck"  	, function(data, status) {
// 	 			alert(data.usercheck);
			if (data.usercheck === undefined||data.usercheck==null) {
				openModal();
// 				modalOpen('Opps?','Your credentials cannot be verified','error');
			} else {
// 				window.open('http://www.google.com', '_blank', 'toolbar=0,location=0,menubar=0');
				window.location = href;
// 				openModal();
			}
		});
        	return false;
        }
        
		function loginUser() {
		 	var username	= $("#username").val();
// 		 	alert(username);
		 	var	login_password = $("#login_password").val();
// 		 	alert(login_password);
	 		$.get("authCheck?email=" + username + "&password=" + login_password	, function(data, status) {
// 			 console.log(data.user44.email);
// 				alert(data.user44);
			if (data.user44 === undefined) {
				modalOpen('Are you sure?','You wont be able to revert this! Test','error');
			} else {
// 				alert(data.user44.email+" ok");
				$("#userloginform").submit();
				 
			}
		});
		}
		
    	function logout() {
			modalOpen('Success','You will be sign out','success');
			window.location = "redirect";
			
		}
    	
    	function notWorking() {
			modalOpen('Sorry?','Not Working !!','warning');
		}
 
		$("#customBtn").click(function() {
			window.location = "oauth2/authorization/google";
// 			window.location: "oauth2/authorization/google";
		});
		
	 
	</script>
	 
</body>

</html>