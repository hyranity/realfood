<%-- 
    Document   : exceptionReport
    Created on : Apr 11, 2019, 12:49:37 AM
    Author     : Richard Khoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Exception Report</title>
    <link href="CSS/reportGenerated.css" rel="stylesheet">
</head>

<body>

<table>
    <caption><a onclick="printThisPage()"><b>RealFood Canteen</b></a></caption>
    <caption class="address">Lot 1, Ground Floor, Pusat Dagangan Donggongon, Jalan Sapau, Pekan Donggongon, 89500 Penampang, Sabah Tel:088-718481</caption><br/>
    <caption class="reportType"><u>Exception Report For Order Cancellation for February</u></caption>
    <caption class="timeGenerate"> Generated On: 31 February 2019</caption>
    
  <thead>
    <tr>
      <th scope="col">Meal ID</th>
      <th scope="col">Quantity</th>
      <th scope="col">Cash Refunded  (RM)</th>
    </tr>    
  </thead>
  <tbody>
    <tr>
      <td data-label="MealID">M000532</td>
      <td data-label="Quantity">10</td>
      <td data-label="CashRefunded">4032</td>

    </tr>
    <tr>
      <td data-label="MealID">M000532</td>
      <td data-label="Quantity">10</td>
      <td data-label="CashRefunded">4032</td>

    </tr>
    <tr>
      <td data-label="MealID">M000532</td>
      <td data-label="Quantity">10</td>
      <td data-label="CashRefunded">4032</td>

    </tr>
    <tr>
      <td data-label="MealID">M000532</td>
      <td data-label="Quantity">10</td>
      <td data-label="CashRefunded">4032</td>

    </tr>
    <tr>
      <td data-label="MealID">M000532</td>
      <td data-label="Quantity">10</td>
      <td data-label="CashRefunded">4032</td>

    </tr>
    <tr>
      <td data-label="MealID">M000532</td>
      <td data-label="Quantity">10</td>
      <td data-label="CashRefunded">4032</td>
      
    </tr>
    <tr>
      <td data-label="MealID">M000532</td>
      <td data-label="Quantity">10</td>
      <td data-label="CashRefunded">4032</td>
      
    </tr>
    <tr>
      <td data-label="MealID">M000532</td>
      <td data-label="Quantity">10</td>
      <td data-label="CashRefunded">4032</td>
      
    </tr>
    <tr>
      <td data-label="MealID">M000532</td>
      <td data-label="Quantity">10</td>
      <td data-label="CashRefunded">4032</td>
      
    </tr>
    <tr>
      <td data-label="MealID">M0005320</td>
      <td data-label="Quantity">10</td>
      <td data-label="CashRefunded">4032</td>
      
    </tr>
    <tr>
      <td data-label="MealID">M0005321</td>
      <td data-label="Quantity">10</td>
      <td data-label="CashRefunded">4032</td>
      
    </tr>
    <tr>
      <td data-label="MealID">M0005322</td>
      <td data-label="Quantity">10</td>
      <td data-label="CashRefunded">4032</td>
      
    </tr>
    <tr>
      <td data-label="MealID">M0005323</td>
      <td data-label="Quantity">10</td>
      <td data-label="CashRefunded">4032</td>
      
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