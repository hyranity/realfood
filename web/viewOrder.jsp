<%@page import="java.util.Date"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Calendar"%>
<%@page import="Model.Meal"%>
<%@page import="Model.Ordermeal"%>
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
        <link href="CSS/commonStyles.css" rel="stylesheet">
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

            
            // Allow student only
            if (!permission.equalsIgnoreCase("student")) {
                request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {
                Studentorder so = new Studentorder();

                try {
                    so = (Studentorder) session.getAttribute("studentOrder");
                } catch (Exception e) {
                    // This will be triggered if the page is accessed directly, hence redirect to dashboard
                    response.sendRedirect("studentDashboard.jsp");
                }

                String orderId = so.getOrderid();

                // Set the chosen date
                Calendar cal = Calendar.getInstance();
                cal.setTime(so.getChosendate());
                String dateStr = cal.get(Calendar.DAY_OF_MONTH) + " " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + ", " + cal.get(Calendar.YEAR) + " (" + cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH) + ")";
                String couponCode = so.getCouponcode();
                int totalPrice = so.getTotalprice();
                
                Calendar today = Calendar.getInstance();
                
                boolean isBlockedFromCanceling = false;
                boolean expired = false;
                if( today.get(Calendar.DATE) == cal.get(Calendar.DATE)){
                    isBlockedFromCanceling = true;
                }
                
                if(today.get(Calendar.DATE) > cal.get(Calendar.DATE))
                    expired = true;
               
        %>
        <div class="outsideContainer">

            <h1>My Order</h1>
            <h5 id="subtitle">Here's where you can view order details or cancel the order.</h5>
            <br/>
            <div class="errorMsg">${errorMsg}</div>
            <div class="successMsgAbsolute">${successMsg}</div>
            <div class="orderContainer" >
                
                 <%
                    if(!so.getIscanceled() && !so.getIsredeemed() && !expired ){
                    %>
                    <a href=""><div class="couponPage">Print coupon code</div></a>
                    
                    <%
                    }
                    %>

                <br/>
                <div class="orderDetails">
                    
                    <%
                    if(so.getIsredeemed()){
                    %>
                    <h2 id="orderId" style="color: green;"><%=orderId%></h2>
                    <div style="color: green; font-weight: bold;">(REDEEMED)</div>
                    
                    <%
                    } else if(so.getIscanceled()){
                    %>
                    <h2 id="orderId" style="color: red;"><%=orderId%></h2>
                    <div style="color: red; font-weight: bold;">(CANCELLED)</div>
                    
                    <%
                    } else  if(expired){
                    %>
                    <h2 id="orderId" style="color: red;"><%=orderId%></h2>
                    <div style="color: red; font-weight: bold;">(EXPIRED)</div>
                    
                    <%
                    }else{
                    %>
                    <h2 id="orderId"><%=orderId%></h2>
                    <%}%>
                    
                    <br/>
                    <%
                        for (Ordermeal om : so.getOrdermealList()) {

                            Meal meal = om.getMealid();
                            String mealName = om.getMealid().getMealname();
                            int quantity = om.getQuantity();
                            
                            if(om.getIsredeemed()){
                    %>
                    <p class="mealItem" style="color: white; background-color:darkgreen;"><%=mealName%></p>
                    <%
                    }else{
                    %>
                    <p class="mealItem" ><%=mealName%></p>
                    <!-- Display the meal time -->
                    <%
                        }
                        if (meal.getIsbreakfast()) {
                    %>
                    <p class="breakfast">Breakfast</p>
                    <%
                    } else if (meal.getIslunch()) {
                    %>
                    <p class="lunch">Lunch</p>
                    <%
                    } else {
                    %>
                    <p class="allDay">All Day</p>
                    <%
                        }
                    %>

                    <p class="quantity">x<%=quantity%></p>
                    <br/>
                    <%}%>
                    <br/>
                    <br/>
                    <p class="smallText">to be served on</p>
                    <br/>
                    <p class="chosenDate" id="chosenDate"><%=dateStr%></p>
                    <br/>
                    <p class="smallText">total price</p>
                    <br/>
                    <p class="totalPrice" id="totalPrice"><%=totalPrice%> Credits</p>
                    <br/>
                    <p class="smallText">coupon code</p>
                    <br/>
                    <p class="couponCode" id="couponCode"><%=couponCode%></p>
                    <br/>
                    <br/>
                    <%
                        if (!so.getIscanceled() && !so.getIsredeemed() && !isBlockedFromCanceling && !expired) {
                    %>
                    <a href="#" onclick="confirmtoggleOverlay()"> <div class="toggleOverlay">Cancel Order</div></a>      
                    <a href="LoadOrderForEditServlet?orderId=<%=orderId%>"><div class="editOrderBt">Edit Order</div></a>
                    <%
                        }
                    %>
                    <%
                        if (isBlockedFromCanceling && !so.getIsredeemed() && !so.getIscanceled()) {
                    %>
                     <div style="color: red; font-weight: bold; margin: 10px;">Can't cancel nor edit (Must cancel 1 day earlier)</div>
                    <%
                        }
                    %>
                    
                </div>

            </div>
        </div>
        <a href="DisplayOrdersServlet"><div class="back" style="margin-bottom: 30px;">Back</div></a>
        <%
            if (!so.getIscanceled() && !so.getIsredeemed() && !isBlockedFromCanceling && !expired) {
        %>
        <div class="toggleConfirmation">
            <h5>Cancel order?</h5>
            <p>The order will be canceled and you will receive an 80% refund.</p>
            <a href="OrderCancellationServlet?orderId=<%=orderId%>"><div class="overlayConfirm">Yes</div></a>
            <a href="#"><div class="overlayCancel">No</div></a>
        </div>
        <%}%>
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

                            $(".toggleOverlay").hover(function () {
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

                            $(".toggleOverlay").click(function confirmtoggleOverlay() {
                                $(".toggleConfirmation").css("display", "inline-block");
                                $(".outsideContainer").css("opacity", "0.5");
                                $(".outsideContainer :input").prop("disabled", true);
                            });

                            $(".overlayCancel").click(function confirmtoggleOverlay() {
                                $(".toggleConfirmation").css("display", "none");
                                $(".outsideContainer").css("opacity", "1");
                                $(".outsideContainer :input").prop("disabled", false);
                            });
                        });
    </script>
</html>
