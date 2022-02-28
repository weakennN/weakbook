class Post {

    #post

    constructor(post, postElement) {
        this.#post = post;
        let instance = this;
        postElement.querySelector(".like").onclick = function () {
            instance.like();
        }
    }

    like() {
        AjaxManager.request(this.#post.links.like.link, null, "POST", function (data) {
            console.log("post was liked");
        })
    }
}