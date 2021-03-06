class WebSocketManager {

    #socket;
    #stompClient;

    constructor(connectToPath, connectionFunction) {
        this.#socket = new SockJS(connectToPath);
        this.#stompClient = Stomp.over(this.#socket);
        this.#stompClient.connect({}, connectionFunction);
    }

    subscribe(path, subscribeFunction) {
        this.#stompClient.subscribe(path, subscribeFunction)
    }

    send(path, data, headers = {}) {
        this.#stompClient.send(path, headers, JSON.stringify(data));
    }

    close() {
        this.#socket.close()
        this.#stompClient.disconnect(function (frame) {
            console.log(frame);
        });
    }
}