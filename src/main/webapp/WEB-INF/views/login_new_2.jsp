<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>


<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="materialize is a material design based mutipurpose responsive template">
<meta name="keywords" content="material design, card style, material template, portfolio, corporate, business, creative, agency">
<meta name="author" content="trendytheme.net">
<title>eAssess</title>
<!--  favicon -->
<link href="./resources/userprofile/images/E-assess_E.png" rel="shortcut icon">
<link href='https://fonts.googleapis.com/css?family=Raleway:400,300,500,700,900' rel='stylesheet' type='text/css'>
<!-- Material Icons CSS -->
<link href="./resources/eAssess/assets/fonts/iconfont/material-icons.css" rel="stylesheet">
<!-- owl.carousel -->
<link href="./resources/eAssess/assets/owl.carousel/assets/owl.carousel.css" rel="stylesheet">
<link href="./resources/eAssess/assets/owl.carousel/assets/owl.theme.default.min.css" rel="stylesheet">
<!-- FontAwesome CSS -->
<link href="./resources/eAssess/assets/fonts/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<!-- materialize -->
<link href="./resources/eAssess/assets/materialize/css/materialize.min.css" rel="stylesheet">
<!-- Bootstrap -->
<link href="./resources/eAssess/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- shortcodes -->
<link href="./resources/eAssess/assets/css/shortcodes/shortcodes.css" rel="stylesheet">
<link href="./resources/eAssess/assets/css/shortcodes/login.css" rel="stylesheet">
<!-- Style CSS -->
<link href="./resources/eAssess/assets/css/style.css" rel="stylesheet">
<!--               <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script> -->
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.6.0/slick.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<!-- jQuery library -->

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<style type="text/css">
.customer-logos {
	/*   background-color: #111; */
	
}

/* Slider */
.slick-slide {
	margin: 0px 20px;
}

.slick-slide img {
	width: 100%;
}

.slick-slider {
	position: relative;
	display: block;
	box-sizing: border-box;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	-webkit-touch-callout: none;
	-khtml-user-select: none;
	-ms-touch-action: pan-y;
	touch-action: pan-y;
	-webkit-tap-highlight-color: transparent;
}

.slick-list {
	position: relative;
	display: block;
	overflow: hidden;
	margin: 0;
	padding: 0;
}

.slick-list:focus {
	outline: none;
}

.slick-list.dragging {
	cursor: pointer;
	cursor: hand;
}

.slick-slider .slick-track, .slick-slider .slick-list {
	-webkit-transform: translate3d(0, 0, 0);
	-moz-transform: translate3d(0, 0, 0);
	-ms-transform: translate3d(0, 0, 0);
	-o-transform: translate3d(0, 0, 0);
	transform: translate3d(0, 0, 0);
}

.slide {
	width: 160px;
}

.slick-track {
	position: relative;
	top: 0;
	left: 0;
	display: block;
}

.slick-track:before, .slick-track:after {
	display: table;
	content: '';
}

.slick-track:after {
	clear: both;
}

.slick-loading .slick-track {
	visibility: hidden;
}

.slick-slide {
	display: none;
	float: left;
	height: 100%;
	min-height: 1px;
}

[dir='rtl'] .slick-slide {
	float: right;
}

.slick-slide img {
	display: block;
}

.slick-slide.slick-loading img {
	display: none;
}

.slick-slide.dragging img {
	pointer-events: none;
}

.slick-initialized .slick-slide {
	display: block;
}

.slick-loading .slick-slide {
	visibility: hidden;
}

.slick-vertical .slick-slide {
	display: block;
	height: auto;
	border: 1px solid transparent;
}

.slick-arrow.slick-hidden {
	display: none;
}
</style>
</head>

