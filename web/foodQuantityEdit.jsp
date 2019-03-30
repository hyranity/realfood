<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/quantityModification.css" rel="stylesheet">
        <link href="CSS/mealDetails.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <title>Edit Particulars</title>
    </head>
    <body>
        <!-- Steps -->
        <div class="stepsContainer">
            <h1>Edit a Meal</h1>
            <div class="steps">
                <div>1. Modify food components.</div>
                <div class="currentStep">2. Modify food quantity</div>
                <div>3. Modify meal details</div>
            </div>
        </div>

        <h1 class="title">Modify food quantity</h1>
        <h5 id="subtitle">Modify the quantity of the food components.</h5>

        <div class="mainContainer">
            <div class="recordQuantity">
                <div class="frontPart">
                    <p class="name">French Fries</p>
                </div>
                <div class="quantityEditor">
                    <p class="value">500 calories</p>
                    <p class="symbol" id="sub">-</p>
                    <p class="quantity">x2</p>
                    <p class="symbol" id="add">+</p>
                </div>
            </div>
            <br/>
            <div class="recordQuantity">
                <div class="frontPart">
                    <p class="name">Cheese Sauce</p>
                </div>
                <div class="quantityEditor">
                    <p class="value">200 calories</p>
                    <p class="symbol" id="sub">-</p>
                    <p class="quantity">x23</p>
                    <p class="symbol" id="add">+</p>
                </div>
            </div>
            <br/>
            <div class="total">
                <p>Total: 1400 calories</p>
            </div>
        </div>
        <div class="nextButtonDiv">
            <button class="nextButton">Next step</button>
        </div>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</html>
