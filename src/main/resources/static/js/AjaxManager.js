class AjaxManager {

    static request(url, data, type, success = AjaxManager.defaultSuccess, error = AjaxManager.defaultError
        , contentType = "application/json", cache = false) {
        console.log(data);
        $.ajax({
            type: type,
            contentType: contentType,
            url: url,
            data: data,
            dataType: 'json',
            cache: cache,
            success: function (data) {
                success(data, null)
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