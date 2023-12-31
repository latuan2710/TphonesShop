$(document).ready(function () {
    "use strict";
    var e = $("body");
    $(function () {
        $(".preloader").fadeOut()
    }),
        $(".right-side-toggle").on("click", function () {
            $(".right-sidebar").slideDown(50).toggleClass("shw-rside"),
                $(".fxhdr").on("click", function () {
                    e.toggleClass("fix-header")
                }),
                $(".fxsdr").on("click", function () {
                    e.toggleClass("fix-sidebar")
                });
            var i = $(".fxhdr");
            e.hasClass("fix-header") ? i.attr("checked", !0) : i.attr("checked", !1),
                e.hasClass("fix-sidebar") ? i.attr("checked", !0) : i.attr("checked", !1)
        }),
        $(function () {
            var i = function () {
                var i = 60
                    , s = window.innerWidth > 0 ? window.innerWidth : this.screen.width
                    , l = (window.innerHeight > 0 ? window.innerHeight : this.screen.height) - 1;
                768 > s ? ($("div.navbar-collapse").addClass("collapse"),
                    i = 100) : $("div.navbar-collapse").removeClass("collapse"),
                    1170 > s ? (e.addClass("content-wrapper"),
                        
                        $(".logo span").hide()) : (e.removeClass("content-wrapper"),
                           
                            $(".logo span").show()),
                    l -= i,
                    1 > l && (l = 1),
                    l > i && $("#page-wrapper").css("min-height", l + "px")
            }
                , s = window.location
                , l = $("ul.nav a").filter(function () {
                    return this.href === s || 0 === s.href.indexOf(this.href)
                }).addClass("active").parent().parent().addClass("in").parent();
            l.is("li") && l.addClass("active"),
                $(window).ready(i),
                $(window).on("resize", i)
        }),
        $(".open-close").on("click", function () {
            $("body").hasClass("content-wrapper") ? ($("body").trigger("resize"),
                $("body").removeClass("content-wrapper"),
                $(".logo span").show()) : ($("body").trigger("resize"),
                    $("body").addClass("content-wrapper"),
                    $(".logo span").hide())
        }),
        function (e, i, s) {
            var l = '[data-perform="panel-collapse"]'
                , o = '[data-perform="panel-dismiss"]';
            e(l).each(function () {
                var i = {
                    toggle: !1
                }
                    , s = e(this).closest(".panel")
                    , l = s.find(".panel-wrapper")
                    , o = e(this).children("i");
                l.length || (l = s.children(".panel-heading").nextAll().wrapAll("<div/>").parent().addClass("panel-wrapper"),
                    i = {}),
                    l.collapse(i).on("hide.bs.collapse", function () {
                        o.removeClass("ti-minus").addClass("ti-plus")
                    }).on("show.bs.collapse", function () {
                        o.removeClass("ti-plus").addClass("ti-minus")
                    })
            }),
                e(s).on("click", l, function (i) {
                    i.preventDefault();
                    var s = e(this).closest(".panel")
                        , l = s.find(".panel-wrapper");
                    l.collapse("toggle")
                }),
                e(s).on("click", o, function (i) {
                    function l() {
                        var i = s.parent();
                        s.remove(),
                            i.filter(function () {
                                return e(this).is('[class*="col-"]') && 0 === e(this).children("*").length
                            }).remove()
                    }
                    i.preventDefault();
                    var s = e(this).closest(".panel");
                    l()
                })
        }(jQuery, window, document),
        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        }),
        $(function () {
            $('[data-toggle="popover"]').popover()
        }),
        $(".list-task li label").on("click", function () {
            $(this).toggleClass("task-done")
        }),
        $(".settings_box a").on("click", function () {
            $("ul.theme_color").toggleClass("theme_block")
        }),
        $(".collapseble").on("click", function () {
            $(".collapseblebox").fadeToggle(350)
        }),
        e.trigger("resize"),
        $(".visited li a").on("click", function (e) {
            $(".visited li").removeClass("active");
            var i = $(this).parent();
            i.hasClass("active") || i.addClass("active"),
                e.preventDefault()
        }),
        $("#to-recover").on("click", function () {
            $("#loginform").slideUp(),
                $("#recoverform").fadeIn()
        }),
        $(".navbar-toggle").on("click", function () {
            $(".navbar-toggle i").toggleClass("ti-menu").addClass("ti-close")
        })
});
