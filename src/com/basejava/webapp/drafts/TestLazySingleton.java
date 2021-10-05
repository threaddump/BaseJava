package com.basejava.webapp.drafts;

// classic singleton
public class TestLazySingleton {

    private static TestLazySingleton instance;

    private TestLazySingleton() {
        // intentionally blank
    }

    // not suitable for multithreaded usage
    public static TestLazySingleton getInstance() {
        if (instance == null) {
            instance = new TestLazySingleton();
        }
        return instance;
    }

    public static void main(String[] args) {
        // call example
        TestLazySingleton.getInstance().toString();
    }
}
