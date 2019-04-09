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

            <h1>Edit Canteen Staff</h1>
            <h5 id="subtitle">Here's where you can view and edit staff's details.</h5>
            <br/>
            <div class="mainContainer">

                <form action="#" class="form">
                    <a href="#" onclick="confirmtoggleDisable()"> <div class="toggleDisable">Discontinue</div></a>
                    <div>
                        <input type="text" value="EMP002994" style="background-color: darkgray;"  id="staffid" readonly/>
                    </div>
                    <div id="nameDiv">
                        <input type="text" id="name" placeholder="First Name" value="Henry" />
                        <input type="text" id="name" placeholder="Last Name" value="Luther"/>
                    </div>
                    <div>
                        <input type="text" value="Male" placeholder="Gender" id="gender"  />
                    </div>
                    <div>
                        <input type="text" value="Joined: 16 March, 2017" id="dateJoined"  style="background-color: darkgray;"  readonly/>
                    </div>
                    <div>
                        <input type="text" value="henryluther@staff.com" placeholder="Email" id="email" />
                    </div>
                    <div>
                        <input type="text" value="3995-29-5344" placeholder="MyKAD" id="myKAD" />
                    </div>

                    <br/>
                    <button type="submit" class="submitBtn">Submit changes</button>
                </form>
                <br/>
            </div>
        </div>
        <a href="displayStaff.jsp"><div class="back">Back</div></a>
        <div class="toggleDisableConfirmation">
            <h5>Dismiss staff?</h5>
            <p>The staff will not be able to use the system anymore.</p>
            <a href="#"><div class="toggleDisableConfirm">Yes</div></a>
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
                        });
                        
        <!--  The following code allows a "disabling" overlay -->
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
