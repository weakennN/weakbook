class Post {

    #post
    #postElement;

    constructor(post, postElement) {
        this.#post = post;
        this.#postElement = postElement;
        let instance = this;
        this.#postElement.querySelector(".like").onclick = function () {
            instance.#like();
        }
    }

    #like() {
        let instance = this;
        AjaxManager.request(this.#post.links.like.link, null, "POST", function (data) {
            if (data.liked) {
                instance.#postElement.querySelector(".like").classList.replace("far", "fas");
            } else {
                instance.#postElement.querySelector(".like").classList.replace("fas", "far");
            }
        })
    }
}