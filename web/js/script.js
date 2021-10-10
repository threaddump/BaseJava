// automatic height resize for <textarea>
function auto_height(obj) {
    obj.style.height = "1px";
    obj.style.height = (obj.scrollHeight) + "px";
}

// strip "new line" characters from user input
function strip_newline(obj) {
    obj.value = obj.value.replace(/\n/g,'');
}