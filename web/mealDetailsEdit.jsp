<%@page import="util.Auto"%>
<%@page import="Model.Meal"%>
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
        <link href="CSS/mealDetails.css" rel="stylesheet">
    </head>
    <body>
        <%
            session = request.getSession(false);

            // If user is not logged in, redirect to login page
            if (session.getAttribute("permission") == null) {
                request.setAttribute("errorMsg", "Please login.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {
                // Allow staff only
                String permission = (String) session.getAttribute("permission");
                if (!permission.equalsIgnoreCase("canteenStaff") && !permission.equals("manager")) {
                    request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }

                Meal meal = new Meal();

                // If meal is null, means this page is accessed directly. Redirect if so.
                try {
                    meal = (Meal) session.getAttribute("meal");
                    if (meal == null) {
                        response.sendRedirect("ManageMealsServlet");
                    }
                } catch (Exception e) {
                    response.sendRedirect("ManageMealsServlet");
                }

                // Assign values
                String name = meal.getMealname();
                String id = meal.getMealid();
                String description = meal.getDescription();
                String imageLink = meal.getMealimagelink();
                int price = meal.getPrice();
                String dateDiscontinued = "";
                String dateAdded = Auto.dateToString(meal.getDateadded());
                int totalCalories = meal.getTotalcalories();
                boolean isBreakfast = meal.getIsbreakfast();
                boolean isLunch = meal.getIslunch();
                boolean isDiscontinued = true;
                
                session.setAttribute("meal", meal);

                try {
                    dateDiscontinued = Auto.dateToString(meal.getDatediscontinued());
                    if (dateDiscontinued == null) {
                        dateDiscontinued = "not discontinued";
                    }
                } catch (NullPointerException e) {
                    dateDiscontinued = "not discontinued";
                }

                // If not discontinued, show that it is not
                if (!meal.getIsdiscontinued()) {
                    dateDiscontinued = "not discontinued";
                    isDiscontinued = false;
                }

        %>
        <!-- Steps -->
        <div class="stepsContainer">
            <h1>Edit a Meal</h1>
            <div class="steps">
                <div>1. Modify food components.</div>
                <div>2. Modify food quantity</div>
                <div class="currentStep">3. Modify meal details</div>
            </div>
        </div>

        <div class="sideDisplay">

            <div class="recordQuantity">
                <div class="frontPart">
                    <p class="name">Spaghetti Bolognese</p>

                </div>
                <div class="quantityEditor">
                    <p class="value">1500 Credits</p>
                    <p class="quantity" data-quantity="5">x2</p>

                </div>
            </div>
        </div>

        <div class="outsideContainer"><br/><br/><br/>

            <h1>Finalize meal details</h1>
            <h5 id="subtitle">Here's where you can edit the meal's details.</h5>
            <br/>
            <div class="mainContainer">
                <div class="errorMsg">${errorMsg}</div>
                <div class="successMsg">${successMsg}</div>
                <form action="MealDetailsEditServlet" class="form">
                    <%                        if (!isDiscontinued) {
                    %>
                    <a href="#" onclick="confirmtoggleDisable()"> <div class="toggleDisable" id="discontinue">Discontinue</div></a>
                    <%
                    } else {
                    %>
                    <a href="#" onclick="confirmtoggleDisable()"> <div class="toggleDisable" id="enable">Re-enable</div></a>
                    <%}%>
                    <div>
                        <input type="text" value="<%=id%>" style="background-color: darkgray;"  id="mealId" name="mealId" readonly/>
                    </div>
                    <div>
                        <input type="text" id="mealName" value="<%=name%>" placeholder="Meal Name" name="mealName"  required/>
                    </div>
                    <div>
                        <input type="text" value="<%=description%>" id="description" placeholder="Meal Description" name="description" required/>
                    </div>
                    <div>
                        <input type="number" value="<%=price%>" id="price" placeholder="Price" name="price"/>
                    </div>
                    <div>
                        <input type="text" value="<%=imageLink%>" id="imageLink" placeholder="Image Link" name="imageLink" required/>
                    </div>
                    <div>
                        <input type="text" value="<%=totalCalories%>" id="totalCalories" style="background-color: darkgray;"readonly/>
                    </div>
                    <div>
                        <input type="text" value="Added: <%=dateAdded%>" id="dateAdded"  style="background-color: darkgray;"  readonly/>
                    </div>
                    <div>
                        <input type="text" value="<%=dateDiscontinued%>" id="dateDiscontinued"  style="background-color: darkgray; font-weight: 500;"  readonly/>
                    </div>
                    <div class="mealTime" style="margin-top: 50px;">
                        <p>Meal will be served during:</p>
                        <%
                            if (isBreakfast) {    // If breakfast is already chosen previously, display it
                        %>
                        <input type="checkbox" value="breakfast" id="breakfast" name="mealTime" checked/>
                        <%} else {%>
                        <input type="checkbox" value="breakfast" id="breakfast" name="mealTime"/>
                        <%}%>
                        <label for="breakfast"><div id="breakfastDiv">Breakfast</div></label>

                        <%
                            if (isLunch) {    // If lunch is already chosen previously, display it
                        %>
                        <input type="checkbox" value="lunch" id="lunch" name="mealTime" checked/>
                        <%} else {%>
                        <input type="checkbox" value="lunch" id="lunch" name="mealTime"/>
                        <%}%>
                        <label for="lunch"><div id="lunchDiv">Lunch</div></label>
                    </div>
                    <button type="submit" class="submitBtn">Add meal</button>
                </form>
                <br/>
            </div>
        </div>
        <a href="displayMeals.jsp"><div class="back">Back</div></a><br/><br/>
        <div class="toggleDisableConfirmation">
            <%
                if (!isDiscontinued) {
            %>
            <h5>Discontinue meal?</h5>
            <p>The meal will be discontinued.</p>
            <%
            } else {
            %>
            <h5>Re-enable meal?</h5>
            <p>The meal will be discontinued.</p>
            <%
                }
            %>
            <a href="MealDiscontinuationServlet?mealId=<%=id%>"><div class="toggleDisableConfirm">Yes</div></a>
            <a href="#"><div class="toggleDisableCancel">No</div></a>
        </div>
        <%}%>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Some javascript. It will dynamically change the subtitle based on what is hovered over. -->
    <script>
                        $(document).ready(function () {
                            $("#mealid").hover(function () {
                                $("#subtitle").html("That's the meal ID. It uniquely defines the meal. It can't be changed.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can edit the meal's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#mealName").hover(function () {
                                $("#subtitle").html("That's the meal's name.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can edit the meal's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#description").hover(function () {
                                $("#subtitle").html("That's the description of the meal.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can edit the meal's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#price").hover(function () {
                                $("#subtitle").html("That's how much credits the meal will cost.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can edit the meal's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#imageLink").hover(function () {
                                $("#subtitle").html("That's the link of where the image was uploaded to. You can edit the link if you want to change the image.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can edit the meal's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $(".mealTime").hover(function () {
                                $("#subtitle").html("That's what time the meal can be ordered (and redeemed). You may select both.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can edit the meal's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#totalCalories").hover(function () {
                                $("#subtitle").html("That's the total number of calories in the meal. It's auto-calculated.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can edit the meal's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#dateAdded").hover(function () {
                                $("#subtitle").html("That's when the food was added. No point in editing it.");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can edit the meal's details.");
                                $("#subtitle").css("color", "white");
                            });
                            $("#dateDiscontinued").hover(function () {
                                $("#subtitle").html("That's when the food was discontinued. If it's not discontinued, it will show 'not discontinued'. ");
                                $("#subtitle").css("color", "gold");
                            }, function () {
                                $("#subtitle").html("Here's where you can edit the meal's details.");
                                $("#subtitle").css("color", "white");
                            });

                            $(".toggleDisable").hover(function () {
                                $("#subtitle").html("Discontinue the meal");
                                $("#subtitle").css("color", "red");
                            }, function () {
                                $("#subtitle").html("Here's where you can edit the meal's details.");
                                $("#subtitle").css("color", "white");
                            });

                            $(".toggleDisable").click(function confirmtoggleDisable() {
                                $(".toggleDisableConfirmation").css("display", "inline-block");
                                $(".outsideContainer").css("opacity", "0.5");
                                $(".outsideContainer :input").prop("disabled", true);
                            });

                            $(".toggleDisableCancel").click(function confirmtoggleDisable() {
                                $(".toggleDisableConfirmation").css("display", "none");
                                $(".outsideContainer").css("opacity", "1");
                                $(".outsideContainer :input").prop("disabled", false);
                            });


                        });
    </script>
</html>
