class ChatRoom {

    #webSocketManager;

    constructor() {
        let socket = this.#webSocketManager = new WebSocketManager("/chat", function () {
            console.log(socket);
            socket.subscribe("/user/queue/chat", function (message) {
                console.log(message);
            })
            socket.send("/app/message", {message: "testMessage", chatRoomId: 4})
        });
    }

    sendMessage() {
        this.#webSocketManager.send("/app/message", {message: "testMessage"});
    }
}