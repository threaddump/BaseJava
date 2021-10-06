package com.basejava.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {

    private final Link link;
    private final List<Position> positions;

    public Organization(Link link, Position... positions) {
        this(link, Arrays.asList(positions));
    }

    public Organization(Link link, List<Position> positions) {
        Objects.requireNonNull(link, "link must not be null");
        Objects.requireNonNull(positions, "positions must not be null");
        this.link = link;
        this.positions = positions;
    }

    public Link getLink() {
        return link;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!link.equals(that.link)) return false;
        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "link=" + link +
                ", positions=" + positions +
                '}';
    }
}