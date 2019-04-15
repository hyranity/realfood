<%@page import="java.util.ArrayList"%>
<%@page import="Model.Mealfood"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
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

            String permission = (String) session.getAttribute("permission");

            
            if (permission == null) {
                request.setAttribute("errorMsg", "Please login.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {
                // Allow staff only
                if (!permission.equalsIgnoreCase("canteenStaff") && !permission.equalsIgnoreCase("manager")) {
                    request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
                
                List<Mealfood> mealFoodList = new ArrayList();
                try {
                    mealFoodList = (List<Mealfood>) session.getAttribute("mealFoodList");
                } catch (Exception ex) {
                    // Display error messages if any
                    System.out.println("ERROR: " + ex.getMessage());
                }
        %>
        <!-- Steps -->
        <div class="stepsContainer">
            <h1>Create a Meal</h1>
            <div class="steps">
                <div>1. Select meal components.</div>
                <div>2. Edit meal quantity</div>
                <div class="currentStep">3. Finalize meal details</div>
            </div>
        </div>

        <div class="sideDisplay">
            <h3 style="color: gold;">Food selection summary</h3>
            <br/>
                <%
                    for (Mealfood mf : mealFoodList) {
                        String foodName = mf.getFoodid().getFoodname();
                        int quantity = mf.getQuantity();
                %>
               <div class="recordQuantity">
                    <p class="name"><b><%=foodName%> x<%=quantity%></b></p>
                    <br/>
            </div>
            <% } %>
            
        </div>

        <div class="outsideContainer"><br/><br/><br/>

            <h1>Finalize meal details</h1>
            <h5 id="subtitle">Complete the meal creation by filling in its details.</h5>
            <br/>
            <div class="mainContainer">
                <div class="errorMsg">${errorMsg}</div>
                <form action="MealFinalizationServlet" class="form" id="finalMealForm">
                    <div>
                        <input type="text" id="mealName" max="20" value="" placeholder="Name" name="mealName"/>
                    </div>
                    <div>
                        <input type="text" value="" id="description" placeholder="Description" name="description"/>
                    </div>
                    <div>
                        <input type="number" value="" id="price" placeholder="Price" name="price"/>
                    </div>
                    <div>
                        <input type="text" value="" id="imageLink" placeholder="Image link" name="imageLink"/>
                    </div>
                    <div class="mealTime">
                        <p>Meal will be served during:</p>
                        <input type="checkbox" value="breakfast" id="breakfast" name="mealTime"/>
                        <label for="breakfast"><div id="breakfastDiv">Breakfast</div></label>

                        <input type="checkbox" value="lunch" id="lunch" name="mealTime"/>
                        <label for="lunch"><div id="lunchDiv">Lunch</div></label>
                    </div>

                    <br/>
                    <input type="submit" class="submitBtn" value="Add meal" form="finalMealForm">
                </form>
                <br/>
            </div>
        </div>
        <a href="dashboardCanteenStaff.jsp"><div class="back">Back</div></a>
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
                $("#subtitle").html("Complete the meal creation by filling in its details.");
                $("#subtitle").css("color", "white");
            });
            $("#mealName").hover(function () {
                $("#subtitle").html("That's the meal's name.");
                $("#subtitle").css("color", "gold");
            }, function () {
                $("#subtitle").html("Complete the meal creation by filling in its details.");
                $("#subtitle").css("color", "white");
            });
            $("#description").hover(function () {
                $("#subtitle").html("That's the description of the meal.");
                $("#subtitle").css("color", "gold");
            }, function () {
                $("#subtitle").html("Complete the meal creation by filling in its details.");
                $("#subtitle").css("color", "white");
            });
            $("#price").hover(function () {
                $("#subtitle").html("That's how much credits the meal will cost.");
                $("#subtitle").css("color", "gold");
            }, function () {
                $("#subtitle").html("Complete the meal creation by filling in its details.");
                $("#subtitle").css("color", "white");
            });
            $("#imageLink").hover(function () {
                $("#subtitle").html("Upload an image of the meal to a site like IMGUR and copy the link here. ");
                $("#subtitle").css("color", "gold");
            }, function () {
                $("#subtitle").html("Complete the meal creation by filling in its details.");
                $("#subtitle").css("color", "white");
            });
            $(".mealTime").hover(function () {
                $("#subtitle").html("That's what time the meal can be ordered (and redeemed). You may select both.");
                $("#subtitle").css("color", "gold");
            }, function () {
                $("#subtitle").html("Complete the meal creation by filling in its details.");
                $("#subtitle").css("color", "white");
            });


        });
    </script>
</html>
