<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.*"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Account</title>
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/recordInfo.css" rel="stylesheet">
        <link href="CSS/students.css" rel="stylesheet">
        <link href="CSS/studentStats.css" rel="stylesheet">
    </head>
    <body>
         <%
            session = request.getSession(false);
            Student stud = new Student();
            
            String permission = (String) session.getAttribute("permission");
            
            // If user is not logged in, redirect to login page
            if (permission == null) {
                request.setAttribute("errorMsg", "Please login.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            else {
                
                int credits = stud.getCredits();    // Obtain student's credits amount
                // Allow student only
                if(!permission.equalsIgnoreCase("student")){
                     request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
        %>
        <div class="outsideContainer" >

            <h1 >My Statistics</h1>
            <h5 id="subtitle">Here's some interesting facts about you this past week.</h5>
                <br/>
                <div class="mainContainer">
                    
                    <div class="sectionContainer" style="border-color: darkgoldenrod">
                        <p>You've consumed about</p>
                        <h3 class="number" style="color: darkgoldenrod">12039</h3>
                        <p>calories</p>
                    </div>
                    
                    <div class="sectionContainer" style="border-color: darkred;">
                        <p>You've spent about</p>
                        <h3 class="number" style="color: darkred;">18412</h3>
                        <p>credits</p>
                    </div>
                    
                    <div class="sectionContainer" style="border-color: darkcyan;">
                        <p>You've ordered</p>
                        <h3 class="number" style="color: darkcyan;">13</h3>
                        <p>meals</p>
                    </div>
                    
                </div>
                <div class="back" href="studentDashboard.jsp">Back<br/><br/></div>
        </div>
        
        <h6 class="credits">1000 credits</h6>
        <%}%>
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
</html>
