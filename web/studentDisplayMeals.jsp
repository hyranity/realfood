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
        <link href="CSS/studentDisplayMeals.css" rel="stylesheet">
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="CSS/student.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <link href="CSS/students.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Choose meals.</title>
    </head>
    <body>
        <div class="stepsContainer">
            <h1>steps</h1>
            <div class="steps">
                <div>1. Select a date</div>
                <div class="currentStep">2. Choose meals.</div>
                <div>3. Edit particulars</div>
                <div>4. Payment</div>
            </div>
        </div>
        <h1 class="title">Choose meals.</h1>
        <h5 id="subtitle">Click on the meals you would like to have.</h5>
        
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
                            <input type="checkbox" id="cbox1"/>
                            <label class="meal" for="cbox1">
                                <h5>Onion burger</h5>
                                <div style="position: relative;">
                                    <h6 class="breakfast">Breakfast</h6>
                                    <img src="Images/sebastien-marchand-232356-unsplash.jpg" alt=""/>
                                </div>
                                <p class="description">Super awesome food, sprinkled with cheese.</p>
                                <p class="calories">1920 Calories</p>

                                <div class="foodPart">
                                    <p class="componentTitle">Consists of:</p>
                                    <p class="component">Chicken slices, Lssssssss sssssssssssssssssettuce, Tomatoes, Pickles, Cheese</p>
                                </div>
                                <p class="price">1200 credits</p>
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
                            </label>
                        </td>
                    </tr>
                </table>

            </div>
            <div class="nextButtonDiv">
                <button class="nextButton">Next step</button>
            </div>
        </form>
        <h6 class="credits">1000 credits</h6>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="studentDisplayMeals.js" type="text/javascript"></script>
    <!--Footer-->
    <footer class="footerContainer" style="margin-top: 200px;">
         
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
