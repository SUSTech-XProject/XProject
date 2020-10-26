function update_num() {
    $.post("/test", function() {
        // when get the result, call it
        // similar to F5 (refresh the website)
        location.reload();
    });
}