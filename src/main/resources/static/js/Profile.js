class Profile {

    static init(user) {
        ProfileBuilder.build(user);
    }
}

class ProfileBuilder {

    static build(user) {
        document.getElementById("profile-image").setAttribute("src", user.profilePicture);
        document.getElementById("profile-name").innerHTML = user.firstName + " " + user.lastName;
        document.getElementById("banner-image").setAttribute("src", user.bannerPicture);
        if (user.owner)
            document.getElementById("profile-holder").querySelector("div").appendChild(this.#createEditProfileButton());
    }

    static #createEditProfileButton() {
        // TODO: finish
        return $(`<button class="btn btn-primary me-3 mt-2 ms-auto edit-profile-btn">Edit profile</button>`).get(0);
    }
}

$(document).ready(function () {
    let tokens = window.location.href.split("/");
    AjaxManager.request("/getUser/" + tokens[tokens.length - 1], null, "GET", function (data) {
        Profile.init(data);
    })
    PostManager.init("/getPosts/" + tokens[tokens.length - 1]);
})