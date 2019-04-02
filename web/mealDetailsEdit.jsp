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
        <link href="CSS/mealDetails.css" rel="stylesheet">
    </head>
    <body>
        <!-- Steps -->
        <div class="stepsContainer">
            <h1>Edit a Meal</h1>
            <div class="steps">
                <div>1. Modify food components.</div>
                <div class="currentStep">2. Modify food quantity</div>
                <div >3. Modify meal details</div>
            </div>
        </div>
        
        <div class="outsideContainer"><br/><br/><br/>

            <h1>Finalize meal details</h1>
            <h5 id="subtitle">Here's where you can edit the meal's details.</h5>
            <br/>
            <div class="mainContainer">

                <form action="#" class="form">
                     <a href="#" onclick="confirmRemoval()"> <div class="removal">Discontinue</div></a>
                    <div>
                        <input type="text" value="M000001" style="background-color: darkgray;"  id="mealId" required/>
                        </div>
                    <div>
                        <input type="text" id="mealName" value="Spaghetti Cabonara" placeholder="Meal Name"  required/>
                    </div>
                    <div>
                        <input type="text" value="It's a very tasty food" id="description" placeholder="Meal Description" required/>
                    </div>
                    <div>
                        <input type="text" value="3894" id="price" placeholder="Price"/>
                    </div>
                    <div>
                        <input type="text" value="example.imgur.com/example123" id="imageLink" placeholder="Image Link" required/>
                    </div>
                     <div>
                        <input type="text" value="1903" id="totalCalories" style="background-color: darkgray;"  readonly/>
                    </div>
                     <div>
                        <input type="text" value="16 March, 2017" id="dateAdded"  style="background-color: darkgray;"  readonly/>
                    </div>
                    <div>
                        <input type="text" value="not discontinued" id="dateDiscontinued"  style="background-color: darkgray; font-weight: 500;"  readonly/>
                    </div>
                     <div class="mealTime" style="margin-top: 50px;">
                        <p>Meal will be served during:</p>
                        <input type="checkbox" value="" id="breakfast" name="breakfast"/>
                        <label for="breakfast"><div id="breakfastDiv">Breakfast</div></label>
                        
                        <input type="checkbox" value="" id="lunch" name="lunch"/>
                         <label for="lunch"><div id="lunchDiv">Lunch</div></label>
                    </div>
                    <button type="submit" class="submitBtn">Add meal</button>
                </form>
                <br/>
            </div>
        </div>
        <a href="displayMeals.jsp"><div class="back">Back</div></a><br/><br/>
        <div class="removalConfirmation">
            <h5>Discontinue meal?</h5>
            <p>The meal will be permanently discontinued.</p>
            <a href="#"><div class="removalConfirm">Yes</div></a>
            <a href="#"><div class="removalCancel">No</div></a>
        </div>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Some javascript. It will dynamically change the subtitle based on what is hovered over. -->
    <script>
                                $(document).ready(function () {
                                    $("#mealid").hover(function () {
                                        $("#subtitle").html("That's the meal ID. It uniquely defines the meal. It can't be changed.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can edit the meal's details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#mealName").hover(function () {
                                        $("#subtitle").html("That's the meal's name.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can edit the meal's details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#description").hover(function () {
                                        $("#subtitle").html("That's the description of the meal.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can edit the meal's details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#price").hover(function () {
                                        $("#subtitle").html("That's how much credits the meal will cost.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can edit the meal's details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#imageLink").hover(function () {
                                        $("#subtitle").html("That's the link of where the image was uploaded to. You can edit the link if you want to change the image.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can edit the meal's details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $(".mealTime").hover(function () {
                                        $("#subtitle").html("That's what time the meal can be ordered (and redeemed). You may select both.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can edit the meal's details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#totalCalories").hover(function () {
                                        $("#subtitle").html("That's the total number of calories in the meal. It's auto-calculated.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can edit the meal's details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#dateAdded").hover(function () {
                                        $("#subtitle").html("That's when the food was added. No point in editing it.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can edit the meal's details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#dateDiscontinued").hover(function () {
                                        $("#subtitle").html("That's when the food was discontinued. If it's not discontinued, it will show 'not discontinued'. ");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can edit the meal's details.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    
                                    $(".removal").hover(function () {
                                        $("#subtitle").html("Discontinue the meal");
                                        $("#subtitle").css("color", "red");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can edit the meal's details.");
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
