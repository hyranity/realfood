<%-- 
    Document   : forgetPassword
    Created on : Mar 31, 2019, 9:14:44 PM
    Author     : Richard Khoo
--%>

<!DOCTYPE HTML>
<html>
<head>
<link href="CSS/forgotPassword.css" rel="stylesheet">

</head>
<body>
<div class="containerBox">
	<!--<h2>Reset Password Form</h2>-->
	<div class="forgetPassword">
		<h1>Forgot Password</h1>
		<p> Please Enter Your Email To Reset Your Password.</p>
		<form>
                    <input type="email" value="Email address" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Email address';}" required>
			<input type="submit" value="Reset my Password">
		</form>
	</div>
</div>

</body>
</html>
