package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (findIndex(r.getUuid()) != -1) {
            System.err.println("ArrayStorage.save(): duplicate uuid " + r.getUuid() + ". Use update() instead");
            return;
        }
        if (size == storage.length) {
            System.err.println("ArrayStorage.save(): out of free space");
            return;
        }
        storage[size++] = r;
    }

    public Resume get(String uuid) {
        final int idx = findIndex(uuid);
        if (idx == -1) {
            System.err.println("ArrayStorage.get(): uuid " + uuid + " not found");
            return null;
        }
        return storage[idx];
    }

    public void update(Resume r) {
        final int idx = findIndex(r.getUuid());
        if (idx == -1) {
            System.err.println("ArrayStorage.update(): uuid " + r.getUuid() + " not found");
            return;
        }
        storage[idx] = r;
    }

    public void delete(String uuid) {
        final int idx = findIndex(uuid);
        if (idx == -1) {
            System.err.println("ArrayStorage.delete(): uuid " + uuid + " not found");
            return;
        }
        storage[idx] = storage[size - 1];
        storage[--size] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    /**
     * @return Index of matching element of storage[], or -1 (not found)
     */
    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
