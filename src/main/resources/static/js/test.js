//alert("hello");
/*$.post("/test",
    {
    },
    function (data, status) {
        alert("Data: " + data + "\nStatus: " + status);
    });

 */
let data = {
    content: "content123"
};
$.ajax({
    type: "POST",
    contentType: 'application/json',
    dataType: 'json',
    data: JSON.stringify(data),
    url: "/savePost",
    success: function (data) {
        alert(data);
    },
    error: function (e) {
        alert("error")
    }
});