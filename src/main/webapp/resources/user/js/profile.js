function logout() {
	modalOpen('Success', 'You will be sign out', 'success');
	window.location = "redirect";

}


$("#profile33").click(function() {
	$(".profile-photo-show").toggleClass("photo-show");
	$(".ww").toggleClass("photo-show");
});


document.querySelector(".ww").addEventListener("click", function(e) {
	if (e.target != this) return
	$(".profile-photo-show").toggleClass("photo-show");
	$(".ww").toggleClass("photo-show");
});