<%-- 
    Document   : topUp
    Created on : Mar 31, 2019, 11:06:40 PM
    Author     : Richard Khoo
--%>

<%@page import="Model.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><head>
        <title>Top Up</title>

        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">

        <link href="CSS/commonStyles.css" rel="stylesheet" type="text/css"> 
        <link href="CSS/topUp.css" rel="stylesheet" type="text/css">
    </head>
    <body
        <%
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

            // If user is not logged in, redirect to login page
            // Allow student only
            if (!permission.equalsIgnoreCase("canteenStaff") && !permission.equalsIgnoreCase("manager")) {
                request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {

                // Get student details
                Student stud = new Student();
                try {
                    stud = (Student) session.getAttribute("studTopUp");
                } catch (Exception ex) {
                    // Display error messages if any
                    request.setAttribute("errorMsg", "Hmm...we could not read your student ID as input for some reason.");
                    request.getRequestDispatcher("topUpValue.jsp").forward(request, response);
                    return;
                }

                String studName = stud.getFirstname() + " " + stud.getLastname();
                String studId = stud.getStudentid();

        %>
        <div class="mainContainer" style="margin-top: -300px;">
            <h1>Credit Point Top Up</h1>
            <h5 style="font-size: 20px; margin-top: -20px;">Enter top amount amount in RM</h5>

            <div class="errorMsg">${errorMsg}</div>
            <div class="successMsg">${successMsg}</div>

            <!-- Show alerts if any -->


            <form action="TopUpServlet" method="POST" id="topUpForm" name="topUpForm">
                <div class="studInfo">Student name: <%=studName%></div>
                <br/>
                <div class="topUpForm">

                    <input type="text" maxlength="10" placeholder="Student ID" name="studentId" id="studentid" value="<%=studId%>" style="background-color: dimgrey;" readonly/>

                    <br/>

                    <!-- Number validation was made possible thanks to klent @  https://stackoverflow.com/questions/28950814/how-to-prevent-a-user-from-entering-negative-values-in-html-input -->
                    <input type="number" name="cashAmt" oninput="validity.valid || (value = '');" min="0" step="1" maxlength="4" placeholder="Cash Amount (RM)" id="cashAmt" required/>
                    <br/>
                    <div class="amountText">
                        credit amount
                        <p class="creditAmount">0</p>
                         </div>
                    <br/>
                    <button type="submit" form="topUpForm" value="Submit">Top up</button>
                    <br/>

                </div>
            </form>


        </div>

        <a href="dashboardCanteenStaff.jsp"><div class="backBt">Back</div></a>
        <%
            }
        %>
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
