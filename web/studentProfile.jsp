<%@page import="util.Auto"%>
<%@page import="Model.Student"%>
<%@page import="Model.*"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Account</title>

        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/recordInfo.css" rel="stylesheet">
        <link href="CSS/students.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
    </head>
    <body>
        <%
            session = request.getSession(false);
            
            String permission = (String) session.getAttribute("permission");
            
            if (permission == null) {
                request.setAttribute("errorMsg", "Please login.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            else {
                Student stud = (Student) session.getAttribute("stud");
                int credits = stud.getCredits();    // Obtain student's credits amount
                
                // Allow manager only
                if(!permission.equalsIgnoreCase("student")){
                     request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
               
            } 
                Student student = (Student) session.getAttribute("stud");
                String id = student.getStudentid();
                String fname = student.getFirstname();
                String lname = student.getLastname();
                String myKad = student.getMykad();
                String dateJoined = Auto.dateToString(student.getDatejoined());
                String email = student.getEmail();
                String gender = "";

                //Gender setting
                if (student.getGender() == 'M') {
                    gender = "Male";
                } else {
                    gender = "Female";
                }
        %>
        <div class="outsideContainer">

            <h1 >My Account</h1>
            <h5 id="subtitle">Here's your account details. Hover over a field for more info.</h5>
            <div class="errorMsg">${errorMsg}</div>
            <div class="successMsg">${successMsg}</div>
            <br/>
            <div class="mainContainer">

                <form action="StudentAccountManagement" class="form">
                    <div>
                        <label>Student ID</label><br/>
                        <input type="text" value="<%=id%>" style="background-color: darkgray;"  id="studentid"/>
                    </div>
                    <div id="nameDiv">
                        <label>Username</label><br/>
                        <input type="text" id="name" style="background-color: darkgray;" value="<%=fname%>" readonly />
                        <input type="text" id="name" style="background-color: darkgray;" value="<%=lname%>" readonly/>
                    </div>
                    <div>
                        <label>Gender</label><br/>
                        <input type="text" value="<%=gender%>" style="background-color: darkgray;" id="gender" readonly />
                    </div>
                    <div>
                        <label>Date Joined</label><br/>
                        <input type="text" value="Joined: <%=dateJoined%>" style="background-color: darkgray;" id="dateJoined" readonly />
                    </div>
                    <div>
                        <label>Email</label><br/>
                        <input type="text" value="<%=email%>" placeholder="Email" id="email" name="email"/>
                    </div>
                    <div>
                        <label>MyKad Number</label><br/>
                        <input type="text" value="<%=myKad%>" style="background-color: darkgray;" id="myKAD" readonly />
                    </div>
                    <div>
                        <label>Old Password</label><br/>
                        <input type="password"  placeholder="Current Password" minlength="6" maxlength="20" id="currentPassword" name="currentPassword" required/>
                    </div>
                    <div>
                        <label>New Password</label><br/>
                        <input type="password" placeholder="New Password" minlength="6" maxlength="20" id="password" name="password"/>
                    </div>
                    <div>
                        <label>Confirm Password</label><br/>
                        <input type="password"  placeholder="Confirmation Password" minlength="6" maxlength="20" id="cPassword" name="cPassword" />
                    </div>
                    
                    <input type="submit" value="Save changes" class="submitBtn">
                </form>
            </div>
        </div>
                    <a style="display: block; margin-bottom: 20px;" href="LoadStudentDashboard"><div class="back">Back</div></a>
             <!-- Display student's credits -->
            <h6 class="credits"><%=credits%> credits</h6>
    </div>
    <%
        }
    %>
</body>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Some javascript. It will dynamically change the subtitle based on what the student hovers over. -->
<script>
    $(document).ready(function () {
        $("#studentid").hover(function () {
            $("#subtitle").html("That's your student ID. It uniquely defines you.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. Hover over a field for more info. ");
            $("#subtitle").css("color", "white");
        });
        $("#nameDiv").hover(function () {
            $("#subtitle").html("That's your name. It's you...right?");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. Hover over a field for more info. ");
            $("#subtitle").css("color", "white");
        });
        $("#gender").hover(function () {
            $("#subtitle").html("That's your gender. Don't go changing genders now.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. Hover over a field for more info. ");
            $("#subtitle").css("color", "white");
        });
        $("#dateJoined").hover(function () {
            $("#subtitle").html("That's when you first joined. Having fun so far?");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. Hover over a field for more info. ");
            $("#subtitle").css("color", "white");
        });
        $("#email").hover(function () {
            $("#subtitle").html("That's your email given by the school.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. Hover over a field for more info. ");
            $("#subtitle").css("color", "white");
        });
        $("#myKAD").hover(function () {
            $("#subtitle").html("That's your myKAD number. Don't show it to others.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. Hover over a field for more info. ");
            $("#subtitle").css("color", "white");
        });
        $("#password").hover(function () {
            $("#subtitle").html("That's your password. You may edit it with a new one.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. Hover over a field for more info. ");
            $("#subtitle").css("color", "white");
        });
        $("#cPassword").hover(function () {
            $("#subtitle").html("That's where you're supposed to type the confirmation password if you've edited your password..");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. Hover over a field for more info. ");
            $("#subtitle").css("color", "white");
        });
        $("#currentPassword").hover(function () {
            $("#subtitle").html("Type your current password there if you would like to update any of the details.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. Hover over a field for more info. ");
            $("#subtitle").css("color", "white");
        });
    });
</script>
</html>
