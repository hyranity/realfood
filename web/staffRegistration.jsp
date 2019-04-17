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
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="CSS/commonStyles.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
           <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">

    </head>
    
<header>

</header>
    
    <body>
        <!-- Main container -->
            
            
            <div class="container">           
                <div class="errorMsg">${errorMsg}</div>
                <div class="signupContent">
                    
                    <form action="RegistrationServlet" method="POST" >
                        <h1 style="text-align: center" >Staff Registration</h1>
                        <h5>Fill in the blanks with your personal details.</h5>
                        
                        <!-- User type: Staff -->
                    <input type="hidden" value="staff" name="userType"/>
                        
                        <div class="formGroup">
                            <input type="text" class="formInput2" name="fname" placeholder="First Name" maxlength="25" required/>
                            <input type="text" class="formInput2" name="lname" placeholder="Last Name" maxlength="25" required/>
                        </div>
                        
                        <div class="formGroup">
                            <select class="formInput" name="gender">
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                            </select>
                        </div>
                        
                        <div class="formGroup">
                            <input type="email" class="formInput" name="email" placeholder="Email" maxlength="30" required/>
                        </div>
                        
                        <div class="formGroup">
                            <input type="password" class="formInput" name="password" id="password" placeholder="Password" minlength="6"  maxlength="20" required/>
                        </div>
                        
                        <div class="formGroup">
                            <input type="password" class="formInput" name="re_password" id="confirmationPassword" placeholder="Repeat your password" minlength="6"  maxlength="20" required/>
                        </div>
                        
                        <!---->
                        
                        <div class="formGroup">
                            <input title="Please Follow as xxxxxxxxxxxx" required pattern="[0-9]{6}[0-9]{2}[0-9]{4}" class="formInput" name="myKad" id="myKAD" placeholder="MyKAD" required/>
                        </div>
                    
                        <!---->
                        
                        <div class="formGroup" >
                            <input type="checkbox" onclick="myFunction()" id="showPass" /><label style="text-align: center;" for="showPass" id="showPassLabel">Click here to show password</label><br>
                            
                        </div>
                        
                        <div class="formButton" style="text-align: center;">
                            <input type="submit" name="submit" id="submit" class="submitButton" value="Create Account" style="text-align: center; cursor: pointer;" /><br/>
                            <a href="dashboardManager.jsp"><div class="submitButton" style="margin-top: 16px; margin-left: 134px;">Return To DashBoard</div></a>
                            
                        </div>
                        
                    </form>
                    
                </div>
            </div>
    <!-- JS -->
 <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <script src="registration.js" type="text/javascript"></script>
    <script>
        
        </script>
    </body>
</html>
