class Comment {

    #comment;
    #commentElement = null;
    #countReplies;
    #currentOffset;
    #repliesElement;

    constructor(commentData) {
        this.#comment = commentData;
        this.#countReplies = this.#comment.countReplies;
        this.#currentOffset = 0;
        this.#createRepliesContainerElement();
    }

    createComment(comment) {
        this.#commentElement = $(`<div class="comment mb-2" id="comment-6">
                                        <div class="d-flex flex-row">
                                            <img style="width: 45px;height: 45px;border-radius: 50%;" src="https://www.esa.int/var/esa/storage/images/esa_multimedia/images/2020/07/solar_orbiter_s_first_views_of_the_sun5/22136942-2-eng-GB/Solar_Orbiter_s_first_views_of_the_Sun_pillars.gif" alt="">
                                            <div class="d-flex flex-column ms-2 comment-body">
                                                <div style="padding: 10px;border-radius: 10px;background: #f5f4f4">
                                                    <p class="mb-0" style="font-weight: 620;">Lyuboslav Medarov</p>
                                                    <p class="mb-0">${comment.comment}</p>
                                                </div>
                                                <div class="d-flex flex-row">
                                                    <span class="me-3">Like</span>
                                                    <span class="ms-auto likes">Likes</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>`).get(0);
// TODO function thay inits like, likes, and reply buttons
        let replyElement = $("<span>Reply</span>").get(0);
        let replyContainer = this.#repliesElement;
        replyElement.onclick = function () {
            replyContainer.style.display = "block";
        }

        this.#commentElement.appendChild(this.#repliesElement);
        this.#commentElement.getElementsByClassName("likes").item(0).before(replyElement)

        if (comment.hasMoreReplies) {
            let hasMoreCommentsElement = $("<p>View more comments</p>").get(0);
            let commentClass = this;
            hasMoreCommentsElement.onclick = function () {
                AjaxManager.request("/getReplies/4?" + "offset=0", {}, "GET", function (comments) {
                    commentClass.#loadComments(comments);
                });
            };
            this.#repliesElement.appendChild(hasMoreCommentsElement);
        }

        if (comment.countReplies > 0) {
            this.#createRepliesElement();
        }

        return this.#commentElement;
    }

    #createRepliesElement() {
        let func = this.#replyElementActon;
        let repliesElement = $(`<span class="mb-2">Replies ${this.#comment.countReplies}</span>`).get(0);
        let commentClass = this;
        repliesElement.onclick = function (event) {
            func(event, commentClass, repliesElement);
        };
        this.#commentElement.getElementsByClassName("comment-body").item(0).appendChild(repliesElement);
    }

    #replyElementActon(event, commentClass, element) {
        AjaxManager.request("/getReplies/4?" + "offset=0", {}, "GET", function (comments) {
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
        console.log(comments);
        for (let commentData of comments) {
            let comment = new Comment(commentData)
            this.#repliesElement.appendChild(comment.createComment(commentData))
        }
        this.#currentOffset += comments.length;
    }

    #createRepliesContainerElement() {
        this.#repliesElement = $(`<div class="replies" style="display: none;margin-left: 35px">
                            <div class="d-flex flex-row">
                                <img class="mt-1" style="width: 35px;height: 35px;border-radius: 50%;"
                                     src="https://www.esa.int/var/esa/storage/images/esa_multimedia/images/2020/07/solar_orbiter_s_first_views_of_the_sun5/22136942-2-eng-GB/Solar_Orbiter_s_first_views_of_the_Sun_pillars.gif"
                                     alt="">
                            </div>
                            </div>`).get(0);
        let replyInput = $('<input class="ms-2 form-control reply-to" type="text">').get(0);
        this.#repliesElement.children.item(0).appendChild(replyInput);
        let commentClass = this;
        let func = this.#reply;
        replyInput.onkeypress = function (event) {
            console.log(event);
            if (event.key === "Enter") {
                func(commentClass, replyInput);
            }
        }
    }

    #reply(commentClass, inputElement) {
        let comment = inputElement.value;
        AjaxManager.request("/comment", JSON.stringify({
            comment: comment,
            replyTo: commentClass.#comment.id,
            postId: commentClass.#comment.postId
        }), "POST", function (commentData) {
            let comment = new Comment(commentData);
            commentClass.#repliesElement.appendChild(comment.createComment(commentData));
        })
    }
}
