<%-- 
    Document   : summaryReport
    Created on : Apr 11, 2019, 12:50:46 AM
    Author     : Richard Khoo
--%>

<%@page import="util.Auto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Summary Sales Report</title>
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

        
        // Allow manager only
        if (!permission.equalsIgnoreCase("manager")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            
            String todayDate = Auto.dateToString(Auto.getToday());
    %>
<table>
    <caption><b>RealFood Canteen</b></caption><div class="noprint"><button onclick="goBack()">Back</button> <button onclick="printThisPage()">Print this page</button></div>
    <caption class="address">Lot 1, Ground Floor, Pusat Dagangan Donggongon, Jalan Sapau, Pekan Donggongon, 89500 Penampang, Sabah Tel:088-718481</caption><br/>
    <caption class="reportType"><u>Semiannual Sales Report For ${title}</u></caption>
    <caption class="timeGenerate"> Generated On: <%=todayDate%></caption>
    
    
  <thead>
      <th scope="col">Month</th>
      <th scope="col">Meal ID</th>
      <th scope="col">Meal Name</th>
      <th scope="col">Quantity</th>
      <th scope="col">Amount (RM)</th>
  </thead>
    ${outputOverall}
  </tbody>
  
    <!-- Print Total Part-->
    <tr>
      <th scope="col">Total</th>
      <th scope="col"></th>
      <th scope="col"></th>
      <th scope="col"></th>
      <th scope="col">RM ${totalAmount}</th>
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

function goBack() {
  window.history.back();
}

</script>

</html>