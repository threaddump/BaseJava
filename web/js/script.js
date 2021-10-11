// functions used by edit.jsp

// automatic height resize for <textarea>
function auto_height(obj) {
    obj.style.height = '1px';
    obj.style.height = (2 + obj.scrollHeight) + 'px';
}

// strip "new line" characters from user input
function strip_newline(obj) {
    obj.value = obj.value.replace(/\n/g,'');
}

function setup_textarea_handlers() {
    $('.textarea_single_line').on('input', function (e) { strip_newline(this); });
    $('textarea').on('focus', function (e) { auto_height(this); });
    $('textarea').on('input', function (e) { auto_height(this); });
}

function set_remove_button_handler(container_cls, button_cls) {
    $($(container_cls)).on('click', button_cls, function (e) {
        e.preventDefault();
        $(this).parent('div').parent('fieldset').remove();
    });
}

$(document).ready(function() {
    setup_textarea_handlers();

    set_remove_button_handler('.EXPERIENCE_org_container', '.button_remove');
    set_remove_button_handler('.EDUCATION_org_container', '.button_remove');

    // TODO: use this draft to impl org addition
    // NOTE: objects may have multiple classes. use "pos_add", "pos_remove", "org_add", "org_remove"!
    $($('.EXPERIENCE_org_header')).on('click', '.org_add', function (e) {
        e.preventDefault();
        $($('.EXPERIENCE_org_container')).append('kekeke<br />');
    });

    // highlight inputs holding editor state
    $('.editor_counter').css('background', 'yellow');
    $('.editor_prefix').css('background', 'yellow');

    // so, i'm able to find and alter the counter
    //var counter = $('.EXPERIENCE_org_header').children('div').children('.form_counter');
    //counter.css('background', 'yellow');
    //alert(counter[0].value);
    //counter[0].value++;
    //alert(counter[0].value);
});