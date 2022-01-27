$(window).scroll(function () {
    if ($(window).scrollTop() + $(window).height() > $(document).height() - 100) {
        console.log($(window).scrollTop() + " " + $(window).height() + " " + $(document).height())
    }
});