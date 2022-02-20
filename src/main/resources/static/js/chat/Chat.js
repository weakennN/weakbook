class Chat {

    static #chatRoomsBox;
    static #currentChatRoom = null;

    static init() {
        this.#chatRoomsBox = document.getElementById("chat-rooms-box");
    }

    static getChatRooms() {
        AjaxManager.request("/chat/getChatRooms", {}, "GET", function (data) {
            Chat.#createChatRooms(data);
        })
    }

    static createNewChatRoom() {
        AjaxManager.request("/chat/createChatRoom", "8", "POST", function (data) {

        })
    }

    static sendMessage() {
        this.#currentChatRoom.sendMessage();
    }

    static #createChatRooms(chatRooms) {
        for (let chatRoom of chatRooms) {
            let chatRoomElement = $(`<div class="d-flex flex-row chat-room-box">
                    <img class="align-self-center" style="width: 50px;height: 50px;border-radius: 50%" src="${chatRoom.roomImage}" alt="">
                    <div class="ms-2">
                        <p class="friend-name">${chatRoom.name}</p>
                        <p class="latest-message">${chatRoom.latestMessage}</p>
                    </div>
                </div>`).get(0);
            // TODO: use the current chatRoomElement
            chatRoomElement.onclick = function () {
                if (Chat.#currentChatRoom != null
                    && Chat.#currentChatRoom.getChatRoomId() === chatRoom.id) {
                    return;
                }
                if (Chat.#currentChatRoom != null) {
                    Chat.#currentChatRoom.disconnect();
                }
                Chat.#currentChatRoom = new ChatRoom(chatRoom.id)
            };
            Chat.#chatRoomsBox.appendChild(chatRoomElement);
        }
    }
}

$(document).ready(function () {
    /*setTimeout(function () {
        Chat.currentChatRoom.disconnect();
    }, 100000)

    //   Chat.sendMessage();

     */
    Chat.init();
    Chat.getChatRooms();
})