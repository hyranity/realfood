<%@page import="Model.Studentorder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Order</title>
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/recordInfo.css" rel="stylesheet">
        <link href="CSS/viewOrder.css" rel="stylesheet">
        <link href="CSS/students.css" rel="stylesheet">
    </head>
    <body>
        <%
            request.getSession(false);
        
        String permission = "";
        
        try {
            permission = (String) session.getAttribute("permission");
            
            if (permission == null) {
                request.setAttribute("errorMsg", "Please login.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            
        } catch (NullPointerException ex) {
            request.setAttribute("errorMsg", "Please login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // If user is not logged in, redirect to login page
        // Allow student only
        if (!permission.equalsIgnoreCase("student")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            Studentorder so = new Studentorder();
            
            try {
                    
                } catch (Exception e) {
                    
                }
        %>
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
                        <a href="#" onclick="confirmtoggleDisable()"> <div class="toggleDisable">Cancel Order</div></a>
                    </div>
                    
            </div>
        </div>
        <a href=""><div class="back" style="margin-bottom: 30px;">Back</div></a>
        <div class="toggleDisableConfirmation">
            <h5>Cancel order?</h5>
            <p>The order will be canceled and you will receive an 80% refund.</p>
            <a href="#"><div class="toggleDisableConfirm">Yes</div></a>
            <a href="#"><div class="toggleDisableCancel">No</div></a>
        </div>
<h6 class="credits">1000 credits</h6>
    <%}%>
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
                                    
                                    $(".toggleDisable").hover(function () {
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

                                    $(".toggleDisable").click(function confirmtoggleDisable() {
                                        $(".toggleDisableConfirmation").css("display", "inline-block");
                                        $(".outsideContainer").css("opacity", "0.5");
                                        $(".outsideContainer :input").prop("disabled", true);
                                    });

                                    $(".toggleDisableCancel").click(function confirmtoggleDisable() {
                                        $(".toggleDisableConfirmation").css("display", "none");
                                        $(".outsideContainer").css("opacity", "1");
                                        $(".outsideContainer :input").prop("disabled", false);
                                    });
                                });
    </script>
</html>
