class AjaxManager {

    static request(url, data, type, success = AjaxManager.defaultSuccess, error = AjaxManager.defaultError
        , contentType = "application/json", dataTpe = "json") {
        $.ajax({
            type: type,
            contentType: contentType,
            url: url,
            data: data,
            dataType: dataTpe,
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