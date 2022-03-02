class AjaxManager {

    static request(url, data, type, success = AjaxManager.defaultSuccess, error = AjaxManager.defaultError
        , contentType = "application/json") {
        $.ajax({
            type: type,
            contentType: contentType,
            url: url,
            data: data,
            dataType: 'json',
            success: function (data) {
                console.log(data);
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