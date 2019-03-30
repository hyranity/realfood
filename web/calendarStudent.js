var link = "";
var day = "";
var month = "";
var dow = "";
var defaultTitle="";

$(document).ready(function () {
    defaultTitle = $(".dynamicTitle").data("date");
    $('.weekday').click(function appendLink() {
        link += $(this).attr('data-date');
    }
    );});
    
    $(document).ready(function(){
        $(".weekday").hover(function (){
            day = $(this).data('day');
            month = $(this).data('month');
            dow = $(this).data('dow');
            $(".dynamicTitle").html(dow + ', ' + month + ' ' + day);
            $(".title").html("That day is ");
        }, function(){
            $(".dynamicTitle").html(defaultTitle);
            $(".title").html("Current day is ");
        });
    });


function updateButtonLink() {
    document.getElementById("nextButton").attr("href", link);
}


