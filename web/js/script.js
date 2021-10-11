// --------------------------------------------------------------------------------------------

// Functions used by pages generated by edit.jsp

// --------------------------------------------------------------------------------------------

// Some stuff for <textarea>

function auto_height(obj) {
    obj.style.height = '1px';
    obj.style.height = (2 + obj.scrollHeight) + 'px';
}

function strip_newline(obj) {
    obj.value = obj.value.replace(/\n/g,'');
}

function setup_textarea_handlers() {
    $('.textarea_single_line').on('input', function (e) { strip_newline(this); });
    $('textarea').on('focus', function (e) { auto_height(this); });
    $('textarea').on('input', function (e) { auto_height(this); });
}

// --------------------------------------------------------------------------------------------

// The Editor.
//
// Handlers are automatically attached to buttons based on their location and class.
// Internal editor state is held in hidden inputs (we use a pair for each "add" button).
// The code for "remove" buttons is universal.

function is_editor_debugged() {
    return false;
}

function show_hidden_inputs() {
    $('input[type="hidden"]').each(function() { this.type = 'text'; });
}

function highlight_editor_states() {
    if (is_editor_debugged()) {
        show_hidden_inputs();

        $('.editor_counter').css('background', 'yellow');
        $('.editor_prefix').css('background', 'yellow');
    }
}

function set_remove_button_handler(container_cls, button_cls) {
    $($(container_cls)).on('click', button_cls, function (e) {
        e.preventDefault();

        $(this).parent('div').parent('fieldset').remove();
    });
}

function set_add_button_handler(root_cls, button_cls, gen_editor_part) {
    var debug = is_editor_debugged();

    var root = $(root_cls);
    if (debug) { root.css('background', 'yellow'); }

    $(root).on('click', button_cls, function (e) {
        e.preventDefault();

        var parent_div = $(this).parent('span').parent('div');
        if (debug) { parent_div.css('background', 'red'); }

        var counter_input = parent_div.children('.editor_counter')[0];
        var counter = counter_input.value;
        counter_input.value++;

        var prefix_input = parent_div.children('.editor_prefix')[0];
        var prefix = prefix_input.value;

        var container_cls = ('.' + prefix + '_container');
        if (debug) { console.debug('container_cls=' + container_cls); }
        var container = $(container_cls);
        if (debug) { container.css('background', 'red'); }

        container.append(gen_editor_part(prefix + '_' + counter));
    });
}

// --------------------------------------------------------------------------------------------

// The content of these functions was simply ripped from html code produced by edit.jsp.
// Prefix (p) is an "indexed prefix" according to edit.jsp notation.

var genOrganizationEditor = function (p) {
    if (is_editor_debugged()) {
        is_editor_debugged('Creating OrganizationEditor for prefix=' + p);
    }

    var text_or_hidden = is_editor_debugged() ? 'text' : 'hidden';

    return  '<fieldset id="' + p + '_fieldset">\n' +
            '    <!-- org removal button -->\n' +
            '    <div class="form_div">\n' +
            '        <button id="' + p + '_remove" class="button_remove">\n' +
            '        <img src="img/action/cross.svg" class="img_action">Удалить организацию</button>\n' +
            '    </div>\n' +
            '    <!-- display inputs for link (title and url) -->\n' +
            '    <div class="form_div">\n' +
            '        <label for="' + p + '_title" class="form_label">Название:</label>\n' +
            '        <span class="form_span">\n' +
            '            <input type="text" name="' + p + '_title" id="' + p + '_title" value="">\n' +
            '        </span>\n' +
            '    </div>\n' +
            '    <div class="form_div">\n' +
            '        <label for="' + p + '_url" class="form_label">URL сайта:</label>\n' +
            '        <span class="form_span">\n' +
            '            <input type="text" name="' + p + '_url" id="' + p + '_url" value="">\n' +
            '        </span>\n' +
            '    </div>\n' +
            '    <!-- header with a pos addition button -->\n' +
            '    <div class="form_div ' + p + '_pos_header" style="">\n' +
            '        <label class="form_label"></label>\n' +
            '        <span class="form_span">\n' +
            '            <button id="' + p + '_pos_add" class="button_add pos_add">\n' +
            '            <img src="img/action/plus.svg" class="img_action">Добавить позицию</button>\n' +
            '        </span>\n' +
            '        <input type="' + text_or_hidden + '" name="' + p + '_pos_counter" class="editor_counter" value="0" style="background: yellow;">\n' +
            '        <input type="' + text_or_hidden + '" name="' + p + '_pos_prefix" class="editor_prefix" value="' + p + '_pos" style="background: yellow;">\n' +
            '    </div>\n' +
            '    <!-- positions wrapped in a fieldset flag -->\n' +
            '    <div class="' + p + '_pos_container" style="">\n' +
            '    </div>\n' +
            '</fieldset>';
}

