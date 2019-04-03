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
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
           <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">

    </head>
    
    <header>
        <div>
            <ul>
                <a href="#"  class="right"">Login</a>
                <a href="#"  class="right" style="background-color: #ffffff; color: #000000;">Register</a>
                <a href="#"  class="right"">Student</a>
                <a href="#"  class="right">Home</a>
            </ul>
        </div>
</header>
    
    <body>
        <!-- Main container -->
            
            
            <div class="container">           
                <div class="signupContent">
                    
                    <form action="RegistrationServlet" method="POST" id="" class="">
                        <h1 style="text-align: center" >Staff Registration</h1>
                        <h5>Fill in the blanks with your personal details.</h5>
                        
                        <div class="formGroup">
                            <input type="text" class="formInput" id="userID" name="staffID" placeholder="User ID (or existing Student ID)" maxlength="10" required/>
                        </div>
                        
                        <div class="formGroup">
                            <input type="text" class="formInput2" name="fname" placeholder="First Name" maxlength="25" required/>
                            <input type="text" class="formInput2" name="lname" placeholder="Last Name" maxlength="25" required/>
                        </div>
                        
                        <div class="formGroup">
                            <select class="formInput">
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                            </select>
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
                        
                        <!---->
                        
                        <div class="formGroup">
                            <input type="text" class="formInput" name="myKad" id="myKAD" placeholder="MyKAD" maxlength="12" required/>
                        </div>
                    
                        <!---->
                        
                        <div class="formGroup" >
                            <input type="checkbox" onclick="myFunction()" id="showPass" /><label style="text-align: center;" for="showPass" id="showPassLabel">Click here to show password</label><br>
                            <input type="checkbox" onchange="document.getElementById('submit').disabled = !this.checked;" id="acceptTerm"/><label class="termService" for="acceptTerm">Click here to Agree To Our <a style="color: white;">Terms & Conditions</a> </label>
                        </div>
                        
                        <div class="formButton" style="text-align: center">
                            <input type="submit" name="submit" id="submit" class="submitButton" value="Sign up" style="text-align: center" disabled/>
                        </div>
                        
                    </form>
                    
                    <p class="loginhere" style="color: gold;">
                        Already Have An Account? <a href="#" class="clickHere">Login here</a>
                    </p>
                </div>
            </div>
    <!-- JS -->
 <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <script src="registration.js" type="text/javascript"></script>
    <script>
        
        </script>
    </body>
</html>
