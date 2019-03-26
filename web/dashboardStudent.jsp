<%-- 
    Document   : dashboardStudent
    Created on : Mar 25, 2019, 7:42:13 PM
    Author     : mast3
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
        <link href="CSS/dashboardStudent.css" rel="stylesheet">
        <title>My Dashboard</title>
    </head>
    <body>
        <div class="dashboardContainer">
            <h1>Welcome, Michael</h1>
            <h6 class="credits">1000 credits</h6>
            <div class="buttonsContainer">
                <a href="#"><div class="buttonDiv">my orders <span class="badge badge-info">10</span></div></a>
                <a href="#"><div class="buttonDiv">notifications <span class="badge badge-info" >4</span></div></a>
                <br>
                <a href="#"><div class="buttonDiv">my account</div></a>
                <a href="#"><div class="buttonDiv">my orders <span class="badge badge-info" >4</span></div></a>
            </div>
            <div class="logout">logout</div>
        </div>
    </body>
</html>
