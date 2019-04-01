<%-- 
    Document   : login
    Created on : Mar 16, 2019, 3:49:28 PM
    Author     : mast3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

        <link rel="stylesheet" href="CSS/login.css">
        <link rel="stylesheet" href="CSS/headerFooter.css">
        
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="CSS/commonStyles.css">

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
        <div class="formContainer">
            <div class="formInnerContainer">
                
                <!-- Error message -->
                <div class="errorMsg"></div>
                
                <!-- Login part -->
                <h1 id="title">Login</h1>
                <form>
                    <input name="id" id="id" type="text" placeholder="ID" onchange="updateHeading()" required>
                    <br>
                    <input name="password" type="password" placeholder="password" required>
                    <button type="submit">let's go</button>

                    <p class="passwordForgot">I forgot my password. <a href="#" id="forgotPasswordTrigger">HELP</a></p>
                </form>
            </div>
        </div>
        <div class="coverOverlay"></div>

        <!-- Reset password overlay -->
        <div class="forgotPasswordOverlay">
            <form action="" method="POST">
                <div class='closeBt'>x</div>
                <h2>Stay calm!</h2>
                <h5>Please enter your personal email that you've used to register your account. We'll email you a link which you can click to reset your password.</h5>
                <input type="text" placeholder="email">
                <br/>
                <a href=""><div class='resetBt'>Send reset link</div></a>
            </form>
        </div>

    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="login.js" type="text/javascript"></script>
    <script>
<!--  The following code allows a "disabling" overlay -->
                       $(document).ready(function() {
                       $("#forgotPasswordTrigger").click(function () {
                        $(".coverOverlay").css("display", "block");
                        $(".forgotPasswordOverlay").css("display","block");
                                $(".forgotPasswordOverlay").css("z-index", "1");
                        });
                                $(".closeBt").click(function () {
                        $(".coverOverlay").css("display", "none");
                                $(".forgotPasswordOverlay").css("z-index", "0");
                                $(".forgotPasswordOverlay").css("display","none");
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
