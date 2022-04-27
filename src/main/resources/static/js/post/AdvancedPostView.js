class AdvancedPostView {

    static commentsSection;
    static post;
    static writeCommentInput;
    static commentsCount;
    static numberLikes;
    static numberComments;
    static like;
    static comment;
    static postImage;
    static owner;
    static ownerImage;
    static content;

    static init() {
        this.commentsSection = document.getElementById("comments");
        this.writeCommentInput = document.getElementById("write-comment");
        this.numberLikes = document.getElementById("number-likes");
        this.numberComments = document.getElementById("number-comments");
        this.postImage = document.getElementById("post-image");
        this.ownerImage = document.getElementById("post-owner-image");
        this.owner = document.getElementById("post-owner-name");
        this.content = document.getElementById("post-content");
        this.like = document.getElementById("like");
        this.comment = document.getElementById("comment");
        this.commentsCount = 0;

        this.comment.onclick = function () {
            AdvancedPostView.writeCommentInput.focus();
        }

        this.writeCommentInput.onkeypress = function (event) {
            if (event.key === "Enter") {
                let comment = AdvancedPostView.writeCommentInput.value;
                AjaxManager.request(AdvancedPostView.post.links.comment.link, JSON.stringify({
                    comment: comment,
                    replyTo: null,
                    postId: AdvancedPostView.post.id
                }), "POST", function (data) {
                    AdvancedPostView.#appendComments([data]);
                })
            }
        }
    }

    static #appendComments(comments) {
        for (let commentData of comments) {
            let comment = new Comment(commentData);
            AdvancedPostView.commentsSection.appendChild(comment.createComment(commentData));
        }
    }

    static #initPostTop(post) {
        this.like.onclick = function () {
            AjaxManager.request(AdvancedPostView.post.links.like.link, null, "POST", function (data) {
                if (data.liked) {
                    like.style.color = "#007eff";
                } else {
                    like.style.color = "#818181";
                }
            });
        }
        if (this.post.liked)
            this.like.style.color = "#007eff";
        else
            this.like.style.color = "#818181";
        this.numberLikes.innerHTML = post.numberLikes;
        this.numberComments.innerHTML = post.numberComments;
        this.postImage.setAttribute("src", post.imagesUrls[0]);
        this.ownerImage.setAttribute("src", post.user.profilePicture);
        this.owner.innerHTML = post.user.firstName + " " + post.user.lastName;
        this.content.innerHTML = post.content;
    }

    static #createShowMoreCommentsElement() {
        let element = $("<p class='show-more-comments'>Show more comments</p>").get(0);
        element.onclick = function () {
            AjaxManager.request(AdvancedPostView.post.links.comments.link + "?offset=" + AdvancedPostView.commentsCount, {}, "GET", function (comments) {
                AdvancedPostView.#appendComments(comments);
                console.log(comments);
                AdvancedPostView.commentsCount += comments.length;
            })
        }
        document.getElementById("view-more-comments").appendChild(element);
    }

    static getPost(url, success = null) {
        AjaxManager.request(url, {}, "GET", function (data) {
            AdvancedPostView.post = data;
            AdvancedPostView.#appendComments(AdvancedPostView.post.comments);
            AdvancedPostView.commentsCount += AdvancedPostView.post.comments.length;
            console.log(AdvancedPostView.commentsCount);
            if (AdvancedPostView.post.numberComments > 7) {
                AdvancedPostView.#createShowMoreCommentsElement();
            }
            AdvancedPostView.#initPostTop(AdvancedPostView.post);
            if (success !== null) {
                success();
            }
        });
    }
}