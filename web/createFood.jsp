<%-- 
    Document   : createFood
    Created on : Apr 4, 2019, 9:10:37 AM
    Author     : Richard Khoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Account</title>
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/recordInfo.css" rel="stylesheet">
         <link href="CSS/commonStyles.css" rel="stylesheet">
    </head>
    <body>
        <div class="outsideContainer">

            <h1>Create Food</h1>
            <h5 id="subtitle">Here's where you can Create Food Details.</h5>
            <br/>
            <div class="mainContainer">

                <form action="#" class="form">
                    
                    <div>
                        <input type="text" value="F000001" style="background-color: darkgray;" placeholder="Food ID"  id="foodid" readonly/>
                    </div>
                    <div>
                        <input type="text" id="foodName" placeholder="Food Name" value="" required/>
                    </div>
                    <div>
                        <input type="text" value="200" placeholder="Calories" id="calories" required/>
                    </div>
                    <div>
                        <input type="text" value="" placeholder="Credit Point" id="creditPoint" required/>
                    </div>
                    <div>
                        <input type="text" value="16 March, 2017" id="dateAdded"  style="background-color: darkgray;"  readonly/>
                    </div>
                    
                    <br/>
                    <a href="#" onclick="confirmadding()"><div class="adding">Create</div></a>
                </form>
                <br/>
            </div>
        </div>
        <a href="displayStaff.jsp"><div class="back">Back</div></a>
        <div class="addingConfirmation">
            <h5>Creating food?</h5>
            <p>The Food WIll Be Created. Do You Want To Proceed?</p>
            <a href="#"><div class="addingConfirm">Yes</div></a>
            <a href="#"><div class="addingCancel">No</div></a>
        </div>
        <div class="coverOverlay"></div>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Some javascript. It will dynamically change the subtitle based on what the user hovers over. -->
    <script>
                        $(document).ready(function () {
                            $("#foodid").hover(function () {
                                $("#subtitle").html("That's the food ID. It uniquely defines the food.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can Create Food Details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#foodName").hover(function () {
                                $("#subtitle").html("That's the food's name.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can Create Food Details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#calories").hover(function () {
                                $("#subtitle").html("That's the number of calories in the food.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can Create Food Details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#dateAdded").hover(function () {
                                $("#subtitle").html("That's when the food was added. No point in editing it.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can Create Food Details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#dateDiscontinued").hover(function () {
                                $("#subtitle").html("That's when the food was discontinued. If it's not discontinued, it will show 'not discontinued'. ");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can Create Food Details.");
                                $("#subtitle").css("color", "white");
                            });

                            $(".adding").hover(function () {
                                $("#subtitle").html("Create the food");
                                $("#subtitle").css("color", "green");
                            }, function () {
                                $("#subtitle").html("Here's where you can Create Food Details.");
                                $("#subtitle").css("color", "white");
                            });

                            $(".adding").click(function confirmadding() {
                                $(".addingConfirmation").css("display", "inline-block");
                                $(".outsideContainer :input").prop("disabled", true);
                            });

                            $(".addingCancel").click(function confirmadding() {
                                $(".addingConfirmation").css("display", "none");
                                $(".outsideContainer :input").prop("disabled", false);
                            });

<!--  The following code allows a "disabling" overlay -->
                            $(".adding").click(function () {
                                $(".coverOverlay").css("display", "block");
                                $(".addingConfirmation").css("z-index", "1");
                            });

                            $(".addingCancel").click(function () {
                                $(".coverOverlay").css("display", "none");
                                $(".addingConfirmation").css("z-index", "0");
                            });
                        });
    </script>
</html>
