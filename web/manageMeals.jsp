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
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <link href="CSS/manageMeals.css" rel="stylesheet">
        <link href="CSS/manageRecords.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Choose meals.</title>
    </head>
    <body>
        <%
            request.getSession(false);

        
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
          %>
        <h1 class="title">Manage meals.</h1>
        <h5 id="subtitle">Here you can add or edit meals. To view meals' specific info, you will need to press "edit" first.</h5>
        <div class="errorMsg">${errorMsg}</div>
        <div class="successMsg">${successMsg}</div>
        <!-- Search bar -->
        <form class="searchForm">
            <input type="text" id="myInput" name="query" onkeyup="myFunction()" placeholder="search..." class="searchBar"/>
        </form>

        <!-- Table -->
        
            <div class="mealsContainer">
                <table>
                    <td>
                            <a href="DisplayFoodSelectionServlet"> <label class="meal" id="add">+</label></a>
                        </td>
                    ${queryResult}
                </table>
                            
            </div>
            <div><a class="nextButton" href="dashboardCanteenStaff.jsp" type="submit" >Back</a></div><br/><br/>
        
        <%}%>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="studentDisplayMeals.js" type="text/javascript"></script>
    <script src="searchFilter.js" type="text/javascript"></script>
</html>
