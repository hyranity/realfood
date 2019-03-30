<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Account</title>
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="CSS/recordInfo.css" rel="stylesheet">
    </head>
    <body>
        <div class="outsideContainer">

            <h1>Edit Food</h1>
            <h5 id="subtitle">Here's where you can view and edit food details.</h5>
            <br/>
            <div class="mainContainer">

                <form action="#" class="form">
                    <a href="#" onclick="confirmRemoval()"> <div class="removal">Discontinue</div></a>
                    <div>
                        <input type="text" value="F000001" style="background-color: darkgray;" placeholder="Food ID"  id="foodid" readonly/>
                    </div>
                    <div>
                        <input type="text" id="foodName" placeholder="Food Name" value="Chicken Wing" />
                    </div>
                    <div>
                        <input type="text" value="200" placeholder="Calories" id="calories" />
                    </div>
                    <div>
                        <input type="text" value="16 March, 2017" id="dateAdded"  style="background-color: darkgray;"  readonly/>
                    </div>
                    <div>
                        <input type="text" value="not discontinued" id="dateDiscontinued"  style="background-color: darkgray; font-weight: 500;"  readonly/>
                    </div>
                    
                    <br/>
                    <button type="submit" class="submitBtn">Submit changes</button>
                </form>
                <br/>
            </div>
        </div>
        <a href="displayStaff.jsp"><div class="back">back</div></a>
        <div class="removalConfirmation">
            <h5>Discontinue food?</h5>
            <p>The food will be discontinued permanently.</p>
            <a href="#"><div class="removalConfirm">Yes</div></a>
            <a href="#"><div class="removalCancel">No</div></a>
        </div>

    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Some javascript. It will dynamically change the subtitle based on what the user hovers over. -->
    <script>
                                $(document).ready(function () {
                                    $("#foodid").hover(function () {
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
                                    
                                    $(".removal").hover(function () {
                                        $("#subtitle").html("Discontinue the food");
                                        $("#subtitle").css("color", "red");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view and edit food's details.");
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
            <!--Footer-->
    <footer class="footerContainer">
         
    <section class="footerBottom">

    <div class="footerBottom" style="border:0px; font-size: 10px;">   
    Copyright©2019 RealFood - All Rights Reserved -
    </div>
<i style="font-size:14px" class="fa">&#xf230</i> <a href="https://www.facebook.com/RealFood-2569784913093353/?ref=br_tf&epa=SEARCH_BOX" style="font-size: 12px">FACEBOOK</a>
<i style="font-size:14px;color:red" class="fa">&#xf2b3</i> <a href="https://www.google.com/gmail/" style="font-size: 12px">Gmail</a>
<span style="color: white;font-size: 12px"><b>Email:</b> johannljx-sm17@student.tarc.edu.my | khootw-sm17@student.tarc.edu.my <b>Hotline:</b> 1600 99 8888 <b>Contact Number:</b> +60123456789</span>
  </section>
        
</footer>
</html>
