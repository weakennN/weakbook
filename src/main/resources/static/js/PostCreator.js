class PostCreator {

    static #postImages = []
    static #dragOver;
    static #fileBrowser = null;
    static #postImageElement;

    static init() {
        this.#dragOver = document.getElementById("image-drag-over");
        this.#postImageElement = document.getElementById("post-images");
        this.#dragOver.addEventListener('dragover', function (event) {
            PostCreator.#prevent(event);
            event.dataTransfer.dropEffect = 'copy';
        });
        this.#dragOver.addEventListener('drop', function (event) {
            PostCreator.#prevent(event);
            Promise.all(encodeImages(event.dataTransfer.files)).then(PostCreator.#upload);
        });
        document.getElementById("file-browser").onclick = this.#dragOverOnClick.bind(this)
    }

    static #upload(images) {
        for (let image of images) {
            PostCreator.#postImages.push(image.split(",")[1]);
            PostCreator.#postImageElement.appendChild(PostCreator.#createImageElement(image))
        }
        console.log(PostCreator.#postImages)
    }

    static #prevent(event) {
        event.stopPropagation();
        event.preventDefault();
    }

    static #dragOverOnClick() {
        if (this.#fileBrowser === null) {
            this.#fileBrowser = $("<input multiple type='file' accept='image/png, image/jpeg'>").get(0);
            this.#fileBrowser.addEventListener("change", function () {
                console.log(PostCreator.#fileBrowser.files);
                Promise.all(encodeImages(PostCreator.#fileBrowser.files)).then(PostCreator.#upload);
            })
        }
        this.#fileBrowser.click();
    }

    static #createImageElement(imageSrc) {
        return $(`<div class="col-6">
                        <img class="img-fluid" style="border-radius: 15px;object-fit: cover;height: 100%" src="${imageSrc}" alt="">
                    </div>`).get(0);
    }

    static getImages() {
        return this.#postImages;
    }
}

$(document).ready(function () {
    PostCreator.init();
})
