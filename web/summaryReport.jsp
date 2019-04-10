<%-- 
    Document   : summaryReport
    Created on : Apr 11, 2019, 12:50:46 AM
    Author     : Richard Khoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Summary Sales Report</title>
    <link href="CSS/reportGenerated.css" rel="stylesheet">
</head>

<body>

<table>
    <caption><a onclick="printThisPage()"><b>RealFood Canteen</b></a></caption>
    <caption class="address">Lot 1, Ground Floor, Pusat Dagangan Donggongon, Jalan Sapau, Pekan Donggongon, 89500 Penampang, Sabah Tel:088-718481</caption><br/>
    <caption class="reportType"><u>Summary Sales Report From January To June 2019</u></caption>
    <caption class="timeGenerate"> Generated On: 31 January 2019</caption>
    
  <thead>
    <tr>
      <th scope="col">Month</th>
      <th scope="col">Meal ID</th>
      <th scope="col">Amount (RM)</th>
    </tr>    
  </thead>
  <tbody>
    <tr>
      <td data-label="Month">1</td>
      <td data-label="MealID">M0001</td>
      <td data-label="Amount (RM)">2200</td>
    </tr>
    <tr>
      <td data-label="Month">2</td>
      <td data-label="MealID">M0001</td>
      <td data-label="Amount (RM)">2200</td>
    </tr>
    <tr>
      <td data-label="Month">3</td>
      <td data-label="MealID">M0001</td>
      <td data-label="Amount (RM)">2200</td>
    </tr>
    <tr>
      <td data-label="Month">4</td>
      <td data-label="MealID">M0001</td>
      <td data-label="Amount (RM)">2200</td>
    </tr>
    <tr>
      <td data-label="Month">5</td>
      <td data-label="MealID">M0001</td>
      <td data-label="Amount (RM)">2200</td>
    </tr>
    <tr>
      <td data-label="Month">6</td>
      <td data-label="MealID">M0001</td>
      <td data-label="Amount (RM)">2200</td>
    </tr>
    <tr>
      <td data-label="Month">7</td>
      <td data-label="MealID">M0001</td>
      <td data-label="Amount (RM)">2200</td>
    </tr>
    <tr>
      <td data-label="Month">8</td>
      <td data-label="MealID">M0001</td>
      <td data-label="Amount (RM)">2200</td>
    </tr>
    <tr>
      <td data-label="Month">9</td>
      <td data-label="MealID">M0001</td>
      <td data-label="Amount (RM)">2200</td>
    </tr>
    <tr>
      <td data-label="Month">10</td>
      <td data-label="MealID">M0001</td>
      <td data-label="Amount (RM)">2200</td>
    </tr>
    <tr>
      <td data-label="Month">11</td>
      <td data-label="MealID">M0001</td>
      <td data-label="Amount (RM)">2200</td>
    </tr>
    <tr>
      <td data-label="Month">12</td>
      <td data-label="MealID">M0001</td>
      <td data-label="Amount (RM)">2200</td>
    </tr>
    <tr>
      <td data-label="Month">13</td>
      <td data-label="MealID">M0001</td>
      <td data-label="Amount (RM)">2200</td>
    </tr>
  </tbody>
  
    <!-- Print Total Part-->
    <tr>
      <th scope="col">Total</th>
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