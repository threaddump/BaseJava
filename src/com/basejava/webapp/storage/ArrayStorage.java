package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        final String uuid = r.getUuid();
        final int idx = findIndex(uuid);
        if (idx == -1) {
            System.out.println("Resume " + uuid + " does not exist");
        } else {
            storage[idx] = r;
        }
    }

    public void save(Resume r) {
        final String uuid = r.getUuid();
        if (findIndex(uuid) != -1) {
            System.out.println("Resume " + uuid + " already exist");
        } else if (size == STORAGE_LIMIT) {
            System.out.println("ArrayStorage.save(): out of free space");
        } else {
            storage[size++] = r;
        }
    }

    public void delete(String uuid) {
        final int idx = findIndex(uuid);
        if (idx == -1) {
            System.out.println("Resume " + uuid + " does not exist");
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

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
