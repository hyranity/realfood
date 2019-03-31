<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Order</title>
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="CSS/recordInfo.css" rel="stylesheet">
        <link href="CSS/viewOrder.css" rel="stylesheet">
        <link href="CSS/students.css" rel="stylesheet">
    </head>
    <body>
        <div class="outsideContainer">

            <h1>My Order</h1>
            <h5 id="subtitle">Here's where you can view order details or cancel the order.</h5>
            <br/>
            <div class="orderContainer">

                <a href=""><div class="couponPage">Print coupon code</div></a>
                    <br/>
                    <div class="orderDetails">
                        <h2 id="orderId">O000001</h2>
                        <br/>
                        <p class="mealItem" >Spaghetti Carbonara w/  BBQ Pork Sauce</p><p class="lunch">Lunch</p><p class="quantity">x1</p>
                        <br/>
                        <p class="mealItem" >Spaghetti Carbonara w/  BBQ Pork Sauce</p><p class="breakfast">Bkfast</p><p class="quantity">x1</p>
                        <br/>
                        <p class="mealItem" >Spaghetti Carbonara w/  BBQ Pork Sauce</p><p class="lunch">Lunch</p><p class="quantity">x1</p>
                        <br/>
                        <p class="mealItem" >Spaghetti Carbonara w/  BBQ Pork Sauce</p><p class="breakfast">Bkfast</p><p class="quantity">x1</p>
                        <br/>
                        <br/>
                        <p class="smallText">to be served on</p>
                        <br/>
                        <p class="chosenDate" id="chosenDate">14 May, 2019 (Tuesday)</p>
                        <br/>
                        <p class="smallText">total price</p>
                        <br/>
                        <p class="totalPrice" id="totalPrice">1200 Credits</p>
                        <br/>
                        <p class="smallText">coupon code</p>
                        <br/>
                        <p class="couponCode" id="couponCode">aK5sXmS</p>
                        <br/>
                        <br/>
                        <a href="#" onclick="confirmRemoval()"> <div class="removal">Cancel Order</div></a>
                    </div>
                    
            </div>
        </div>
        <a href=""><div class="back">back</div></a>
        <div class="removalConfirmation">
            <h5>Discontinue order?</h5>
            <p>The order will be canceled and you will receive an 80% refund.</p>
            <a href="#"><div class="removalConfirm">Yes</div></a>
            <a href="#"><div class="removalCancel">No</div></a>
        </div>
<h6 class="credits">1000 credits</h6>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Some javascript. It will dynamically change the subtitle based on what the user hovers over. -->
    <script>
                                $(document).ready(function () {
                                    $("#orderId").hover(function () {
                                        $("#subtitle").html("That's the order ID. It uniquely defines the order. It can't be changed.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view order details or cancel the order.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $(".mealName").hover(function () {
                                        $("#subtitle").html("That's the meal's name.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view order details or cancel the order.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#chosenDate").hover(function () {
                                        $("#subtitle").html("That's the date where the meals are to be served and when you will need to collect it.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view order details or cancel the order.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $(".quantity").hover(function () {
                                        $("#subtitle").html("That's how much of that meal you've ordered. ");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view order details or cancel the order.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#totalPrice").hover(function () {
                                        $("#subtitle").html("That's the total price of the order in credits. ");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view order details or cancel the order.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $(".breakfast").hover(function () {
                                        $("#subtitle").html("This meal will be served at breakfast.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view order details or cancel the order.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $(".lunch").hover(function () {
                                        $("#subtitle").html("This meal will be served at lunch.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view order details or cancel the order.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    
                                    $(".removal").hover(function () {
                                        $("#subtitle").html("Cancel the order.");
                                        $("#subtitle").css("color", "red");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view order details or cancel the order.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $(".couponCode").hover(function () {
                                        $("#subtitle").html("Use this code when redeeming your meals.");
                                        $("#subtitle").css("color", "gold");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view order details or cancel the order.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $(".couponPage").hover(function () {
                                        $("#subtitle").html("Click here to lead you to page where you can print your code.");
                                        $("#subtitle").css("color", "darkgoldenrod");
                                    }, function () {
                                        $("#subtitle").html("Here's where you can view order details or cancel the order.");
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
