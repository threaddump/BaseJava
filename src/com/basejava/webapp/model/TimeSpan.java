package com.basejava.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class TimeSpan implements Serializable {
    private static final long serialVersionUID = 1L;

    private final LocalDate begin;
    private final LocalDate end;

    public TimeSpan(LocalDate begin, LocalDate end) {
        Objects.requireNonNull(begin, "begin must not be null");
        Objects.requireNonNull(end, "end must not be null");
        this.begin = begin;
        this.end = end;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeSpan timeSpan = (TimeSpan) o;

        if (!begin.equals(timeSpan.begin)) return false;
        return end.equals(timeSpan.end);
    }

    @Override
    public int hashCode() {
        int result = begin.hashCode();
        result = 31 * result + end.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TimeSpan{" +
                "begin=" + begin +
                ", end=" + end +
                '}';
    }
}
