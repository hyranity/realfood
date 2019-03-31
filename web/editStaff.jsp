<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Account</title>
        <!-- Attribution: https://fonts.google.com/specimen/Montserrat?selection.family=Montserrat:100,200,400 -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,200,400" rel="stylesheet">
        <link rel="stylesheet" href="CSS/headerFooter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="CSS/recordInfo.css" rel="stylesheet">
        <link href="CSS/commonStyles.css" rel="stylesheet">
    </head>
    <body>
        <div class="outsideContainer">

            <h1>Edit Canteen Staff</h1>
            <h5 id="subtitle">Here's where you can view and edit staff's details.</h5>
            <br/>
            <div class="mainContainer">

                <form action="#" class="form">
                    <a href="#" onclick="confirmRemoval()"> <div class="removal">Discontinue</div></a>
                    <div>
                        <input type="text" value="EMP002994" style="background-color: darkgray;"  id="staffid" readonly/>
                    </div>
                    <div id="nameDiv">
                        <input type="text" id="name" placeholder="First Name" value="Henry" />
                        <input type="text" id="name" placeholder="Last Name" value="Luther"/>
                    </div>
                    <div>
                        <input type="text" value="Male" placeholder="Gender" id="gender"  />
                    </div>
                    <div>
                        <input type="text" value="Joined: 16 March, 2017" id="dateJoined"  style="background-color: darkgray;"  readonly/>
                    </div>
                    <div>
                        <input type="text" value="henryluther@staff.com" placeholder="Email" id="email" />
                    </div>
                    <div>
                        <input type="text" value="3995-29-5344" placeholder="MyKAD" id="myKAD" />
                    </div>

                    <br/>
                    <button type="submit" class="submitBtn">Submit changes</button>
                </form>
                <br/>
            </div>
        </div>
        <a href="displayStaff.jsp"><div class="back">back</div></a>
        <div class="removalConfirmation">
            <h5>Dismiss staff?</h5>
            <p>The staff will not be able to use the system anymore.</p>
            <a href="#"><div class="removalConfirm">Yes</div></a>
            <a href="#"><div class="removalCancel">No</div></a>
        </div>
        <div class="coverOverlay"></div>
    </body>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

                                <!-- Some javascript. It will dynamically change the subtitle based on what the staff hovers over. -->
    <script>
                        $(document).ready(function () {
                        $("#staffid").hover(function () {
                                $("#subtitle").html("That's the staff ID. It uniquely defines the staff. It can't be changed.");
                        $("#subtitle").css("color", "gold");
                        }, function () {
                                $("#subtitle").html("Here's where you can view and edit staff's details.");
                        $("#subtitle").css("color", "white");
                    });
                                $("#nameDiv").hover(function () {
                        $("#subtitle").html("That's the staff's name.");
                                $("#subtitle").css("color", "gold");
                        }, function () {
                        $("#subtitle").html("Here's where you can view and edit staff's details.");
                                $("#subtitle").css("color", "white");
                        });
                                $("#gender").hover(function () {
                        $("#subtitle").html("That's the staff's gender.");
                                $("#subtitle").css("color", "gold");
                        }, function () {
                        $("#subtitle").html("Here's where you can view and edit staff's details.");
                                $("#subtitle").css("color", "white");
                        });
                                $("#dateJoined").hover(function () {
                        $("#subtitle").html("That's when the staff's first joined. No point in editing it.");
                                $("#subtitle").css("color", "gold");
                        }, function () {
                        $("#subtitle").html("Here's where you can view and edit staff's details.");
                                $("#subtitle").css("color", "white");
                        });
                                $("#email").hover(function () {
                        $("#subtitle").html("That's the staff's email.");
                                $("#subtitle").css("color", "gold");
                        }, function () {
                        $("#subtitle").html("Here's where you can view and edit staff's details.");
                                $("#subtitle").css("color", "white");
                        });
                                $("#myKAD").hover(function () {
                        $("#subtitle").html("That's the staff's MyKAD number...you didn't mistype it, right?");
                                $("#subtitle").css("color", "gold");
                        }, function () {
                        $("#subtitle").html("Here's where you can view and edit staff's details.");
                                $("#subtitle").css("color", "white");
                        });
                                $(".removal").hover(function () {
                        $("#subtitle").html("Dismiss the staff");
                                $("#subtitle").css("color", "red");
                        }, function () {
                        $("#subtitle").html("Here's where you can view and edit staff's details.");
                                $("#subtitle").css("color", "white");
                        });
                                $(".removal").click(function confirmRemoval() {
                        $(".removalConfirmation").css("display", "inline-block");
                                $(".outsideContainer :input").prop("disabled", true);
                        });
                                $(".removalCancel").click(function confirmRemoval() {
                        $(".removalConfirmation").css("display", "none");
                                $(".outsideContainer :input").prop("disabled", false);
                        });
                        
        <!--  The following code allows a "disabling" overlay -->
                            $(".removal").click(function () {
                                $(".coverOverlay").css("display", "block");
                                $(".removalConfirmation").css("z-index", "1");
                            });

                            $(".removalCancel").click(function () {
                                $(".coverOverlay").css("display", "none");
                                $(".removalConfirmation").css("z-index", "0");
                            });
                        });
    </script>
    <!--Footer-->
    <footer class="footerContainer">

        <section class="footerBottom">

            <div class="footerBottom" style="border:0px; font-size: 10px;">   
                Copyright©2019 RealFood - All Rights Reserved -
            </div>
            <i style="font-size:14px" class="fa">&#xf230</i> <a href="https://www.facebook.com/RealFood-2569784913093353/?ref=br_tf&epa=SEARCH_BOX" style="font-size: 12px">FACEBOOK</a>
            <i style="font-size:14px;color:red" class="fa">&#xf2b3</i> <a href="https://www.google.com/gmail/" style="font-size: 12px">Gmail</a>
            <span style="color: white;font-size: 12px"><b>Email:</b> johannljx-sm17@student.tarc.edu.my | khootw-sm17@student.tarc.edu.my <b>Hotline:</b> 1600 99 8888 <b>Contact Number:</b> +60123456789</span>
        </section>

    </footer>
</html>
