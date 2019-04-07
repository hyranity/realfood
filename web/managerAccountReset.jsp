<%-- 
    Document   : managerAccountReset
    Created on : Apr 7, 2019, 6:19:10 PM
    Author     : Richard Khoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<link href="CSS/resetAccount.css" rel="stylesheet">
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link href="CSS/recordInfo.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
        <link href="CSS/mealDetails.css" rel="stylesheet">
        
</head>
<body>
    
<div class="containerBox">
	<!--<h2>Reset Password Form</h2>-->
	<div class="resetAccount">
		<h1>Reset Manager Account</h1>
		<p> Please Enter To Reset Your Account</p>
		<form>
                    <input type="text" id="staffid" value="" placeholder="Your ID" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'ID Number';}" required>
                    <input type="password" id="password" value="" placeholder="Your Password" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = '';}" required>
                    <input type="password" value="" id="confirmationPassword" placeholder="Retype Your Password Again" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = '';}" required>
                    <input type="checkbox" onclick="myFunction()" id="showPass" /><label style="margin-left: 90px;" for="showPass" id="showPassLabel">Click here to show password</label><br>
                    <input type="submit" value="Reset my Account"/>
		</form>
                <br/><a href="#"><div class="back">Back</div></a>
	</div>

</div>
    <script>
        
/*Show The Password*/
function myFunction() {
  var x = document.getElementById("password");
  var y = document.getElementById("confirmationPassword");
  if (x.type === "password" && y.type === "password") {
    x.type = "text";
    y.type = "text";
  } else {
    x.type = "password";
    y.type = "password";
  }
  
  
   if( document.getElementById("showPass").checked){
        $("#showPassLabel").html("Click here to hide password");
        }
        else{
            $("#showPassLabel").html("Click here to show password");
        }
}

/* Check Both Password is same or Not */
var password = document.getElementById("password")
  , confirm_password = document.getElementById("confirmationPassword");

function validatePassword(){
  if(password.value != confirmationPassword.value) {
    confirmationPassword.setCustomValidity("Your Passwords Does Not Match");
  } else {
    confirmationPassword.setCustomValidity('');
  }
}

password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;
    </script>
</body>
</html>
