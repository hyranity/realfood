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
        <link href="CSS/manageRecords.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <link href="CSS/foodSelection.css" rel="stylesheet">
        <title>Manage food</title>
    </head>
    <body>
        <!-- Steps -->
        <div class="stepsContainer">
            <h1>Edit Meal</h1>
            <div class="steps">
                <div class="currentStep">1. Modify food components.</div>
                <div>2. Modify food quantity</div>
                <div>3. Modify meal details</div>
            </div>
        </div>

        <h2>Modify Food Components</h2>
        <h6 id="subtitle">You can deselect existing components or select new ones.</h6>

        <!-- Search bar -->
        <form class="searchForm">
            <input type="text" name="query" placeholder="search..." class="searchBar"/>
        </form>
        <div class="bodyContainer">
            <form action="#" method="post">
                <table class="recordTable">
                    <tr>
                        <td>
                            <input type="checkbox" id="cbox1" checked/>
                            <label for="cbox1">
                                <div class="record">
                                    <h6>F00001</h6>
                                    <p class="name">Honey Mustard</p>
                                    <br/><br/>
                                    <p class="calories">120 calories</p>
                                </div>
                            </label>
                        </td>
                        <td>
                            <input type="checkbox" id="cbox2"/>
                            <label for="cbox2">
                                <div class="record">
                                    <h6>F00002</h6>
                                    <p class="name">Honey Fries</p>
                                    <br/><br/>
                                    <p class="calories">425 calories</p>
                                    <br/>
                                </div>
                            </label>
                        </td>
                    </tr>
                </table>
                <div class="nextButtonDiv">
                    <button class="nextButton">Next step</button>
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
