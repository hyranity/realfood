<%-- 
    Document   : selectReport
    Created on : Apr 15, 2019, 12:36:01 PM
    Author     : Richard Khoo
--%>

<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link href="CSS/selectReport.css" rel="stylesheet">
<link href="CSS/commonStyles.css" rel="stylesheet">

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Select Report</title>
    </head>
    <body style="text-align: center;">
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

            // Allow staff only
            if (!permission.equalsIgnoreCase("canteenStaff")) {
                request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {
                
                
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
        %>

        <!--element start here-->
        <div class="containerBox">
            <!--<h2>Report</h2>-->
            <div class="reportGenerate" style="text-align: center; display: inline-block;">
                <h1>Order Preparation Information</h1>
                <p>Enter date.</p>
                <div class="errorMsg">${errorMsg}</div>

                <form action="OrderPreparationGenerator" id="formForm">

                    <div>
                        <input type="number" class="formInput" name="day" id="day"  minlength="1" maxlength="2" placeholder="DAY" required/>
                    </div>

                    <select class="formInput" name="month" id="monthchoice" required>
                        <option selected value="">MONTH</option>
                        <option value="january">January</option>
                        <option value="February">February</option>
                        <option value="March">March</option>
                        <option value="April">April</option>
                        <option value="May">May</option>
                        <option value="June">June</option>
                        <option value="July">July</option>
                        <option value="August">August</option>
                        <option value="September">September</option>
                        <option value="October">October</option>
                        <option value="November">November</option>
                        <option value="December">December</option>
                    </select>


                    <select class="formInput" id="year" name="year" required>
                        <option selected value=""> YEAR </option>
                        <%
                        for (int i = 0; i < 10; i++) {
                                
                        %>
                        <option value="<%=year%>"><%=year%></option>
                        <%
                        year++;
                        }
                        %>
                    </select>

                    <a href="dashboardCanteenStaff.jsp"><div class="nextButton" >Back</div></a>
                    <button class="nextButton" type="submit" target="_blank" value="Generate Order Preparation Info"/>Get Order Preparation Info</button>

                </form>

            </div>
        </div>
        <!--element end here-->
        <%}%>
    </body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
    <script>
       
    </script>
</html>
