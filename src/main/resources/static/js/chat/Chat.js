class Chat {

    static #currentChatRoom = new ChatRoom();

    static getChatRooms() {
        AjaxManager.request("/chat/getChatRooms", {}, "GET", function (data) {
            console.log(Chat.#currentChatRoom);
        })
    }

    static createNewChatRoom() {
        AjaxManager.request("/chat/createChatRoom", "8", "POST", function (data) {
            console.log(data);
        })
    }

    static sendMessage() {
        this.#currentChatRoom.sendMessage();
    }
}

$(document).ready(function () {
    // Chat.getChatRooms();
 //   Chat.sendMessage();
})