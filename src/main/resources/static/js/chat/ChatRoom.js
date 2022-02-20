class ChatRoom {

    #webSocketManager;
    #passedMessages = 0;
    #chatRoomId;
    static #chatBox = document.getElementById("chat-box");
    static #messageBox = document.getElementById("message-box");

    constructor(chatRoomId) {
        this.#chatRoomId = chatRoomId;
        ChatRoom.#chatBox.innerHTML = "";
        let instance = this;
        let socket = this.#webSocketManager = new WebSocketManager("/chat", function () {
            console.log(socket);
            socket.subscribe("/user/queue/chat", function (data) {
                console.log(JSON.parse(data.body));
                ChatRoom.#createMessage(JSON.parse(data.body));
            })
            ChatRoom.#messageBox.onkeypress = function (e) {
                if (e.key === "Enter") {
                    socket.send("/app/message", {message: ChatRoom.#messageBox.value, chatRoomId: instance.#chatRoomId})
                }
            }
        });
        AjaxManager.request("/chat/getMessages?chatRoomId=" + instance.#chatRoomId + "&offset="
            + instance.#passedMessages, {}, "GET", function (data) {
            console.log(data);
            for (let message of data) {
                ChatRoom.#createMessage(message);
            }
        })
    }

    sendMessage() {
        this.#webSocketManager.send("/app/message", {message: "testMessage"});
    }

    static #createMessage(message) {
        let messageElement;
        if (message.infoMessage) {

        } else if (message.fromCurrentUser) {
            messageElement = $(`<p class='own-message message'>${message.message}</p>`).get(0);
        } else {
            messageElement = $(`<div class='d-flex flex-row'>
                        <div class='d-flex'>
                            <img class='align-self-center' style='width: 30px;height: 30px;border-radius: 50%' src='${message.user.profilePicture}' alt=''>
                        </div>
                        <p class='message other-message align-self-center mb-0'>
                            ${message.message}</p>
                    </div>
                    </div>`).get(0);
        }
        this.#chatBox.appendChild(messageElement);
    }

    disconnect() {
        this.#webSocketManager.close();
    }

    getChatRoomId() {
        return this.#chatRoomId;
    }
}