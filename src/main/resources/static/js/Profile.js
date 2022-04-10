class Profile {

    static init(user) {
        ProfileBuilder.build(user);
    }
}

class ProfileBuilder {

    static #container;

    static build(user) {
        this.#container = document.getElementById("container");
        this.#top(user);
        this.#middle(user);
        this.#bottom(user);
    }

    static #top(user) {

    }

    static #middle(user) {

    }

    static #bottom(user) {

    }
}

$(document).ready(function () {
    let tokens = window.location.href.split("/");
    AjaxManager.request("/getUser/" + tokens[tokens.length - 1], null, "GET", function (data) {
        Profile.init(data);
    })
    PostManager.init("/getOwnPosts");
})