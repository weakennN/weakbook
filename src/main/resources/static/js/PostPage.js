class PostPage {

    static #commentsSection;
    static #post;
    static #writeCommentInput;
    static #commentsCount = 0;

    static init() {
        this.#commentsSection = document.getElementById("comments");
        this.#writeCommentInput = document.getElementById("write-comment");

        AjaxManager.request(window.location.pathname, {}, "GET", function (data) {
            PostPage.#post = data;
            PostPage.#appendComments(PostPage.#post.comments);
            PostPage.#commentsCount += PostPage.#post.comments.length;
            console.log(PostPage.#commentsCount);
            if (PostPage.#post.numberComments > 7) {
                PostPage.#createShowMoreCommentsElement();
            }
        });

        this.#writeCommentInput.onkeypress = function (event) {
            if (event.key === "Enter") {
                let comment = PostPage.#writeCommentInput.value;
                // send comment
            }
        }
    }

    static #appendComments(comments) {
        for (let commentData of comments) {
            let comment = new Comment(commentData);
            PostPage.#commentsSection.appendChild(comment.createComment(commentData));
        }
    }

    static #createShowMoreCommentsElement() {
        let element = $("<p class='show-more-comments'>Show more comments</p>").get(0);
        element.onclick = function () {
            AjaxManager.request("/getPostComments/16?offset=" + PostPage.#commentsCount, {}, "GET", function (comments) {
                PostPage.#appendComments(comments);
                console.log(comments);
                PostPage.#commentsCount += comments.length;
            })
        }
        document.getElementById("view-more-comments").appendChild(element);
    }
}

$(document).ready(function () {
    PostPage.init();
})