class PostModal extends AdvancedPostView {

    static open() {
        bootstrap.Modal.getOrCreateInstance(document.querySelector('#post-modal')).show();
    }

    static clear() {
        document.getElementById("comments").innerHTML = "";
    }
}