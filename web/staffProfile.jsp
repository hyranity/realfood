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
        <link href="CSS/commonStyles.css" rel="stylesheet">
    </head>
    <body>
        <%

            session = request.getSession(false);
            
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
            <div class="errorMsg">${errorMsg}</div>
            <div class="successMsg">${successMsg}</div>
            <br/>
            <div class="mainContainer">

                <form action="CanteenStaffAccountEdit" class="form">
                    <div>
                        <label>Staff ID</label><br/>
                        <input type="text" value="<%=id%>" style="background-color: darkgray;"  id="empId"/>
                    </div>
                    <div id="nameDiv">
                        <label>Username</label><br/>
                        <input type="text" id="name" style="background-color: darkgray;" value="<%=fname%>" readonly />
                        <input type="text" id="name" style="background-color: darkgray;" value="<%=lname%>" readonly/>
                    </div>
                    <div>
                        <label>Gender</label><br/>
                        <input type="text" value="<%=gender%>" style="background-color: darkgray;" id="gender"  readonly/>
                    </div>
                    <div>
                        <label>Date Joined</label><br/>
                        <input type="text" value="Joined: <%=dateJoined%>" style="background-color: darkgray;" id="dateJoined" readonly />
                    </div>
                    <div>
                        <label>Email</label><br/>
                        <input type="text" value="<%=email%>" style="background-color: darkgray;" id="staffEmail" readonly />
                    </div>
                    <div>
                        <label>MyKad Number</label><br/>
                        <input title="xxxx-xx-xxxx" required pattern="[0-9]{6}-[0-9]{2}-[0-9]{4}" value="<%=myKad%>" style="background-color: darkgray;" id="myKAD" readonly />
                    </div>
                    <div>
                        <label>Old Password</label><br/>
                        <input type="password"  placeholder="Current Password" minlength="6" maxlength="20" id="currentPassword" name="currentPassword" required/>
                    </div>
                    <div>
                        <label>New Password</label><br/>
                        <input type="password" value="" placeholder=" New Password" id="password" minlength="6" maxlength="20" name="password" required/>
                    </div>
                    <div>
                        <label>Confirm Password</label><br/>
                        <input type="password" value="" placeholder="Confirmation Password" minlength="6" maxlength="20" id="cPassword" name="cPassword" required/>
                    </div>
                    
                    <input type="submit" value="Update password" class="submitBtn">
                </form>
            </div>
        </div>
        <a style="display: block; margin-bottom: 20px;" href="dashboardCanteenStaff.jsp"><div class="back">Back</div></a>
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
            $("#subtitle").html("Here's your account details. You may only edit your password. Hover over a field to get more info.");
            $("#subtitle").css("color", "white");
        });
        $("#nameDiv").hover(function () {
            $("#subtitle").html("That's your name. It's you...right?");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password. Hover over a field to get more info.");
            $("#subtitle").css("color", "white");
        });
        $("#gender").hover(function () {
            $("#subtitle").html("That's your gender. Don't go changing genders now.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password. Hover over a field to get more info.");
            $("#subtitle").css("color", "white");
        });
        $("#dateJoined").hover(function () {
            $("#subtitle").html("That's when you first joined. Having fun so far?");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password. Hover over a field to get more info.");
            $("#subtitle").css("color", "white");
        });
        $("#staffEmail").hover(function () {
            $("#subtitle").html("That's your email given by higher management.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password. Hover over a field to get more info.");
            $("#subtitle").css("color", "white");
        });
        $("#myKAD").hover(function () {
            $("#subtitle").html("Here's your account details. You may only edit your password. Hover over a field to get more info.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password. Hover over a field to get more info.");
            $("#subtitle").css("color", "white");
        });
        $("#password").hover(function () {
            $("#subtitle").html("That's your password. You may edit it with a new one.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password. Hover over a field to get more info.");
            $("#subtitle").css("color", "white");
        });
        $("#cPassword").hover(function () {
            $("#subtitle").html("That's where you're supposed to type the confirmation password if you've edited your password.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password. Hover over a field to get more info.");
            $("#subtitle").css("color", "white");
        });
        $("#currentPassword").hover(function () {
            $("#subtitle").html("Type your old password there if you would like to update your password.");
            $("#subtitle").css("color", "gold");
        }, function () {
            $("#subtitle").html("Here's your account details. You may only edit your password. Hover over a field to get more info.");
            $("#subtitle").css("color", "white");
        });
    });
</script>
</html>
