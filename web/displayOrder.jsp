<%-- 
    Document   : displayStudents
    Created on : Mar 28, 2019, 7:33:45 AM
    Author     : mast3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.*"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/manageRecords.css" rel="stylesheet">
        <link href="CSS/students.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <title>My orders</title>
    </head>
    <body>
                <%
            session = request.getSession(false);
            
            String permission = (String) session.getAttribute("permission");
            
            
            if (permission == null) {
                request.setAttribute("errorMsg", "Please login.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            else {
                Student stud = (Student) session.getAttribute("stud");
                int credits = stud.getCredits();    // Obtain student's credits amount
                
                // Allow student only
                if(!permission.equalsIgnoreCase("student")){
                     request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
        %>
        <h1>My Orders</h1><br><br>
        <h4 id="subtitle">Here you can manage orders. Viewing orders also allow you to print the coupon code.</h4>

        <div class="successMsgAbsolute">${successMsg}</div>
        <div class="errorMsg">${errorMsg}</div>
<div style="margin-top: 60px;">
        <table class="recordTable">   
            <tr>
                <td>
                    <a href="calendarStudent.jsp"><div class="add">
                            +
                        </div></a>
                </td>
                ${queryResult}
            </tr>
        </table> 
    </div>
        <!-- Display student's credits -->
            <h6 class="credits"><%=credits%> credits</h6>
            <div style="margin-bottom: 30px;"><a class="backBt" href="LoadStudentDashboard"  >Back</a></div>
        <%}%>     
</body>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
    $(document).ready(function () {
        $(".editButton").hover(function () {
            $("#subtitle").html("Edit orders (or view details).");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here you can manage orders. Edit orders to view their details.");
            $("#subtitle").css("color", "white");
        });
        $(".add").hover(function () {
            $("#subtitle").html("Add new orders.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here you can manage orders. Edit orders to view their details.");
            $("#subtitle").css("color", "white");
        });
        $(".searchBar").hover(function () {
            $("#subtitle").html("Search by ID or name.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here you can manage orders. Edit orders to view their details.");
            $("#subtitle").css("color", "white");
        });
    });
</script>
        
</html>
