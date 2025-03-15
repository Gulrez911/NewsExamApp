<!DOCTYPE html>
<html lang="en">
<head>
<!--  -->
<!-- <meta name="google-signin-client_id" content="344993853369-4taqdv01ek7clj1mqnh9q7nn4m50e755.apps.googleusercontent.com"> -->
   	<meta name="google-signin-client_id" content="1031150543105-cqeanuk2t9cfn5sveu27mqr5u1n7com4.apps.googleusercontent.com">
<!--    	<meta name="google-signin-client_id" content="747944710575-urmuh4k6062dp20uprn8a9m7trqamage.apps.googleusercontent.com"> -->
<!--    	<meta name="google-signin-client_id" content="367719537620-ti1q7uj6o40p1k1cdo7r66hd5p6eips0.apps.googleusercontent.com"> -->

<!--  -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bootstrap Navbar Color Schemes</title>
 
<style>
 .data {
    display: none;
}

.g-signin2 {
    position: absolute;
    top: 50%;
    left: 50%;
    margin-top: -50px;
    margin-left: -50px;
}
</style>
</head>
<body>
	  <h2 class="alert alert-success">Sign-In With Google Using JavaScript</h2>
    <div class="g-signin2" data-onsuccess="onSignIn"></div>

    <div class="data">
        <p>Name</p>
        <p id="name"></p>
        <p>Image</p>
        <img id="image" class="rounded-circle" width="100" height="100" />
        <p>Email</p>
        <p id="email"></p>
        <button type="button" class="btn btn-danger" onclick="signOut();">Sign Out</button>
    </div>

    <script src="index.js" async defer></script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8"
        crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>


 
<script type="text/javascript">

// function onSignIn(googleUser) {
// 	  var profile = googleUser.getBasicProfile();
// 	  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
// 	  console.log('Name: ' + profile.getName());
// 	  console.log('Image URL: ' + profile.getImageUrl());
// 	  console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
// 	}
	
function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    $("#name").text(profile.getName());
    $("#email").text(profile.getEmail());
    $("#image").attr('src', profile.getImageUrl());
    $(".data").css("display", "block");
    $(".g-signin2").css("display", "none");
}
	
// <a href="#" onclick="signOut();">Sign out</a>
function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        alert("You have been signed out successfully");
        $(".data").css("display", "none");
        $(".g-signin2").css("display", "block");
    });
}
</script>
</body>