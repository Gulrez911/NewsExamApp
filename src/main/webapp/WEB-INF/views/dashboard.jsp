<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ctet.data.*, java.text.*, java.util.*"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>Bootstrap 5 Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"></script>

<script src="./resources/userprofile/js/jquery.min.js"></script>
<!-- <link href="./resources/newDesing/css/modal.css" rel="stylesheet"></link> -->
<!-- test -->
<link href="./resources/css/home.css" rel="stylesheet" type="text/css" />

<style>
.card {
	box-shadow: 0 10px 15px -3px rgb(0 0 0/ 7%), 0 4px 6px -2px
		rgb(0 0 0/ 5%);
}

.navbar {
	min-height: 80px;
}

.navbar-brand {
	padding: 0 15px;
	height: 60px;
	line-height: 60px;
}

.w3-bar .w3-bar-item {
	padding: 8px 16px;
	float: left;
	width: auto;
	border: none;
	display: block;
	outline: 0;
}

.nav-link {
	font-size: x-large;
	color: white !important;
}
</style>
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light ">
		<div class="container-fluid bg-black ">
			<a class="navbar-brand" href="#">Navbar</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav mx-auto mb-2 mb-lg-0 ">
					<li class="nav-item"><a class="nav-link active" style="background-color: darkblue;" aria-current="page" href="#">Tutorials</a></li>
					<li class="nav-item"><a class="nav-link" aria-current="page" href="#">Practice</a></li>
					<li class="nav-item"><a class="nav-link" aria-current="page" href="#">Test Series</a></li>
					<li class="nav-item"><a class="nav-link" aria-current="page" href="#">Exams</a></li>
					<li class="nav-item"><a class="nav-link" aria-current="page" href="#">GK & Current Affairs</a></li>
					<li class="nav-item"><a class="nav-link" aria-current="page" href="#">KBC</a></li>

				</ul>
				<form class="d-flex">
					<input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
					<button class="btn btn-success" type="submit">Search</button>
				</form>
			</div>
		</div>
	</nav>



	<div class="container">
		<div class="row">
			<c:forEach items="${listTest}" var="listTest" varStatus="loop">
				<div class="col-sm-3" style="padding-bottom: 50px; flex: 0 0 auto; width: 22%;">
					<div class="card" style="width: 260px;">
						<img src="https://mdbootstrap.com/img/new/standard/nature/183.jpg" class="card-img-top" alt="..." style="height: 100px">
						<div class="card-body">
							<h5 class="card-title text-center">${listTest.testName}</h5>
							<p class="card-text text-center">Lorem ipsum dolor sit amet consectetur adipisicing elit. Numquam unde vitae</p>
							<div class="d-flex justify-content-center">
								<a href="javascript:startTest(${listTest.id})" class="btn btn-primary ">Start Test</a>
							</div>
						</div>
					</div>

				</div>
			</c:forEach>


		</div>
	</div>

	<!-- Button trigger modal -->
<!-- 	<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">Launch demo modal</button> -->

	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">...</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- test  -->
	
	<div id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true"
                class="modal fade text-left">
                <div role="document" class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header row d-flex justify-content-between mx-1 mx-sm-3 mb-0 pb-0 border-0">
                            <div class="tabs" id="tab01">
                                <h6 class="text-muted" onclick="display()">My Apps</h6>
                            </div>
                            <div class="tabs active" id="tab02">
                                <h6 class="font-weight-bold" onclick="display1()">Knowledge Center</h6>
                            </div>
                             
                        </div>
                        <div class="line"></div>
                        <div class="modal-body p-0">
                            <fieldset id="tab011">
                                <div class="bg-light">
                                    <h5 class="text-center mb-4 mt-0 pt-4">My Apps</h5>
                                    <h6 class="px-3">Most Used Apps</h6>
                                    <ol class="pb-4">
                                        <li>Watsapp</li>
                                        <li>Instagram</li>
                                        <li>Chrome</li>
                                        <li>Linkedin</li>
                                    </ol>
                                </div>
                              
                            </fieldset>
                            <fieldset class="show" id="tab021" style="display: none;">
                                <div class="bg-light">
                                    <h5 class="text-center mb-4 mt-0 pt-4">Knowledge Center</h5>
                                    <form>
                                        <div class="form-group pb-5 px-3"> <select name="account" class="form-control">
                                                <option selected disabled>Select Product</option>
                                                <option>Product 1</option>
                                                <option>Product 2</option>
                                                <option>Product 3</option>
                                                <option>Product 4</option>
                                            </select> </div>
                                    </form>
                                </div>
                                
                            </fieldset>
                            
                        </div>
                         <div class="modal-footer d-flex flex-column justify-content-center border-0">
                            <p class="text-muted">Can't find what you're looking for?</p> <button type="button"
                                class="btn btn-primary">Contact Support Team</button>
                        </div>
                    </div>
                </div>
            </div>
            
            <!--  end modal-->
            
            	<!-- Modal -->
	<div class="modal" id="myModal2">
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
	<script type="text/javascript">
	function display() {
		$('#tab011').css('display','none');
		$('#tab021').css('display','block');
	 
	}
	function display1() {
		$('#tab021').css('display','none');
		$('#tab011').css('display','block');
	}

		function startTest(id) {
// 			$('#exampleModal').modal('show'); 
			
// 			$('#myModal').modal('show'); 
			$('#myModal2').modal('show'); 

		}
	</script>
</body>

</html>