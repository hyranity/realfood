<%-- 
    Document   : studentDisplayMeals
    Created on : Mar 17, 2019, 3:45:43 PM
    Author     : mast3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="CSS/studentDisplayMeals.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <link href="CSS/manageMeals.css" rel="stylesheet">
        <link href="CSS/manageRecords.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Choose meals.</title>
    </head>
    <body>
        <h1 class="title">Manage meals.</h1>
        <h5 id="subtitle">Here you can add or edit meals. To view meals' specific info, you will need to press "edit" first.</h5>
        <!-- Search bar -->
        <form class="searchForm">
            <input type="text" name="query" placeholder="search..." class="searchBar"/>
        </form>

        <!-- Table -->
        <form action="#" method="post" id="mealOrder">
            <div class="mealsContainer">
                <table>
                    <tr>
                        <td>
                            <label class="meal" id="add">+</label>
                        </td>
                        <td>
                            <input type="checkbox" id="cbox1"/>
                            <label class="meal" for="cbox1">
                                <h5>Onion burger</h5>
                                <div style="position: relative;">
                                    <h6 class="breakfast">Breakfast</h6>
                                    <img src="Images/sebastien-marchand-232356-unsplash.jpg" alt=""/>
                                </div>
                                <p class="description">Super awesome food, sprinkled with cheese.</p>
                                <p class="calories">1920 Calories</p>
                                <a href=""><div class="editButton">Edit</div></a>
                                <div class="foodPart">
                                    <p class="componentTitle">Consists of:</p>
                                    <p class="component">Chicken slices, Lssssssss sssssssssssssssssettuce, Tomatoes, Pickles, Cheese</p>
                                </div>
                                <p class="price">1200 credits</p>
                                <a href=""><div class="editMealButton">Edit</div></a>
                            </label>
                        </td>
                        <td>
                            <input type="checkbox" id="cbox2"/>
                            <label class="meal" for="cbox2">
                                <h5>Spaghetti</h5>
                                <div style="position: relative;">
                                    <h6 class="lunch">Lunch</h6>
                                    <img src="Images/jorge-zapata-44723-unsplash.jpg" alt=""/>
                                </div>

                                <p class="description">With the power of the flour, this is perfection realized</p>
                                <p class="calories">1920 Calories</p>
                                <div class="foodPart">
                                    <p class="componentTitle">Consists of:</p>
                                    <p class="component">Chicken slices, Lssssssss sssssssssssssssssettuce, Tomatoes, Pickles, Cheese</p>
                                </div>
                                <p class="price">500 credits</p>
                                <a href=""><div class="editMealButton">Edit</div></a>
                            </label>
                        </td>
                        <td>
                            <input type="checkbox" id="cbox2"/>
                            <label class="meal" for="cbox2">
                                <h5>Spaghetti</h5>
                                <div style="position: relative;">
                                    <h6 class="lunch">Lunch</h6>
                                    <img src="Images/jorge-zapata-44723-unsplash.jpg" alt=""/>
                                </div>

                                <p class="description">With the power of the flour, this is perfection realized</p>
                                <p class="calories">1920 Calories</p>
                                <div class="foodPart">
                                    <p class="componentTitle">Consists of:</p>
                                    <p class="component">Chicken slices, Lssssssss sssssssssssssssssettuce, Tomatoes, Pickles, Cheese</p>
                                </div>
                                <p class="price">500 credits</p>
                                <a href=""><div class="editMealButton">Edit</div></a>
                            </label>
                        </td>
                    </tr>
                </table>

            </div>
        </form>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="studentDisplayMeals.js" type="text/javascript"></script>
    <!--Footer-->
    <footer class="footerContainer">
         
    <section class="footerBottom">

    <div class="footerBottom" style="border:0px; font-size: 10px;">   
    Copyright©2019 RealFood - All Rights Reserved -
    </div>
<i style="font-size:14px" class="fa">&#xf230</i> <a href="https://www.facebook.com/RealFood-2569784913093353/?ref=br_tf&epa=SEARCH_BOX" style="font-size: 12px">FACEBOOK</a>
<i style="font-size:14px;color:red" class="fa">&#xf2b3</i> <a href="https://www.google.com/gmail/" style="font-size: 12px">Gmail</a>
<span style="color: white;font-size: 12px"><b>Email:</b> johannljx-sm17@student.tarc.edu.my | khootw-sm17@student.tarc.edu.my <b>Hotline:</b> 1600 99 8888 <b>Contact Number:</b> +60123456789</span>
  </section>
        
</footer>
</html>
