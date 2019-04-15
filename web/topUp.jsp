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
            <h1>Credit Point Top Up</h1>
            <h5 style="font-size: 20px; margin-top: -20px;">Enter student's ID</h5>

            <!-- Show alerts if any -->
            <div class="errorMsg">${errorMsg}</div>
                <div class="successMsg">${successMsg}</div>
                
                <form action="TopUpIDServlet" method="POST" id="topUpForm" name="topUpForm">
                    
            <div class="topUpForm">
                
                    <input type="text" maxlength="10" placeholder="Student ID" name="studentId" id="studentid"/>
                    <br/>
                    <button type="submit" form="topUpForm" value="Submit">next</button>
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
    <script>
        $(document).ready(function () {
            $("#cashAmt").on("input", function () {
                //Code is possible thanks to Neha Jain @ https://stackoverflow.com/questions/18510845/maxlength-ignored-for-input-type-number-in-chrome
                //Checks if the number is too long
                if (this.value.length > this.maxLength) {
                    this.value = this.value.slice(0, this.maxLength);
                }
                var $creditAmt = $("#cashAmt").val() * 100;
                $(".creditAmount").text($creditAmt);
            });
        });
    </script>
</html>
