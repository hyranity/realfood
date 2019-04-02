<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/quantityModification.css" rel="stylesheet">
        <link href="CSS/students.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <title>Edit Particulars</title>
    </head>
    <body>
        <div class="stepsContainer">
            <h1>steps</h1>
             <div class="steps">
                <div>1. Update meal selection.</div>
                <div class="currentStep">2. Update particulars</div>
                <div>3. Confirm update</div>
            </div>
        </div>

        <h1 class="title">Update particulars</h1>
        <h5 id="subtitle">Modify the quantity of your meals.</h5>

        <div class="mainContainer">
            <div class="recordQuantity">
                <div class="frontPart">
                    <p class="name">Spaghetti Bolognese</p>

                </div>
                <div class="quantityEditor">
                    <p class="value">1500 Credits</p>
                    <p class="symbol" id="sub">-</p>
                    <p class="quantity" data-quantity="5">x2</p>
                    <p class="symbol" id="add">+</p>
                </div>
            </div>
            <br/>
            <div class="recordQuantity">
                <div class="frontPart">
                    <p class="name">Peppermint Ice Cream</p>
                </div>
                <div class="quantityEditor">
                    <p class="value">500 Credits</p>
                    <p class="symbol" id="sub">-</p>
                    <p class="quantity" data-quantity="5">x23</p>
                    <p class="symbol" id="add">+</p>
                </div>
            </div>
            <br/>
            <div class="total">
                <p>Total: 4000 Credits</p>
            </div>
            <h6 class="credits">1000 credits</h6>
        </div>
        <div>
            <button class="nextButton">Back</button>
            <button class="nextButton">Next step</button>
        </div>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</html>
