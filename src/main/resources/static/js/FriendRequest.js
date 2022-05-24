class FriendRequest {

    #id;
    #user;

    constructor(user, id) {
        this.#user = user;
        this.#id = id;
    }

    createElement() {
        let element = $(`<div class="d-flex flex-row" style="padding: 10px 15px">
                        <a href="${this.#user.links.self.link}" class="mt-1 d-flex flex-row">
                            <img class="mt-1" style="width: 40px;height: 40px;border-radius: 50%;align-self: center"
                                 src="${this.#user.profilePicture}">
                        </a>
                        <div class="ms-3">
                            <p style="font-size: 17px" class="mb-1">${this.#user.firstName + " " + this.#user.lastName}</p>
                            <div class="d-flex flex-row">
                                <button class="btn btn-primary accept">Accept</button>
                                <button class="btn btn-danger decline ms-1">Decline</button>
                            </div>
                        </div>
                    </div>`).get(0);
        element.querySelector(".accept").onclick = function () {
            AjaxManager.request("/friend/accept/" + this.#id, null, "POST", function () {
                console.log("success");
            });
        }.bind(this);
        element.querySelector(".decline").onclick = function () {
            AjaxManager.request("/friend/decline/" + this.#id, null, "POST", function () {
                console.log("success");
            });
        }.bind(this);
        return element;
    }
}