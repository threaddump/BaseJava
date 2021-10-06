package com.basejava.webapp.model;

import java.util.Objects;

public class Organization {

    private final Link link;
    private final TimeSpan timeSpan;
    private final String title;
    private final String description;

    public Organization(Link link, TimeSpan timeSpan, String title, String description) {
        Objects.requireNonNull(link, "link must not be null");
        Objects.requireNonNull(timeSpan, "timeSpan must not be null");
        Objects.requireNonNull(title, "title must not be null");
        // nulls in description are allowed intentionally
        this.link = link;
        this.timeSpan = timeSpan;
        this.title = title;
        this.description = description;
    }

    public Link getLink() {
        return link;
    }

    public TimeSpan getTimeSpan() {
        return timeSpan;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!link.equals(that.link)) return false;
        if (!timeSpan.equals(that.timeSpan)) return false;
        if (!title.equals(that.title)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + timeSpan.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "link=" + link +
                ", timeSpan=" + timeSpan +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
