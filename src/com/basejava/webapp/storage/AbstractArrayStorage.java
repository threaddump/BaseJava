package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        final String uuid = r.getUuid();
        final int idx = findIndex(uuid);
        if (idx < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            storage[idx] = r;
        }
    }

    public void save(Resume r) {
        final String uuid = r.getUuid();
        final int idx = findIndex(uuid);
        if (idx >= 0) {
            throw new ExistStorageException(uuid);
        } else if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", uuid);
        } else {
            insertElem(r, idx);
            size++;
        }
    }

    public Resume get(String uuid) {
        final int idx = findIndex(uuid);
        if (idx < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[idx];
    }

    public void delete(String uuid) {
        final int idx = findIndex(uuid);
        if (idx < 0) {
            throw new NotExistStorageException(uuid);
        }
        fillDeletedElem(idx);
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

    protected abstract void insertElem(Resume r, int idx);

    /**
     * Remove entry from storage and maintain its continuity.
     */
    protected abstract void fillDeletedElem(int idx);

    /**
     * @return Index of matching element of storage[], or negative number (not found).
     */
    protected abstract int findIndex(String uuid);
}
