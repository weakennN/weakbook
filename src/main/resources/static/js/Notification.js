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

    static #loaded = false;

    static init() {
        document.getElementById("navbar-notifications-btn").onclick = function () {
            if (!NavbarNotifications.#loaded) {
                AjaxManager.request("/notifications", null, "GET", function (data) {
                    NavbarNotifications.#loaded = true;
                    for (let notification of data) {
                        document.getElementById("navbar-notifications")
                            .appendChild(NavbarNotifications.#createNotification(notification));
                    }
                }.bind(this));
                AjaxManager.request("/notifications/see", null, "PATCH", function (data) {
                    console.log("notifications seen")
                    console.log(data);
                })
            }
            document.getElementById("navbar-notifications").style.display = "block";
            setTimeout(function () {
                document.onclick = function () {
                    document.getElementById("navbar-notifications").style.display = "none";
                    document.onclick = null;
                }
            }, 100);
        }.bind(this);
    }

    static #createNotification(notification) {
        if (notification.type === "FRIEND_REQUEST") {

        } else {
            return $(`<a href="${notification.link}" class="d-flex flex-row" style="padding: 10px 15px">
                        <img style="width: 50px;height: 50px;border-radius: 50%;margin-top: 5px"
                             src="${notification.sender.profilePicture}"
                             alt="">
                        <div class="ms-3">
                            <p class="mb-0">${notification.message}</p>
                        </div> 
                    </a>`).get(0);
        }
    }
}

$(document).ready(function () {
    Notification.connect();
    NavbarNotifications.init();
})