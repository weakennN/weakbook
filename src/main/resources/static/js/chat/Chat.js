class Chat {

    static #chatRoomsBox;
    static #currentChatRoom = null;

    static init() {
        this.#chatRoomsBox = document.getElementById("chat-rooms-box");
        document.getElementById("chat-room-searcher").oninput = function () {
            AjaxManager.request("/search?query=" + document.getElementById("chat-room-searcher").value + "&limit=5", null, "GET", function (data) {
                document.getElementById("chat-room-results").innerHTML = "";
                for (let user of data) {
                    let element = $(`<div class="d-flex flex-row">
                        <img style="width: 40px;height: 40px;border-radius: 50%" src="${user.profilePicture}" alt="">
                        <p class="ms-2">${user.firstName + " " + user.lastName}</p>
                      </div>`).get(0);
                    element.onclick = function () {
                        this.#createChatRoom(user.id);
                    }.bind(this);
                    document.getElementById("chat-room-results").appendChild(element);
                }
            }.bind(this))
        }.bind(this);
    }

    static #createChatRoom(userId) {
        AjaxManager.request("/chat/chatRooms/" + userId, null, "POST", function (data) {
            this.#createChatRooms([data]);
        }.bind(this))
    }

    static getChatRooms() {
        AjaxManager.request("/chat/chatRooms", {}, "GET", function (data) {
            console.log(data);
            Chat.#createChatRooms(data);
        })
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
                Chat.#currentChatRoom = new ChatRoom(chatRoom)
            };
            Chat.#chatRoomsBox.appendChild(chatRoomElement);
        }
    }
}

$(document).ready(function () {
    Chat.init();
    Chat.getChatRooms();
})