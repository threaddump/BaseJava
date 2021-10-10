package com.basejava.webapp.web;

import com.basejava.webapp.model.ContactType;

// TODO: refactor
public class ContactRenderer {

    public static String toHtml(ContactType type, String contact) {
        if (contact == null) {
            return "";
        }

        switch(type) {
            case PHONE:
            case HOME_PHONE:
                return type.getTitle() + ": " + "<img src=\"img/icon/phone.svg\" class=\"img_icon\">" + contact;
            case MOBILE_PHONE:
                return type.getTitle() + ": " + "<img src=\"img/icon/mobile.svg\" class=\"img_icon\">" + contact;
            case SKYPE:
                return type.getTitle() + ": " + "<a href='skype:" + contact + "'><img src=\"img/icon/skype.svg\" class=\"img_icon\">" + contact + "</a>";
            case EMAIL:
                return type.getTitle() + ": " + "<a href='mailto:" + contact + "'><img src=\"img/icon/email.svg\" class=\"img_icon\">" + contact + "</a>";
            case LINKEDIN:
                return "<a href='https://www.linkedin.com/in/" + contact + "'><img src=\"img/icon/linkedin.svg\" class=\"img_icon\">Профиль LinkedIn</a>";
            case GITHUB:
                return "<a href='https://github.com/" + contact + "'><img src=\"img/icon/github.svg\" class=\"img_icon\">Профиль GitHub</a>";
            case STACKOVERFLOW:
                return "<a href='https://stackoverflow.com/users/" + contact + "'><img src=\"img/icon/stackoverflow.svg\" class=\"img_icon\">Профиль StackOverflow</a>";
            case HOME_PAGE:
                return "<a href='" + contact + "'>Домашняя страница</a>";
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
