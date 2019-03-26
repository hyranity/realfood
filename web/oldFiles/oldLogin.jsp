<%-- 
    Document   : login
    Created on : Mar 7, 2019, 7:40:24 AM
    Author     : mast3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link  rel="stylesheet" href="CSS/oldLogin.css">
        <link  rel="stylesheet" href="CSS/headerFooter.css">
             <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <style type="text/css">
            
        </style>
        <title>Login</title>
    </head>
    
<header>
        <a href="#">RealFood</a>
        <div class="header-left">
            <ul>
            <a href="#">Home</a>
            <a href="#">Product & Service</a>
            <a href="#">Contact Us</a>
            <a href="#">About Us</a>
            <a href="#" class="right"><img src="Images/signUp.png" height="42" width="42">Sign Up</a>
            <a href="#" class="right"><img src="Images/logIn.png" height="42" width="42">Log In</a>
            
            </ul>
        </div>
</header>
    
    <body>
        <!-- Container of both staff and student sections -->

        <div class="loginContainer">
            <!-- Staff section -->
            <div class="staff">
                <p class="title">staff</p>
                <br>
                <form class="staffForm" action="">
                    <input type="text" class="textBox" placeholder="username">
                    <br>
                    <input type="password" class="textBox" placeholder="password">
                    <br>
                    <button type="submit" value="loginStaff">
                        login
                    </button>
                    <br><br>
                    <p class="signUpQuestion">
                        Don't have an account yet? You can <a href="hi">sign up here</a>
                    </p>
                </form>
            </div>

            <!-- Student section -->
            <div class="student">
                <p class="title">student</p>
                <br>
                <form class="studentForm" action="">
                    <input type="text" class="textBox" placeholder="username">
                    <br>
                    <input type="password" class="textBox" placeholder="password">
                    <br>
                    <button type="submit" value="loginStudent">
                        login
                    </button>
                    <br><br>
                    <p class="signUpQuestion">
                        Don't have an account yet? You can <a href="hi">sign up here</a>
                    </p>
                </form>
            </div>
        </div>
        
          
        <!-- End of container -->
        
        <!-- JQuery link code provided by https://www.w3schools.com/jquery/jquery_get_started.asp -->
        <!-- Thanks to Google for allowing me to use JQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="oldLogin.js" type="text/javascript"></script>
        
    </body>
    <footer class="footerContainer">
  
     <ul class="footerTop">
         <li><h1 class="footerHeader">Find Us</h1><hr width="75%"></li>
          <li><a href='#' class="footerWord">Testing</a></li>
     </ul>
  
      <ul class="footerTop">
      <li><h1 class="footerHeader">About Us</h1><hr width="75%"></li>
          <li><a href='#' class="footerWord">Testing</a></li>
          <li><a href='#' class="footerWord">Testing</a></li>
     </ul>
        
  
<section class="footerBottom">
<div class="footerBottom" style="border:none">   
 
Copyright©2019 RealFood - All Rights Reserved -
    </div>

  </section>
</footer>
</html>