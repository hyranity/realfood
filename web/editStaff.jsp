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

            String permission = "";

            try {
                permission = (String) session.getAttribute("permission");

                if (permission == null) {
                    request.setAttribute("errorMsg", "Please login.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }

            } catch (NullPointerException ex) {
                request.setAttribute("errorMsg", "Please login.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            // Allow staff only
            if (!permission.equalsIgnoreCase("manager")) {
                request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {
                Staff staff = new Staff();
                try {
                    staff = (Staff) session.getAttribute("staffForEdit");
                    staff.getFirstname();
                } catch (Exception ex) {
                    // If null, redirect
                    response.sendRedirect("DisplayStaffServlet");
                }
                String id = staff.getStaffid();
                String fname = staff.getFirstname();
                String lname = staff.getLastname();
                boolean isHired = staff.getIshired();
                String myKad = staff.getMykad();
                String dateJoined = Auto.dateToString(staff.getDatejoined());
                String dateDismissed = "";
                String email = staff.getEmail();
                String gender = "";

                //Gender setting
                if (staff.getGender() == 'M') {
                    gender = "Male";
                } else {
                    gender = "Female";
                }

                // Date dismissed setting
                try {
                    dateDismissed = Auto.dateToString(staff.getDatedismissed());
                    if (dateDismissed == null) {
                        dateDismissed = "not dismissed";
                    }
                } catch (NullPointerException e) {
                    dateDismissed = "not dismissed";
                }

                // If not hired, show that it is not
                if (staff.getIshired()) {
                    dateDismissed = "not dismissed";
                } else {
                    dateDismissed = "Dismissed: " + dateDismissed;
                }
        %>
        <div class="outsideContainer">

            <h1>Edit Canteen Staff</h1>
            <h5 id="subtitle">Here's where you can view and edit staff's details.</h5>
            <br/>
            <div class="mainContainer">

                <form action="#" class="form">
                    <%                        if (staff.getIshired()) {
                    %>
                    <a href="#" onclick="confirmtoggleDisable()"> <div class="toggleDisable" id="dismiss">Dismiss</div></a>
                    <%
                    } else {
                    %>
                    <a href="#" onclick="confirmtoggleDisable()"> <div class="toggleDisable" id="rehire">Re-hire</div></a>
                    <%}%>
                    <div>
                        <input type="text" value="<%=id%>" style="background-color: darkgray;"  id="staffid" readonly/>
                    </div>
                    <div id="nameDiv">
                        <input type="text" id="name" placeholder="First Name" value="<%=fname%>"  name="fname"/>
                        <input type="text" id="name" placeholder="Last Name" value="<%=lname%>" name="lname"/>
                    </div>
                    <div>
                        <input type="text" value="<%=gender%>" placeholder="Gender" id="gender"  name="gender"/>
                    </div>
                    <div>
                        <input type="text" value="<%=email%>" placeholder="Email" id="email" name="email"/>
                    </div>
                    <div>
                        <input type="text" value="<%=myKad%>" placeholder="MyKAD" id="myKAD" name="myKAD"/>
                    </div>
                    <div>
                        <input type="text" value="Joined: 16 March, 2017" id="dateJoined"  style="background-color: darkgray;"  readonly/>
                    </div>
                    <div>
                        <input type="text" value="<%=dateDismissed%>" id="dateDismissed"  style="background-color: darkgray; font-weight: 500;"  readonly/>
                    </div>
                    <div>
                        <input type="password"  placeholder="Manager Password" id="managerPassword" name="managerPassword" required/>
                    </div>
                    <br/>
                    <button type="submit" class="submitBtn">Submit changes</button>
                </form>
                <br/>
            </div>
        </div>
        <a href="DisplayStaffServlet"><div class="back">Back</div></a>
        < <div class="toggleDisableConfirmation">
            <%
                if (isHired) {
            %>
            <h5>Dismiss staff?</h5>
            <p>The staff will be dismissed and their access will be blocked from now on.</p>
            <%
            } else {
            %>
            <h5>Re-hire staff?</h5>
            <p>The staff will be re-hired and their access will be restored.</p>
            <%
                }
            %>
            <a href="ToggleStaffDismissal"><div class="toggleDisableConfirm">Yes</div></a>
            <a href="#"><div class="toggleDisableCancel">No</div></a>
        </div>
        <div class="coverOverlay"></div>
        <%}%>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Some javascript. It will dynamically change the subtitle based on what the staff hovers over. -->
    <script>
                        $(document).ready(function () {
                            $("#staffid").hover(function () {
                                $("#subtitle").html("That's the staff ID. It uniquely defines the staff. It can't be changed.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can view and edit staff's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#nameDiv").hover(function () {
                                $("#subtitle").html("That's the staff's name.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can view and edit staff's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#gender").hover(function () {
                                $("#subtitle").html("That's the staff's gender.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can view and edit staff's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#dateJoined").hover(function () {
                                $("#subtitle").html("That's when the staff's first joined. No point in editing it.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can view and edit staff's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#dateDismissed").hover(function () {
                                $("#subtitle").html("That's when the staff was dismissed. No point in editing it.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can view and edit staff's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#managerPassword").hover(function () {
                                $("#subtitle").html("Type your current password there if you would like to update any of the details.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's your account details. Hover over a field for more info. ");
                                $("#subtitle").css("color", "white");
                            });
                            $("#email").hover(function () {
                                $("#subtitle").html("That's the staff's email.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can view and edit staff's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#myKAD").hover(function () {
                                $("#subtitle").html("That's the staff's MyKAD number...you didn't mistype it, right?");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can view and edit staff's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $(".toggleDisable").hover(function () {
                                $("#subtitle").html("Dismiss the staff");
                                $("#subtitle").css("color", "red");
                            }, function () {
                                $("#subtitle").html("Here's where you can view and edit staff's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $(".toggleDisable").click(function confirmtoggleDisable() {
                                $(".toggleDisableConfirmation").css("display", "inline-block");
                                $(".outsideContainer :input").prop("disabled", true);
                            });
                            $(".toggleDisableCancel").click(function confirmtoggleDisable() {
                                $(".toggleDisableConfirmation").css("display", "none");
                                $(".outsideContainer :input").prop("disabled", false);
                            });<!--  The following code allows a "disabling" overlay -->
                            $(".toggleDisable").click(function () {
                                $(".coverOverlay").css("display", "block");
                                $(".toggleDisableConfirmation").css("z-index", "1");
                            });

                            $(".toggleDisableCancel").click(function () {
                                $(".coverOverlay").css("display", "none");
                                $(".toggleDisableConfirmation").css("z-index", "0");
                            });
                        });
    </script>
</html>
