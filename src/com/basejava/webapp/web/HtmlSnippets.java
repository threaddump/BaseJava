package com.basejava.webapp.web;

public class HtmlSnippets {

    public static String getEditButton(String uuid) {
        return "<a href=\"resume?uuid=" + uuid + "&action=edit\" " + getToolTipAttributes("edit_" + uuid) +
                "><img src=\"img/action/edit.svg\" class=\"img_action\"></a>" + getToolTipBubble("edit_" + uuid, "Изменить");
    }

    public static String getAckDeleteButton(String uuid) {
        return "<a href=\"resume?uuid=" + uuid + "&action=ack_delete\" " + getToolTipAttributes("ack_delete_" + uuid) +
                "><img src=\"img/action/cross.svg\" class=\"img_action\"></a>" + getToolTipBubble("ack_delete_" + uuid, "Удалить");
    }

    public static String getToolTipBubble(String id, String text) {
        return "<div class=\"float_bubble\" id=\"bubble_" + id + "\" style=\"display: none;\">" + text + "</div>";
    }

    public static String getToolTipAttributes(String id) {
        return " onmouseover=\"document.getElementById('bubble_" + id + "').style.display='block'\"" +
                " onmouseout=\"document.getElementById('bubble_" + id + "').style.display='none'\" ";
    }
}
