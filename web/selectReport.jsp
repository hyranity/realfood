<%-- 
    Document   : selectReport
    Created on : Apr 15, 2019, 12:36:01 PM
    Author     : Richard Khoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link href="CSS/selectReport.css" rel="stylesheet">
<link href="CSS/commonStyles.css" rel="stylesheet">

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Select Report</title>
    </head>
    <body>
        <!--element start here-->
        <div class="containerBox">
            <!--<h2>Report</h2>-->
            <div class="reportGenerate">
                <h1>Generate Report</h1>
                <p> Select What Report You Want To Generate !</p>

                <form action="#">
                    
                    <select class="formInput" name="select1" id="select1">
                        <option selected value="1">--Select--</option>
                        <option value="1" id="">Daily Transaction Report</option>
                        <option value="1">Exception Report</option>
                        <option value="1">Monthly Sales Report</option>
                        <option value="2">Summary Report</option>
                    </select>
                    
                        <!--Daily Transaction Report / Exception Report / Monthly Report-->
                        <select class="formInput"name="select2" id="select2">

                        <option value="1">January</option>
                        <option value="1">February</option>
                        <option value="1">March</option>
                        <option value="1">April</option>
                        <option value="1">May</option>
                        <option value="1">June</option>
                        <option value="1">July</option>
                        <option value="1">August</option>
                        <option value="1">September</option>
                        <option value="1">October</option>
                        <option value="1">November</option>
                        <option value="1">December</option>
                        
                        <!--Summary Report-->
                        <option value="2"> --Select-- </option>
                        <option value="2">January - June</option>
                        <option value="2">July - December</option>
                         </select>
                        <div>
                        <input class="formInput" type="number" minlength="1" maxlength="2" placeholder="day"/>
                        </div>
                        <select class="formInput">
                            <option selected> --Select-- </option>
                            <option>2010</option>
                            <option>2011</option>
                            <option>2012</option>
                            <option>2013</option>
                            <option>2014</option>
                            <option>2015</option>
                            <option>2016</option>
                            <option>2017</option>
                            <option>2018</option>
                            <option>2019</option>
                            
                        </select>
                        
                        <a href="dashboardManager.jsp"><div class="nextButton" action="dashboardManager.jsp" type="submit" >Back</div></a>
                        <button class="nextButton" type="submit" target="_blank" value="Generate Report"/>Generate Report</button>
                        
                </form>
                
            </div>
        </div>
<!--element end here-->

    </body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
    <script>
  $(document).ready(function() {
  $('#select2').append('<option value="1" selected="selected"> --Select-- </option>');
  var options = $("#select1").data('options').filter('[value=' + id + ']');
  $('#select2').html(options);
        });
        
        $("#select1").change(function() {
  if ($(this).data('options') === undefined) {
    /*Taking an array of all options-2 and kind of embedding it on the select1*/
    $(this).data('options', $('#select2 option').clone());
  }
  var id = $(this).val();
  var options = $(this).data('options').filter('[value=' + id + ']');
  $('#select2').html(options);
});
    </script>
</html>
