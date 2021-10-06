package com.basejava.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrgSection extends Section {
    private static final long serialVersionUID = 1L;

    private final List<Organization> orgs;

    public OrgSection(Organization... orgs) {
        this(Arrays.asList(orgs));
    }

    public OrgSection(List<Organization> orgs) {
        Objects.requireNonNull(orgs, "orgs must not be null");
        this.orgs = orgs;
    }

    public List<Organization> getOrgs() {
        return orgs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrgSection that = (OrgSection) o;

        return orgs.equals(that.orgs);
    }

    @Override
    public int hashCode() {
        return orgs.hashCode();
    }

    @Override
    public String toString() {
        return "OrgSection{" +
                "orgs=" + orgs +
                '}';
    }
}
