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
            <input type="text" id="myInput" name="query" onkeyup="myFunction()" placeholder="search..." class="searchBar"/>
        </form>

        <!-- Table -->
        <form action="#" method="post" id="mealOrder">
            <div class="mealsContainer">
                <table id="myTable">
                    <tr>
                        <td>
                            <a href="DisplayFoodSelectionServlet"> <label class="meal" id="add">+</label></a>
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
            <div><a class="nextButton" href="dashboardCanteenStaff.jsp" type="submit" >Back</a></div><br/><br/>
        </form>
        
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="studentDisplayMeals.js" type="text/javascript"></script>
    <script src="searchFilter.js" type="text/javascript"></script>
</html>
