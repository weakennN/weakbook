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
        document.getElementById("friends-row").appendChild($(`<div class="col-lg-2 col-md-3 col-sm-6 col-12">
                    <a href="${friend.links.self.link}">
                        <img class="img-fluid"
                             src="${friend.profilePicture}"
                             alt="">
                        <p class="mb-0 mt-2">${friend.firstName + " " + friend.lastName}</p>
                    </a>
                </div>`).get(0));
    }
}

function getFriends(offset) {
    AjaxManager.request("/friend/" + userId + "?offset=" + offset, null, "GET", function (data) {
        document.getElementById("count-friends").innerHTML = data.count;
        appendFriendElements(data.friends);
        friendsOffset += data.friends.length;
    });
}
