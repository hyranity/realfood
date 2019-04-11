<%-- 
    Document   : payment
    Created on : Mar 30, 2019, 3:26:59 PM
    Author     : Richard Khoo
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Model.*"%>
<%@page import="java.util.*"%>
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
            
            String permission = "";
            String[] dateValue = null;
            Student stud = new Student();

            try {
                permission = (String) session.getAttribute("permission");
                stud = (Student) session.getAttribute("stud");
                stud.getFirstname();
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

                Studentorder studOrder = new Studentorder();
                List<Date> chosenDates = new ArrayList();
                
                // Attempt to get studOrder
                try {
                    studOrder = (Studentorder) session.getAttribute("studOrder");
                    studOrder.getTotalprice(); // If null, it will cause an exception
                    chosenDates = (List<Date>) session.getAttribute("chosenDates");
                } catch (Exception e) {
                    // If cannot get, means user did not follow the steps
                    request.getRequestDispatcher("calendarStudent.jsp").forward(request, response);
                    System.out.println("Couldn't get data from session for studentOrderPayment.jsp: " + e.getMessage());
                }

                int credits = stud.getCredits();
                int totalPrice = studOrder.getTotalprice();
                
// For formatting the dates
SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
                

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
        <h5 id="subtitle">Date(s) booked:</h5>
        <h6 style="color: gold; font-size: 15px; ">
            <!-- Print the dates -->
            <%                int count = 0;
                for (Date date : chosenDates) {
                    
                    String dateStr = sm.format(date);
            %>

            <!-- Print commas if not the first one -->

            <% if (count > 0) {%>
            , 
            <% }%>

          <%=dateStr%>


            <%
                    count++;
                }%>

        </h6>

        <%
            for (int i = 0; i < studOrder.getOrdermealList().size(); i++) {
                Ordermeal om = studOrder.getOrdermealList().get(i);

                String mealName = om.getMealid().getMealname();
                int price = om.getMealid().getPrice();
                int quantity = om.getQuantity();
        %>
        <div class="mainContainer2">
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
                <p>Total: <%=totalPrice%> Credits</p>
            </div>

        </div>



        <div class="container">
            <form action="">

                <div style="display: inline-block; text-align: center; border-radius: 50px;">
                    <button class="nextButton" href="" type="submit" >Back</button>
                </div>

                <div style="display: inline-block; text-align: center; border-radius: 50px;">

                    <!-- If student cannot afford, block the button -->
                    <%
                        if (totalPrice > credits) {
                    %>
                    <button class="insufficientButton" href="" type="submit" disabled>Not enough credits</button>
                    <%
                    } else {
                    %>
                    <a class="nextButton" href="ProcessPaymentServlet" type="submit">PAY <%=totalPrice%> CREDITS</a>
                    <%}%>
                </div>
            </form>
            <h6 class="credits"><%=credits%> credits</h6>
        </div>
        <%}%>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</html>

