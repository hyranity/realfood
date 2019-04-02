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
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <link href="CSS/mealDetails.css" rel="stylesheet">
    </head>
    <body>
        <!-- Steps -->
        <div class="stepsContainer">
            <h1>Create a Meal</h1>
            <div class="steps">
                <div>1. Select meal components.</div>
                <div>2. Edit meal quantity</div>
                <div class="currentStep">3. Finalize meal details</div>
            </div>
        </div>
        
        <div class="outsideContainer"><br/><br/><br/>

            <h1>Finalize meal details</h1>
            <h5 id="subtitle">Complete the meal creation by filling in its details.</h5>
            <br/>
            <div class="mainContainer">

                <form action="#" class="form">
                    <div>
                        <input type="text" id="mealName" value="" placeholder="name" />
                    </div>
                    <div>
                        <input type="text" value="" id="description" placeholder="description"/>
                    </div>
                    <div>
                        <input type="text" value="" id="price" placeholder="price"/>
                    </div>
                    <div>
                        <input type="text" value="" id="imageLink" placeholder="image link"/>
                    </div>
                    <div class="mealTime">
                        <p>Meal will be served during:</p>
                        <input type="checkbox" value="" id="breakfast" name="breakfast"/>
                        <label for="breakfast"><div id="breakfastDiv">Breakfast</div></label>
                        
                        <input type="checkbox" value="" id="lunch" name="lunch"/>
                         <label for="lunch"><div id="lunchDiv">Lunch</div></label>
                    </div>
                    
                    <br/>
                    <button type="submit" class="submitBtn">add meal</button>
                </form>
                <br/>
            </div>
        </div>
        <a href="displayStaff.jsp"><div class="back">back</div></a>

    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Some javascript. It will dynamically change the subtitle based on what is hovered over. -->
    <script>
                                $(document).ready(function () {
                                    $("#mealid").hover(function () {
                                        $("#subtitle").html("That's the meal ID. It uniquely defines the meal. It can't be changed.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Complete the meal creation by filling in its details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#mealName").hover(function () {
                                        $("#subtitle").html("That's the meal's name.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Complete the meal creation by filling in its details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#description").hover(function () {
                                        $("#subtitle").html("That's the description of the meal.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Complete the meal creation by filling in its details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#price").hover(function () {
                                        $("#subtitle").html("That's how much credits the meal will cost.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Complete the meal creation by filling in its details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#imageLink").hover(function () {
                                        $("#subtitle").html("Upload an image of the meal to a site like IMGUR and copy the link here. ");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Complete the meal creation by filling in its details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $(".mealTime").hover(function () {
                                        $("#subtitle").html("That's what time the meal can be ordered (and redeemed). You may select both.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Complete the meal creation by filling in its details.");
                                        $("#subtitle").css("color", "white");
                                    });

                                   
                                });
    </script>
    <!--Footer-->
    <footer class="footerContainer" style="margin-top: 200px;">
         
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
