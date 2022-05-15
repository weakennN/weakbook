class ChatRoom {

    webSocketManager;
    passedMessages = 0;
    chatRoom;
    static chatBox = document.getElementById("chat-box");
    static messageBox = document.getElementById("message-box");

    constructor(chatRoom) {
        this.initScrollAction();
        this.chatRoom = chatRoom;
        ChatRoom.chatBox.innerHTML = "";
        let socket = this.webSocketManager = new WebSocketManager(this.chatRoom.links.connect.link, function () {
            console.log(socket);
            socket.subscribe(this.chatRoom.links.subscribe.link, function (data) {
                console.log(JSON.parse(data.body));
                ChatRoom.createMessage(JSON.parse(data.body));
                this.passedMessages += 1;
            }.bind(this))
            ChatRoom.messageBox.onkeypress = function (e) {
                if (e.key === "Enter") {
                    let message = {
                        message: ChatRoom.messageBox.value,
                        chatRoomId: this.chatRoom.id
                    };
                    AjaxManager.request(this.chatRoom.links.message.link, JSON.stringify(message), "POST", function () {
                    });
                    message.fromCurrentUser = true;
                    ChatRoom.createMessage(message);
                    ChatRoom.messageBox.value = "";
                    this.passedMessages += 1;
                }
            }.bind(this);
        }.bind(this));
        AjaxManager.request(this.chatRoom.links.messages.link + "?offset=" + this.passedMessages, {}, "GET", function (data) {
            console.log(data);
            for (let i = 0; i < data.length; i++) {
                ChatRoom.createMessage(data[data.length - 1 - i]);
            }
            this.passedMessages += data.length;
            ChatRoom.chatBox.scrollTop = ChatRoom.chatBox.scrollHeight;
        }.bind(this))
    }

    static createMessage(message, insertFirst = false) {
        console.log(message);
        console.log(this.chatBox);
        let messageElement;
        if (message.infoMessage) {

        } else if (message.fromCurrentUser) {
            messageElement = $(`<p class='own-message message'>${message.message}</p>`).get(0);
        } else {
            messageElement = $(`<div class='d-flex mt-2 flex-row'>
                        <a href="${message.user.links.self.link}" class='d-flex'>
                            <img class='align-self-center' style='width: 30px;height: 30px;border-radius: 50%' src='${message.user.profilePicture}' alt=''>
                        </a>
                        <p class='message other-message align-self-center mb-0'>
                            ${message.message}</p>
                    </div>
                    </div>`).get(0);
        }
        if (insertFirst)
            this.chatBox.insertBefore(messageElement, this.chatBox.children[0])
        else
            this.chatBox.appendChild(messageElement);
    }

    disconnect() {
        this.webSocketManager.close();
    }

    getChatRoomId() {
        return this.chatRoom.id;
    }

    initScrollAction() {
        let send = true;
        ChatRoom.chatBox.onscroll = function () {
            console.log();
            if (ChatRoom.chatBox.scrollTop <= 100) {
                if (send) {
                    send = false;
                    AjaxManager.request(this.chatRoom.links.messages.link + "?offset=" + this.passedMessages, {}, "GET", function (data) {
                        for (let i = 0; i < data.length; i++) {
                            ChatRoom.createMessage(data[data.length - 1 - i], true);
                        }
                        send = true;
                        this.passedMessages += data.length;
                    }.bind(this));
                }
            }
        }.bind(this);
    }
}