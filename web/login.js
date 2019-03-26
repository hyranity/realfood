
   $('#id').on('input', function() {
    if(this.value.match("^STU")){
        $("#title").text("Student Login");
    }
    else if(this.value.match("^EMP"))
        $("#title").text("Staff Login");
    else
        $("#title").text("Login");
});
