// <!-- Some javascript. It will dynamically change the subtitle based on what the user hovers over. -->
$(document).ready(function () {
    $("#foodId").hover(function () {
        $("#subtitle").html("That's the food ID. It uniquely defines the food. It can't be changed.");
        $("#subtitle").css("color", "gold");
    }, function () {
        $("#subtitle").html("Here's where you can view and edit food's details.");
        $("#subtitle").css("color", "white");
    });
    $("#foodName").hover(function () {
        $("#subtitle").html("That's the food's name.");
        $("#subtitle").css("color", "gold");
    }, function () {
        $("#subtitle").html("Here's where you can view and edit food's details.");
        $("#subtitle").css("color", "white");
    });
    $("#calories").hover(function () {
        $("#subtitle").html("That's the number of calories in the food.");
        $("#subtitle").css("color", "gold");
    }, function () {
        $("#subtitle").html("Here's where you can view and edit food's details.");
        $("#subtitle").css("color", "white");
    });
    $("#dateAdded").hover(function () {
        $("#subtitle").html("That's when the food was added. No point in editing it.");
        $("#subtitle").css("color", "gold");
    }, function () {
        $("#subtitle").html("Here's where you can view and edit food's details.");
        $("#subtitle").css("color", "white");
    });
    $("#dateDiscontinued").hover(function () {
        $("#subtitle").html("That's when the food was discontinued. If it's not discontinued, it will show 'not discontinued'. ");
        $("#subtitle").css("color", "gold");
    }, function () {
        $("#subtitle").html("Here's where you can view and edit food's details.");
        $("#subtitle").css("color", "white");
    });

    $("#disable").hover(function () {
        $("#subtitle").html("Discontinue the food");
        $("#subtitle").css("color", "red");
    }, function () {
        $("#subtitle").html("Here's where you can view and edit food's details.");
        $("#subtitle").css("color", "white");
    });
    
    $("#enable").hover(function () {
        $("#subtitle").html("Re-enable the food");
        $("#subtitle").css("color", "red");
    }, function () {
        $("#subtitle").html("Here's where you can view and edit food's details.");
        $("#subtitle").css("color", "white");
    });

    $(".toggleOverlay").click(function toggleOverlay() {
        $(".toggleConfirmation").css("display", "inline-block");
        $(".outsideContainer :input").prop("disabled", true);
    });

    $(".overlayCancel").click(function confirmToggle() {
        $(".toggleConfirmation").css("display", "none");
        $(".outsideContainer :input").prop("disabled", false);
    });

// The following code allows a "disabling" overlay 
    $(".toggleOverlay").click(function () {
        $(".coverOverlay").css("display", "block");
        $(".toggleConfirmation").css("z-index", "1");
    });

    $(".overlayCancel").click(function () {
        $(".coverOverlay").css("display", "none");
        $(".toggleConfirmation").css("z-index", "0");
    });
});
