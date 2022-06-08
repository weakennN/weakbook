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
        this.#postElement.querySelector(".carousel-inner").onclick = function () {
            AdvancedPostView.init();
            PostModal.clear();
            AdvancedPostView.getPost(instance.#post.links.self.link, function () {
                PostModal.open();
                window.history.pushState(postState, "", this.#post.links.view.link);
            }.bind(this));
        }.bind(this);
        this.#postElement.querySelector(".likes").onclick = function () {
            LikesModal.open(instance.#post.links.likes.link);
        }
    }

    #like() {
        let instance = this;
        AjaxManager.request(this.#post.links.like.link, null, "POST", function (data) {
            if (data.liked)
                instance.#postElement.querySelector(".like").classList.replace("far", "fas");
            else
                instance.#postElement.querySelector(".like").classList.replace("fas", "far");
        })
    }
}

let postState = [];