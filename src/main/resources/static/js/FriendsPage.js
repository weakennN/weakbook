let friendsOffset = 0;
$(document).ready(function () {
    getFriends(friendsOffset);
    window.onscroll = function () {
        if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 100) {
            getFriends(friendsOffset);
        }
    };
});

function appendFriendElements(friends) {
    for (let friend of friends) {
        let element = $(`<div class="col-lg-2 col-md-3 col-sm-6 col-12">
                            <div class="d-flex flex-column">
                            <a href="${friend.links.self.link}">
                            <img class="img-fluid"
                                     src="${friend.profilePicture}"
                                     alt="">
                            </a>
                            <div class="d-flex flex-row">
                                <a href="${friend.links.self.link}">
                                   <p class="mb-0 mt-2">${friend.firstName + " " + friend.lastName}</p>
                                </a>
                            </div>
                    </div>
                </div>`).get(0);
        let removeFriendButton = $(`<i class="fas fa-user-times ms-auto" style="cursor: pointer;color: #f62c2c;font-size: 1.55rem;align-self: center"></i>`).get(0);
        removeFriendButton.onclick = function () {
            FriendManager.removeFriend(friend.id);
            document.getElementById("friends-row").removeChild(element);
        }
        element.querySelector(".flex-row").appendChild(removeFriendButton);
        document.getElementById("friends-row").appendChild(element);
    }
}

function getFriends(offset) {
    AjaxManager.request("/friend/" + userId + "?offset=" + offset, null, "GET", function (data) {
        document.getElementById("count-friends").innerHTML = data.count;
        appendFriendElements(data.friends);
        friendsOffset += data.friends.length;
    });
}
