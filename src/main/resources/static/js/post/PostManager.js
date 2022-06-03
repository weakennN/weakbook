class PostManager {

    static #passedPosts = 0;
    static #url;

    static init(url) {
        this.#url = url;
        PostManager.getPosts();
        window.onscroll = function () {
            if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 100) {
                PostManager.getPosts();
            }
        };
    }

    static createPost(post) {
        console.log(post);
        let postElement = $(`<div class='card post mb-3'> 
                            <div class='card-body'> 
                                <div class='d-flex flex-row'> 
                                    <div class='mt-1' style='width: 40px;height: 40px;'> 
                                    <a href="${post.links.user.link}">
                                         <img class='post-image' 
                                             src="${post.user.profilePictureUrl}" alt="">
                                    </a>
                                    </div> 
                                    <div class='ms-2'> 
                                        <a href='${post.links.user.link}' class='card-title mb-0 post-user-name'>${post.user.firstName + " " + post.user.lastName}</a> 
                                        <p class='post-time mb-0'>10h</p> 
                                    </div> 
                                </div> 
                                <p class='card-text'>${post.content}</p> 
                            </div> 
                            <div id="images-${post.id}" class="carousel slide" data-bs-ride="carousel">
                                  <div class="carousel-inner"></div>
                                  <div class="navigation-buttons"></div>
                                </div>
                            <div class='px-3'>
                                <div class='d-flex mt-2'> 
                                    <p class='mb-0 likes'>${post.numberLikes} Likes</p> 
                                    <a href="${post.links.view.link}" class='ms-auto mb-0'>${post.numberComments} Comments</a> 
                                </div>
                                <hr class='mt-2 mb-0'>
                                <div class="d-flex flex-row mt-2">
                                    <i style="font-size: 32px;color: #6060ff" class="${post.liked === true ? "fas" : "far"} fa-thumbs-up like"></i>
                                    <a href="${post.links.view.link}" class="ms-auto">
                                        <i style="font-size: 32px;color: #6060ff" class="far fa-comment"></i>
                                    </a>
                                </div>
                                <div class='py-2'> 
                                    <input placeholder='Write a comment.' style='border-radius: 20px' class='form-control' type='text'> 
                                </div> 
                            </div> 
                        </div>`).get(0);
        for (let imageUrl of post.imagesUrls) {
            postElement.querySelector(".carousel-inner").appendChild($(`<div class="carousel-item">
                                                                          <img src="${imageUrl}" class="d-block w-100" alt="...">
                                                                        </div>`).get(0));
        }
        if (post.imagesUrls.length > 1) {
            postElement.querySelector(".navigation-buttons").innerHTML = `<button class="carousel-control-prev" type="button" data-bs-target="#images-${post.id}" data-bs-slide="prev">
                                                                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                                                                <span class="visually-hidden">Previous</span>
                                                                              </button>
                                                                              <button class="carousel-control-next" type="button" data-bs-target="#images-${post.id}" data-bs-slide="next">
                                                                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                                                                <span class="visually-hidden">Next</span>
                                                                              </button>`;
        }
        postElement.querySelector(".carousel-inner").children[0].classList.add("active");
        $("#posts").append(postElement);
        return postElement;
    }

    static #send = true;

    static getPosts() {
        console.log(PostManager.#url);
        console.log(PostManager.#passedPosts);
        if (PostManager.#send) {
            PostManager.#send = false;
            AjaxManager.request(PostManager.#url + "?passedPosts=" + PostManager.#passedPosts, null, "GET", function (data) {
                for (let postData of data) {
                    let postElement = PostManager.createPost(postData);
                    let post = new Post(postData, postElement);
                }
                console.log(data);
                PostManager.#passedPosts += data.length;
                PostManager.#send = true;
            });
        }
    }

    static post() {
        let images = PostCreator.getImages();
        console.log(images);
        AjaxManager.request("/posts", JSON.stringify({
            base64Images: images,
            content: document.getElementById("post-create-content").value
        }), "POST", function (data) {
            PostManager.createPost(data);
        })
    }
}