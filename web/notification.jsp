<%-- 
    Document   : notification
    Created on : Mar 31, 2019, 9:40:41 PM
    Author     : mast3
--%>

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
            
            String permission = (String) session.getAttribute("permission");
            
            // If user is not logged in, redirect to login page
            if (permission == null) {
                request.setAttribute("errorMsg", "Please login.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            else {
                // Allow student only
                if(!permission.equalsIgnoreCase("student")){
                     request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
        %>
        <h1>My Notifications</h1>
        <h5>Here you can view all the notifications you have received.</h5>
        <div class="notificationContainer">


            <div class="notification" id="n1" data-notificationid="no1" >
                <h5>Meal Discontinued</h5>
                <p>Spaghetti Bolognese has been discontinued. You have been...</p>
                <p class="date">12/2/19</p>
            </div>
            
            <div class="notificationOverlay" id="no1">
                <h3>Meal Discontinued</h3>
                <p class="date">12/2/19</p>
                <p>Spaghetti Bolognese has been discontinued. You have been refunded 3000 credits based on your current orders. We are deeply sorry for the inconvenience caused.</p>
                <div class="close" data-notificationid="no1">x</div>
                <a href=""><div class="readBt">I have read and understood this.</div></a>
            </div>
            <div><br/><br/><button class="nextButton" href="" type="submit" >Back</button></div>
        </div>
        <div class="coverOverlay"></div>
        <%}%>
    </body>
     <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        $(document).ready(function(){
            
            <!--  The following code allows a "disabling" overlay -->
            $(".notification").click(function(){
                var id = $(this).data("notificationid");
                id = "#" + id;
                $(id).css("display","block");
                
                $(".coverOverlay").css("display","block");
                $(id).css("z-index","1");
            });
            
            $(".close").click(function(){
                var id = $(this).data("notificationid");
                id = "#" + id;
                $(id).css("display","none");
                $(".coverOverlay").css("display","none");
                $(id).css("z-index","0");
            });
        });
    </script>
</html>
