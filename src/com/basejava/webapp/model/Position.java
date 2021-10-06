package com.basejava.webapp.model;

import java.util.Objects;

public class Position {
    private final TimeSpan timeSpan;
    private final String title;
    private final String description;

    public Position(TimeSpan timeSpan, String title, String description) {
        Objects.requireNonNull(timeSpan, "timeSpan must not be null");
        Objects.requireNonNull(title, "title must not be null");
        // nulls in description are allowed intentionally
        this.timeSpan = timeSpan;
        this.title = title;
        this.description = description;
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

        Position position = (Position) o;

        if (!timeSpan.equals(position.timeSpan)) return false;
        if (!title.equals(position.title)) return false;
        return description != null ? description.equals(position.description) : position.description == null;
    }

    @Override
    public int hashCode() {
        int result = timeSpan.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "timeSpan=" + timeSpan +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
