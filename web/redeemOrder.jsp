<%-- 
    Document   : topUp
    Created on : Mar 31, 2019, 11:06:40 PM
    Author     : Richard Khoo
--%>

<%@page import="Model.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><head>
        <title>Top Up</title>

        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">

        <link href="CSS/commonStyles.css" rel="stylesheet" type="text/css"> 
        <link href="CSS/topUp.css" rel="stylesheet" type="text/css">
    </head>
    <body
        <%
            // If user is not logged in, redirect to login page
            session = request.getSession(false);
            if (session.getAttribute("staff") == null) {
                if (session.getAttribute("stud") != null) {
                    request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                } else {
                    request.setAttribute("errorMsg", "Please login.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            } else {
                
            

        %>
        <div class="mainContainer">
            <h1>Redeem Order</h1>
            <h5 style="font-size: 20px; margin-top: -20px;">Enter coupon code</h5>

            <!-- Show alerts if any -->
            <div class="errorMsg">${errorMsg}</div>
                <div class="successMsg">${successMsg}</div>
                
                <form action="FindOrderServlet" method="POST" id="findOrderForm" name="findOrderForm">
                    
            <div class="topUpForm">
                
                    <input type="text" maxlength="14" placeholder="Coupon Code" name="couponCode" required/>
                    <br/>
                    <button type="submit" form="findOrderForm" value="Submit">next</button>
                    <br/>
                    <br/>
                    </div>
                </form>
        </div>

        <a href="dashboardCanteenStaff.jsp"><div class="backBt">Back</div></a>
        <%
            }
        %>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</html>
