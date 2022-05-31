class FriendManager {

    static sendFriendRequest(receiverId) {
        AjaxManager.request("/friend/request/" + receiverId, null, "POST", function (data) {
            console.log(data);
        });
    }

    static removeFriend(friendId) {
        AjaxManager.request("/friend/remove/" + friendId, null, "DELETE", function (data){
            console.log(data);
        })
    }
}