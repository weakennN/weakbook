$(document).ready(function () {
    document.getElementById("search-input").addEventListener("focusin", function () {
        document.getElementById("search-box-suggestions").style.opacity = "1";
    });
    document.getElementById("search-input").addEventListener("focusout", function () {
        document.getElementById("search-box-suggestions").style.opacity = "0";
    });
    document.getElementById("search-input").oninput = function () {
        AjaxManager.request("/search?query=" + document.getElementById("search-input").value + "&limit=7"
            , null, "GET", function (data) {
                if (data.length === 0)
                    document.getElementById("search-box-suggestions").innerHTML = "<p class='text-center mb-0' style='font-size: 19px;padding: 10px;color: #363535'>No results</p>"
                else
                    appendToSearchSuggestions(data);
            })
    }

    function appendToSearchSuggestions(data) {
        deleteSuggestions();
        let searchSuggestions = document.getElementById("search-box-suggestions");
        for (let user of data) {
            searchSuggestions.appendChild($(`<a href="${user.links.self}" class="d-flex flex-row search-box-suggestion">
                                                <img src="${user.profilePicture}">
                                                    <p class="ms-2 mb-0" style="align-self: center;font-weight: 500">${user.firstName + " " + user.lastName}</p>
                                            </a>`).get(0));
        }
    }

    function deleteSuggestions() {
        document.getElementById("search-box-suggestions").innerHTML = "";
    }
});