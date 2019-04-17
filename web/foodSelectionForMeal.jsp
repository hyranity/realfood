<%-- 
    Document   : displayStudents
    Created on : Mar 28, 2019, 7:33:45 AM
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
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="CSS/manageRecords.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <link href="CSS/foodSelection.css" rel="stylesheet">
        <title>Manage food</title>
    </head>
    <body>
        <!-- Steps -->
        <div class="stepsContainer">
            <h1>Create a Meal</h1>
            <div class="steps">
                <div class="currentStep">1. Select food components.</div>
                <div>2. Edit food quantity</div>
                <div>3. Finalize meal details</div>
            </div>
        </div>
 

        <h2>Select Food Components</h2>
        <h6 id="subtitle">Select the food that compromises your meal.</h6>

        <br/>   
        <div class="errorMsg">${errorMsg}</div>
        <br/>
        <div class="bodyContainer">
            <form action="SelectFoodServlet" method="post">
                <table class="recordTable">
                    ${queryResult}
                </table>
                <div>
                    <a class="backBt" href="ManageMealsServlet">Back</a>
                    <input type="submit" value="Next step" class="nextButton">
                </div>
            </form>
        </div>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $(".editButton").hover(function () {
                $("#subtitle").html("Edit food (or view details).");
                $("#subtitle").css("color", "gold");
            }, function () {
                $("#subtitle").html("Select the food that compromises your meal.");
                $("#subtitle").css("color", "white");
            });
            $(".searchBar").hover(function () {
                $("#subtitle").html("Search by ID or name.");
                $("#subtitle").css("color", "gold");
            }, function () {
                $("#subtitle").html("Select the food that compromises your meal.");
                $("#subtitle").css("color", "white");
            });
        });
    </script>

</html>
