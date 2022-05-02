class Notification {

    static #notificationContainer = document.getElementById("notifications");

    static connect() {
        let notificationWS = new WebSocketManager("/notifications", function (data) {
            notificationWS.subscribe("/user/queue/notifications", function (data) {
                console.log(data);
                Notification.#createNotification(JSON.parse(data.body));
            });
        });
    }

    static #createNotification(notificationData) {
        let notificationElement = $(`<div class="toast" role="alert" aria-live="assertive" data-bs-autohide="false" aria-atomic="true">
           <div class="toast-header">
                <p class="mb-0" style="font-size: 19px;color black">Notification</p>
                <button type="button" class="btn-close ms-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <a href="${notificationData.link}" style="text-decoration: none">
                <div class="toast-body">${notificationData.message}</div>
            </a>
        </div>`).get(0);
        this.#notificationContainer.appendChild(notificationElement);
        bootstrap.Toast.getOrCreateInstance(notificationElement).show();
    }
}

class NavbarNotifications {

    static init() {
        console.log("hwllo")
        AjaxManager.request("/notifications", null, "GET", function (data) {
            console.log(data);
        });
    }
}

$(document).ready(function () {
    Notification.connect();
    console.log("hwllo")
    NavbarNotifications.init();
})