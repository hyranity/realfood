<%-- 
    Document   : dashboardStudent
    Created on : Mar 25, 2019, 7:42:13 PM
    Author     : mast3
--%>

<%@page import="Model.Student"%>
<%@page import="Model.*"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/dashboard.css" rel="stylesheet">
        <title>My Dashboard</title>
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
           Student stud = (Student) session.getAttribute("stud");
           String fname = stud.getFirstname();
           int credits = stud.getCredits();    // Obtain student's credits amount
        %>
        
        <div class="dashboardContainer">
            <h1>Welcome, <%=fname%> <span class="badge badge-success">Student</span></h1>
            <h4 id="subtitle">Here's your dashboard.</h4>
            <!-- Display student's credits -->
            <h6 class="credits"><%=credits%> credits</h6>
            <div class="buttonsContainer">
                <a href="DisplayOrdersServlet"><div class="buttonDiv" id="order">my orders</div></a>
                <a href="DisplayNotifications"><div class="buttonDiv" id="notification">my notifications</div></a>
                <br/>
                <a href="calendarStudent.jsp"><div class="buttonDiv" id="makeOrder">make an order</div></a>
                <a href="studentProfile.jsp"><div class="buttonDiv" id="account">my account</div></a>
            </div>
            <a href="LogoutServlet"><div class="logout">logout</div></a>
        </div>

            <%}%>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#order").hover(function () {
                $("#subtitle").html("That is where you can add, edit, and cancel your orders.");
            }, function () {
                $("#subtitle").html("Here's your dashboard.");
            });
            $("#notification").hover(function () {
                $("#subtitle").html("That is where you can view all your notifications.");
            }, function () {
                $("#subtitle").html("Here's your dashboard.");
            });
            $("#makeOrder").hover(function () {
                $("#subtitle").html("That is where you can make an order.");
            }, function () {
                $("#subtitle").html("Here's your dashboard.");
            });
            $("#account").hover(function () {
                $("#subtitle").html("That is where you can view and edit your account details.");
            }, function () {
                $("#subtitle").html("Here's your dashboard.");
            });
        });
    </script>
            
</html>
