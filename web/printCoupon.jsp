<%-- 
    Document   : printCoupon
    Created on : Apr 1, 2019, 7:24:10 AM
    Author     : mast3
--%>

<%@page import="Model.Ordermeal"%>
<%@page import="util.Auto"%>
<%@page import="Model.Studentorder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/printCoupon.css" rel="stylesheet">
        <title>Print Coupon</title>
    </head>
    <body>
        <%
            request.getSession(false);

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
                Studentorder so = new Studentorder();
                
               

                try {
                    so = (Studentorder) session.getAttribute("studentOrder");
                } catch (Exception e) {
                    // This will be triggered if the page is accessed directly, hence redirect to dashboard
                    response.sendRedirect("studentDashboard.jsp");
                }
                
 String name = so.getStudentid().getFirstname() + " " + so.getStudentid().getLastname();
                 String id = so.getStudentid().getFirstname();
                   String chosenDate = Auto.dateToString(so.getChosendate());
                   String couponCode = so.getCouponcode();
        %>
        <div class="couponContainer">
            <h1>RealFood Canteen</h1>
            <hr/>
            <p><b>Student ID: </b><%=id%></p>
            <p><b>Student Name: </b><%=name%></p>
            <p><b>Redemption Date: </b><%=chosenDate%></p>
            <hr/>
            <h5>Coupon Code</h5>
            <br/>
            <h3><%=couponCode%></h3>
            <br/>
            <br/>
            <div class="orders">
                <ul>
                    <%
                    for(Ordermeal om : so.getOrdermealList()){
                        
                    String mealName = om.getMealid().getMealname();
                    int quantity = om.getQuantity();
                    String mealTime = "";
                    if(om.getMealid().getIslunch() && om.getMealid().getIsbreakfast())
                        mealTime = "(All Day)";
                    else if(om.getMealid().getIslunch())
                        mealTime = "(Lunch)";
                    else
                        mealTime = "(Breakfast)";
                   
                    %>
                    <li>
                        <p class="name"><%=mealName%></p><p class="quantity">x<%=quantity%></p><p class="mealTime"><%=mealTime%></p>
                    </li>  
                    <%}%>
                </ul>
            </div>
            <br/>
            <p class="smallText">total credits cost</p>
            <br/>
            <p class="cost">1920</p>
        </div>
        <%}%>
    </body>
</html>
