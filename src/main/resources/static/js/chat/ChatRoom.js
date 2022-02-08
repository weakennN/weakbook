class ChatRoom {

    #webSocketManager;

    constructor() {
        this.#webSocketManager = new WebSocketManager("/user/queue/chat");
    }
}