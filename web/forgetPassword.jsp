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
		<h1>Reset Password</h1>
		<p> Please Enter New Password To Reset Your Password.</p>
		<form>
                    <input type="password" id="password" value="" placeholder="New Password" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Email address';}" required>
                    <input type="password" value="" id="confirmationPassword" placeholder="Confirmation Password" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Email address';}" required>
                    <input type="checkbox" onclick="myFunction()" id="showPass" /><label style="margin-left: 90px;" for="showPass" id="showPassLabel">Click here to show password</label><br>
                    <input type="submit" value="Reset my Password"/>
		</form>
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
