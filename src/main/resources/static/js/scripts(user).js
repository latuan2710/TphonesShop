function showPass() {
	if ($("#password").attr("type") == "password") {
		$("#password").attr("type", "text")
	} else {
		$("#password").attr("type", "password")
	}
	$("#basic-addon2 .fa").toggleClass("fa-eye-slash")
	$("#basic-addon2 .fa").toggleClass("fa-eye")
}

$(window).ready(function() {
	$("#ProductPhotoImg").height($(".single-product-content").height() + "px")
	updateCartCount($(".cart-items li").length - 1)
})

function updateCartCount(n) {
	$(".cart-total .bigcounter").text(n)
}

$(window).on('scroll', function() {
	if ($(this).scrollTop() > 50) {
		$('.header-sticky').addClass("is-sticky");
	}
	else {
		$('.header-sticky').removeClass("is-sticky");
	}
});

$(window).ready(function() {
	$(".map").height($("div.single-footer-wiedget.mb-30.col-md-6.col-12").height() + "px");
	$(".map").html(`<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3915.807998549231!2d106.66129797657385!3d11.053017325985426!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3174d1d7df763eaf%3A0xf4323e44f2867057!2zxJDhuqFpIGjhu41jIFF14buRYyB04bq_IE1p4buBbiDEkMO0bmc!5e0!3m2!1svi!2s!4v1691226148630!5m2!1svi!2s" 
				width="400" height="300" style="border:0;" 
				allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>`)
})