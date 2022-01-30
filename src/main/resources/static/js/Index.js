$(window).scroll(function () {
    if ($(window).scrollTop() + $(window).height() > $(document).height() - 100) {
        console.log($(window).scrollTop() + " " + $(window).height() + " " + $(document).height())
    }
});

AjaxManager.request("/post/16", {}, "GET", function (data) {
    console.log(data);
})

window.history.pushState("", "", '/post/16');
