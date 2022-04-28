class Comment {

    #comment;
    #commentElement = null;
    #countReplies;
    #currentOffset;
    #repliesElement;

    // TODO: fix bug with replying
    constructor(commentData) {
        this.#comment = commentData;
        console.log(this.#comment);
        this.#countReplies = this.#comment.countReplies;
        this.#currentOffset = 0;
        this.#createRepliesContainerElement();
    }

    createComment(comment) {
        this.#commentElement = $(`<div class="comment mb-2" id="comment-6">
                                        <div class="d-flex flex-row">
                                            <img style="width: 45px;height: 45px;border-radius: 50%;" src="${comment.user.profilePicture}"" alt="">
                                            <div class="d-flex flex-column ms-2 comment-body">
                                                <div style="padding: 10px;border-radius: 10px;background: #f5f4f4">
                                                    <p class="mb-0" style="font-weight: 620;">${comment.user.firstName + " " + comment.user.lastName}</p>
                                                    <p class="mb-0">${comment.comment}</p>
                                                </div>
                                                <div class="d-flex flex-row">
                                                    <span class="me-3 like-comment">Like</span>
                                                    <span class="ms-auto comment-likes">Likes</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>`).get(0);
        this.#commentElement.querySelector(".like-comment").onclick = function () {
            this.like();
        }.bind(this);
        let replyElement = $("<span class='comment-reply'>Reply</span>").get(0);
        let replyContainer = this.#repliesElement;
        replyElement.onclick = function () {
            replyContainer.style.display = "block";
        }

        this.#commentElement.appendChild(this.#repliesElement);
        this.#commentElement.getElementsByClassName("comment-likes").item(0).before(replyElement)

        if (comment.hasMoreReplies) {
            let hasMoreCommentsElement = $("<p>View more comments</p>").get(0);
            let commentClass = this;
            hasMoreCommentsElement.onclick = function () {
                AjaxManager.request(commentClass.#comment.links.replies.link + "?offset=0", {}, "GET", function (comments) {
                    commentClass.#loadComments(comments);
                });
            }.bind(this);
            this.#repliesElement.appendChild(hasMoreCommentsElement);
        }

        if (comment.countReplies > 0) {
            this.#createRepliesElement();
        }

        return this.#commentElement;
    }

    #createRepliesElement() {
        let func = this.#replyElementActon;
        let repliesElement = $(`<span class="mb-2 count-replies">Replies ${this.#comment.countReplies}</span>`).get(0);
        let commentClass = this;
        repliesElement.onclick = function (event) {
            func(event, commentClass, repliesElement);
        };
        this.#commentElement.getElementsByClassName("comment-body").item(0).appendChild(repliesElement);
    }

    #replyElementActon(event, commentClass, element) {
        AjaxManager.request(commentClass.#comment.links.replies.link + "?offset=0", {}, "GET", function (comments) {
            commentClass.#loadComments(comments);
            commentClass.#repliesElement.style.display = "block";
            element.onclick = function () {
                if (commentClass.#repliesElement.style.display === "block") {
                    commentClass.#repliesElement.style.display = "none";
                } else {
                    commentClass.#repliesElement.style.display = "block";
                }
            }
        });
    }

    #loadComments(comments) {
        for (let commentData of comments) {
            let comment = new Comment(commentData);
            this.#repliesElement.querySelector(".replies").appendChild(comment.createComment(commentData));
        }
        this.#currentOffset += comments.length;
    }

    #createRepliesContainerElement() {
        this.#repliesElement = $(`<div class="replies-container mt-2" style="display: none;margin-left: 35px">
                                       <div class="replies"></div>
                                  </div>`).get(0);
        let replyInput = $('<input placeholder="Write a comment..." class="form-control reply-to write-comment" type="text">').get(0);
        this.#repliesElement.appendChild(replyInput);
        let commentClass = this;
        let func = this.#reply;
        replyInput.onkeypress = function (event) {
            if (event.key === "Enter") {
                func(commentClass, replyInput);
                replyInput.value = "";
            }
        }
    }

    #reply(commentClass, inputElement) {
        let comment = inputElement.value;
        console.log(commentClass);
        AjaxManager.request(commentClass.#comment.links.comment.link, JSON.stringify({
            comment: comment,
            replyTo: commentClass.#comment.id,
            postId: commentClass.#comment.postId
        }), "POST", function (commentData) {
            let comment = new Comment(commentData);
            commentClass.#repliesElement.querySelector(".replies").appendChild(comment.createComment(commentData));
        })
    }

    like() {
        AjaxManager.request(this.#comment.links.like.link, "", "POST", function (data) {
            console.log(data);
        }, null, "text/plain", "text");
    }
}
