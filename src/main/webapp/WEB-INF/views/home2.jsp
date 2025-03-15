<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<!--  -->
<!-- <meta name="google-signin-client_id" content="344993853369-4taqdv01ek7clj1mqnh9q7nn4m50e755.apps.googleusercontent.com"> -->
   	<meta name="google-signin-client_id" content="1031150543105-cqeanuk2t9cfn5sveu27mqr5u1n7com4.apps.googleusercontent.com">
<!--  -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bootstrap Navbar Color Schemes</title>
<!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"> -->
<!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"> -->
<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script> -->
<!--  -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"></script>
<link href="./resources/css/home.css" rel="stylesheet" type="text/css" />


<script src="https://apis.google.com/js/platform.js" async defer></script>
<style>
/* Custom style */
.navbar {
	margin-bottom: 1rem;
}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container-fluid">
			<a href="#" class="navbar-brand">Brand</a>
			<button type="button" class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbarCollapse1">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarCollapse1">
				<div class="navbar-nav">
					<a href="#" class="nav-item nav-link active">Home</a> <a href="#" class="nav-item nav-link">About</a> <a href="#" class="nav-item nav-link">Products</a>
				</div>
				<form class="d-flex ms-auto">
					<input type="text" class="form-control me-sm-2" placeholder="Search">
					<button type="submit" class="btn btn-outline-light">Search</button>
				</form>
				<div class="d-flex ms-auto">
					<!-- 						<button type="submit" class="btn btn-outline-light">Sign In</button> -->

					<button type="button" class="btn btn-outline-light" data-bs-toggle="modal" data-bs-target="#myModal">Sign In</button>

					<!-- 						<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal"> -->
					<!--     Open modal -->
					<!--   </button> -->
				</div>


			</div>
		</div>
	</nav>

	<!--  end -->

