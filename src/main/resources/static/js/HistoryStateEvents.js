$(document).ready(function () {
    window.addEventListener('popstate', function () {
        if (window.location.pathname) {
            window.location.reload();
        }
    });
})