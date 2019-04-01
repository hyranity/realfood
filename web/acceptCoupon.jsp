<%-- 
    Document   : acceptCoupon
    Created on : Apr 1, 2019, 9:55:11 PM
    Author     : Richard Khoo
--%>

<!DOCTYPE HTML>
<html>
<head>
<link href="CSS/acceptCoupon.css" rel="stylesheet">

</head>
<body>
<div class="containerBox">
	<!--<h2>Reset Password Form</h2>-->
	<div class="acceptCoupon">
		<h1>Coupon Redeem</h1>
		<p> Please Enter The Coupon Code.</p>
		<form>
                    <input type="text" id="couponCode" value="" placeholder="Coupon" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Coupon';}" required>
                    <br/>
                    <label style="color: green; display: block">Redeem Successfully !!</label>
                    
                    
                    <input type="submit" value="Redeem"/>
		</form>
	</div>
</div>
    
</body>
</html>
