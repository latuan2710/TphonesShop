function showPass() {
	if ($("#password").attr("type") == "password") {
		$("#password").attr("type", "text")
	} else {
		$("#password").attr("type", "password")
	}
	$("#basic-addon2 .fa").toggleClass("fa-eye-slash")
	$("#basic-addon2 .fa").toggleClass("fa-eye")
}

$(window).ready(function () {
	$(".map").height($("div.single-footer-wiedget.mb-30.col-md-6.col-12").height() + "px");
	$(".map").html(`<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3915.807998549231!2d106.66129797657385!3d11.053017325985426!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3174d1d7df763eaf%3A0xf4323e44f2867057!2zxJDhuqFpIGjhu41jIFF14buRYyB04bq_IE1p4buBbiDEkMO0bmc!5e0!3m2!1svi!2s!4v1691226148630!5m2!1svi!2s" 
				width="400" height="300" style="border:0;" 
				allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>`)
	updateCart()
	$("#ProductPhotoImg").height($(".single-product-content").height() + "px")

	$.get("/getBrandList", function (data) {
		let menu = "";
		let widget = "";
		data.forEach(brand => {
			menu += `<li class="sub-menu-li"><a href="/all-product?brand=${brand.name}">${brand.name}</a></li>`;
			widget+=`<li><a href="/all-product?brand=${brand.name}">${brand.name}</a></li>`;
		});
		$('ul.dropdown').html(menu);
		$('#brand-widget').html(widget);
	})
})

$(window).on('scroll', function () {
	if ($(this).scrollTop() > 50) {
		$('.header-sticky').addClass("is-sticky");
	}
	else {
		$('.header-sticky').removeClass("is-sticky");
	}
});

function product_carousel(id) {
	let product_width = parseInt($(id + " .product-list .col-md-3.col-6").width()) + 30;
	let isDown = false;
	let startX;
	$(id + " button.to-left").click(function () {
		document.querySelector(id + ' .product-list .row').scrollBy({
			left: -product_width,
			top: 0,
			behavior: 'smooth'
		})
	})
	$(id + " button.to-right").click(function () {
		document.querySelector(id + ' .product-list .row').scrollBy({
			left: product_width,
			top: 0,
			behavior: 'smooth'
		})
	})
	$(".product-block-content .row").mousedown((e) => {
		isDown = true;
		startX = e.clientX
	})
	$(".product-block-content .row").mouseleave(() => {
		isDown = false;
	})
	$(".product-block-content .row").mouseup(() => {
		isDown = false;
	})
	$(".product-block-content .row").mousemove((e) => {
		if (!isDown) return;
		this.scrollLeft -= (e.clientX - startX);
		startX = e.clientX;
	})
}

// function addToCart() {

// }

function updateCart() {
	$.post("/get-cart", function (data) {
		if (data != "") {
			let total = 0;
			let str = `<a href="/cart">
			<span class="cart-info">
			  <span class="cart-total"><span class="bigcounter">${data.length}</span></span>
			  <span class="text">My Cart</span>
			</span>
		  </a>
		  <!--Cart Dropdown Start-->
		  <div class="header-cart">
			<ul class="cart-items">
			  <li class="single-cart-item single-cart-item-loop">`
			data.forEach(order => {
				total += order.finalPrice;
				str += `<div class="cart-single-item">
				<div class="cart-img">
				  <a href="/product/${order.product.name}" class="d-flex justify-content-center w-100">
					<img src="${order.product.featuredImage}" alt="Product title here - red" style="
					max-height: 100px;">
				  </a>
				  <span class="cart-sticker">${order.quantity}x</span>
				</div>
				<div class="cart-content">
				  <h5 class="product-name"><a
					  href="/product/${order.product.name}">${order.product.name}</a></h5>
				  <span class="product-price"><span class="money"
					  data-currency-usd="39.00$">$${order.price}</span>
					  </span>
				</div>
				<div class="cart-item-remove">
				  <a onclick="quickRemoveCart(${order.id})" href="javascript:void(0);">
					<i class="fa fa-trash"></i>
				  </a>
				</div>
			  </div>`
			});
			str += `
				</li>
                    </ul>
                    <div class="cart-total cart-total-price">
                      <h5>Total: <span class="shopping-cart__total"><span class="money">$${total}</span></span></h5>
                    </div>
                    <div class="checkout">
                      <a class="minicart-edit-cart" href="/cart">View Cart</a>
                      <a href="/checkout">Checkout</a>
                    </div>
            </div>
			`
			$('.mini-cart').html(str);
		} else {
			$('.mini-cart').html(`<a href="/cart">
			<span class="cart-info">
			  <span class="cart-total"><span class="bigcounter">0</span></span>
			  <span class="text">My Cart</span>
			</span>
		  </a><div class="header-cart">
		  <ul class="cart-items">
			<li class="cart-empty-title d-block">
			  <h5>Your cart is currently empty.</h5>
			</li></ul></div>`);
		}
	})
}

function quickRemoveCart(id) {
	$.post('/remove-cart', { id: id }, function (data) {
		if (data) {
			updateCart()
		}
	})
}

function removeCart(id) {
	$.post('/remove-cart', { id: id }, function (data) {
		if (data) {
			location.reload()
		}
	})
}

function alert_action(alert_action) {
	if (alert_action == 'true') {
		$.toast({
			heading: 'Successfully!',
			position: 'top-right',
			loaderBg: '#ff6849',
			icon: 'success',
			hideAfter: 3500,
			stack: 6
		});
	} else if (alert_action == 'false') {
		$.toast({
			heading: 'Error',
			position: 'top-right',
			loaderBg: '#ff6849',
			icon: 'error',
			hideAfter: 3500
		});
	}
	localStorage.removeItem('alert-action')
}

function search(e) {
	let key = $(e).val();
	console.log(key);
	if (key == "") {
		$("ul.search-results").addClass("d-none")
	} else {
		$.post("/search", { key: key }, function (data) {
			if (data != "") {
				$("ul.search-results").removeClass("d-none")
				let str = ""
				let i = 0;
				data.forEach(product => {
					str += `
					<li>
						<a href="/product/${product.name}">
							<span class="img-container">
								<img src="/product-upload/${product.id}/${product.id}.jpg"
									alt="product-img" loading="lazy">
							</span>
							<span class="title">${product.name}</span></a>
					</li>
					`
				});
				$("ul.search-results").html(str)
			} else {
				$("ul.search-results").addClass("d-none")
			}
		})
	}
}

function quickAdd(id) {
	$.post('/add-to-cart',
		{
			product_id: id,
		},
		function (data) {
			if (data) {
				localStorage.setItem("alert-action", data);
				location.reload()
			} else {
				location.href = '/login'
			}
		})
};