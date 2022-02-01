class Comment {

    #commentElement = null;
    #countReplies;
    #currentOffset;
    #comment;

    constructor(commentData) {
        this.#comment = commentData;
        this.#countReplies = this.#comment.countReplies;
        this.#currentOffset = 0;
    }

    createComment(comment, storeElement = false) {
        let hasMoreCommentsElement = null;
        console.log(comment);
        if (comment.hasMoreReplies) {
            hasMoreCommentsElement = $("<p>View more comments</p>").get(0);
            let commentClass = this;
            console.log(hasMoreCommentsElement);
            hasMoreCommentsElement.addEventListener("click", function () {
                AjaxManager.request("/getComments/4?" + "offset=0", {}, "GET", function (comments) {
                    commentClass.#loadComments(comments);
                });
            });
        }
        let commentElement = $(`<div class="comment" id="comment-${comment.id}">
                    <div class="d-flex flex-row">
                        <img style="width: 60px;height: 60px;border-radius: 50%"
                             src="https://www.google.com/search?q=images&sxsrf=APq-WBuKyYlL1YnJaFCFiEqorkevkQNT0A:1643635811668&tbm=isch&source=iu&ictx=1&vet=1&fir=PAzbIhIYcWWFUM%252CnBiD9BWYMB87aM%252C_%253BeXUC-3WyVcZa-M%252CMP_pVERxQybf_M%252C_%253BUVAHTXdge9JbrM%252CtnVTsEa64LdCyM%252C_%253BLK6S_eMkLDVwQM%252CB51x0PBR9KNzvM%252C_%253BITUG1uFAV1aQ9M%252CX9GBbDAacv-dWM%252C_%253BarFfSjMu_GX7sM%252CUkcvm3PybD5jEM%252C_%253BtTplitM2kjOQtM%252C0VsE6im_lxNqHM%252C_%253BMMg1IYvp7cpiyM%252CjMPx2dPKbYbCVM%252C_%253BsSAWficq0VlQLM%252CG9GbNX6HcZ2O_M%252C_%253BpFGFNV5oo1rdVM%252CT_IX7ra8y3ok2M%252C_&usg=AI4_-kTiI2xpFRypwEXxcf20vF_8geIHBQ&sa=X&ved=2ahUKEwjly8bFjNz1AhVNR_EDHZEsBigQ9QF6BAgcEAE#imgrc=eXUC-3WyVcZa-M"
                             alt="">
                        <p class="ms-2">${comment.user.firstName + " " + comment.user.lastName}</p>
                    </div>
                    <p class="mt-2">${comment.comment}</p>
                 <div class="replies" style="display: none">
                       
                </div>
                </div>`).get(0);
        if (hasMoreCommentsElement != null){
            commentElement.getElementsByClassName("replies").item(0).appendChild(hasMoreCommentsElement);
        }
        if (storeElement) {
            this.#commentElement = commentElement;
        }

        if (comment.countReplies > 0) {
            this.#createRepliesElement();
        }

        return commentElement;
    }

    #createRepliesElement() {
        let func = this.#replyElementActon;
        let repliesElement = $("<p>Replies</p>").get(0);
        let commentClass = this;
        repliesElement.addEventListener("click", function (event) {
            func(event, commentClass);
        })
        this.#commentElement.getElementsByClassName("replies").item(0).before(repliesElement);
    }

    #replyElementActon(event, commentClass) {
        AjaxManager.request("/getComments/4?" + "offset=0", {}, "GET", function (comments) {
            commentClass.#loadComments(comments);
            commentClass.#commentElement.getElementsByClassName("replies").item(0).style.display = "block";
        });
    }

    #loadComments(comments) {
        for (let comment of comments) {
            this.#commentElement.getElementsByClassName("replies")
                .item(0).appendChild(this.createComment(comment))
        }
        this.#currentOffset += comments.length;
    }
}
