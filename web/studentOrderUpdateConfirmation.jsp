<%-- 
    Document   : studentOrderUpdateConfirmation
    Created on : Apr 1, 2019, 11:22:13 PM
    Author     : Richard Khoo
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="Model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/quantityModification.css" rel="stylesheet">
        <link href="CSS/students.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <link href="CSS/payment.css" rel="stylesheet">
        <title>Order Update Confirmation</title>
    </head>
    <body>
        <%
            session = request.getSession(false);
            
            String permission = "";
            Student stud = new Student();

            try {
                permission = (String) session.getAttribute("permission");
                stud = (Student) session.getAttribute("stud");
                stud.getFirstname(); // Triggers exceptions, if any
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

                Studentorder studOrder = new Studentorder();
                Studentorder currentStudOrder = new Studentorder();
                Date chosenDate = new Date();
                
                // Attempt to get studOrder (updated) and original student order
                try {
                    studOrder = (Studentorder) session.getAttribute("studOrderEdit");
                   currentStudOrder = (Studentorder) session.getAttribute("currentStudOrder");
                   
                    // If null, it will cause an exception
                    chosenDate = studOrder.getChosendate();
                } catch (Exception e) {
                    // If cannot get, means user did not follow the steps
                    request.getRequestDispatcher("calendarStudent.jsp").forward(request, response);
                    System.out.println("Couldn't get data from session for studentOrderPayment.jsp: " + e.getMessage());
                }

                int credits = stud.getCredits();    // Obtain student's credits amount
                int totalPrice = studOrder.getTotalprice();
                int originalPrice = currentStudOrder.getTotalprice();
                int grandTotal = originalPrice - totalPrice;
                int originalGrandTotal = currentStudOrder.getTotalprice();
                boolean isARefund = true;
                String totalStr ="";
                
                if(grandTotal < 0){
                    totalStr = "Total charged: " + java.lang.Math.abs(grandTotal); // If negative means student will be charged, hence convert negative credits to display
                    
                    isARefund = false; // Since this charges the student, then it is not a refund
                }
                else
                    totalStr = "Total refunded " + grandTotal; // If positive means student is charged
                
                grandTotal = java.lang.Math.abs(grandTotal);
                
// For formatting the dates
SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
String dateStr = sm.format(chosenDate);
                
// Set isARefund in session
session.setAttribute("isARefund", isARefund);

        %>
        <div class="stepsContainer">
            <h1>Update an Order</h1>
             <div class="steps">
                <div>1. Update meal selection.</div>
                <div>2. Update particulars</div>
                <div class="currentStep">3. Confirm update</div>
            </div>
        </div>

       <!------------>

        <h1 class="title">Update Confirmation</h1>
        <h5 id="subtitle">Date booked:</h5>
        <!-- Print the date -->
        <h6 style="color: gold; font-size: 15px; ">
          <%=dateStr%>
        </h6>
<div class="mainContainer2">
        <%
            for (int i = 0; i < studOrder.getOrdermealList().size(); i++) {
                Ordermeal om = studOrder.getOrdermealList().get(i);

                String mealName = om.getMealid().getMealname();
                int price = om.getMealid().getPrice();
                int quantity = om.getQuantity();
        %>
        
            <div class="recordQuantity2">
                <div class="frontPart">
                    <p class="name"><%=mealName%></p>

                </div>
                <div class="quantityEditor">    
                    <p class="value"><%=price%> Credits</p>
                    <p class="quantity" style="background-color: black;">x<%=quantity%></p>
                </div>
            </div>
            <br/>
            <%}%>
            <div class="total2">
                    <p style="">Original: <%=originalGrandTotal%> credits</p>
                    <p style="">New: <%=totalPrice%> credits</p>
                <p style="font-size: 24px; color: gold;"><%=totalStr%> credits</p>
                <p style="color: darkcyan; margin-top: 100px;">Note: You are updating a single day's order only.</p>
            </div>
        </div>
        <div class="container">
            <form action="">

                <div style="display: inline-block; text-align: center; border-radius: 50px;">
                    <button class="nextButton" href="" type="submit" >Back</button>
                </div>

                <div style="display: inline-block; text-align: center; border-radius: 50px;">

                    <!-- If student cannot afford if it is not a refund, block the button -->
                    <%
                        if (grandTotal > credits && !isARefund) {
                    %>
                    <button class="insufficientButton" href="" type="submit" disabled>Not enough credits</button>
                    <%
                        // Since it's a refund, display REFUND button
                    } else if(isARefund){
                    %>
                    <a class="nextButton" href="ProcessOrderUpdateServlet" type="submit">REFUND <%=grandTotal%> CREDITS</a>
                 
                    <%
                        // Since not a refund and student can afford, show PAY button
                    } else {
                    %>
                    <a class="nextButton" href="ProcessOrderUpdateServlet" type="submit">PAY <%=grandTotal%> CREDITS</a>
                    <%}%>
                </div>
            </form>
                <!-- Display student's credits -->
            <h6 class="credits"><%=credits%> credits</h6>
</div>
        <%}%>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</html>
