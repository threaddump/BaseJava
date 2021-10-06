package com.basejava.webapp.drafts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = "./.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File(".\\doc\\");
        System.out.println(dir.isDirectory());

        // care: java.io.* classes may return null or throw exceptions
        for (String name : dir.list()) {
            System.out.println(name);
        }

        // see https://habr.com/ru/post/178405/ for methods of exception handling
        /*
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            System.out.println(fis.read());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        */

        // automatic resource mgmt since Java 7
        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
