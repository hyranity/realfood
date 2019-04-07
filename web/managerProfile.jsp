<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Account</title>
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/recordInfo.css" rel="stylesheet">
    </head>
    <body>
          <%
            session = request.getSession(false);
            
            String permission = (String) session.getAttribute("permission");
            
            // If user is not logged in, redirect to login page
            if (permission == null) {
                request.setAttribute("errorMsg", "Please login.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            else {
                // Allow manager only
                if(!permission.equalsIgnoreCase("manager")){
                     request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
        %>
        <div class="outsideContainer">

            <h1>My Account</h1>
            <h5 id="subtitle">Here's your account details. You may only edit your password. Hover over a field to get more info.</h5>
                <br/>
                <div class="mainContainer">

                    <form action="#" class="form">
                        <div>
                            <input type="text" value="EMP20949" id="empId"/>
                        </div>
                        <div id="nameDiv">
                            <input type="text" id="name" value="Michael" required />
                            <input type="text" id="name" value="McArthur" required/>
                        </div>
                        <div>
                            <input type="text" value="Male" id="gender"  required/>
                        </div>
                        <div>
                            <input type="text" value="Joined: 16 March, 2017"id="dateJoined" required />
                        </div>
                        <div>
                            <input type="text" value="bolt@staff.com" id="staffEmail" required />
                        </div>
                        <div>
                            <input type="text" value="00392-12-5567" id="myKAD" required />
                        </div>
                        <div>
                            <input type="password" value="password" placeholder="Password" id="password" required/>
                        </div>
                        <div>
                            <input type="password" value="" placeholder="Confirmation Password" id="cPassword" required/>
                        </div>
                        <button type="submit" class="submitBtn">Submit changes</button>
                    </form>
                </div>
        </div>
        <div class="back" href="dashboardStaff.jsp">Back</div>

    <%}%>
</body>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Some javascript. It will dynamically change the subtitle based on what the student hovers over. -->
<script>
                                $(document).ready(function () {
                                    $("#empId").hover(function(){
                                        $("#subtitle").html("That's your student ID. It uniquely defines you.");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#nameDiv").hover(function(){
                                        $("#subtitle").html("That's your name. It's you...right?");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#gender").hover(function(){
                                        $("#subtitle").html("That's your gender. Don't go changing genders now.");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#dateJoined").hover(function(){
                                        $("#subtitle").html("That's when you first joined. Having fun so far?");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#staffEmail").hover(function(){
                                        $("#subtitle").html("That's your email given by higher management.");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#myKAD").hover(function(){
                                        $("#subtitle").html("That's your myKAD number. Don't show it to others.");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#password").hover(function(){
                                        $("#subtitle").html("That's your password. You may edit it with a new one.");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                        $("#subtitle").css("color", "white");
                                    });
                                    $("#cPassword").hover(function(){
                                        $("#subtitle").html("That's where you're supposed to type the confirmation password if you've edited your password..");
                                        $("#subtitle").css("color", "gold");
                                    }, function(){
                                        $("#subtitle").html("Here's your account details. You may only edit your password.");
                                        $("#subtitle").css("color", "white");
                                    });
                                });
</script>
</html>
