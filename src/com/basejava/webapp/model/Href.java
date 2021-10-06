package com.basejava.webapp.model;

import java.util.Objects;

public class Href {

    private final String title;
    private final String url;

    public Href(String title, String url) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(url, "url must not be null");
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Href href = (Href) o;

        if (!title.equals(href.title)) return false;
        return url.equals(href.url);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Href{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
