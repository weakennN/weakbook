class Post {

    static #passedPosts = 0;
    static send;

    static createPost(post) {
        let postElement = $("<div class='card post mb-3'>\n" +
            "                <div class='card-body'>\n" +
            "                    <div class='d-flex flex-row'>\n" +
            "                        <div class='mt-1' style='width: 40px;height: 40px;'>\n" +
            "                            <img class='post-image'\n" +
            "                                 src=" + post.user.profilePictureUrl + " \n" +
            "                                 alt=''>\n" +
            "                        </div>\n" +
            "                        <div class='ms-2'>\n" +
            "                            <a href='#' class='card-title mb-0 post-user-name'>Lyuboslav</a>\n" +
            "                            <p class='post-time mb-0'>10h</p>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                    <p class='card-text'>" + post.content + "</p>\n" +
            "                </div>\n" +
            "                <img class='card-img-bottom'\n" +
            "                     src=" + post.imagesUrls[0] + " \n" +
            "                     alt='Card image cap'>\n" +
            "                <div class='px-3'>\n" +
            "                    <div class='d-flex mt-2'>\n" +
            "                        <p class='mb-0'>" + post.numberLikes + " Likes</p>\n" +
            "                        <p class='ms-auto mb-0'>" + post.numberComments + " Comments</p>\n" +
            "                    </div>\n" +
            "                    <hr class='mt-2 mb-0'>\n" +
            "                    <div class='py-2'>\n" +
            "                        <input placeholder='Write a comment.' style='border-radius: 20px' class='form-control'\n" +
            "                               type='text'>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>").get(0);
        $("#posts").append(postElement);
    }

    static #send = true;

    static getPosts() {
        if (Post.#send) {
            Post.#send = false;
            AjaxManager.request("/getPosts", {passedPosts: Post.#passedPosts}, "GET", function (data) {
                for (let post of data) {
                    Post.createPost(post);
                }
                console.log(data);
                Post.#passedPosts += data.length;
                Post.#send = true;
            });
        }
    }

    static post() {
        let pictureFiles = document.getElementById("add-post-pictures").files;
        let promises = encodeImages(pictureFiles);

        Promise.all(promises).then(function (values) {
            AjaxManager.request("/savePost", JSON.stringify({
                base64Images: values,
                content: $("#post-content").val()
            }), "POST", function (data) {
                Post.createPost(data);
            })
        })
    }
}

$(document).ready(function () {
    Post.getPosts();
    $(window).scroll(function () {
        if ($(window).scrollTop() + $(window).height() > $(document).height() - 400) {
            Post.getPosts();
        }
    });
})