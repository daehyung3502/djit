$("#applicationForm").submit(function(event) {
    event.preventDefault();
    $.ajax({
        url: "/application",
        type: "POST",
        data: new FormData(this),
        processData: false,
        contentType: false,
        success: function(response) {
            alert(response.message);
            window.location.href = response.redirectUrl;
        }
    });
});