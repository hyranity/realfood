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
        <title>Manage food</title>
    </head>
    <body>
        <h1>Manage Food</h1><br><br>
        <h4 id="subtitle">Here you can manage food. Edit food to view their details.</h4>

        <!-- Search bar -->
        <form class="searchForm">
            <input type="text" name="query" placeholder="search..." class="searchBar"/>
        </form>
<div class="bodyContainer">
        <table class="recordTable">
            <tr>
                <td>
                    
                        <div class="add">
                            +
                        </div>
                </td>
                <td>
                    <div class="record">
                        <h6>F00001</h6>
                        <p>Honey Mustard</p>
                        <p>120 calories</p>
                        <a href=""><div class="editButton">Edit</div></a>
                    </div>
                </td>
                <td>
                    <div class="record">
                        <h6>F00002</h6>
                        <p>Honey Mustard</p>
                        <p>120 calories</p>
                        <br/>
                        <p class="status" style="color: red; font-weight: bold;">Discontinued</p>
                        <a href=""><div class="editButton">Edit</div></a>
                    </div>
                </td>
            </tr>
        </table>
    </div>
        <div><button class="nextButton" href="" type="submit" >Back</button></div>
</body>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
    $(document).ready(function () {
        $(".editButton").hover(function () {
            $("#subtitle").html("Edit food (or view details).");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here you can manage food. Edit food to view their details.");
            $("#subtitle").css("color", "white");
        });
        $(".add").hover(function () {
            $("#subtitle").html("Add new food.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here you can manage food. Edit food to view their details.");
            $("#subtitle").css("color", "white");
        });
        $(".searchBar").hover(function () {
            $("#subtitle").html("Search by ID or name.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here you can manage food. Edit food to view their details.");
            $("#subtitle").css("color", "white");
        });
    });
</script>
        
</html>