<body id="top" class="has-header-search">

	<!--header start-->
	<header id="header" class="tt-nav nav-border-bottom">

		<div class="header-sticky light-header ">

			<div class="container">
				<div id="materialize-menu" class="menuzord">

					<!--logo start-->
					<a href="index.html" class="logo-brand"> <img class="retina" src="./resources/eAssess/images/Logo.png" alt="" />
					</a>
					<!--logo end-->

					<!--mega menu start-->
					<ul class="menuzord-menu pull-right">

						<li><a href="prospect">Contact</a></li>

					</ul>
					<!--mega menu end-->

				</div>
			</div>
		</div>

	</header>
	<!--header end-->

	<section class="banner-wrapper parallax-bg banner-12 overlay dark-5 valign-wrapper height-650" data-stellar-background-ratio="0.5">
		<div class="valign-cell">
			<div class="container">
				<div class="col-md-7">
					<div class="text-left">
						<h1 class="intro-title text-capitalize white-text">Best Way to measure the effectiveness of your Training Programs</h1>
						<!--<a href="#" class="btn btn-lg waves-effect waves-light mt-30">Learn More</a> -->
					</div>
				</div>
				<div class="col-md-5">

					<div class="loginform">
						<h3>Login</h3>
						<form name="userloginform" class="userform" method="post" modelAttribute="user" action="authenticate">
							<div class="formfield">
								<label>Email</label>
								<form:input path="user.email" name="email" id="username" required="true" />
							</div>
							<div class="formfield">
								<label>Password</label>
								<form:password path="user.password" name="password" id="password" required="true" />
							</div>

							<div class="formfield">
								<label>&nbsp;</label> <input type="submit" value="Login">
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>
	</section>

	<section class="whatwehave section-padding">
		<div class="container">
			<div class="text-center mb-50">
				<h2 class="section-title">What We Have</h2>
				<hr class="line">
			</div>

			<div class="row equal-height-row">

				<div class="col-md-12">
					<div class="col-md-4 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon">
								<img src="./resources/eAssess/images/MCQ.jpg">
							</div>
							<div class="desc">
								<h2>Single/Multiple Choice Questions</h2>
							</div>
						</div>
					</div>
					<div class="col-md-4 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/fill_in_blank.png">
							</div>
							<div class="desc">
								<h2>Fill in the Blanks Questions</h2>
							</div>
						</div>
					</div>
					<div class="col-md-4 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/match_the_following.png">
							</div>
							<div class="desc">
								<h2>Match the following Questions</h2>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-12">
					<div class="col-md-4 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/subjective.png">
							</div>
							<div class="desc">
								<h2>Subjective Questions</h2>
							</div>
						</div>
					</div>
					<div class="col-md-4 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/image_upload.png">
							</div>
							<div class="desc">
								<h2>Upload Image as Answers</h2>
							</div>
						</div>
					</div>
					<div class="col-md-4 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/video_upload1.jpg">
							</div>
							<div class="desc">
								<h2>Upload Video as Answers</h2>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-12">
					<div class="col-md-4 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/coding.png">
							</div>
							<div class="desc">
								<h2>Coding Questions - Java, Python Dot Net, PHP, and many more</h2>
							</div>
						</div>
					</div>
					<div class="col-md-4 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/web_proctoring.png">
							</div>
							<div class="desc">
								<h2>Web Proctored Tests</h2>
							</div>
						</div>
					</div>
					<div class="col-md-4 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/languages.png">
							</div>
							<div class="desc">
								<h2>Support for Languages</h2>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-12">
					<div class="col-md-4 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/highly_configured.png">
							</div>
							<div class="desc">
								<h2>Highly Configured Tests</h2>
							</div>
						</div>
					</div>
					<div class="col-md-4 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/test_results.png">
							</div>
							<div class="desc">
								<h2>Test Level Reports</h2>
							</div>
						</div>
					</div>
					<div class="col-md-4 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/across_tests.png">
							</div>
							<div class="desc">
								<h2>Reports across Tests</h2>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-12">
					<div class="col-md-4 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/user_profile.png">
							</div>
							<div class="desc">
								<h2>Detailed User Profile - Strengths/Weaknesses of Test Givers</h2>
							</div>
						</div>
					</div>
					<div class="col-md-4 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/content_repository.png">
							</div>
							<div class="desc">
								<h2>Vast Content Repository</h2>
							</div>
						</div>
					</div>
					<div class="col-md-4 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/cloud_onPremises_deployment.png">
							</div>
							<div class="desc">
								<h2>Both Cloud/ and On Premises Deployment</h2>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-12 lastrow">
					<div class="col-md-12 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/train_and_assess.png">
							</div>
							<div class="desc">
								<h2>(TRAIN AND ASSESS) Modules - Share Training Content Videos/Meeting Links/Other material along with a bunch of test's only to your audience</h2>
							</div>
						</div>
					</div>
					<div class="col-md-12 mb-30">
						<div class="featured-item border-box radius-4 hover brand-hover equal-height-column">
							<div class="icon mb-10">
								<img src="./resources/eAssess/images/integration.png">
							</div>
							<div class="desc">
								<h2>Integration with other Systems(LMS) in your Enterprise - SSO / Custom</h2>
							</div>
						</div>
					</div>
				</div>

			</div>

		</div>
	</section>

	<section class="waystouse section-padding gray-bg">
		<div class="container">

			<div class="text-center mb-50">
				<h2 class="section-title">Ways to Use</h2>
				<hr class="line">
			</div>

			<div class="row">
				<div class="col-md-12">

					<div class="border-box-tab">

						<!-- Nav tabs -->
						<ul class="nav nav-tabs nav-justified" role="tablist">
							<li role="presentation" class="active"><a href="#tab-18" class="waves-effect waves-dark" role="tab" data-toggle="tab"> Test Platform</a></li>
							<li role="presentation"><a href="#tab-19" class="waves-effect waves-dark" role="tab" data-toggle="tab"> Test & Content Platform</a></li>
							<li role="presentation"><a href="#tab-20" class="waves-effect waves-dark" role="tab" data-toggle="tab"> Test, Content & LMS platform</a></li>
							<li role="presentation"><a href="#tab-21" class="waves-effect waves-dark" role="tab" data-toggle="tab"> Enterprise</a></li>
						</ul>

						<!-- Tab panes -->
						<div class="panel-body">
							<div class="tab-content">
								<div role="tabpanel" class="tab-pane fade in active" id="tab-18">
									<div class="row equal-height-row">
										<div class="col-md-12">
											<div class="col-md-4"></div>
											<div class="col-md-4">
												<div class="itembox">
													<img class="leftangle" src="./resources/eAssess/images/leftangle.png">

													<div class="icon">
														<i class="material-icons brand-icon"></i>
													</div>
													<div class="desc">User</div>
												</div>
											</div>
											<div class="col-md-4"></div>
										</div>
										<div class="bottomdiagram mt-20">
											<div class="item">
												<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
												<div class="itembox">
													<div class="desc">Buy Exam Sessions</div>
												</div>
											</div>
											<div class="item">
												<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
												<div class="itembox">
													<div class="desc">Get Admin Access</div>
												</div>
											</div>
											<div class="item">
												<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
												<div class="itembox">
													<div class="desc">Create Questions</div>
												</div>
											</div>
											<div class="item">
												<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
												<div class="itembox">
													<div class="desc">Create Tests</div>
												</div>
											</div>
											<div class="item">
												<div class="itembox">
													<div class="desc">Share Tests</div>
												</div>
											</div>
										</div>
									</div>
								</div>

								<div role="tabpanel" class="tab-pane fade" id="tab-19">
									<div class="row equal-height-row">
										<div class="col-md-12">
											<div class="col-md-4"></div>
											<div class="col-md-4">
												<div class="itembox">
													<img class="leftangle" src="./resources/eAssess/images/leftangle.png">

													<div class="icon">
														<i class="material-icons brand-icon"></i>
													</div>
													<div class="desc">User</div>
												</div>
											</div>
											<div class="col-md-4"></div>
										</div>
										<div class="bottomdiagram mt-20">
											<div class="item">
												<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
												<div class="itembox">
													<div class="desc">Buy Exam Sessions and Content</div>
												</div>
											</div>
											<div class="item">
												<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
												<div class="itembox">
													<div class="desc">Get Admin Access</div>
												</div>
											</div>

											<div class="fullitem">
												<div class="item">
													<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
													<div class="itembox">
														<div class="desc">Create Questions</div>
													</div>
												</div>
												<div class="item">
													<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
													<div class="itembox">
														<div class="desc">Create Tests</div>
													</div>
												</div>
												<div class="item">
													<img class="useexistarrow" src="./resources/eAssess/images/arrow.png"> <img class="leftarrow" src="./resources/eAssess/images/arrow.png">
													<div class="itembox">
														<div class="desc">Use Existing Content - Questions & Tests</div>
													</div>
												</div>
											</div>

											<div class="sharetestsitem item">
												<div class="itembox">
													<div class="desc">Share Tests</div>
												</div>
											</div>
										</div>
									</div>
								</div>

								<div role="tabpanel" class="tab-pane fade" id="tab-20">
									<div class="row equal-height-row">
										<div class="col-md-12">
											<div class="col-md-4"></div>
											<div class="col-md-4">
												<div class="itembox">
													<img class="leftangle" src="./resources/eAssess/images/leftangle.png">

													<div class="icon">
														<i class="material-icons brand-icon"></i>
													</div>
													<div class="desc">User</div>
												</div>
											</div>
											<div class="col-md-4"></div>
										</div>
										<div class="bottomdiagram mt-20">
											<div class="item">
												<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
												<div class="itembox">
													<div class="desc">Buy Modules, Exam Sessions and Content</div>
												</div>
											</div>
											<div class="item">
												<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
												<div class="itembox">
													<div class="desc">Get Admin Access</div>
												</div>
											</div>
											<div class="item">
												<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
												<div class="itembox">
													<div class="desc">Create Training Content/Module</div>
												</div>
											</div>
											<div class="item">
												<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
												<div class="itembox">
													<div class="desc">Map Multiple Tests to a Module</div>
												</div>
											</div>
											<div class="sharemoduleitem item">
												<div class="itembox">
													<div class="desc">Share Module(Training content + Tests) to your audience</div>
												</div>
											</div>
										</div>
									</div>
								</div>


								<div role="tabpanel" class="tab-pane fade" id="tab-21">
									<div class="row equal-height-row">
										<div class="col-md-12">
											<div class="col-md-4"></div>
											<div class="col-md-4">
												<div class="itembox">
													<img class="leftangle" src="./resources/eAssess/images/leftangle.png">

													<div class="icon">
														<i class="material-icons brand-icon"></i>
													</div>
													<div class="desc">User</div>
												</div>
											</div>
											<div class="col-md-4"></div>
										</div>
										<div class="bottomdiagram mt-20">
											<div class="item">
												<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
												<div class="itembox">
													<div class="desc">Buy Enterprise License</div>
												</div>
											</div>
											<div class="integrateitem item">
												<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
												<div class="itembox">
													<div class="desc">Integrate with exixsting LMS, if any through SSO</div>
												</div>
											</div>

											<div class="fullitem">
												<div class="item">
													<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
													<div class="itembox">
														<div class="desc">Create Training Content/Module</div>
													</div>
												</div>
												<div class="item">
													<img class="leftarrow" src="./resources/eAssess/images/arrow.png">
													<div class="itembox">
														<div class="desc">Map Multiple Tests to a Module</div>
													</div>
												</div>
												<div class="enterpriseexistitem1 item">
													<img class="useexistarrow" src="./resources/eAssess/images/arrow.png"> <img class="leftarrow" src="./resources/eAssess/images/arrow.png">
													<div class="itembox">
														<div class="desc">Use Existing Content - Questions & Tests</div>
													</div>
												</div>
												<div class="enterpriseexistitem2 item">
													<img class="useexistarrow" src="./resources/eAssess/images/arrow.png"> <img class="leftarrow" src="./resources/eAssess/images/arrow.png">
													<div class="itembox">
														<div class="desc">Create Questions & Tests</div>
													</div>
												</div>
											</div>

											<div class="lastitem">
												<div class="item">
													<div class="itembox">
														<div class="desc">Share Module(Training + Tests) to your audience</div>
													</div>
												</div>
												<div class="item">
													<div class="itembox">
														<div class="desc">Share Tests</div>
													</div>
												</div>
											</div>

										</div>
									</div>
								</div>
							</div>
						</div>

					</div>

				</div>
				<!-- /.col-md-12 -->
			</div>
			<!-- /.row -->

		</div>
		<!-- /.container -->
	</section>


	<!-- </div> -->

	<section class="whocanuse section-padding" style="padding-bottom: 0px">
		<div class="container">
			<div class="text-center mb-50">
				<h2 class="section-title">Who can use eAssess</h2>
				<hr class="line">
			</div>

			<div class="row equal-height-row">
				<div class="item col-md-3 mb-30">
					<div class="featured-item school border-box radius-4 equal-height-column">
						<div class="overlay"></div>
						<div class="desc">
							<h2>Schools</h2>
						</div>
					</div>
				</div>
				<div class="item col-md-3 mb-30">
					<div class="featured-item colleges border-box radius-4 hover brand-hover equal-height-column">
						<div class="overlay"></div>
						<div class="desc">
							<h2>Colleges</h2>
						</div>
					</div>
				</div>
				<div class="item col-md-3 mb-30">
					<div class="featured-item coaching border-box radius-4 hover brand-hover equal-height-column">
						<div class="overlay"></div>
						<div class="desc">
							<h2>Coaching Classes</h2>
						</div>
					</div>
				</div>
				<div class="item col-md-3 mb-30">
					<div class="featured-item privatetrainers border-box radius-4 hover brand-hover equal-height-column">
						<div class="overlay"></div>
						<div class="desc">
							<h2>Private Trainers</h2>
						</div>
					</div>
				</div>
				<div class="item col-md-3 mb-30">
					<div class="featured-item trainingorganizations border-box radius-4 hover brand-hover equal-height-column">
						<div class="overlay"></div>
						<div class="desc">
							<h2>Training Organizations</h2>
						</div>
					</div>
				</div>
				<div class="col-md-12 mb-30">
					<div class="col-md-3"></div>
					<div class="col-md-12 mb-30">
						<div class="featured-item enterprise border-box radius-4 hover brand-hover equal-height-column">
							<div class="overlay"></div>
							<div class="desc">
								<h2>Enterprises (Any Domain) - To Conduct Interview Drives, Campus Recruitment, Internal Training, Measure Effectiveness of e Learning, Conduct Hackathons, Surveys, Coding Tests and more</h2>
							</div>
						</div>
					</div>
					<div class="col-md-3"></div>
				</div>
			</div>
		</div>
	</section>


	<section class="whocanuse gray-bg">
		<div class="container">
			<div class="text-center mb-50 mt-50">
				<h2 class="section-title">Meet Our Clients</h2>
				<hr class="line">
			</div>

		</div>
	</section>
	<section class="waystouse section-padding gray-bg">

		<div class="container gray-bg">
			<div class="row" style="padding-bottom: 44px">
				<div class="customer-logos">
					<div class="slide">
						<img src="./resources/userprofile/images/logo-abm2.png" style="height: 90px;">
					</div>
					<div class="slide">
						<img src="./resources/userprofile/images/LTI.png" style="height: 110px;">
					</div>
					<div class="slide">
						<img src="./resources/userprofile/images/cognizant.png" style="height: 100px;">
					</div>
					<div class="slide">
						<img src="./resources/userprofile/images/panorma.png" style="height: 100px;">
					</div>
					<div class="slide">
						<img src="./resources/userprofile/images/Pearson-Institute.png" style="height: 100px;">
					</div>
					<div class="slide">
						<img src="./resources/userprofile/images/AIE.png" style="height: 90px;">
					</div>
					<div class="slide">
						<img src="./resources/userprofile/images/Infrasoft copy.png" style="height: 150px;">
					</div>
				</div>
			</div>
		</div>
	</section>
	<footer class="footer footer-four">
		<div class="primary-footer brand-bg text-center">
			<div class="container">



				<hr class="mt-15">

				<div class="row">
					<div class="col-md-12 mt-20">
						<div class="col-md-4">
							<div class="footer-logo">
								<img src="./resources/eAssess/images/Logo.png" alt="">
							</div>
						</div>
						<div class="col-md-8">
							<span class="copy-text">Copyright &copy; 2020 <a href="#">E-Assess</a> &nbsp; | &nbsp; All Rights Reserved &nbsp;
							</span>
						</div>

					</div>
					<!-- /.col-md-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.container -->
		</div>
		<!-- /.primary-footer -->
	</footer>

	<!--  Login Modal -->
	<div class="modal fade" id="loginmodal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="login-wrapper">
			<div class="card-wrapper"></div>
			<div class="card-wrapper">
				<h1 class="title">Login</h1>
				<a href="/AssesmentApp/OnetPage">Assessment Profiler</a>

				<form name="userloginform" class="userform" method="post" modelattribute="user" action="authenticate">
					<div class="input-container">
						<input id="username" name="email" class="form-control" type="email" required="true" value=""> <label for="username">Username</label>
						<div class="bar"></div>
					</div>
					<div class="input-container">
						<input id="password" name="password" class="form-control" required="true" type="password" value=""> <label for="password">Password</label>
						<div class="bar"></div>
					</div>
					<div class="input-container">
						<input id="companyName" name="companyName" class="form-control" required="true" type="text" value=""> <label for="Company">Company</label>
						<div class="bar"></div>
					</div>
					<div class="button-container">
						<button type="submit" name="submit" class="btn-block waves-effect waves-light btn">Login</button>

					</div>
					<div class="footer">
						<a href="http://beforesubmit.com/qe-assess/login.html#">Forgot your password?</a>
					</div>
				</form>

			</div>
		</div>
	</div>

	<!-- jQuery -->

	<!--         <script src="./resources/eAssess/assets/materialize/js/materialize.min.js"></script> -->
	<!--         <script src="./resources/eAssess/assets/owl.carousel/owl.carousel.min.js"></script> -->

	<!-- 		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/5.4.0/bootbox.js"></script> -->


	<script>
		$(document).ready(function() {
			$('.customer-logos').slick({
				slidesToShow : 4,
				slidesToScroll : 1,
				autoplay : true,
				autoplaySpeed : 1000,
				arrows : false,
				dots : false,
				pauseOnHover : false,
				responsive : [ {
					breakpoint : 768,
					settings : {
						slidesToShow : 3
					}
				}, {
					breakpoint : 520,
					settings : {
						slidesToShow : 2
					}
				} ]
			});
		});

		if ($('.quote-carousel').length > 0) {

			$('.quote-carousel').owlCarousel({
				loop : true,
				autoHeight : true,
				responsive : {
					0 : {
						items : 1
					},
					600 : {
						items : 1
					},
					1000 : {
						items : 1
					}
				}
			});
		}
	</script>

	<c:if test="${message != null}">
		<script>
			bootbox.alert({
				message : "${message}",
				backdrop : true
			});
		</script>
	</c:if>

</body>

</html>
