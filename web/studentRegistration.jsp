<%-- 
    Document   : studentRegistration
    Created on : Apr 1, 2019, 8:18:34 PM
    Author     : Richard Khoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <link rel="stylesheet" href="CSS/registration.css">
        <link  rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="CSS/commonStyles.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
            <!-- Error message -->
        <div class="errorMsg">${errorMsg}</div>
            <div class="signupContent">
                
                

                <form action="RegistrationServlet" method="POST">
                    <h1 style="text-align: center" >Student Registration</h1>
                    <h5>Fill in the blanks with your personal details.</h5>

                    <!-- User type: Student -->
                    <input type="hidden" value="student" name="userType"/>
                    
                    <div class="formGroup">
                        <input type="text" class="formInput" id="userID" name="id" placeholder="Student ID (or existing Student ID)" maxlength="10" required/>
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
                        <input type="checkbox" onclick="myFunction()" id="showPass" /><label style="text-align: center;" for="showPass" id="showPassLabel">Click here to show password</label><br>
                        <input type="checkbox" onchange="document.getElementById('submit').disabled = !this.checked;" id="acceptTerm"/><label class="termService" for="acceptTerm">Click here to Agree To Our <a style="color: white;">Terms & Conditions</a> </label>
                    </div>

                    <div class="formButton" style="text-align: center">
                        <input type="submit" name="submit" id="submit" class="submitButton" value="Sign up" style="text-align: center"/>
                    </div>

                </form>

                <p class="loginhere" style="color: gold;">
                    Already Have An Account? <a href="login.jsp" class="clickHere">Login here</a>
                </p>
            </div>
        </div>
    </body>
    <!-- JS -->
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <script src="registration.js" type="text/javascript"></script>
    <script>

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

