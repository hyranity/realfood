<%-- 
    Document   : studentDisplayMeals
    Created on : Mar 17, 2019, 3:45:43 PM
    Author     : mast3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/studentDisplayMeals.css" rel="stylesheet">
        <link href="CSS/student.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <link href="CSS/students.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Choose meals.</title>
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

            
            // Allow student only
            if (!permission.equalsIgnoreCase("student")) {
                request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {
        %>
        <div class="stepsContainer">
            <h1>Make an Order</h1>
            <div class="steps">
                <div>1. Select a date</div>
                <div class="currentStep">2. Choose meals.</div>
                <div>3. Edit particulars</div>
                <div>4. Payment</div>
            </div>
        </div>
        <h1 class="title">Choose meals.</h1>
        <h5 id="subtitle">Click on the meals you would like to have.</h5>
        <div class="errorMsg">${errorMsg}</div>

        <!-- Search bar -->
        <form class="searchForm">
            <input type="text" name="query" placeholder="search..." class="searchBar"/>
        </form>

        <!-- Table -->
        <form action="SelectMealServlet" method="post" id="mealOrder">
            <div class="mealsContainer">
                <table>
                    <tr>
                        ${queryResult}
                </table>

            </div>
            <div>
                <input class="nextButton" form="mealOrder">Back</button>
                <button class="nextButton">Next step</button>
                <br/><br/><br/>
            </div>
            <%}%>
        </form>
        <h6 class="credits">1000 credits</h6>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="studentDisplayMeals.js" type="text/javascript"></script>
</html>
