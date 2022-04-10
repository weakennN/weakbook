class PostManager {

    static #passedPosts = 0;
    static #url;

    static init(url) {
        this.#url = url;
        PostManager.getPosts();
        $(window).scroll(function () {
            if ($(window).scrollTop() + $(window).height() > $(document).height() - 400) {
                PostManager.getPosts(PostManager.#url);
            }
        });
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
                                        <a href='${post.links.user.link}' class='card-title mb-0 post-user-name'>Lyuboslav</a> 
                                        <p class='post-time mb-0'>10h</p> 
                                    </div> 
                                </div> 
                                <p class='card-text'>${post.content}</p> 
                            </div> 
                            <img class='card-img-bottom' 
                                 src="${post.imagesUrls[0]}"
                                 alt='Card image cap'> 
                            <div class='px-3'>
                                <div class='d-flex mt-2'> 
                                    <a class='mb-0'>${post.numberLikes} Likes</a> 
                                    <a href="${post.links.self.link}" class='ms-auto mb-0'>${post.numberComments} Comments</a> 
                                </div>
                                <hr class='mt-2 mb-0'>
                                <div class="d-flex flex-row mt-2">
                                    <i style="font-size: 32px;color: #6060ff" class="${post.liked === true ? "fas" : "far"} fa-thumbs-up like"></i>
                                    <a href="${post.links.self.link}" class="ms-auto">
                                        <i style="font-size: 32px;color: #6060ff" class="far fa-comment"></i>
                                    </a>
                                </div>
                                <div class='py-2'> 
                                    <input placeholder='Write a comment.' style='border-radius: 20px' class='form-control' type='text'> 
                                </div> 
                            </div> 
                        </div>`).get(0);
        $("#posts").append(postElement);
        return postElement;
    }

    static #send = true;

    static getPosts() {
        if (PostManager.#send) {
            PostManager.#send = false;
            AjaxManager.request(PostManager.#url, {passedPosts: PostManager.#passedPosts}, "GET", function (data) {
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
        let pictureFiles = document.getElementById("add-post-pictures").files;
        let promises = encodeImages(pictureFiles);

        Promise.all(promises).then(function (values) {
            AjaxManager.request("/savePost", JSON.stringify({
                base64Images: values,
                content: $("#post-content").val()
            }), "POST", function (data) {
                PostManager.createPost(data);
            })
        })
    }
}