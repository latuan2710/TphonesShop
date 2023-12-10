if ($(window).width() <= 766) {
	$('body').toggleClass('content-wrapper')
}
function onlyNumbers(event) {
	if (event.ctrlKey || event.altKey || event.metaKey || event.key === "Backspace" || event.key === "Delete" || event.key === "ArrowLeft" || event.key === "ArrowRight") {
		return true;
	}
	if (/^\d|-$/i.test(event.key)) {
		return true;
	}
	event.preventDefault();
	return false;
}
function showPass() {
	if ($("#password").attr("type") == "password") {
		$("#password").attr("type", "text")
	} else {
		$("#password").attr("type", "password")
	}
	$("#basic-addon2 .fa").toggleClass("fa-eye-slash")
	$("#basic-addon2 .fa").toggleClass("fa-eye")
}
function alert_action(alert) {
	if (alert == "success") {
		$.toast({
			heading: 'Successfully!',
			position: 'top-right',
			loaderBg: '#ff6849',
			icon: 'success',
			hideAfter: 3500,
			stack: 6
		});
	} else if (alert == "error") {
		$.toast({
			heading: 'Error',
			position: 'top-right',
			loaderBg: '#ff6849',
			icon: 'error',
			hideAfter: 3500
		});
	} else if (alert == "warning") {
		$.toast({
			heading: 'Warning',
			text: 'You can not delete this!',
			position: 'top-right',
			loaderBg: '#ff6849',
			icon: 'warning',
			hideAfter: 3500,
			stack: 6
		});
	}
}