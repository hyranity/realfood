<%-- 
    Document   : notification
    Created on : Mar 31, 2019, 9:40:41 PM
    Author     : mast3
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="Model.Notificationstudent"%>
<%@page import="Model.Student"%>
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
                List<Notificationstudent> nsList = new ArrayList();
                try {
                    nsList = (List<Notificationstudent>) session.getAttribute("nsList");
                } catch (Exception e) {
                    // This will be triggered if the page is accessed directly, hence redirect to dashboard
                    response.sendRedirect("studentDashboard.jsp");
                }

        %>
        <h1>My Notifications</h1>
        <h5>Here you can view all the notifications you have received. (Those that you have read & understood are green)</h5>
        <div class="notificationContainer">

            <%                for (Notificationstudent ns : nsList) {
                    String brief = ns.getNotificationid().getDescription().substring(0, 25) + "....";
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(ns.getNotificationid().getDateissued());
                    String hour = "";
                    
                    if(cal.get(Calendar.HOUR) == 0)
                        hour = "12";
                    else
                        hour= cal.get(Calendar.HOUR) + 1 + "";
                    
                    String marker = "";
                    if (cal.get(Calendar.AM_PM) == cal.get(Calendar.PM)) {
                        marker = "PM";
                    } else {
                        marker = "AM";
                    }
                    String displayTime = cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR) + " @ " + hour + " " + cal.get(Calendar.MINUTE) + marker;

                    if (ns.getIsread()) {
            %>
            <div class="notification" id="n<%=ns.getNotificationid().getNotificationid()%>" data-notificationid="no<%=ns.getNotificationid().getNotificationid()%>" >
                <h5 style="background-color: darkgreen;"><%=ns.getNotificationid().getTitle()%></h5>
                <p style="background-color: lightgreen;"><%=brief%></p>
                <p class="date" style="background-color: darkgreen">12/2/19</p>
            </div>
            <%
            } else {
            %>
            <div class="notification" id="n<%=ns.getNotificationid().getNotificationid()%>" data-notificationid="no<%=ns.getNotificationid().getNotificationid()%>" >
                <h5><%=ns.getNotificationid().getTitle()%></h5>
                <p><%=brief%></p>
                <p class="date">12/2/19</p>
            </div>
            <%
                }
            %>


            <div class="notificationOverlay" id="no<%=ns.getNotificationid().getNotificationid()%>">
                <h3><%=ns.getNotificationid().getTitle()%></h3>
                <p class="date"><%=displayTime%></p>
                <p><%=ns.getNotificationid().getDescription()%></p>
                <div class="close" data-notificationid="no<%=ns.getNotificationid().getNotificationid()%>">x</div>
                <a href="ReadNotification?nsId=<%=ns.getNotificationstudentid()%>"><div class="readBt">I have read and understood this.</div></a>
            </div>
            <%
                }
            %>

            <div><br/><br/><button class="nextButton" href="dashboardStudent.jsp" type="submit" >Back</button></div>
        </div>
        <div class="coverOverlay"></div>
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
