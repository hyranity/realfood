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
            <h1>steps</h1>
            <div class="steps">
                <div class="currentStep">1. Select food components.</div>
                <div>2. Edit food quantity</div>
                <div>3. Finalize meal details</div>
            </div>
        </div>

        <h2>Select Food Components</h2>
        <h6 id="subtitle">Select the food that compromises your meal.</h6>

        <!-- Search bar -->
        <form class="searchForm">
            <input type="text" name="query" placeholder="search..." class="searchBar"/>
        </form>
        <div class="bodyContainer">
            <form action="#" method="post">
                <table class="recordTable">
                    <tr>
                        <td>
                            <input type="checkbox" id="cbox1"/>
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
    <!--Footer-->
    <footer class="footerContainer">
         
    <section class="footerBottom">

    <div class="footerBottom" style="border:0px; font-size: 10px;">   
    Copyright©2019 RealFood - All Rights Reserved -
    </div>
<i style="font-size:14px" class="fa">&#xf230</i> <a href="https://www.facebook.com/RealFood-2569784913093353/?ref=br_tf&epa=SEARCH_BOX" style="font-size: 12px">FACEBOOK</a>
<i style="font-size:14px;color:red" class="fa">&#xf2b3</i> <a href="https://www.google.com/gmail/" style="font-size: 12px">Gmail</a>
<span style="color: white;font-size: 12px"><b>Email:</b> johannljx-sm17@student.tarc.edu.my | khootw-sm17@student.tarc.edu.my <b>Hotline:</b> 1600 99 8888 <b>Contact Number:</b> +60123456789</span>
  </section>
        
</footer>
</html>
