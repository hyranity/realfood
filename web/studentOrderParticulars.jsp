<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/quantityModification.css" rel="stylesheet">
        <link href="CSS/students.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <title>Edit Particulars</title>
    </head>
    <body>
        <div class="stepsContainer">
            <h1>steps</h1>
            <div class="steps">
                <div>1. Select a date</div>
                <div>2. Choose a meal</div>
                <div class="currentStep">3. Edit particulars</div>
                <div>4. Payment</div>
            </div>
        </div>

        <h1 class="title">Edit particulars</h1>
        <h5 id="subtitle">Modify the quantity of your meals.</h5>

        <div class="mainContainer">
            <div class="recordQuantity">
                <div class="frontPart">
                    <p class="name">Spaghetti Bolognese</p>

                </div>
                <div class="quantityEditor">
                    <p class="value">1500 Credits</p>
                    <p class="symbol" id="sub">-</p>
                    <p class="quantity" data-quantity="5">x2</p>
                    <p class="symbol" id="add">+</p>
                </div>
            </div>
            <br/>
            <div class="recordQuantity">
                <div class="frontPart">
                    <p class="name">Peppermint Ice Cream</p>
                </div>
                <div class="quantityEditor">
                    <p class="value">500 Credits</p>
                    <p class="symbol" id="sub">-</p>
                    <p class="quantity" data-quantity="5">x23</p>
                    <p class="symbol" id="add">+</p>
                </div>
            </div>
            <br/>
            <div class="total">
                <p>Total: 4000 Credits</p>
            </div>
            <h6 class="credits">1000 credits</h6>
        </div>
        <div class="nextButtonDiv">
            <button class="nextButton">Next step</button>
        </div>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <!--Footer-->
    <footer class="footerContainer" style="margin-top: 200px;">
         
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