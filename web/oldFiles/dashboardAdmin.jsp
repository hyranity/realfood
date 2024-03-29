<%-- 
    Document   : LoadStudentDashboard
    Created on : Mar 25, 2019, 7:42:13 PM
    Author     : Johann
--%>

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
        <div class="dashboardContainer">
            <h1>Welcome, <span class="badge badge-danger">Administrator</span></h1>
            <h4 id="subtitle">Here's your dashboard.</h4>
            <div class="buttonsContainer">
                <a href="#"><div class="buttonDiv" id="manager">manage manager account</div></a>
                <a href="#"><div class="buttonDiv" id="school">manage school staff accounts</div></a>
                <br/>
                <a href="#"><div class="buttonDiv" id="account">my account</div></a>
            </div>
            <a href="#"><div class="logout">logout</div></a>
        </div>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#manager").hover(function () {
                $("#subtitle").html("That is where you can add or manage the hired status of the canteen manager.");
            }, function () {
                $("#subtitle").html("Here's your dashboard.");
            });
            $("#school").hover(function () {
                $("#subtitle").html("That is where you can add or manage the hired status of the school staff.");
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
