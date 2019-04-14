<%@page import="Model.Food"%>
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
            String previousUrl = "";

            // Checks for previous URL. if no previous URL detected, means the user directly accessed the pages.
            try {
                previousUrl = request.getHeader("referer");
                if (previousUrl == null) {
                    request.setAttribute("errorMsg", "Direct access detected. Please don't directly access pages..");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            } catch (Exception e) {
                request.setAttribute("errorMsg", "Direct access detected. Please don't directly access pages..");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            
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

        %>
        <div class="outsideContainer">

            <h1>Edit Food</h1>
            <h5 id="subtitle">Here's where you can view and edit food details.</h5>
            <br/>
            <div class="mainContainer">
                <div class="errorMsg">${errorMsg}</div>
                <div class="successMsg">${successMsg}</div>
                <form action="ProcessFoodEditServlet" class="form" method="POST">
                    ${query}

                    <br/>
                    <button type="submit" class="submitBtn">Submit changes</button>
                </form>
                <br/>
            </div>
        </div>
        <a href="ManageFoodServlet"><div class="back">Back</div></a>
        <div class="toggleConfirmation">
            ${discontinueDialog}

            <a href="FoodDiscontinuationServlet?foodId=${foodId}"><div class="overlayConfirm">Yes</div></a>
            <a href="#"><div class="overlayCancel">No</div></a>
        </div>
        <div class="coverOverlay"></div>
        <%}%>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript" src="Javascript/Record.js"></script>
</html>
