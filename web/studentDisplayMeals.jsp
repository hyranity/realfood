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
              <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Choose a Meal.</title>
    </head>
    <body>
        <div class="stepsContainer">
            <h1>steps</h1>
            <div class="steps">
                <div>1. Select a date</div>
                <div class="currentStep">2. Choose a meal</div>
                <div>3. Edit particulars</div>
                <div>4. Payment</div>
            </div>
        </div>
        <form action="">
        <div class="mealsContainer">
            
           <input type="checkbox" id="cbox1"/>
            <label class="meal" for="cbox1">
                    <h6 class="breakfast">Breakfast</h6>
                    <h5>Onion Burger</h5>
                    <img src="Images/sebastien-marchand-232356-unsplash.jpg" alt=""/>
                    <p class="description">Super awesome food, sprinkled with cheese.</p>
                    <p class="componentTitle">Consists of:</p>
                    <p class="component">Chicken slices, Lettuce, Tomatoes, Pickles, Cheese</p>
                    <p class="price">1200 credits</p>
            </label>
            
            <input type="checkbox" id="cbox2"/>
            <label class="meal" for="cbox2">
                    <h6 class="lunch">Lunch</h6>
                    <h5>Spaghetti</h5>
                    <img src="Images/jorge-zapata-44723-unsplash.jpg" alt=""/>
                    <p class="description">With the power of the flour, this is perfection realized</p>
                    <p class="componentTitle">Consists of:</p>
                    <p class="component">Chicken, Cheese powder, Onion, Salt, Vinegar</p>
                    <p class="price">500 credits</p>
            </label>
            
        </div>
        </form>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="studentDisplayMeals.js" type="text/javascript"></script>
</html>
