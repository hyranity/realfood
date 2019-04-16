<%-- 
    Document   : Order Preparation
    Created on : Apr 11, 2019, 12:49:37 AM
    Author     : Richard Khoo
--%>

<%@page import="util.Auto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Order Preparation Info</title>
    <link href="CSS/reportGenerated.css" rel="stylesheet">
    <link href="CSS/orderPrep.css" rel="stylesheet">
    <link href="CSS/commonStyles.css" rel="stylesheet">
    <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
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

        
        // Allow canteenStaff only
        if (!permission.equalsIgnoreCase("canteenStaff")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            
        String todayDate = Auto.dateToString(Auto.getToday());
    %>
<h1>Order Preparation</h1>  
    
    <table>
    
  <thead>
    <tr style="border-bottom: 1px solid white;">
        <th scope="col" style="color: gold">Meal Name</th>
        <th scope="col" style="color: gold">Meal Quantity</th>
      <th scope="col">Food Name</th>
      <th scope="col">Food Per Meal</th>
      <th scope="col">Total Food</th>
      
    </tr>    
  </thead>
  <tbody>
      ${queryResult}
  </tbody>
  
    <!-- Print Total Part-->
    <tr style="border-top: 1px solid white;">
      <th scope="col">Total</th>
      <th scope="col">${totalMeal}</th>
      <th scope="col"></th>
      <th scope="col"></th>
      <th scope="col">${totalFood}</th>
    </tr>    
</table>
    <div style="margin-top: 30px ;margin-bottom: 30px;"><a class="backBt" href="selectOrderPrepDate.jsp" type="submit" >Back</a></div><br/><br/>
    <%}%>
</body>

<script>
function printThisPage() {
  window.print();
}
</script>

</html>