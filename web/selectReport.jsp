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
    <body style="text-align: center;">
        <!--element start here-->
        <div class="containerBox">
            <!--<h2>Report</h2>-->
            <div class="reportGenerate" style="text-align: center; display: inline-block;">
                <h1>Generate Report</h1>
                <p> Select What Report You Want To Generate !</p>
                <div class="errorMsg">${errorMsg}</div>
                
                <form action="#" id="formForm">
                    <select class="formInput" name="select1" id="reportType">
                        <option selected value="">REPORT TYPE</option>
                        <option value="1" id="daily">Daily Transaction Report</option>
                        <option value="2" id="exception">Exception Report</option>
                        <option value="3" id="monthly">Monthly Sales Report</option>
                        <option value="4" id="semiannual">Summary Report</option>
                    </select>

                    <!--Daily Transaction Report / Exception Report / Monthly Report-->
                    <select class="formInput" name="month" id="monthchoice" style="display: none;">
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

                    <!--Summary Report-->
                    <select class="formInput" name="selection" id="semichoice" style="display: none;">
                        <option value=""> SEMIANNUAL SELECTION </option>
                        <option value="firstHalf">January - June</option>
                        <option value="secondHalf">July - December</option>
                    </select>
                    
                    <div>
                        <input type="number" value="1" style="display:none;" class="formInput" name="day" id="day"  pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])" placeholder="DAY" required/>
                    </div>
                    
                    <select class="formInput" id="year" name="year" style="display:none;">
                        <option selected value=""> YEAR </option>
                        <option value="2010">2010</option>
                        <option value="2011">2011</option>
                        <option value="2012">2012</option>
                        <option value="2013">2013</option>
                        <option value="2014">2014</option>
                        <option value="2015">2015</option>
                        <option value="2016">2016</option>
                        <option value="2017">2017</option>
                        <option value="2018">2018</option>
                        <option value="2019">2019</option>

                    </select>

                    <a href="dashboardManager.jsp"><div class="backBt" action="dashboardManager.jsp" type="submit" >Back</div></a>
                    <button class="nextButton" type="submit" value="Generate Report"/>Generate Report</button>

                </form>

            </div>
        </div>
        <!--element end here-->

    </body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $('#select2').append('<option value="1" selected="selected"> --Select-- </option>');
            var options = $("#select1").data('options').filter('[value=' + id + ']');
            $('#select2').html(options);
        });

        // Code thanks to https://stackoverflow.com/questions/1953063/detect-when-a-specific-option-is-selected-with-jquery
        $("#reportType").change(function () {
            var id = $(this).find("option:selected").attr("id");

            switch (id) {
                case "daily":
                    $("#monthchoice").css("display", "block");
                    $("#semichoice").css("display", "none");
                    $("#day").css("display", "block");
                    $("#year").css("display", "block");
                    $("#formForm").attr("action", "DailyTransactionReport");
                    break;
                case "monthly":
                    $("#monthchoice").css("display", "block");
                    $("#semichoice").css("display", "none");
                    $("#day").css("display", "none");
                    $("#year").css("display", "block");
                    $("#formForm").attr("action", "MonthlySalesReport");
                    break;
                case "exception":
                    $("#monthchoice").css("display", "block");
                    $("#semichoice").css("display", "none");
                    $("#day").css("display", "none");
                    $("#year").css("display", "block");
                    $("#formForm").attr("action", "ExceptionReport");
                    break;
                case "semiannual":
                    $("#monthchoice").css("display", "none");
                    $("#semichoice").css("display", "block");
                    $("#day").css("display", "none");
                    $("#year").css("display", "block");
                    $("#formForm").attr("action", "SummaryReport");
                    break;
                default:
                    $("#monthchoice").css("display", "none");
                    $("#semichoice").css("display", "none");
                    $("#day").css("display", "none");
                    $("#year").css("display", "none");
                    $("#formForm").attr("action", "#");
                    break;
            }
        });

        $("#select1").change(function () {
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
