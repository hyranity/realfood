<%@page import="util.Auto"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Calendar"%>
<%@page import="Model.Meal"%>
<%@page import="Model.Ordermeal"%>
<%@page import="Model.Studentorder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Redeem Order</title>
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/recordInfo.css" rel="stylesheet">
        
        
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <link href="CSS/redeemMeals.css" rel="stylesheet">
        <link href="CSS/viewOrder.css" rel="stylesheet">
    </head>
    <body>
        <%
            request.getSession(false);

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
            if (!permission.equalsIgnoreCase("canteenStaff")) {
                request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {
                Studentorder so = new Studentorder();

                try {
                    so = (Studentorder) session.getAttribute("studentOrderRedeem");
                } catch (Exception e) {
                    // This will be triggered if the page is accessed directly, hence redirect to dashboard
                    request.setAttribute("errorMsg", "Oops! Could not obtain order.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
                }

                String orderId = so.getOrderid();

                // Set the chosen date
                Calendar cal = Calendar.getInstance();
                cal.setTime(so.getChosendate());
                String dateStr = cal.get(Calendar.DAY_OF_MONTH) + " " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + ", " + cal.get(Calendar.YEAR) + " (" + cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH) + ")";
                String couponCode = so.getCouponcode();
                int totalPrice = so.getTotalprice();
                
                Calendar today = Calendar.getInstance();
                
                boolean expired = false;
                boolean stillEarly = false;
                
                if(Auto.daysBetween(today, cal)<0)
                    expired = true;
                
              if(Auto.daysBetween(today, cal)>0)
                    stillEarly = true;
               
        %>
        <div class="outsideContainer" style="text-align: center;">

            <h1>Redeem Order</h1>
            <h5 id="subtitle">Select the meals to redeem by clicking their names, then click "redeem".</h5>
            <br/>
            <div class="errorMsg">${errorMsg}</div>
            <div class="successMsg">${successMsg}</div>
            
            <div class="orderContainer" >
                
                <br/>
                <div class="orderDetails">
                      <%
                    if(so.getIsredeemed()){
                    %>
                    <h2 id="orderId" style="color: green;"><%=orderId%></h2>
                    <div style="color: green; font-weight: bold;">(REDEEMED)</div>
                    
                    <%
                    } else{
                    %>
                    <h2 id="orderId"><%=orderId%></h2>
                    <%}%>
                 
                    <form action="ProcessRedemption">
                    <br/>
                    <%
                        for (Ordermeal om : so.getOrdermealList()) {

                            Meal meal = om.getMealid();
                            String mealName = om.getMealid().getMealname();
                            int quantity = om.getQuantity();
                            int omId = om.getOrdermealid();
                            
                            if(om.getIsredeemed()){
                    %>
                    <input type="checkbox" id="<%=omId%>" value="<%=omId%>" name="chosenMeals" disabled checked>
                    <label  class="mealItem" for="<%=omId%>" ><%=mealName%></label>
                    <%
                    } else if(om.getIscanceled()){
                    %>
                    <input type="checkbox" id="<%=omId%>" value="<%=omId%>" name="chosenMeals" style="background-color: red;" disabled>
                    <label  class="mealItem" for="<%=omId%>" ><%=mealName%></label>
                    <%
                    } else{
                    %>
                    <input type="checkbox" id="<%=omId%>" value="<%=omId%>" name="chosenMeals">
                    <label  class="mealItem" for="<%=omId%>" ><%=mealName%></label>
                    <!-- Display the meal time -->
                    <%
                        }if (meal.getIsbreakfast()) {
                    %>
                    <p class="breakfast">Bkfast</p>
                    <%
                    } else if (meal.getIslunch()) {
                    %>
                    <p class="lunch">Lunch</p>
                    <%
                    } else {
                    %>
                    <p class="allDay">All Day</p>
                    <%
                        }
                    %>

                    <p class="quantity">x<%=quantity%></p>
                    
                    <br/>
                    <%}%>
                    <br/>
                    <br/>
                    <p class="smallText">to be served on</p>
                    <br/>
                    <p class="chosenDate" id="chosenDate"><%=dateStr%></p>
                    <br/>
                    <p class="smallText">total price</p>
                    <br/>
                    <p class="totalPrice" id="totalPrice"><%=totalPrice%> Credits</p>
                    <br/>
                    <p class="smallText">coupon code</p>
                    <br/>
                    <p class="couponCode" id="couponCode"><%=couponCode%></p>
                    <br/>
                    <br/>
                    <%
                    if(stillEarly){
                    %>
                    <input type="submit" value="Redeem date not today" style="background-color: dimgray; color: white;" class="redeemBt" disabled>
                     <%
                    } else{
                    %>
                    <input type="submit" value="Redeem selected meals" class="redeemBt">
                    <%
                    } 
                    %>
                    </form>
                </div>

            </div>
        </div>
        <a href="redeemOrder.jsp"><div class="backBt" style="margin-bottom: 30px;">Back</div></a>
        <%}%>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</html>
