<%-- 
    Document   : notification
    Created on : Mar 31, 2019, 9:40:41 PM
    Author     : mast3
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="Model.Student"%>
<%@page import="Model.*"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/notification.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <link href="CSS/students.css" rel="stylesheet">
        <link href="CSS/recordInfo.css" rel="stylesheet">
        <title>My Notifications</title>
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
                
                Student stud = (Student) session.getAttribute("stud");
                int credits = stud.getCredits();    // Obtain student's credits amount
                List<Notification> nList = new ArrayList();
                try {
                    nList = (List<Notification>) session.getAttribute("nsList");
                } catch (Exception e) {
                    // This will be triggered if the page is accessed directly, hence redirect to dashboard
                    response.sendRedirect("studentDashboard.jsp");
                }

        %>
        <h1>My Notifications</h1>
        <h5>Here you can view all the notifications you have received. (Those that you have read & understood are green)</h5>
        <div class="notificationContainer">

            <%                for (Notification ns : nList) {
                    String brief = ns.getDescription().substring(0, 25) + "....";
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(ns.getDateissued());
                    String hour = "";
                    
                    if(cal.get(Calendar.HOUR) == 0)
                        hour = "12";
                    else
                        hour= cal.get(Calendar.HOUR) + "";
                    
                    String marker = "";
                    if (cal.get(Calendar.AM_PM) == cal.get(Calendar.PM)) {
                        marker = "PM";
                    } else {
                        marker = "AM";
                    }
                    String displayTime = cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR) + " @ " + hour + " " + cal.get(Calendar.MINUTE) + marker;

                    if (ns.getIsread()) {
            %>
            <div class="notification" style="background-color: lightgreen;" id="n<%=ns.getNotificationid()%>" data-notificationid="no<%=ns.getNotificationid()%>" >
                <h5 style="background-color: darkgreen;"><%=ns.getTitle()%></h5>
                <p style="background-color: lightgreen; border-color: lightgreen;"><%=brief%></p>
                <p class="date" style="background-color: darkgreen">12/2/19</p>
            </div>
                <div class="notificationOverlay" id="no<%=ns.getNotificationid()%>" style="background-color: darkgreen; color: white;">
                <h3><%=ns.getTitle()%></h3>
                <p class="date"><%=displayTime%></p>
                <p class="description"><%=ns.getDescription()%></p>
                <div class="close" data-notificationid="no<%=ns.getNotificationid()%>">x</div>
                </div><br/>
            <%
            } else {
            %>
            <div class="notification" id="n<%=ns.getNotificationid()%>" data-notificationid="no<%=ns.getNotificationid()%>" >
                <h5><%=ns.getTitle()%></h5>
                <p><%=brief%></p>
                <p class="date">12/2/19</p>
            </div>
                <div class="notificationOverlay" id="no<%=ns.getNotificationid()%>">
                <h3><%=ns.getTitle()%></h3>
                <p class="date"><%=displayTime%></p>
                <p class="description"><%=ns.getDescription()%></p>
                <div class="close" data-notificationid="no<%=ns.getNotificationid()%>">x</div>
                <a href="ReadNotification?nsId=<%=ns.getNotificationid()%>"><div class="readBt">I have read and understood this.</div></a>
            </div><br/>
            <%
                }
            %>


            
            <%
                }
            %>

            <div><br/><br/><a class="nextButton" href="LoadStudentDashboard" type="submit" >Back</a></div>
        </div>
        <div class="coverOverlay"></div>
        <!-- Display student's credits -->
            <h6 class="credits"><%=credits%> credits</h6>
        <%}%>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {

<!--  The following code allows a "disabling" overlay -->
            $(".notification").click(function () {
                var id = $(this).data("notificationid");
                id = "#" + id;
                $(id).css("display", "block");

                $(".coverOverlay").css("display", "block");
                $(id).css("z-index", "1");
            });

            $(".close").click(function () {
                var id = $(this).data("notificationid");
                id = "#" + id;
                $(id).css("display", "none");
                $(".coverOverlay").css("display", "none");
                $(id).css("z-index", "0");
            });
        });
    </script>
</html>
