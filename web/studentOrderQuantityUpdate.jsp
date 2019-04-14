<%@page import="Model.Studentorder"%>
<%@page import="Model.*"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/quantityModification.css" rel="stylesheet">
        <link href="CSS/students.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <title>Edit Particulars</title>
    </head>
    <body>
  <%
            session = request.getSession(false);

            String permission = "";
            Student stud = new Student();

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

            
            // Allow student only
            if (!permission.equalsIgnoreCase("student")) {
                request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {
                
             // Verify that the student accessed this properly
            Studentorder studOrder = new Studentorder();
            Studentorder currentStudOrder = new Studentorder();
            try {
                // Load the student's order from session
                studOrder = (Studentorder) session.getAttribute("studOrderEdit");
                currentStudOrder = (Studentorder) session.getAttribute("currentStudOrder");
            } catch (Exception e) {
                // If any error, means that the steps are not followed correctly
                response.sendRedirect("DisplayOrdersServlet");
            }
            
            int credits = stud.getCredits();    // Obtain student's credits amount
            int originalPrice = currentStudOrder.getTotalprice();
        %>
        <div class="stepsContainer">
            <h1>steps</h1>
             <div class="steps">
                <div>1. Update meal selection.</div>
                <div class="currentStep">2. Update particulars</div>
                <div>3. Confirm update</div>
            </div>
        </div>

        <h1 class="title">Update particulars</h1>
        <h5 id="subtitle">Modify the quantity of your meals.</h5>

        <form action="MealQuantityEditServlet" method="POST" id="mealQuantityForm">
            <div class="mainContainer">
            ${queryResultQuantity}
            
           <div class="total">
               <p style="color: white;">Original total: <%=originalPrice%> credits</p>
                <p id="totalPrice">New total: ${totalPrice} credits</p>
            </div>

            <br/>
            </div>
            <h6 class="credits">${totalPrice} credits</h6>

            <div class="nextButtonDiv">
                <button class="nextButton">Back</button>
                <input class="nextButton" form="mealQuantityForm" type="submit" value="Next Step">
                <br/>
            </div>
        </form>
            <!-- Display student's credits -->
            <h6 class="credits"><%=credits%> credits</h6>
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
                var priceId = "#price" + targetId;
                var price = quantity * parseInt($(priceId).data("price"));
                $("#price" + targetId).text(price + " credits");

                var sum = 0;

                $(".value").each(function () {
                    var priceId = "#price" + targetId;
                    var price = quantity * parseInt($(priceId).data("price"));

                    sum += parseInt($(this).html());
                });


                $("#totalPrice").html("New total: " + sum + " credits");

            });
        });
    </script>
</html>
