<%-- 
    Document   : payment
    Created on : Mar 30, 2019, 3:26:59 PM
    Author     : Richard Khoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/quantityModification.css" rel="stylesheet">
        <link href="CSS/students.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <link href="CSS/payment.css" rel="stylesheet">
        <title>Payment.</title>
    </head>
    <body>
                        <%
            session = request.getSession(false);
            
            String permission = (String) session.getAttribute("permission");
            
            // If user is not logged in, redirect to login page
            if (permission == null) {
                request.setAttribute("errorMsg", "Please login.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            else {
                // Allow student only
                if(!permission.equalsIgnoreCase("student")){
                     request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
        %>
        <div class="stepsContainer">
            <h1>steps</h1>
            <div class="steps">
                <div>1. Select a date</div>
                <div>2. Choose a meal</div>
                <div>3. Edit particulars</div>
                <div class="currentStep">4. Payment</div>
            </div>
        </div>
<!------------>

        <h1 class="title">Payment Confirmation</h1>
        <h5 id="subtitle"></h5>

        <div class="mainContainer2">
            <div class="recordQuantity2"">
                <div class="frontPart">
                    <p class="name">Spaghetti Bolognese</p>

                </div>
                <div class="quantityEditor">
                    <p class="value">1500 Credits</p>
                    <p class="quantity" data-quantity="5">x2</p>
                </div>
            </div>
            <br/>
            <div class="recordQuantity2">
                <div class="frontPart">
                    <p class="name">Peppermint Ice Cream</p>
                </div>
                <div class="quantityEditor">
                    <p class="value">500 Credits</p>
                    <p class="quantity" data-quantity="5">x23</p>
                </div>
            </div>
            <br/>
            <div class="total2">
                <p>Total Payment Is 4000 Credits Point</p>
            </div>
           
        </div>
        
        
        <div class="container">
                     <form action="">
                        
                        <div class="formGroup">
                            <input type="text" value="STU123456" class="formInput" id="userID" name="userID" style="background-color: darkgray;" placeholder="User ID" maxlength="10" readonly/>
                        </div>
                        
                        <div class="formGroup">
                            <input type="password" value="123456" class="formInput" name="oassword" id="password" placeholder="Password" maxlength="20" required/>
                        </div>
            
            <div style="display: inline-block; text-align: center; border-radius: 50px;">
            <button class="nextButton" href="" type="submit" >Back</button>
            </div>
            
            <div style="display: inline-block; text-align: center; border-radius: 50px;">
            <button class="nextButton" href="" type="submit" >PAY 4000 CP</button>
            </div>
        </form>
        <h6 class="credits">1000 credits</h6>
</div>
        <%}%>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</html>

