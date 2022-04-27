$(document).ready(function () {
    AdvancedPostView.init();
    AdvancedPostView.getPost(window.location.pathname, function () {
        AjaxManager.request("/getPosts/" + AdvancedPostView.post.user.id + "?passedPosts=0", null, "GET", function (data) {
            for (let post of data) {
                document.getElementById("more-posts").appendChild($(`<a href="${post.links.self.link}" style="position: relative;justify-content: center"
                                                                                 class="col-lg-4 col-md-6 col-12 mb-2 d-flex extra-post">
                                                                                <img style="position: relative" class="img-fluid extra-post-image"
                                                                                     src="${post.imagesUrls[0]}" alt="">
                                                                                <div class="d-flex flex-md-row flex-column extra-post-content">
                                                                                    <div class="d-flex flex-md-row flex-column mb-md-0 mb-2">
                                                                                        <i class="fas fa-thumbs-up align-self-center me-1"></i>
                                                                                        <p class="mb-0 text-center">${post.numberLikes} Likes</p>
                                                                                    </div>
                                                                                    <div class="d-flex flex-md-row flex-column ms-md-3">
                                                                                        <i class="fas fa-comment align-self-center me-1"></i>
                                                                                        <p class="mb-0 text-center">${post.numberComments} Comments</p>
                                                                                    </div>
                                                                                </div>
                                                                            </a>`).get(0));
            }
        })
    });
});