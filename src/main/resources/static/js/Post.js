class Post {
    #passedPosts = 0;

    createPost(post) {

    }

    getPosts() {
        let data = {
            passedPosts: this.#passedPosts
        }
        AjaxManager.request("/getPosts", data, "GET", function () {
            alert("hello");
        });
    }
}