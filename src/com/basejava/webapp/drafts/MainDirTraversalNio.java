package com.basejava.webapp.drafts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.LinkedList;

public class MainDirTraversalNio {
    public static void main(String[] args) throws IOException {
        final Path root = Paths.get("./").toAbsolutePath().normalize();

        System.out.println("traverseUsingQueue(): ");
        traverseUsingQueue(root);

        System.out.println("traverseUsingRecursion(): ");
        traverseUsingRecursion(root, 0);
    }

    private static void traverseUsingQueue(Path root) throws IOException {
        final Deque<Path> pathQueue = new LinkedList<>();
        pathQueue.addLast(root);

        while (!pathQueue.isEmpty()) {
            final Path path = pathQueue.removeFirst();
            System.out.println(path);
            Files.list(path).filter(Files::isRegularFile).forEach(System.out::println);
            Files.list(path).filter(Files::isDirectory).forEach(pathQueue::addLast);
        }
    }

    private static void traverseUsingRecursion(Path root, int nestingLevel) {
        printPath(root, nestingLevel);
        try {
            Files.list(root).filter(Files::isRegularFile).forEach(p -> printPath(p, nestingLevel));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.list(root).filter(Files::isDirectory).forEach(p -> traverseUsingRecursion(p, nestingLevel + 1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printPath(Path path, int count) {
        for (int i = 0; i < count; i++) {
            System.out.print('\t');
        }
        System.out.println(path.getFileName());
    }
}
