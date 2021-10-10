package com.basejava.webapp.web;

import com.basejava.webapp.model.TimeSpan;
import com.basejava.webapp.util.DateUtil;

import java.util.stream.Collectors;

// TODO: refactor
public class HtmlSnippets {

    public static String getCreateLink() {
        return "<a href=\"resume?action=create\"><img src=\"img/action/plus.svg\" class=\"img_action\">Новое резюме</a>";
    }

    public static String getListLink() {
        return "<a href=\"resume?action=list\"><img src=\"img/action/back.svg\" class=\"img_action\">Назад к списку</a>";
    }

    public static String getViewLink(String uuid) {
        return "<a href=\"resume?uuid=" + uuid + "&action=view\"><img src=\"img/action/eye.svg\" class=\"img_action\">Назад к резюме</a>";
    }

    public static String getEditLink(String uuid) {
        return "<a href=\"resume?uuid=" + uuid + "&action=edit\"><img src=\"img/action/edit.svg\" class=\"img_action\">Изменить</a>";
    }

    public static String getEditButton(String uuid) {
        return "<a href=\"resume?uuid=" + uuid + "&action=edit\" " + getToolTipAttributes("edit_" + uuid) +
                "><img src=\"img/action/edit.svg\" class=\"img_action\"></a>" + getToolTipBubble("edit_" + uuid, "Изменить");
    }

    public static String getAckDeleteLink(String uuid) {
        return "<a href=\"resume?uuid=" + uuid + "&action=ack_delete\"><img src=\"img/action/cross.svg\" class=\"img_action\">Удалить</a>";
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

    public static String getTimeSpanStr(TimeSpan timeSpan) {
        return DateUtil.format(timeSpan.getBegin()) + " - " + DateUtil.format(timeSpan.getEnd());
    }

//    public static String escapeHTML(String str) {
//        return str.chars().mapToObj(c -> c > 127 || "\"'<>&".indexOf(c) != -1 ?
//                "&#" + c + ";" : String.valueOf((char) c)).collect(Collectors.joining());
//    }

    public static String escapeHTML(String str) {
        return str.codePoints().mapToObj(c -> c > 127 || "\"'<>&".indexOf(c) != -1 ?
                        "&#" + c + ";" : new String(Character.toChars(c)))
                .collect(Collectors.joining());
    }

    public static boolean isNullOrEmpty(String str) {
        return (str == null) || (str.trim().length() == 0);
    }
}
