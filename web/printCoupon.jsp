<%-- 
    Document   : printCoupon
    Created on : Apr 1, 2019, 7:24:10 AM
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
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/printCoupon.css" rel="stylesheet">
        <title>Print Coupon</title>
    </head>
    <body>
        <div class="couponContainer">
            <h1>RealFood Canteen</h1>
            <hr/>
            <p><b>Student ID: </b>STU029493</p>
            <p><b>Student Name: </b>Michael Caine</p>
            <p><b>Redemption Date: </b>14 May, 2019</p>
            <hr/>
            <h5>Coupon Code</h5>
            <br/>
            <h3>aHs8xmJ</h3>
            <br/>
            <br/>
            <div class="orders">
                <ul>
                    <li>
                        <p class="name">Spaghetti Bolognese</p><p class="quantity">x5</p><p class="mealTime">(Lunch)</p>
                    </li>
                    <li>
                        <p class="name">Spaghetti Bolognese</p><p class="quantity">x5</p><p class="mealTime">(Lunch)</p>
                    </li>
                   <li>
                        <p class="name">Spaghetti Bolognese</p><p class="quantity">x5</p><p class="mealTime">(Lunch)</p>
                    </li>
                  <li>
                        <p class="name">Spaghetti Bolognese</p><p class="quantity">x5</p><p class="mealTime">(Lunch)</p>
                    </li>                
                </ul>
            </div>
            <br/>
            <p class="smallText">total credits cost</p>
            <br/>
            <p class="cost">1920</p>
        </div>
    </body>
</html>
