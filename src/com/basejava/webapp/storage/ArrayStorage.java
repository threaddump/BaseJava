package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElem(int idx, Resume r) {
        storage[size] = r;
    }

    @Override
    protected void fillDeletedElem(int idx) {
        storage[idx] = storage[size - 1];
    }

    @Override
    protected Integer findKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
