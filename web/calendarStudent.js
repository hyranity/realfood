var link = "";
var day = "";
var month = "";
var dow = "";

$(document).ready(function () {
    $('.weekday').click(function appendLink() {
        link += $(this).attr('data-date');
    }
    );});
    
    $(document).ready(function(){
        $(".weekday").bind('mouseenter', function (){
            day = $(this).data('day');
            month = $(this).data('month');
            dow = $(this).data('dow');
            $(".dynamicTitle").html(dow + ', ' + month + ' ' + day);
            $(".title").html("That day is ");
        });
    });


function updateButtonLink() {
    document.getElementById("nextButton").attr("href", link);
}


