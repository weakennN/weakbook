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
            PostPage.#initPostTools(PostPage.#post);
        });

        this.#writeCommentInput.onkeypress = function (event) {
            if (event.key === "Enter") {
                let comment = PostPage.#writeCommentInput.value;
                AjaxManager.request("/comments/comment", JSON.stringify({
                    comment: comment,
                    replyTo: null,
                    postId: PostPage.#post.id
                }), "POST", function (data) {
                    PostPage.#appendComments([data]);
                })
            }
        }
    }

    static #appendComments(comments) {
        for (let commentData of comments) {
            let comment = new Comment(commentData);
            PostPage.#commentsSection.appendChild(comment.createComment(commentData));
        }
    }

    static #initPostTools(post) {
        let postTools = document.getElementById("post-tools");
        let like = $(`<i style="font-size: 2rem" class="fas fa-thumbs-up ${post.liked === true ? "liked" : ""}"></i>`).get(0);
        like.onclick = function () {
            AjaxManager.request("/post/" + post.id + "/like", null, "POST", function (data) {
                if (data.liked) {
                    like.classList.add("liked");
                } else {
                    like.classList.remove("liked");
                }
            });
        }
        let comment = $(`<i style="font-size: 2rem" class="fas fa-comment ms-auto"></i>`).get(0);
        comment.onclick = function () {
            PostPage.#writeCommentInput.focus();
        }

        document.getElementById("number-likes").innerHTML = post.numberLikes;
        document.getElementById("number-comments").innerHTML = post.numberComments;
        document.getElementById("post-image").setAttribute("src", post.imagesUrls[0]);
        document.getElementById("post-owner-image").setAttribute("src", post.user.profilePicture);
        document.getElementById("post-owner-name").innerHTML = post.user.firstName + " " + post.user.lastName;
        document.getElementById("post-content").innerHTML = post.content;

        postTools.append(like, comment);
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