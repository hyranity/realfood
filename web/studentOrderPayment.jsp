<%-- 
    Document   : payment
    Created on : Mar 30, 2019, 3:26:59 PM
    Author     : Richard Khoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/studentDisplayMeals.css" rel="stylesheet">
        <link href="CSS/payment.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <link href="CSS/recordInfo.css" rel="stylesheet">
        <link href="CSS/students.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment.</title>
    </head>
    <body>
        <div class="stepsContainer">
            <h1>steps</h1>
            <div class="steps">
                <div>1. Select a date</div>
                <div>2. Choose a meal</div>
                <div>3. Edit particulars</div>
                <div class="currentStep">4. Payment</div>
            </div>
        </div>
                    <div class="container">
        <form action="">

                <h1 style="text-align: center" >You total is RM xxxx </h1>
                        
                        <div class="formGroup">
                            <input type="text" value="STU123456" class="formInput" id="userID" name="userID" style="background-color: darkgray;" placeholder="User ID" maxlength="10" readonly/>
                        </div>
                        
                        <div class="formGroup">
                            <input type="password" value="123456" class="formInput" name="oassword" id="password" placeholder="Password" maxlength="20" required/>
                        </div>
            
            <div style="display: inline-block; text-align: center; border-radius: 50px;">
            <button class="nextButton" href="" type="submit" >Back</button>
            </div>
            
            <div style="display: inline-block; text-align: center; border-radius: 50px;">
            <button class="nextButton" href="" type="submit" >PAY XXXX CP</button>
            </div>
        </form>
        <h6 class="credits">1000 credits</h6>
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

