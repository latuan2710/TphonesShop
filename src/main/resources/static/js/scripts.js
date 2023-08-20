if ($(window).width() <= 766) {
	$('body').toggleClass('content-wrapper')
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
function cancel() {
	$(".sweet-overlay").remove();
	$(".sweet-alert").remove()
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