<%-- 
    Document   : monthlySalesReport
    Created on : Apr 11, 2019, 12:53:32 AM
    Author     : Richard Khoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Monthly Sales Report</title>
    <link href="CSS/reportGenerated.css" rel="stylesheet">
</head>

<body>

<table>
    <caption><a onclick="printThisPage()"><b>RealFood Canteen</b></a></caption>
    <caption class="address">Lot 1, Ground Floor, Pusat Dagangan Donggongon, Jalan Sapau, Pekan Donggongon, 89500 Penampang, Sabah Tel:088-718481</caption><br/>
    <caption class="reportType"><u>Sales Report Month January</u></caption>
    <caption class="timeGenerate"> Generated On: 31 January 2019</caption>
    
  <thead>
    ${outputMonthly}
  </tbody>
  
    <!-- Print Total Part-->
    <tr>
      <th scope="col">Total</th>
      <th scope="col"></th>
      <th scope="col"></th>
      <th scope="col"></th>
      <th scope="col"></th>
      <th scope="col">RM121212</th>
    </tr>    
</table>
    <div class="tableBox">
    <br/><label class="leftVerified">Verified by:</label><br/>
    <br/><label class="leftLine">_________________________</label><br/>
    <br/><label class="leftName">Name:</label>
    <br/><label class="leftDate">Date:</label>
    </div>
    
</body>

<script>
function printThisPage() {
  window.print();
}
</script>

</html>
