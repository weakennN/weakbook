class Profile {

    static init(user) {
        console.log(user)
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
        let button;
        if (!user.owner){
            if (user.areFriends) {
                button = $(`<button class="btn btn-danger ms-auto friend-request-btn mt-2">Remove friend</button>`).get(0);
                button.onclick = function () {
                    FriendManager.removeFriend(user.id);
                }
            } else {
                button = $(`<button class="btn btn-success ms-auto friend-request-btn mt-2">Add friend</button>`).get(0);
                button.onclick = function () {
                    FriendManager.sendFriendRequest(user.id);
                }
            }
        }
        document.getElementById("profile-holder").querySelector("div").appendChild(button);
    }

    static #createEditProfileButton() {
        // TODO: finish
        return $(`<button class="btn btn-primary me-3 mt-2 ms-auto edit-profile-btn">Edit profile</button>`).get(0);
    }
}

$(document).ready(function () {
    let tokens = window.location.href.split("/");
    console.log("hi")
    AjaxManager.request("/getUser/" + tokens[tokens.length - 1], null, "GET", function (data) {
        Profile.init(data);
    })
    PostManager.init("/posts/user/" + tokens[tokens.length - 1]);
})