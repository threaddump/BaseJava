package com.basejava.webapp.drafts;

// classic singleton
public class TestSingleton {

    private static TestSingleton instance = new TestSingleton();

    private TestSingleton() {
        // intentionally blank
    }

    public static TestSingleton getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        // call example
        TestSingleton.getInstance().toString();
    }
}
