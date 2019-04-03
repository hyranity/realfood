<%@page import="util.Auto"%>
<%@page import="Model.Staff"%>
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
            // If user is not logged in, redirect to login page
            if (session.getAttribute("staff") == null) {
                request.setAttribute("errorMsg", "Please login.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                Staff staff = (Staff) session.getAttribute("staff");
                String id = staff.getStaffid();
                String fname = staff.getFirstname();
                String lname = staff.getLastname();
                boolean isHired = staff.getIshired();
                String myKad = staff.getMykad();
                String dateJoined = Auto.dateToString(staff.getDatejoined());
                String email = staff.getEmail();
                String gender = "";

                //Gender setting
                if (staff.getGender() == 'M') {
                    gender = "Male";
                } else {
                    gender = "Female";
                }
        %>
        <div class="outsideContainer">

            <h1>My Account</h1>
            <h5 id="subtitle">Here's your account details. You may only edit your password. Hover over a field to get more info.</h5>
            <br/>
            <div class="mainContainer">

                <form action="#" class="form">
                    <div>
                        <input type="text" value="<%=id%>" style="background-color: darkgray;"  id="empId"/>
                    </div>
                    <div id="nameDiv">
                        <input type="text" id="name" style="background-color: darkgray;" value="<%=fname%>" readonly />
                        <input type="text" id="name" style="background-color: darkgray;" value="<%=lname%>" readonly/>
                    </div>
                    <div>
                        <input type="text" value="<%=gender%>" style="background-color: darkgray;" id="gender"  readonly/>
                    </div>
                    <div>
                        <input type="text" value="Joined: <%=dateJoined%>" style="background-color: darkgray;" id="dateJoined" readonly />
                    </div>
                    <div>
                        <input type="text" value="<%=email%>" style="background-color: darkgray;" id="staffEmail" readonly />
                    </div>
                    <div>
                        <input type="text" value="<%=myKad%>" style="background-color: darkgray;" id="myKAD" readonly />
                    </div>
                    <div>
                        <input type="password" value="" placeholder="Password" id="password" required/>
                    </div>
                    <div>
                        <input type="password" value="" placeholder="Confirmation Password" id="cPassword" required/>
                    </div>
                    <button type="submit" class="submitBtn">Update password</button>
                </form>
            </div>
        </div>
                    <a href="dashboardCanteenStaff.jsp"><div class="back">Back</div></a>
    </div>
    <%
        }
    %>
</body>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Some javascript. It will dynamically change the subtitle based on what the student hovers over. -->
<script>
    $(document).ready(function () {
        $("#empId").hover(function () {
            $("#subtitle").html("That's your student ID. It uniquely defines you.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password.");
            $("#subtitle").css("color", "white");
        });
        $("#nameDiv").hover(function () {
            $("#subtitle").html("That's your name. It's you...right?");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password.");
            $("#subtitle").css("color", "white");
        });
        $("#gender").hover(function () {
            $("#subtitle").html("That's your gender. Don't go changing genders now.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password.");
            $("#subtitle").css("color", "white");
        });
        $("#dateJoined").hover(function () {
            $("#subtitle").html("That's when you first joined. Having fun so far?");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password.");
            $("#subtitle").css("color", "white");
        });
        $("#staffEmail").hover(function () {
            $("#subtitle").html("That's your email given by higher management.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password.");
            $("#subtitle").css("color", "white");
        });
        $("#myKAD").hover(function () {
            $("#subtitle").html("That's your myKAD number. Don't show it to others.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password.");
            $("#subtitle").css("color", "white");
        });
        $("#password").hover(function () {
            $("#subtitle").html("That's your password. You may edit it with a new one.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password.");
            $("#subtitle").css("color", "white");
        });
        $("#cPassword").hover(function () {
            $("#subtitle").html("That's where you're supposed to type the confirmation password if you've edited your password..");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password.");
            $("#subtitle").css("color", "white");
        });
    });
</script>
</html>
