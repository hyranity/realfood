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
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="CSS/commonStyles.css">

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <header>
        <div>
            <ul>
                <a href="login.jsp"  class="right" style="background-color: #ffffff; color: #000000;">Login</a>
                <a href="studentRegistration.jsp"  class="right">Register</a>
                <a href="home.jsp"  class="right">Home</a>
            </ul>
        </div>
    </header>

    <body>
        <%
            // To ensure that people only login either as staff or student, not both.
            session.invalidate();
            %>
        <div class="formContainer">
            <div class="formInnerContainer">
                
                <!-- Error message -->
                <div class="errorMsg">${errorMsg}</div>
                <!-- Account message -->
                <div class="accountMsg">${accountMsg}</div>
                <!-- Login part -->
                <h1 id="title">Login</h1>
                <form method="POST" action="LoginServlet">
                    <input name="id" id="id" type="text" placeholder="ID" onchange="updateHeading()" required>
                    <br>
                    <input name="password" type="password" placeholder="password" required>
                    <button type="submit">let's go</button>

                    <p class="passwordForgot">I forgot my password. <a href="#" id="forgotPasswordTrigger" onclick="">HELP</a></p>
                </form>
            </div>
        </div>
        <div class="coverOverlay"></div>

        <!-- Reset password overlay -->
        <div class="forgotPasswordOverlay">
            <form action="" method="POST">
                <div class='closeBt'>x</div>
                <h2>Stay calm! Help is here.        </h2>
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
</html>
