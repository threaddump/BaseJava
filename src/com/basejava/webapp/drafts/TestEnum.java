package com.basejava.webapp.drafts;

import com.basejava.webapp.model.SectionType;

public class TestEnum {

    public enum Singleton {
        INSTANCE
    }

    public static void main(String[] args) {
        Singleton.INSTANCE.valueOf("INSTANCE");

        System.out.println(Singleton.INSTANCE.name());
        System.out.println(Singleton.INSTANCE.ordinal());

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }
    }
}