<div class="g-signin2" data-onsuccess="onSignIn"></div>
<a href="#" onclick="signOut();">Sign out</a>

	<div class="container">
		<div class="row">
			<div class="col-sm-3" style="padding-bottom: 50px;">
				<div class="card" style="height: 96%;">
					<div class="cardimage" style="height: 95px !important;" id="${loop.count}">


						<h5 class="card-title" style="text-align: center; text-transform: uppercase;" title="">Gsfse zs s ds s</h5>
					</div>
					<div class="card-body">
						<ul>


							<li class="a"><a href="#"> seses</a></li>

						</ul>



					</div>
				</div>
			</div>

			<div class="col-sm-3" style="padding-bottom: 50px;">
				<div class="card" style="height: 96%;">
					<div class="cardimage" style="height: 95px !important;" id="${loop.count}">


						<h5 class="card-title" style="text-align: center; text-transform: uppercase;" title="">Gsfse zs s ds s</h5>
					</div>
					<div class="card-body">
						<ul>


							<li class="a"><a href="#"> seses</a></li>

						</ul>



					</div>
				</div>
			</div>

			<div class="col-sm-3" style="padding-bottom: 50px;">
				<div class="card" style="height: 96%;">
					<div class="cardimage" style="height: 95px !important;" id="${loop.count}">


						<h5 class="card-title" style="text-align: center; text-transform: uppercase;" title="">Gsfse zs s ds s</h5>
					</div>
					<div class="card-body">
						<ul>


							<li class="a"><a href="#"> seses</a></li>

						</ul>



					</div>
				</div>
			</div>

			<div class="col-sm-3" style="padding-bottom: 50px;">
				<div class="card" style="height: 96%;">
					<div class="cardimage" style="height: 95px !important;" id="${loop.count}">


						<h5 class="card-title" style="text-align: center; text-transform: uppercase;" title="">Gsfse zs s ds s</h5>
					</div>
					<div class="card-body">
						<ul>


							<li class="a"><a href="#"> seses</a></li>

						</ul>



					</div>
				</div>
			</div>

			<div class="col-sm-3" style="padding-bottom: 50px;">
				<div class="card" style="height: 96%;">
					<div class="cardimage" style="height: 95px !important;" id="${loop.count}">


						<h5 class="card-title" style="text-align: center; text-transform: uppercase;" title="">Gsfse zs s ds s</h5>
					</div>
					<div class="card-body">
						<ul>


							<li class="a"><a href="#"> seses</a></li>

						</ul>



					</div>
				</div>
			</div>

		</div>
	</div>









	<!-- Modal -->
	<div class="modal" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">


				<!-- Modal body -->
				<div class="login-modal-div">
					<div class="modal-content">
						<div class="center">
							<a href="#"> <img width="70px" src="https://upload.wikimedia.org/wikipedia/commons/3/33/Vanamo_Logo.png">
							</a>
						</div>
						<div class="white-bg center modal-header">
							<!--  -->
							<div class="login-register-div">
								<input id="tab1" type="radio" name="tabs" checked> <label class="tab-label" for="tab1">Sign In</label> <input id="tab2" type="radio" name="tabs"> <label class="tab-label"
									for="tab2">Sign Up</label>

								<section id="content1">
									<form method="POST" class="login-form" id="Login">
										<input type="hidden" name="reqType" value="Login">
										<div class="modal-form-group">
											<div class="extra"></div>
										</div>
										<div class="modal-form-group">
											<i class="input-icon material-icons"> </i> <input name="user" id="luser" required="required" type="text" class="modal-form-input" placeholder="Username or email">
										</div>
										<div class="modal-form-group">
											<i class="input-icon material-icons"> </i> <input name="pass" id="password" type="password" required="required" class="modal-form-input" placeholder="Password">
										</div>
										<div class="modal-form-group left">
											<input name="rem" type="hidden" value="false"> <input name="to" type="hidden" value="https://www.geeksforgeeks.org/"> <input name="rem" type="checkbox" checked> <label
												class="modal-form-label" for="remember">Remember me</label> <a class="pull-right forgot-link">Forgot Password</a>
										</div>

										<button class="btn btn-green signin-button" type="submit">Sign In</button>
									</form>
								</section>
								<section id="content2">
									<form method="POST" class="login-form" id="Register">
										<input type="hidden" name="reqType" value="Register">
										<div class="modal-form-group">
											<div class="extra"></div>
										</div>
										<div class="modal-form-group">
											<i class="input-icon material-icons"> </i> <input name="email" id="email" type="email" value="" required="required" class="modal-form-input" placeholder="E-mail">
										</div>
										<div class="modal-form-group">
											<i class="input-icon material-icons"> </i> <input name="pass" id="reg-password" type="password" required="required" class="modal-form-input" placeholder="Password">
										</div>
										<div class="modal-form-group">
											<i class="input-icon material-icons"> </i> <input name="institute" id="organization" autocomplete="off" required="required" type="text" class="modal-form-input typeahead"
												placeholder="Institution/Organization">
										</div>

										<input name="to" type="hidden" value="https://www.geeksforgeeks.org/">
										<button class="btn btn-green signup-button" type="submit">Sign Up</button>
									</form>
								</section>
								<div class="social-signin-div">
									<div class="social-divider">
										<span>or</span>
									</div>
									<div class="google-div social-div pull-left">
										<a id="glogin" href="javascript:void(0)" class="btn btn-social btn-google"> <span class="fa fa-google"></span>Google
										</a>
									</div>
									<div class="facebook-div social-div">
										<a id="fblogin" class="btn btn-social btn-facebook"> <span class="fa fa-facebook"></span>Facebook
										</a>
									</div>
									<p></p>
									<div class="linkedin-div social-div pull-left">
										<a id="inlogin" class="btn btn-social btn-linkedin"> <span class="fa fa-linkedin"></span>LinkedIn
										</a>
									</div>
									<div class="github-div social-div pull-left">
										<a id="gitlogin" class="btn btn-social btn-github"> <span class="fa fa-github"></span>GitHub
										</a>
									</div>
									<div style="padding: 10px 0px; font-size: 14px; font-weight: 500; padding-top: 50px;">
										<a href="https://www.geeksforgeeks.org/why-create-an-account-on-geeksforgeeks/" style="color: #0f9d58;" target="_blank">Why Create an Account?</a>
									</div>
									<div class="tnc-div">
										By creating this account, you agree to our <a href="https://www.geeksforgeeks.org/privacy-policy/" target="_blank">Privacy Policy</a> & <a href="https://www.geeksforgeeks.org/cookie-policy/"
											target="_blank">Cookie Policy</a>.
									</div>
								</div>
							</div>
							<!--  -->
							<div class="forgot-div">
								<form class="login-form" id="Forgot">
									<input type="hidden" name="reqType" value="Forgot">
									<div class="modal-form-group">
										<div class="extra"></div>
									</div>
									<div class="modal-form-group">
										<p class="left">Please enter your email address or userHandle.</p>
									</div>
									<div class="modal-form-group">
										<i class="input-icon material-icons"> </i> <input name="user" id="fuser" type="text" class="modal-form-input" placeholder="Username/Email">
									</div>
									<div class="modal-form-group">
										<center>
											<div id="forgotCaptcha"></div>
										</center>
									</div>
									<div class="modal-form-group left">
										<a class="login-link">Back to Login</a>
									</div>
									<button class="btn btn-green center reset-button" type="submit">Reset Password</button>
								</form>
							</div>
						</div>
					</div>
				</div>
				<!-- Modal footer -->
				<!-- 					<div class="modal-footer"> -->
				<!-- 						<button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button> -->
				<!-- 					</div> -->

			</div>
		</div>
	</div>
<!--  -->

<script type="text/javascript">

// function onSignIn(googleUser) {
// 	  var profile = googleUser.getBasicProfile();
// 	  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
// 	  console.log('Name: ' + profile.getName());
// 	  console.log('Image URL: ' + profile.getImageUrl());
// 	  console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
// 	}
	
function onSignIn(googleUser) {
    // window.location.href='success.jsp';
    var profile = googleUser.getBasicProfile();
//     windo	w.location="authenticateUser?name="+profile.getName()+"&email="+profile.getEmail();
    var imagurl=profile.getImageUrl();
    var name=profile.getName();
    var email=profile.getEmail();
    document.getElementById("myImg").src = imagurl;
    document.getElementById("name").innerHTML = name;
    document.getElementById("myP").style.visibility = "hidden";
    document.getElementById("status").innerHTML = 'Welcome '+name+'!<a href=loginsuccess?email='+email+'&name='+name+'/>Continue with Google login</a></p>'
 }
	
// <a href="#" onclick="signOut();">Sign out</a>
  function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }
</script>
</body>
</html>