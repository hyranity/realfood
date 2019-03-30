<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Account</title>
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="CSS/recordInfo.css" rel="stylesheet">
        <link href="CSS/students.css" rel="stylesheet">
    </head>
    <body>
        <div class="outsideContainer">

            <h1 >My Account</h1>
            <h5 id="subtitle">Here's your account details.</h5>
                <br/>
                <div class="mainContainer">

                    <form action="#" class="form">
                        <div>
                            <input type="text" value="STU00294" style="background-color: darkgray;"  id="studentid"/>
                        </div>
                        <div id="nameDiv">
                            <input type="text" id="name" style="background-color: darkgray;" value="Michael" readonly />
                            <input type="text" id="name" style="background-color: darkgray;" value="McArthur" readonly/>
                        </div>
                        <div>
                            <input type="text" value="Male" style="background-color: darkgray;" id="gender" readonly />
                        </div>
                        <div>
                            <input type="text" value="Joined: 16 March, 2017" style="background-color: darkgray;" id="dateJoined" readonly />
                        </div>
                        <div>
                            <input type="text" value="michaelm@student.com" placeholder="Email" id="email" />
                        </div>
                        <div>
                            <input type="text" value="00392-12-5567" style="background-color: darkgray;" id="myKAD" readonly />
                        </div>
                        <div>
                            <input type="password" value="password" placeholder="Password" id="password" required/>
                        </div>
                        <div>
                            <input type="password" value="" placeholder="Confirmation Password" id="cPassword" required/>
                        </div>
                    </form>
                </div>
        </div>
        <div class="back" href="studentDashboard.jsp">back</div>
        <h6 class="credits">1000 credits</h6>
    </div>
</body>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Some javascript. It will dynamically change the subtitle based on what the student hovers over. -->
<script>
                                $(document).ready(function () {
                                    $("#studentid").hover(function(){
                                        $("#subtitle").html("That's your student ID. It uniquely defines you.");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. ");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#nameDiv").hover(function(){
                                        $("#subtitle").html("That's your name. It's you...right?");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. ");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#gender").hover(function(){
                                        $("#subtitle").html("That's your gender. Don't go changing genders now.");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. ");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#dateJoined").hover(function(){
                                        $("#subtitle").html("That's when you first joined. Having fun so far?");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. ");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#email").hover(function(){
                                        $("#subtitle").html("That's your email given by the school.");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. ");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#myKAD").hover(function(){
                                        $("#subtitle").html("That's your myKAD number. Don't show it to others.");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. ");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#password").hover(function(){
                                        $("#subtitle").html("That's your password. You may edit it with a new one.");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. ");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#cPassword").hover(function(){
                                        $("#subtitle").html("That's where you're supposed to type the confirmation password if you've edited your password..");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. ");
                                        $("#subtitle").css("color", "white");
                                    });
                                });
</script>
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
