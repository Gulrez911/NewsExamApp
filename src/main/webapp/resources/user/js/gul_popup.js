 let overlay = document.querySelector(".overlay");
  let open = document.querySelector("#open");
  let close = document.querySelector("#close");
//  open.addEventListener("click", () => {
    // $("#gul-icon2").css("display", "none");
//    modalOpen();
//  });
   
	close.addEventListener("click", () => {
		modalClose();
	});
	overlay.addEventListener("click", function (e) {
		if (e.target != this) return
		overlay.classList.remove("show");
	})
	function modalClose() {
		overlay.classList.remove("show");
	}
  
function modalOpen(obj, obj2, obj3) {
		if (typeof obj === 'string' || obj instanceof String) {
			console.log("string");
			document.getElementById('gul-title').innerHTML = obj;
		} else {
			document.getElementById('gul-title').innerHTML = "";
		}
		if (typeof obj2 === 'string' || obj2 instanceof String) {
			console.log("string");
			document.getElementById('gul-con').innerHTML = obj2;
		}
		else {
			document.getElementById('gul-con').innerHTML = "";
		}
		if (typeof obj3 === 'string' || obj3 instanceof String || (typeof obj === 'object' && obj !== undefined)) {
			if (obj3 === "success" || obj.icon === "success") {
				$("#gul-icon").css("display", "block");
				$("#gul-icon2").css("display", "none");
				$("#gul-icon3").css("display", "none");
				$("#gul-icon4").css("display", "none");
			} else if (obj3 === "error" || obj.icon === "error") {
				$("#gul-icon2").css("display", "block");
				$("#gul-icon3").css("display", "none");
				$("#gul-icon").css("display", "none");
				$("#gul-icon4").css("display", "none");
			} else if (obj3 === "warning" || obj.icon === "warning") {
				$("#gul-icon3").css("display", "block");
				$("#gul-icon2").css("display", "none");
				$("#gul-icon").css("display", "none");
				$("#gul-icon4").css("display", "none");
			} else if (obj3 === "info" || obj.icon === "info") {
				$("#gul-icon4").css("display", "block");
				$("#gul-icon3").css("display", "none");
				$("#gul-icon2").css("display", "none");
				$("#gul-icon").css("display", "none");
			}
		} else {
			$("#gul-icon4").css("display", "none");
			$("#gul-icon3").css("display", "none");
			$("#gul-icon2").css("display", "none");
			$("#gul-icon").css("display", "none");
		}
		// it's a string
		if (typeof obj === 'object') {
			if (obj.title !== undefined) {
				console.log("not undefined");
				document.getElementById('gul-title').innerHTML = obj.title;
			}
			if (obj.msg !== undefined) {
				console.log("not undefined");
				document.getElementById('gul-con').innerHTML = obj.msg;
			}
		}
		overlay.classList.add("show");
	}
  
    $("#close_button").click(function () {
        modalClose();
  });