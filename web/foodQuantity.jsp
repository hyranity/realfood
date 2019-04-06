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
            <h1>steps</h1>
            <div class="steps">
                <div>1. Select food components.</div>
                <div class="currentStep">2. Edit food quantity</div>
                <div>3. Finalize meal details</div>
            </div>
        </div>

        <h1 class="title">Edit food quantity</h1>
        <h5 id="subtitle">Modify the quantity of the food components.</h5>

        <div class="mainContainer">
            <div class="recordQuantity">
                <div class="frontPart">
                    <p class="name" style="color: black;">French Fries</p>
                </div>
                <div class="quantityEditor">
                    <p class="value" id="calF0001" data-calories="500">500 calories</p>
                    <p class="symbol" id="subF0001" onclick="subtract()">-</p>
                    <input type="number" class="quantity" id="F0001" value="1" disabled="">
                    <p class="symbol" id="addF0001">+</p>
                </div>
            </div>
            <br/>
            <div class="recordQuantity">
                <div class="frontPart">
                    <p class="name" style="color: black;">French Fries</p>
                </div>
                <div class="quantityEditor">
                    <p class="value" id="calF0002" data-calories="500">500 calories</p>
                    <p class="symbol" id="subF0002" onclick="subtract()">-</p>
                    <input type="number" class="quantity" id="F0002" value="1" disabled="">
                    <p class="symbol" id="addF0002">+</p>
                </div>
            </div>
            <br/>
            <div class="total">
                <p id="totalCal">Total: 1400 calories</p>
            </div>
        </div>
        <div class="nextButtonDiv">
            <button class="nextButton">Next step</button>
        </div>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
                        $(document).ready(function () {
                            $(".symbol").click(function (event) {
                                var id = event.target.id;
                                symbolId = id.substring(0, 3);
                                var targetId = id.substring(3);
                                var quantity = parseInt($("#" + targetId).val());

                                // Update quantity
                                if (symbolId == "add") {
                                    quantity++;
                                } else {
                                    if (quantity != 1)
                                        quantity--;
                                }
                                $("#" + targetId).val(quantity);
                                var caloriesId = "#cal" + targetId;
                                var calories = quantity * parseInt($(caloriesId).data("calories"));
                                $("#cal" + targetId).text(calories + " calories");

                                var sum = 0;

                                $(".value").each(function () {
                                    var caloriesId = "#cal" + targetId;
                                    var calories = quantity * parseInt($(caloriesId).data("calories"));

                                    sum += parseInt($(this).html());
                                });


                                $("#totalCal").html("Total: " + sum + " calories");

                            });
                        });
    </script>
</html>
