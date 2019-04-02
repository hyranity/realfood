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
        <title>Manage canteen staff</title>
    </head>
    <body>
        <h1>Manage Staff</h1><br><br>
        <h4 id="subtitle">Here you can manage staff. Edit staff to view their details.</h4>
        
        <form class="searchForm">
            <input type="text" name="query" placeholder="search..." class="searchBar"/>
        </form>
        
        <!-- Container of the results -->
        <div class="bodyContainer">
            <div class="add">
                +
            </div>
            <div class="record">
                <h6>EMP20034</h6>
                <p>Henry</p>
                <p>Echo</p>
                <span class="badge badge-primary">Canteen Staff</span>
                <br/>
                <br/>
                <p class="status" style="color: green; font-weight: bold;">Hired</p>
                <a href=""><div class="editButton">Edit</div></a>
            </div>

           <div class="record">
                <h6>EMP204440634</h6>
                <p>Holmes</p>
                <p>Mycroft</p>
                <span class="badge badge-primary">Canteen Staff</span>
                <br/>
                <br/>
                <p class="status" style="color: green; font-weight: bold;">Hired</p>
                <a href=""><div class="editButton">Edit</div></a>
            </div>
            
            <div class="record">
                <h6>EMP206806354</h6>
                <p>Brandon</p>
                <p>Mycroft</p>
                <span class="badge badge-primary">Canteen Staff</span>
                <br/>
                <br/>
                <p class="status" style="color: red; font-weight: bold;">Not Hired</p>
                <a href=""><div class="editButton">Edit</div></a>
            </div>
                    
        </div>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $(".editButton").hover(function () {
                $("#subtitle").html("Edit staff (or view details).");
                $("#subtitle").css("color", "gold");
            }, function () {
                $("#subtitle").html("Here you can manage staff. Edit staff to view their details.");
                $("#subtitle").css("color", "white");
            });
            $(".add").hover(function () {
                $("#subtitle").html("Add new staff.");
                $("#subtitle").css("color", "gold");
            }, function () {
                $("#subtitle").html("Here you can manage staff. Edit staff to view their details.");
                $("#subtitle").css("color", "white");
            }); 
            $(".searchBar").hover(function () {
                $("#subtitle").html("Search by ID or name.");
                $("#subtitle").css("color", "gold");
            }, function () {
                $("#subtitle").html("Here you can manage staff. Edit staff to view their details.");
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
