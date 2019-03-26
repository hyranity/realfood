var $link="";

function appendLink(){
    $link += $(this).data().date;
    alert($link);
}

function updateButtonLink(){
    document.getElementById("nextButton").attr("href", link);
}


