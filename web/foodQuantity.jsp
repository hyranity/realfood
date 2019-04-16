<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/quantityModification.css" rel="stylesheet">
        <link href="CSS/mealDetails.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <title>Edit Particulars</title>
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
                // Allow student only
                if(!permission.equalsIgnoreCase("canteenStaff") && !permission.equalsIgnoreCase("manager")){
                     request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
        %>
        <!-- Steps -->
        <div class="stepsContainer">
            <h1>steps</h1>
            <div class="steps">
                <div>1. Select food components.</div>
                <div class="currentStep">2. Edit food quantity</div>
                <div>3. Finalize meal details</div>
            </div>
        </div>
        
        <h1 class="title">Edit food quantity</h1>
        <h5 id="subtitle">Modify the quantity of the food components.</h5>
        <form action="FoodQuantityServlet" method="POST" id="mealForm">
        ${queryResultQuantity}
            <div class="total">
                <p id="totalCal">Total: ${caloriesSum} calories</p>
            </div>
            <div style="margin-top: 90px;">
            <a class="nextButton" href="DisplayFoodSelectionServlet">Back</a>
            <input class="nextButton" form="mealForm" type="submit" value="Next Step">
        </div>
    </form>
            <%}%>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
                        $(document).ready(function () {      
                            $(".symbol").click(function (event) {
                                var id = event.target.id;
                                symbolId = id.substring(0, 3);
                                var targetId = id.substring(3);
                                var quantity = parseInt($("#" + targetId).val());

                                // Update quantity
                                if (symbolId == "add") {
                                    quantity++;
                                } else {
                                    if (quantity != 1)
                                        quantity--;
                                }
                                $("#" + targetId).val(quantity);
                                var caloriesId = "#cal" + targetId;
                                var calories = quantity * parseInt($(caloriesId).data("calories"));
                                $("#cal" + targetId).text(calories + " calories");

                                var sum = 0;

                                $(".value").each(function () {
                                    var caloriesId = "#cal" + targetId;
                                    var calories = quantity * parseInt($(caloriesId).data("calories"));

                                    sum += parseInt($(this).html());
                                });


                                $("#totalCal").html("Total: " + sum + " calories");

                            });
                        });
    </script>
</html>
