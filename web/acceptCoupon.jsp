<%-- 
    Document   : acceptCoupon
    Created on : Apr 1, 2019, 9:55:11 PM
    Author     : Richard Khoo
--%>

<!DOCTYPE HTML>
<html>
<head>
        <link href="CSS/acceptCoupon.css" rel="stylesheet">
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/recordInfo.css" rel="stylesheet">
         <link href="CSS/commonStyles.css" rel="stylesheet">

</head>
<body>
<div class="containerBox">
	<!--<h2>Reset Password Form</h2>-->
	<div class="acceptCoupon">
		<h1>Coupon Redeem</h1>
		<p> Please Enter The Coupon Code.</p>
		<form>
                    <input type="text" id="couponCode" value="" placeholder="Coupon" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Coupon';}" onclick="" required>
                    
                    
                    <input type="submit" value="Redeem"/><br/>
                    <a href="displayStaff.jsp"><div class="back">Back</div></a>
		</form>
	</div>
</div>
    

</body>

</html>
