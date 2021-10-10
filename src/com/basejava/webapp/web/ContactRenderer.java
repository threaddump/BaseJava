package com.basejava.webapp.web;

import com.basejava.webapp.model.ContactType;

// TODO: refactor
public class ContactRenderer {

    public static String toHtml(ContactType type, String value) {
        if (value == null) {
            return "";
        }

        switch(type) {
            case PHONE:
            case HOME_PHONE:
                return type.getTitle() + ": " + "<img src=\"img/icon/phone.svg\" class=\"img_icon\">" + value;
            case MOBILE_PHONE:
                return type.getTitle() + ": " + "<img src=\"img/icon/mobile.svg\" class=\"img_icon\">" + value;
            case SKYPE:
                return type.getTitle() + ": " + "<a href='skype:" + value + "'><img src=\"img/icon/skype.svg\" class=\"img_icon\">" + value + "</a>";
            case EMAIL:
                return type.getTitle() + ": " + "<a href='mailto:" + value + "'><img src=\"img/icon/email.svg\" class=\"img_icon\">" + value + "</a>";
            case LINKEDIN:
                return "<a href='https://www.linkedin.com/in/" + value + "'><img src=\"img/icon/linkedin.svg\" class=\"img_icon\">Профиль LinkedIn</a>";
            case GITHUB:
                return "<a href='https://github.com/" + value + "'><img src=\"img/icon/github.svg\" class=\"img_icon\">Профиль GitHub</a>";
            case STACKOVERFLOW:
                return "<a href='https://stackoverflow.com/users/" + value + "'><img src=\"img/icon/stackoverflow.svg\" class=\"img_icon\">Профиль StackOverflow</a>";
            case HOME_PAGE:
                return "<a href='" + value + "'>Домашняя страница</a>";
            default:
                throw new IllegalArgumentException("Unsupported ContactType: " + type);
        }
    }

    public static String getEmailHref(String value) {
        if (value == null) {
            return "";
        }

        return "<a href='mailto:" + value + "'>" + value + "</a>";
    }
}
