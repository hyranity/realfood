<%-- 
    Document   : topUp
    Created on : Mar 31, 2019, 11:06:40 PM
    Author     : Richard Khoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><head>
        <title>Top Up</title>

        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/topUp.css" rel="stylesheet" type="text/css">

    </head>
    <body>
        <div class="mainContainer">
            <h1>
                Credit Point Top Up
            </h1>

            <div class="topUpForm">
                <input type="text" maxlength="10" placeholder="Student ID" id="studentid" required/>
                <br/>
                
                <!-- Number validation was made possible thanks to klent @  https://stackoverflow.com/questions/28950814/how-to-prevent-a-user-from-entering-negative-values-in-html-input -->
                <input type="number" oninput="validity.valid || (value = '');" min="0" step="1" maxlength="10" placeholder="Cash Amount" id="cashAmt" required/>
                
                <br/>
                <button type="submit" form="topUpForm" value="Submit">Top up</button>
                <div class="amountText">
                    credit amount
                    <p class="creditAmount">0</p>
                </div>
            </div>

        </div>

        <a href="displayStaff.jsp"><div class="back">back</div></a>

    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#cashAmt").on("input", function () {
                //Code is possible thanks to Neha Jain @ https://stackoverflow.com/questions/18510845/maxlength-ignored-for-input-type-number-in-chrome
                //Checks if the number is too long
                if (this.value.length > this.maxLength) {
                    this.value = this.value.slice(0, this.maxLength);
                }
                var $creditAmt = $("#cashAmt").val() * 100;
                $(".creditAmount").text($creditAmt);
            });
        });
    </script>
</html>