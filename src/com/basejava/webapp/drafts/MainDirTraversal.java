package com.basejava.webapp.drafts;

import java.io.File;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

public class MainDirTraversal {
    public static void main(String[] args) throws IOException {
        final Deque<String> pathQueue = new LinkedList<>();
        pathQueue.addLast(".");

        int dirCount = 0;
        int fileCount = 0;
        while (!pathQueue.isEmpty()) {
            final String pathName = pathQueue.removeFirst();
            final File fso = new File(pathName);

            if (!fso.isDirectory()) {
                System.out.println("file: " + pathName);
                fileCount++;
                continue;
            }

            System.out.println("dir: " + pathName);
            dirCount++;
            for (String dirEntryPathName : fso.list()) {
                pathQueue.addLast(pathName + "/" + dirEntryPathName);
            }
        }

        System.out.println("done; dirCount=" + dirCount + "; fileCount=" + fileCount);
    }
}
