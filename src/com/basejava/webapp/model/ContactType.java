package com.basejava.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    MOBILE_PHONE("Мобильный тел."),
    HOME_PHONE("Домашний тел."),
    SKYPE("Skype"),
    EMAIL("E-mail"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль StackOverflow"),
    HOME_PAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