var genPositionEditor = function (p) {
    if (is_editor_debugged()) {
        is_editor_debugged('Creating PositionEditor for prefix=' + p);
    }

    return  '<fieldset id="' + p + '_fieldset">\n' +
            '    <!-- pos removal button -->\n' +
            '    <div class="form_div">\n' +
            '        <button id="' + p + '_remove" class="button_remove">\n' +
            '            <img src="img/action/cross.svg" class="img_action">Удалить позицию</button>\n' +
            '    </div>\n' +
            '    <!-- display inputs for position (TimeSpan.begin, TimeSpan.end, title, description) -->\n' +
            '    <div class="form_div">\n' +
            '        <label for="' + p + '_begin" class="form_label">Дата начала:</label>\n' +
            '        <span class="form_span">\n' +
            '            <input type="text" name="' + p + '_begin" id="' + p + '_begin" value="" placeholder="Дата в формате MM/yyyy или &quot;Сейчас&quot;">\n' +
            '        </span>\n' +
            '    </div>\n' +
            '    <div class="form_div">\n' +
            '        <label for="' + p + '_end" class="form_label">Дата окончания:</label>\n' +
            '        <span class="form_span">\n' +
            '            <input type="text" name="' + p + '_end" id="' + p + '_end" value="" placeholder="Дата в формате MM/yyyy или &quot;Сейчас&quot;">\n' +
            '        </span>\n' +
            '    </div>\n' +
            '    <div class="form_div">\n' +
            '        <label for="' + p + '_title" class="form_label">Должность:</label>\n' +
            '        <span class="form_span">\n' +
            '            <textarea name="' + p + '_title" id="' + p + '_title" class="textarea_single_line"></textarea>\n' +
            '        </span>\n' +
            '    </div>\n' +
            '    <div class="form_div">\n' +
            '        <label for="' + p + '_desc" class="form_label">Описание:</label>\n' +
            '        <span class="form_span">\n' +
            '            <textarea name="' + p + '_desc" id="' + p + '_desc"></textarea>\n' +
            '        </span>\n' +
            '    </div>\n' +
            '</fieldset>';
}

// --------------------------------------------------------------------------------------------

$(document).ready(function() {
    setup_textarea_handlers();

    highlight_editor_states();

    set_remove_button_handler('.EXPERIENCE_org_container', '.button_remove');
    set_remove_button_handler('.EDUCATION_org_container', '.button_remove');

    set_add_button_handler('.EXPERIENCE_org_header', '.org_add', genOrganizationEditor);
    set_add_button_handler('.EDUCATION_org_header', '.org_add', genOrganizationEditor);

    set_add_button_handler('.EXPERIENCE_org_container', '.pos_add', genPositionEditor);
    set_add_button_handler('.EDUCATION_org_container', '.pos_add', genPositionEditor);

    console.debug('Editor initialized');
});

// --------------------------------------------------------------------------------------------