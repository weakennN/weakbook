$(document).ready(function () {
    AjaxManager.request(window.location.pathname, {}, "GET", function (data) {
        // $("#comments").append(createComments(data.comments));
        for (let commentData of data.comments) {
            let comment = new Comment(commentData);
            $("#comments").append(comment.createComment(commentData, true));
        }
    })
})

/*function createComments(comments) {
    let result = [];
    for (let comment of comments) {
        let commentElement = $(`<div class="comment" id="comment-${comment.id}">
                    <div class="d-flex flex-row">
                        <img style="width: 60px;height: 60px;border-radius: 50%"
                             src="https://www.google.com/search?q=images&sxsrf=APq-WBuKyYlL1YnJaFCFiEqorkevkQNT0A:1643635811668&tbm=isch&source=iu&ictx=1&vet=1&fir=PAzbIhIYcWWFUM%252CnBiD9BWYMB87aM%252C_%253BeXUC-3WyVcZa-M%252CMP_pVERxQybf_M%252C_%253BUVAHTXdge9JbrM%252CtnVTsEa64LdCyM%252C_%253BLK6S_eMkLDVwQM%252CB51x0PBR9KNzvM%252C_%253BITUG1uFAV1aQ9M%252CX9GBbDAacv-dWM%252C_%253BarFfSjMu_GX7sM%252CUkcvm3PybD5jEM%252C_%253BtTplitM2kjOQtM%252C0VsE6im_lxNqHM%252C_%253BMMg1IYvp7cpiyM%252CjMPx2dPKbYbCVM%252C_%253BsSAWficq0VlQLM%252CG9GbNX6HcZ2O_M%252C_%253BpFGFNV5oo1rdVM%252CT_IX7ra8y3ok2M%252C_&usg=AI4_-kTiI2xpFRypwEXxcf20vF_8geIHBQ&sa=X&ved=2ahUKEwjly8bFjNz1AhVNR_EDHZEsBigQ9QF6BAgcEAE#imgrc=eXUC-3WyVcZa-M"
                             alt="">
                        <p class="ms-2">${comment.user.firstName + " " + comment.user.lastName}</p>
                    </div>
                    <p class="mt-2">${comment.comment}</p>
                </div>`).get(0);
        createReplies(comment, commentElement);
        result.push(commentElement);
    }

    return result;
}

function createReplies(comment, commentElement) {
    console.log(comment.countReplies);
    if (comment.countReplies > 0) {
        let repliesElement = $("<p>Replies</p>").get(0);
        repliesElement.addEventListener("click", function () {
            AjaxManager.request("/getComments/4?" + "offset=0", {}, "GET", function (data) {
                let createdReplies = createComments(data, comment);
                for (const createdReply of createdReplies) {
                    commentElement.appendChild(createdReply);
                }
            })
        });
        commentElement.append(repliesElement);
        return repliesElement;
    }

    return "";
}

 */