!function (e) {
    jQuery(document).ready((function () {
        var i;
        function s() {
            e(window).width() <= 991 ? (e("#cate-toggle .right-menu > a").prepend('<i class="expand menu-expand"></i>'),
                e(".category-menu .right-menu ul").slideUp()) : (e(".category-menu .right-menu > a i").remove(),
                    e(".category-menu .right-menu ul").slideDown())
        }
        function t() {
            e(window).width() <= 991 ? e(".category-menu-list").hide() : e(".category-menu-list").show()
        }
        jQuery(".header-menu-area nav").meanmenu({
            meanMenuContainer: ".mobile-menu",
            meanScreenWidth: "991",
            meanMenuClose: '<i class="ion pe-7s-close"></i>'
        }),
            e(".drodown-show > span").on("click", (function (i) {
                i.preventDefault(),
                    e(this).hasClass("active") ? (e(".drodown-show > a").removeClass("active").siblings(".ht-dropdown").slideUp(),
                        e(this).removeClass("active").siblings(".ht-dropdown").slideUp()) : (e(".drodown-show > span").removeClass("active").siblings(".ht-dropdown").slideUp(),
                            e(this).addClass("active").siblings(".ht-dropdown").slideDown())
            }
            )),
            e("nav .main-menu > li").slice(-2).addClass("last-elements"),
            i = e(".mega-menu").children().length,
            e(".mega-menu").each((function () {
                4 == i && e(this).addClass("mega-menu-full")
            }
            )),
            e(".rx-parent").on("click", (function () {
                e(".rx-child").slideToggle(),
                    e(this).toggleClass("rx-change")
            }
            )),
            e(".category-heading").on("click", (function () {
                e(".category-menu-list").slideToggle(300)
            }
            )),
            s(),
            e(window).resize(s),
            t(),
            e(window).resize(t),
            e(".category-menu-hidden").find(".category-menu-list").hide(),
            e(".category-menu-list").on("click", "li a, li a .menu-expand", (function (i) {
                var s = e(this).hasClass("menu-expand") ? e(this).parent() : e(this);
                if (s.parent().hasClass("right-menu") && ("#" === s.attr("href") || e(this).hasClass("menu-expand")) && (s.siblings("ul:visible").length > 0 ? s.siblings("ul").slideUp() : (e(this).parents("li").siblings("li").find("ul:visible").slideUp(),
                    s.siblings("ul").slideDown())),
                    e(this).hasClass("menu-expand") || "#" === s.attr("href"))
                    return i.preventDefault(),
                        !1
            }
            )),
            e("#cate-toggle li.has-sub>a,#cate-mobile-toggle li.has-sub>a,#shop-cate-toggle li.has-sub>a").on("click", (function () {
                e(this).removeAttr("href");
                var i = e(this).parent("li");
                i.hasClass("open") ? (i.removeClass("open"),
                    i.find("li").removeClass("open"),
                    i.find("ul").slideUp()) : (i.addClass("open"),
                        i.children("ul").slideDown(),
                        i.siblings("li").children("ul").slideUp(),
                        i.siblings("li").removeClass("open"),
                        i.siblings("li").find("li").removeClass("open"),
                        i.siblings("li").find("ul").slideUp())
            }
            )),
            e("#cate-toggle>ul>li.has-sub>a").append('<span class="holder"></span>'),
            e(".cart-plus-minus").append('<div class="dec qtybutton"><i class="ion-ios-arrow-down"></i></div><div class="inc qtybutton"><i class="ion-ios-arrow-up"></i></div>'),
            e(".hero-slider").owlCarousel({
                smartSpeed: 1e3,
                nav: !1,
                loop: !0,
                animateOut: "fadeOut",
                animateIn: "fadeIn",
                autoplay: !1,
                navText: ["prev", "next"],
                responsive: {
                    0: {
                        items: 1,
                        autoplay: !0
                    },
                    600: {
                        items: 1
                    },
                    1e3: {
                        items: 1
                    }
                }
            }),
            e(".testimonial-slider").owlCarousel({
                smartSpeed: 1e3,
                nav: !1,
                navText: ['<i class="zmdi zmdi-chevron-left"></i>', '<i class="zmdi zmdi-chevron-right"></i>'],
                responsive: {
                    0: {
                        items: 1
                    },
                    450: {
                        items: 1
                    },
                    600: {
                        items: 1
                    },
                    1e3: {
                        items: 1
                    }
                }
            }),
            e(".categori-block-content").owlCarousel({
                nav: !1,
                loop: !1,
                navText: ["prev", "next"],
                responsive: {
                    0: {
                        items: 1
                    },
                    600: {
                        items: 1
                    },
                    1e3: {
                        items: 1
                    }
                }
            }),
            e(".feature-product-items").owlCarousel({
                nav: !1,
                loop: !1,
                navText: ["prev", "next"],
                responsive: {
                    0: {},
                    650: {
                        items: 1
                    },
                    768: {
                        items: 1
                    },
                    1e3: {
                        items: 1
                    }
                }
            }),
            e(".testimonial-active").owlCarousel({
                nav: !1,
                loop: !1,
                navText: ["prev", "next"],
                responsive: {
                    0: {
                        items: 1
                    },
                    600: {
                        items: 1
                    },
                    1e3: {
                        items: 1
                    }
                }
            }),
            e(".latest-product-active").owlCarousel({
                smartSpeed: 1e3,
                nav: !0,
                navText: ['<i class="fa fa-angle-left"></i>', '<i class="fa fa-angle-right"></i>'],
                responsive: {
                    0: {
                        items: 1
                    },
                    767: {
                        items: 1
                    },
                    768: {
                        items: 2
                    },
                    1e3: {
                        items: 2
                    },
                    1200: {
                        items: 2
                    }
                }
            }),
            e(".deal-product-active4").owlCarousel({
                smartSpeed: 1e3,
                nav: !0,
                navText: ['<i class="fa fa-angle-left"></i>', '<i class="fa fa-angle-right"></i>'],
                responsive: {
                    0: {
                        items: 1
                    },
                    450: {
                        items: 2
                    },
                    768: {
                        items: 2
                    },
                    1e3: {
                        items: 4
                    },
                    1200: {
                        items: 4
                    }
                }
            }),
            e(".cate-slider-nav .nav-prev").on("click", (function () {
                e(this).parents(".row").next(".cate-product-wrapper").find(".cate-product-slide").trigger("prev.owl.carousel")
            }
            )),
            e(".cate-slider-nav .nav-next").on("click", (function () {
                e(this).parents(".row").next(".cate-product-wrapper").find(".cate-product-slide").trigger("next.owl.carousel")
            }
            )),
            e(".post-gallery").owlCarousel({
                nav: !0,
                autoplay: !0,
                autoplayTimeout: 5e3,
                navText: ['<i class="fa fa-caret-left"></i>', '<i class="fa fa-caret-right"></i>'],
                responsive: {
                    0: {
                        items: 1
                    },
                    450: {
                        items: 1
                    },
                    768: {
                        items: 1
                    },
                    1e3: {
                        items: 1
                    },
                    1200: {
                        items: 1
                    }
                }
            }),
            e.scrollUp({
                scrollText: '<i class="fa fa-angle-double-up"></i>',
                easingType: "linear",
                scrollSpeed: 900,
                animation: "fade"
            });
        var a = e(window);
        function n() {
            var i = a.width()
                , s = e(".cate-filter");
            i < 991 ? s.slideUp() : s.slideDown()
        }
        e(".product-categorie").each((function () {
            var i = e(this).find(".product-tab-filter-toggle")
                , s = e(this).find(".product-tab-filter-toggle span")
                , t = e(this).find(".cate-filter")
                , n = e(this).find(".cate-filter li a")
                , l = t.find(".active").text();
            s.text(l),
                i.on("click", (function () {
                    e(this).siblings(".cate-filter").slideToggle()
                }
                )),
                n.on("click", (function () {
                    var i = a.width()
                        , n = e(this).text();
                    s.text(n),
                        i < 991 && t.slideToggle()
                }
                ))
        }
        )),
            n(),
            a.resize(n),
            e(".single-slide-menu").slick({
                prevArrow: '<i class="fa fa-angle-left"></i>',
                nextArrow: '<i class="fa fa-angle-right slick-next-btn"></i>',
                slidesToShow: 3,
                responsive: [{
                    breakpoint: 1200,
                    settings: {
                        slidesToShow: 3,
                        slidesToScroll: 3
                    }
                }, {
                    breakpoint: 991,
                    settings: {
                        slidesToShow: 2,
                        slidesToScroll: 2
                    }
                }, {
                    breakpoint: 480,
                    settings: {
                        slidesToShow: 2,
                        slidesToScroll: 2
                    }
                }]
            }),
            e(".modal").on("shown.bs.modal", (function (i) {
                e(".single-slide-menu").resize()
            }
            )),
            e(".single-slide-menu a").on("click", (function (i) {
                i.preventDefault();
                var s = e(this).attr("href");
                e(".single-slide-menu a").removeClass("active"),
                    e(this).addClass("active"),
                    e(".product-details-large .tab-pane").removeClass("active show"),
                    e(".product-details-large " + s).addClass("active show")
            }
            )),
            e("#showlogin").on("click", (function () {
                e("#checkout-login").slideToggle(900)
            }
            )),
            e("#showcoupon").on("click", (function () {
                e("#checkout_coupon").slideToggle(900)
            }
            )),
            e("#cbox").on("click", (function () {
                e("#cbox-info").slideToggle(900)
            }
            )),
            e("#ship-box").on("click", (function () {
                e("#ship-box-info").slideToggle(1e3)
            }
            )),
            e(".card-header a").on("click", (function () {
                e(".card").removeClass("actives"),
                    e(this).parents(".card").addClass("actives")
            }
            ))
    }
    ))
}(jQuery);
