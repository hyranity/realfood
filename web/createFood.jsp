<%-- 
    Document   : createFood
    Created on : Apr 4, 2019, 9:10:37 AM
    Author     : Richard Khoo
--%>

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
        <link href="CSS/createFood.css" rel="stylesheet">
    </head>
    <body>
        <%
            session = request.getSession(false);

            String permission = (String) session.getAttribute("permission");

            
            if (permission == null) {
                request.setAttribute("errorMsg", "Please login.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {
                // Allow staff only
                if (!permission.equalsIgnoreCase("canteenStaff") && !permission.equals("manager")) {
                    request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
        %>
        <div class="outsideContainer">
            <!-- Show alerts if any -->
            
            <h1>Create Food</h1>
            <br/>
             <h5 id="subtitle">Here's where you can Create Food Details.</h5>
             <br/>
            <div class="successMsg">${successMsg}</div>
            <br/>
            <div class="errorMsg">${errorMsg}</div>      
            <br/>
            <div class="mainContainer">

                <form action="CreateFoodServlet" method="POST" class="form" id="createForm">

                    <div>
                        <input type="text" id="foodName" placeholder="Food Name" value="" name="foodName" maxlength="20" required/>
                    </div>
                    <div>   
                        <input type="number" value="" placeholder="Calories" id="calories" name="calories" oninput="validity.valid || (value = '');" min="0" step="1" maxlength="4" required/>
                    </div>
                    
                    <br/>
                    <a href="#" onclick="confirmadding()"><div class="adding">Create</div></a>
                    <div class="addingConfirmation" style="margin-left: 32%">
                        <h5>Creating food?</h5>
                        <p>The Food will be created. Do You Want To Proceed?</p>
                        <input type="submit" value="Yes" style="color: green;cursor: pointer; z-index: 1; border:1px solid gold; border-radius: 10px; font-weight: bold; padding:4px; margin:10px;" form="createForm" >
                        <a href="#"><div class="addingCancel">No</div></a>
                    </div>
                </form>
                <br/>
            </div>
        </div>
        <a href="dashboardCanteenStaff.jsp"><div class="back">Back</div></a>

        <div class="coverOverlay"></div>
        <%}%>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Some javascript. It will dynamically change the subtitle based on what the user hovers over. -->
    <script>
                        $(document).ready(function () {
                            $("#foodid").hover(function () {
                                $("#subtitle").html("That's the food ID. It uniquely defines the food.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can Create Food Details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#foodName").hover(function () {
                                $("#subtitle").html("That's the food's name.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can Create Food Details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#calories").hover(function () {
                                $("#subtitle").html("That's the number of calories in the food.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can Create Food Details.");
                                $("#subtitle").css("color", "white");
                            });

                            $("#dateAdded").hover(function () {
                                $("#subtitle").html("That's when the food was added. No point in editing it.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can Create Food Details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#dateDiscontinued").hover(function () {
                                $("#subtitle").html("That's when the food was discontinued. If it's not discontinued, it will show 'not discontinued'. ");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can Create Food Details.");
                                $("#subtitle").css("color", "white");
                            });

                            $(".adding").hover(function () {
                                $("#subtitle").html("Create the food");
                                $("#subtitle").css("color", "green");
                            }, function () {
                                $("#subtitle").html("Here's where you can Create Food Details.");
                                $("#subtitle").css("color", "white");
                            });

                            $(".adding").click(function confirmadding() {
                                $(".addingConfirmation").css("display", "inline-block");
                                $(".outsideContainer :input").prop("disabled", false);
                            });

                            $(".addingCancel").click(function confirmadding() {
                                $(".addingConfirmation").css("display", "none");
                                $(".outsideContainer :input").prop("disabled", false);
                            });

                            $("#calories").on("input", function () {
                                //Code is possible thanks to Neha Jain @ https://stackoverflow.com/questions/18510845/maxlength-ignored-for-input-type-number-in-chrome
                                //Checks if the number is too long
                                if (this.value.length > this.maxLength) {
                                    this.value = this.value.slice(0, this.maxLength);
                                }
                            });


                            $(".adding").click(function () {
                                $(".coverOverlay").css("display", "block");
                                $(".addingConfirmation").css("z-index", "1");
                            });

                            $(".addingCancel").click(function () {
                                $(".coverOverlay").css("display", "none");
                                $(".addingConfirmation").css("z-index", "0");
                            });
                        });
    </script>
</html>
