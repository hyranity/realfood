<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Account</title>
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/studentProfile.css" rel="stylesheet">
    </head>
    <body>
        <div class="dashboardContainer">

            <h1>My Account</h1>
            <h5 id="subtitle">Here's your account details. You may only edit your password. Hover over a field to get more info.</h5>
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
                            <input type="text" value="Male" style="background-color: darkgray;" id="gender"  />
                        </div>
                        <div>
                            <input type="text" value="Joined: 16 March, 2017" style="background-color: darkgray;" id="dateJoined"  />
                        </div>
                        <div>
                            <input type="text" value="michaelm@student.com" style="background-color: darkgray;" id="email" />
                        </div>
                        <div>
                            <input type="text" value="00392-12-5567" style="background-color: darkgray;" id="myKAD" />
                        </div>
                        <div>
                            <input type="password" value="password" id="password"/>
                        </div>
                        <div>
                            <input type="password" value="" id="cPassword"/>
                        </div>
                    </form>
                </div>
        </div>
        <div class="logout">logout</div>
        <h6 class="credits">1000 credits</h6>
    </div>
</body>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Some javascript. It will dynamically change the subtitle based on what the student hovers over. -->
<script>
                                $(document).ready(function () {
                                    $("#studentid").hover(function(){
                                        $("#subtitle").html("That's your student ID. It uniquely defines you.");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                    });
                                    $("#studentid").hover(function(){
                                        $("#subtitle").html("Here's your student ID. It uniquely defines you.");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                    });
                                    $("#nameDiv").hover(function(){
                                        $("#subtitle").html("That's your name. It's you...right?");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                    });
                                    $("#lname").hover(function(){
                                        $("#subtitle").html("That's your last name.");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                    });
                                    $("#gender").hover(function(){
                                        $("#subtitle").html("That's your gender. Don't go changing genders now.");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                    });
                                    $("#dateJoined").hover(function(){
                                        $("#subtitle").html("That's when you first joined. Having fun so far?");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                    });
                                    $("#email").hover(function(){
                                        $("#subtitle").html("That's your email given by the school.");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                    });
                                    $("#myKAD").hover(function(){
                                        $("#subtitle").html("That's your myKAD number. Don't show it to others.");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                    });
                                    $("#password").hover(function(){
                                        $("#subtitle").html("That's your password. You may edit it with a new one.");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                    });
                                    $("#cPassword").hover(function(){
                                        $("#subtitle").html("That's where you're supposed to type the confirmation password if you've edited your password..");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                    });
                                });
</script>
</html>
