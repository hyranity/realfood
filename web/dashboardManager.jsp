<%-- 
    Document   : dashboardStudent
    Created on : Mar 25, 2019, 7:42:13 PM
    Author     : Johann
--%>

<%@page import="Model.Staff"%>
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
            session = request.getSession(false);
            // If user is not logged in, redirect to login page
            if (session.getAttribute("staff") == null) {
                request.setAttribute("errorMsg", "Oops! Please login.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                //Ensure that this is the manager.
                Staff staff = (Staff) session.getAttribute("staff");

                //If not manager, log him/her out and give a warning.
                if (!staff.getStaffrole().equalsIgnoreCase("manager")) {
                    session.invalidate();
                    request.setAttribute("errorMsg", "Hey! You are not allowed to visit that page.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }

                String fname = staff.getFirstname();
        %>
        <div class="dashboardContainer">
            <h1>Welcome, <%=fname%> <span class="badge badge-warning">Manager</span></h1>
            <h4 id="subtitle">Here's your dashboard.</h4>
            <div class="buttonsContainer">
                <a href="DisplayStaffServlet"><div class="buttonDiv" id="staff">manage canteen staff</div></a>
                <a href="#"><div class="buttonDiv" id="report">view reports</div></a>
                <a href="#"><div class="buttonDiv" id="account">my account</div></a>
            </div>
            <a href="LogoutServlet"><div class="logout">logout</div></a>
        </div>
        <%
            }
        %>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#staff").hover(function () {
                $("#subtitle").html("That is where you can add or manage the hired status of the canteen staff");
            }, function () {
                $("#subtitle").html("Here's your dashboard.");
            });
            $("#report").hover(function () {
                $("#subtitle").html("That is where you can view the system's reports.");
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
