class Chat {

    static getChatRooms() {
        AjaxManager.request("/chat/getChatRooms", {}, "GET", function (data) {
            console.log(data);
        })
    }

    static createNewChatRoom() {
        AjaxManager.request("/chat/createChatRoom","8", "POST", function (data) {
            console.log(data);
        })
    }
}

$(document).ready(function () {
    // Chat.getChatRooms();
    Chat.createNewChatRoom();
})