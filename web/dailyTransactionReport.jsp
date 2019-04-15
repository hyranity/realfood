<%-- 
    Document   : ReportGenerated
    Created on : Apr 10, 2019, 7:31:05 PM
    Author     : Richard Khoo
--%>

<%@page import="util.Auto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Daily Transaction Report</title>
    <link href="CSS/reportGenerated.css" rel="stylesheet">
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
        if (!permission.equalsIgnoreCase("manager")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            
        String todayDate = Auto.dateToString(Auto.getToday());
    %>
<table>
    <caption><a onclick="printThisPage()"><b>RealFood Canteen</b></a></caption>
    <caption class="address">Lot 1, Ground Floor, Pusat Dagangan Donggongon, Jalan Sapau, Pekan Donggongon, 89500 Penampang, Sabah Tel:088-718481</caption><br/>
    <caption class="reportType"><u>Daily Transaction Report For ${date}</u></caption>
    <caption class="timeGenerate"> Generated On: <%=todayDate%></caption>
    
 <thead>
      <th scope="col">No.</th>
      <th scope="col">Meal ID</th>
      <th scope="col">Meal Name</th>
      <th scope="col">Quantity</th>
      <th scope="col">Amount (RM)</th>
  </thead>
  <tbody>
      ${outputMonthly}
  </tbody>
  
    <!-- Print Total Part-->
    <tr>
      <th scope="col">Total</th>
      <th scope="col"></th>
      <th scope="col"></th>
      <th scope="col"></th>
      <th scope="col">RM ${totalPrice}</th>
    </tr>    
</table>
    <div class="tableBox">
    <br/><label class="leftVerified">Verified by:</label><br/>
    <br/><label class="leftLine">_________________________</label><br/>
    <br/><label class="leftName">Name:</label>
    <br/><label class="leftDate">Date:</label>
    </div>
    
    <%}%>
</body>

<script>
function printThisPage() {
  window.print();
}
</script>

</html>