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


/*enable and disable*/
var checkBox = document.getElementById('acceptTerm');
 var submitButton = document.getElementById('submit');
 // when unchecked or checked, run the function
 checkBox.onchange = function(){
if(this.checked){
    submitButton.disabled = false;
} else {
    submitButton.disabled = true;
}

}

/*under contruct*/
/*var userID = document.getElementById("userID").value == "";

//check will be true or false
if (userID.value == "EMP"){ //do something if true}
if(!check){//do something if false}

inline option
<input type="text" onchange="var check=(this.value=='text_value') />

<input type="text" onkeyup="check(this)" />
function check(e){
    var check=("your_word"== e.value);
if (check){ //do something if true}
if(!check){//do something if false}
}
*/