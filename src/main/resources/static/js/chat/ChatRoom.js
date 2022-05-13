class ChatRoom {

    webSocketManager;
    passedMessages = 0;
    chatRoom;
    static chatBox = document.getElementById("chat-box");
    static messageBox = document.getElementById("message-box");

    constructor(chatRoom) {
        this.chatRoom = chatRoom;
        ChatRoom.chatBox.innerHTML = "";
        let socket = this.webSocketManager = new WebSocketManager(this.chatRoom.links.connect.link, function () {
            console.log(socket);
            socket.subscribe(this.chatRoom.links.subscribe.link, function (data) {
                console.log(JSON.parse(data.body));
                ChatRoom.createMessage(JSON.parse(data.body));
            })
            ChatRoom.messageBox.onkeypress = function (e) {
                if (e.key === "Enter") {
                    let message = {
                        message: ChatRoom.messageBox.value,
                        chatRoomId: this.chatRoom.id
                    };
                    AjaxManager.request(this.chatRoom.links.message.link, JSON.stringify(message), "POST");
                    message.fromCurrentUser = true;
                    ChatRoom.createMessage(message);
                    ChatRoom.messageBox.value = "";
                }
            }.bind(this);
        }.bind(this));
        AjaxManager.request(this.chatRoom.links.messages.link + "?offset=" + this.passedMessages, {}, "GET", function (data) {
            console.log(data);
            for (let message of data) {
                ChatRoom.createMessage(message);
            }
        })
    }

    static createMessage(message) {
        console.log(message);
        console.log(this.chatBox);
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
        this.chatBox.appendChild(messageElement);
    }

    disconnect() {
        this.webSocketManager.close();
    }

    getChatRoomId() {
        return this.chatRoom.id;
    }
}