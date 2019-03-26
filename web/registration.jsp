<%-- 
    Document   : registerStudent
    Created on : Mar 7, 2019, 9:51:48 AM
    Author     : mast3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <link rel="stylesheet" href="CSS/registration.css">
        <link  rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
           <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">

    </head>
    
    <header>
        <div class="left">
            <ul>
            <a href="#"  >Home</a>
            <a href="#"  class="left">Product & Service</a>
            <a href="#"  class="right" style="background-color: #ffffff; color: #000000">Log In</a>
            <a href="#"  class="right">Back</a>
            </ul>
        </div>
</header>
    
    <body>
        <!-- Main container -->
            
            
            <div class="container">           
                <div class="signupContent">
                    
                    <form method="POST" id="" class="">
                        <h1 style="text-align: center" >Registration</h1>
                        
                        <div class="formGroup">
                            <input type="text" class="formInput" id="userID" name="userID" placeholder="User ID" maxlength="10" required/>
                        </div>
                        
                        <div class="formGroup">
                            <input type="text" class="formInput" name="name" placeholder="Username" maxlength="50" required/>
                        </div>
                        
                        <div class="formGroup">
                            <input type="email" class="formInput" name="email" placeholder="Email" maxlength="30" required/>
                        </div>
                        
                        <div class="formGroup">
                            <input type="password" class="formInput" name="password" id="password" placeholder="Password" maxlength="20" required/>
                        </div>
                        
                        <div class="formGroup">
                            <input type="password" class="formInput" name="re_password" id="confirmationPassword" placeholder="Repeat your password" maxlength="20" required/>
                        </div>
                        
                        <div class="formGroup" >
                            <label style="text-align: center;"/><input type="checkbox" onclick="myFunction()" >Show Password </lable><br>
                            <input type="checkbox" onchange="document.getElementById('submit').disabled = !this.checked;" /><label class="termService"/>You Agree To Our<a href="#" class="clickHere"> Terms & service</a>                          
                        </div>
                        
                        <div class="formButton" style="text-align: center">
                            <input type="submit" name="submit" id="submit" class="submitButton" value="Sign up" style="text-align: center" disabled/>
                        </div>
                        
                    </form>
                    
                    <p class="loginhere">
                        <label>Already Have An Account ? </label><a href="#" class="clickHere">Login here</a>
                    </p>
                </div>
            </div>
    <!-- JS -->

    <script src="registration.js" type="text/javascript"></script>

    </body>
    
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
