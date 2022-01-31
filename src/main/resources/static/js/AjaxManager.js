class AjaxManager {
    /**
     *
     * @param url
     * @param data
     * @param type
     * @param {function|null} success
     * @param error
     * @param contentType
     */
    static request(url, data, type, success = AjaxManager.defaultSuccess, error = AjaxManager.defaultError
        , contentType = "application/json") {
        $.ajax({
            type: type,
            contentType: contentType,
            url: url,
            data: data,
            dataType: 'json',
            success: function (data) {
                success(data);
            },
            error: error
        });
    }

    static defaultSuccess() {
        alert("success");
    }

    static defaultError() {
        alert("error");
    }
}