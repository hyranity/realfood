<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Account</title>
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/profile.css" rel="stylesheet">
    </head>
    <body>
        <div class="outsideContainer">

            <h1>Edit Student</h1>
            <h5 id="subtitle">Here's where you can view and edit student's details.</h5>
            <br/>
            <div class="mainContainer">

                <form action="#" class="form">
                    <a href="#" onclick="confirmRemoval()"> <div class="removal">Unenroll</div></a>
                    <div>
                        <input type="text" value="STU00294" style="background-color: darkgray;"  id="studentid" readonly/>
                    </div>
                    <div id="nameDiv">
                        <input type="text" id="name" value="Michael" />
                        <input type="text" id="name" value="McArthur"/>
                    </div>
                    <div>
                        <input type="text" value="Male" id="gender"  />
                    </div>
                    <div>
                        <input type="text" value="Joined: 16 March, 2017" id="dateJoined"  style="background-color: darkgray;"  readonly/>
                    </div>
                    <div>
                        <input type="text" value="michaelm@student.com" id="email" />
                    </div>
                    <div>
                        <input type="text" value="00392-12-5567" id="myKAD" />
                    </div>

                    <br/>
                    <button type="submit" class="submitBtn">Submit changes</button>
                </form>
                <br/>
            </div>
        </div>
        <a href="displayStudents.jsp"><div class="back">back</div></a>
        <div class="removalConfirmation">
            <h5>Unenroll student?</h5>
            <p>The student will not be able to use the system anymore.</p>
            <a href="#"><div class="removalConfirm">Yes</div></a>
            <a href="#"><div class="removalCancel">No</div></a>
        </div>

    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Some javascript. It will dynamically change the subtitle based on what the student hovers over. -->
    <script>
                                $(document).ready(function () {
                                    $("#studentid").hover(function () {
                                        $("#subtitle").html("That's the student ID. It uniquely defines the student. It can't be changed.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view and edit student's details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#nameDiv").hover(function () {
                                        $("#subtitle").html("That's the student's name.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view and edit student's details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#gender").hover(function () {
                                        $("#subtitle").html("That's the student's gender.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view and edit student's details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#dateJoined").hover(function () {
                                        $("#subtitle").html("That's when the student's first joined. No point in editing it.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view and edit student's details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#email").hover(function () {
                                        $("#subtitle").html("That's the student's email.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view and edit student's details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#myKAD").hover(function () {
                                        $("#subtitle").html("That's the student's MyKAD number...you didn't mistype it, right?");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view and edit student's details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $(".removal").hover(function () {
                                        $("#subtitle").html("Unenroll the student");
                                        $("#subtitle").css("color", "red");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view and edit student's details.");
                                        $("#subtitle").css("color", "white");
                                    });

                                    $(".removal").click(function confirmRemoval() {
                                        $(".removalConfirmation").css("display", "inline-block");
                                        $(".outsideContainer").css("opacity", "0.5");
                                        $(".outsideContainer :input").prop("disabled", true);
                                    });

                                    $(".removalCancel").click(function confirmRemoval() {
                                        $(".removalConfirmation").css("display", "none");
                                        $(".outsideContainer").css("opacity", "1");
                                        $(".outsideContainer :input").prop("disabled", false);
                                    });
                                });
    </script>
</html>
