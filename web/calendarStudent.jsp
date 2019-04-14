

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@ page import = "java.util.Calendar" %>
<%@page import="java.util.Date"%>
<%@page import="java.util.GregorianCalendar"%>
<html>
    <head>
        <title>TODO supply a title</title>
        <link href="CSS/calendar.css" rel="stylesheet">
        <link href="CSS/student.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">

        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
            if (!permission.equalsIgnoreCase("student")) {
                request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {
        %>

        <%
            //The following is to set the first day (Sunday or Monday)
            boolean firstDayIsSunday = true;
            String firstDay = "sunday";
            String strMonday = "monday";

            firstDay = request.getParameter("firstDay"); //Used to obtain whether or not first day is Sunday or Monday

            // If the chosen view is Monday as first day, then show Monday as first day. Otherwise, show Sunday as first day.
            try {
                if (firstDay.equalsIgnoreCase("monday")) {
                    firstDayIsSunday = false;
                } else {
                    firstDayIsSunday = true;
                }
            } catch (NullPointerException ex) {
                firstDayIsSunday = true;
            }
            ;
            int adjustmentInt = 0; // Used to adjust the beginning day (Monday or Sunday)

            // If Sunday is the first day, adjust accordingly
            if (firstDayIsSunday) {
                adjustmentInt = 1;
            } else {
                adjustmentInt = 2;
            }

            //declaration of variables
            String[] date = new String[31];
            Calendar cal = Calendar.getInstance();
            Calendar calNextDay = Calendar.getInstance();; //used to find out the next day
            Calendar calDaysBefore = Calendar.getInstance();; //used to find out how many days before today is Sunday
            SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
            calNextDay.add(Calendar.DATE, 1);
            int day = cal.get(Calendar.DAY_OF_MONTH); //used to increment from current day to 30 days later
            int pastDay = 0; //used to increment from the Sunday before until yesterday
            int nextDay = cal.get(Calendar.DAY_OF_MONTH), countDay = 1;

            boolean isToday = true;
            int numOfPastDaysOfWeek = cal.get(Calendar.DAY_OF_WEEK) - adjustmentInt;

            //Converts today's date into a string
            String dynamicTitle = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH) + ", " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + cal.get(Calendar.DAY_OF_MONTH);

            // If today is Sunday and tomorrow is a new week (first day is Monday), then this week must have "past days" blocks.
            if (numOfPastDaysOfWeek < 0) {
                numOfPastDaysOfWeek += 7;
            }
        %>
        <div class="stepsContainer">
            <h1>Make an Order</h1>
            <div class="steps">
                <div class="currentStep">1. Select dates</div>
                <div>2. Choose a meal</div>
                <div>3. Edit particulars</div>
                <div>4. Payment</div>
            </div>
        </div>
        <div class="calendarContainer">

            <h1 class="title">Current day is</h1><h1 class="dynamicTitle" data-date="<%=dynamicTitle%>"><%=dynamicTitle%></h1>
            <br>
            <p class="instruction">Select dates. All dates will share the same order specifications.</p>
            <br>
            <div class="errorMsg">${errorMsg}</div>
            <br>
            <div class="calendar">
                <form action="DateSelectionServlet" method="POST" id="calendarForm">

                    <%
                        if (firstDayIsSunday) {
                    %>
                    <p class="week" style="color:gold;">S</p>
                    <p class="week">M</p>
                    <p class="week">T</p>
                    <p class="week">W</p>
                    <p class="week">T</p>
                    <p class="week">F</p>
                    <p class="week" style="color:gold;">S</p>
                    <%
                    } else {
                    %>

                    <p class="week">M</p>
                    <p class="week">T</p>
                    <p class="week">W</p>
                    <p class="week">T</p>
                    <p class="week">F</p>
                    <p class="week" style="color:gold;">S</p>
                    <p class="week" style="color:gold;">S</p>
                    <%
                        }
                    %>
                    <br>
                    <%
                        // Used to determine the first day of current week's date.
                        calDaysBefore.add(Calendar.DATE, (Calendar.SUNDAY - 1 - numOfPastDaysOfWeek));

                        //displays extra daily "blocks"  to make sure that today's "block" is correct with the day of the week;
                        //these days are at the past, and are therefore being blocked from ordering meals on
                        for (int i = 0; i < numOfPastDaysOfWeek; i++) {
                    %>
                    <div class="day" style="color: darkslategray"><%=calDaysBefore.get(Calendar.DAY_OF_MONTH)%></div>
                    <%
                            calDaysBefore.add(Calendar.DATE, 1);
                            countDay++;
                        }

                        //displays the daily "block" that can be clicked to pre-order meals
                        for (int i = 0; i < 31; i++) {

                            //If one week already, put <br>
                            if ((countDay - 1) % 7 == 0 && countDay > 0 && countDay >= 7) {

                    %>
                    <br>
                    <%                    }
                        // If today, block orders from being made on today
                        if (isToday) {
                    %>
                    <div class="day" style="background-color: darkgoldenrod; color: white;"><%=day%></div>
                    <%
                        //NOTE: This part of the code is to not allow students to order meals the day before

                        //Increment the day
                        cal.add(Calendar.DATE, 1);
                        day = cal.get(Calendar.DAY_OF_MONTH);

                        //Increment the nextDay, which shows what day is after the current day
                        calNextDay.add(Calendar.DATE, 1);
                        nextDay = calNextDay.get(Calendar.DAY_OF_MONTH);

                        countDay++;

                        //If one week already, put <br>
                        if ((countDay - 1) % 7 == 0 && countDay != 0) {
                    %>
                    <br>
                    <%
                        }

                        // Block the next day so that students cannot order on that day
%>
                    <div class="day" style="color: darkslategray"><%=day%></div>
                    <%

                        isToday = false;
                    } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        // If it is a weekend, block the day
                    %>
                    <div class="day" style="color: darkslategray"><%=day%></div>
                    <%
                    } else {  // If it's not today, no need to highlight

                        Date tempDate = cal.getTime();

                        // Only the following days can be clicked.
                        String dateId = "id" + i;
                        String dateValue = sm.format(tempDate);

                    %>
                    <input  type="checkbox" id="<%=dateId%>" name="chosenDates" value="<%=dateValue%>"> 
                    <label for="<%=dateId%>" class="weekday" data-date="<%=date[i]%>"  data-dow="<%=cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH)%>" data-month="<%=cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)%>"  data-day="<%=cal.get(Calendar.DAY_OF_MONTH)%>" data-hasclicked="false" onclick="appendLink()" ><%=day%></label>
                    <%
                        }

                        // If new month....
                        if (nextDay == 1) {

                            // Put some space in between
                    %> 
                    <br>
                    <%                    numOfPastDaysOfWeek = cal.get(Calendar.DAY_OF_WEEK);

                        // If the first day is Monday, golduce the number of "invisible days" to the calendar.
                        // This is because <br> resets the position set previously
                        if (!firstDayIsSunday && numOfPastDaysOfWeek != 0) {
                            numOfPastDaysOfWeek--;
                        }

                        // "Invisible" days are added so the correct day of the week position of the next day is shown
                        for (int j = 0; j < numOfPastDaysOfWeek; j++) {
                    %>

                    <div class="day" style="opacity:0;">x</div>
                    <%
                        }
                    %>

                    <%
                            }
                            
                            //Increment the day
                            cal.add(Calendar.DATE, 1);
                            day = cal.get(Calendar.DAY_OF_MONTH);

                            //Increment the nextDay, which shows what day is after the current day
                            calNextDay.add(Calendar.DATE, 1);
                            nextDay = calNextDay.get(Calendar.DAY_OF_MONTH);

                            countDay++;
                        }
                    %>


                </form>
                <br/>
                <button class="nextButton" href="" type="submit" >Back</button>&nbsp;
                <input class="nextButton" type="submit" form="calendarForm">
            </div>
            <div class="setting">Set the first day as: 
                <br>

                <%
                    if (firstDayIsSunday) {
                %>
                <a href="?firstDay=sunday" style="color: gold;">Sunday</a>
                <a href="?firstDay=monday">Monday</a>
                <%
                } else {
                %>
                <a href="?firstDay=sunday" >Sunday</a>
                <a href="?firstDay=monday" style="color: gold;">Monday</a>
                <%
                    }
                %>
            </div>
        </div>


        <%}%>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="calendarStudent.js" type="text/javascript"></script>

</html>
