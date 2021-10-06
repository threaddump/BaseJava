package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrgSection extends Section {

    private final List<Organization> content;

    public OrgSection(List<Organization> content) {
        Objects.requireNonNull(content, "content must not be null");
        this.content = content;
    }

    public List<Organization> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrgSection that = (OrgSection) o;

        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public String toString() {
        return "OrgSection{" +
                "content=" + content +
                '}';
    }
}
