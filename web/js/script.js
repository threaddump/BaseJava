// functions used by edit.jsp

// automatic height resize for <textarea>
function auto_height(obj) {
    obj.style.height = "1px";
    obj.style.height = (obj.scrollHeight) + "px";
}

// strip "new line" characters from user input
function strip_newline(obj) {
    obj.value = obj.value.replace(/\n/g,'');
}

function set_remove_button_handler(wrapper_cls) {
    $($(wrapper_cls)).on("click",".button_remove", function (e) {
        e.preventDefault();
        $(this).parent('div').parent('fieldset').remove();
    });
}

$(document).ready(function() {
    set_remove_button_handler(".EXPERIENCE_org_container");
    set_remove_button_handler(".EDUCATION_org_container");

    // TODO: use this draft to impl org addition
    $($(".EXPERIENCE_org_header")).on("click", ".button_add", function (e) {
        e.preventDefault();
        $($(".EXPERIENCE_org_container")).append('kekeke<br />');
    });
});