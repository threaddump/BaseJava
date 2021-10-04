package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        final int idx = findIndex(uuid);
        if (idx == -1) {
            System.out.println("Resume " + uuid + " does not exist");
            return null;
        }
        return storage[idx];
    }

    /**
     * @return Index of matching element of storage[], or -1 (not found)
     */
    protected abstract int findIndex(String uuid);
}
