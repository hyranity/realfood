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
                // Allow staff only
                if(!permission.equalsIgnoreCase("staff")){
                     request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
        %>
        <div class="outsideContainer">

            <h1>Edit Food</h1>
            <h5 id="subtitle">Here's where you can view and edit food details.</h5>
            <br/>
            <div class="mainContainer">

                <form action="#" class="form">
                    <a href="#" onclick="confirmRemoval()"> <div class="removal">Discontinue</div></a>
                    <div>
                        <input type="text" value="F000001" style="background-color: darkgray;" placeholder="Food ID"  id="foodid" readonly/>
                    </div>
                    <div>
                        <input type="text" id="foodName" placeholder="Food Name" value="Chicken Wing" />
                    </div>
                    <div>
                        <input type="text" value="200" placeholder="Calories" id="calories" />
                    </div>
                    <div>
                        <input type="text" value="16 March, 2017" id="dateAdded"  style="background-color: darkgray;"  readonly/>
                    </div>
                    <div>
                        <input type="text" value="not discontinued" id="dateDiscontinued"  style="background-color: darkgray; font-weight: 500;"  readonly/>
                    </div>
                    
                    <br/>
                    <button type="submit" class="submitBtn">Submit changes</button>
                </form>
                <br/>
            </div>
        </div>
        <a href="displayStaff.jsp"><div class="back">Back</div></a>
        <div class="removalConfirmation">
            <h5>Discontinue food?</h5>
            <p>The food will be discontinued permanently.</p>
            <a href="#"><div class="removalConfirm">Yes</div></a>
            <a href="#"><div class="removalCancel">No</div></a>
        </div>
        <div class="coverOverlay"></div>
        <%}%>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Some javascript. It will dynamically change the subtitle based on what the user hovers over. -->
    <script>
                        $(document).ready(function () {
                            $("#foodid").hover(function () {
                                $("#subtitle").html("That's the food ID. It uniquely defines the food. It can't be changed.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can view and edit food's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#foodName").hover(function () {
                                $("#subtitle").html("That's the food's name.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can view and edit food's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#calories").hover(function () {
                                $("#subtitle").html("That's the number of calories in the food.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can view and edit food's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#dateAdded").hover(function () {
                                $("#subtitle").html("That's when the food was added. No point in editing it.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can view and edit food's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#dateDiscontinued").hover(function () {
                                $("#subtitle").html("That's when the food was discontinued. If it's not discontinued, it will show 'not discontinued'. ");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can view and edit food's details.");
                                $("#subtitle").css("color", "white");
                            });

                            $(".removal").hover(function () {
                                $("#subtitle").html("Discontinue the food");
                                $("#subtitle").css("color", "red");
                            }, function () {
                                $("#subtitle").html("Here's where you can view and edit food's details.");
                                $("#subtitle").css("color", "white");
                            });

                            $(".removal").click(function confirmRemoval() {
                                $(".removalConfirmation").css("display", "inline-block");
                                $(".outsideContainer :input").prop("disabled", true);
                            });

                            $(".removalCancel").click(function confirmRemoval() {
                                $(".removalConfirmation").css("display", "none");
                                $(".outsideContainer :input").prop("disabled", false);
                            });

<!--  The following code allows a "disabling" overlay -->
                            $(".removal").click(function () {
                                $(".coverOverlay").css("display", "block");
                                $(".removalConfirmation").css("z-index", "1");
                            });

                            $(".removalCancel").click(function () {
                                $(".coverOverlay").css("display", "none");
                                $(".removalConfirmation").css("z-index", "0");
                            });
                        });
    </script>
</html>
