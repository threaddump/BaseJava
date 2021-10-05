package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    // narrow type of return value
    protected abstract Integer findKey(String uuid);

    @Override
    protected boolean exist(Object key) {
        return ((Integer) key) >= 0;
    }

    @Override
    protected void updateImpl(Object key, Resume r) {
        storage[(Integer) key] = r;
    }

    @Override
    protected void saveImpl(Object key, Resume r) {
        if (STORAGE_LIMIT == size) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertElem((Integer) key, r);
        size++;
    }

    protected abstract void insertElem(int idx, Resume r);

    @Override
    protected Resume getImpl(Object key) {
        return storage[(Integer) key];
    }

    @Override
    protected void deleteImpl(Object key) {
        fillDeletedElem((Integer) key);
        storage[--size] = null;
    }

    /**
     * Remove entry from storage and maintain its continuity.
     */
    protected abstract void fillDeletedElem(int idx);

    @Override
    protected List<Resume> getAllImpl() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    public int size() {
        return size;
    }
}
