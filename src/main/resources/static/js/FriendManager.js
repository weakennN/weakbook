class FriendManager {

    static sendFriendRequest() {
        AjaxManager.request("/friend/request", "9", "POST", function (data) {
            console.log(data);
        }, null, "text/html", "text");
    }
}

/*$(document).ready(function () {
    FriendManager.sendFriendRequest();
})

 */