<%-- 
    Document   : topUp
    Created on : Mar 31, 2019, 11:06:40 PM
    Author     : Richard Khoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><head>
    <title>Top Up</title>
    <link href="CSS/topUp.css" rel="stylesheet" type="text/css">
</head>
<body>

            <h1 class="page-section-heading">
                Credit Point Top Up
            </h1>

            <div class="topupContainer">
                <h2>
                    Student ID
                </h2>

                <div class="topupForm-row">
                    <div class="topupForm-col2">
                        <div class="studentID-group">
                            <div class="area">
                                <input type="text" maxlength="10" placeholder="Student ID" id="studentid" required/>
                            </div>
                        </div>

                    </div>
                    
                    
                </div>
                <div class="value-result">
                            <div class="amountText">
                                Amount Payable
                            </div>

                            <div class="amountNumber" data-bind="text:'RM' + parseFloat(curramount()/100).toFixed(2)">RM50.00</div>

                           
                        </div>
            </div>

        </form>

</body>
</html>
