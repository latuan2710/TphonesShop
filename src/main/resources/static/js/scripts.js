function showPass() {
	if ($("#password").attr("type") == "password") {
		$("#password").attr("type", "text")
	} else {
		$("#password").attr("type", "password")
	}
	$("#basic-addon2 .fa").toggleClass("fa-eye-slash")
	$("#basic-addon2 .fa").toggleClass("fa-eye")
}
function cancel() {
	$(".sweet-overlay").remove();
	$(".sweet-alert").remove()
}