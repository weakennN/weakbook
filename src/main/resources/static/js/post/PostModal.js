class PostModal extends AdvancedPostView {

    static open() {
        this.#clear();
        bootstrap.Modal.getOrCreateInstance(document.querySelector('#post-modal')).show();
    }

    static #clear() {

    }
}