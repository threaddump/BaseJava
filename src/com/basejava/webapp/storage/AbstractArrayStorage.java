package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected abstract Integer findKey(String uuid);

    @Override
    protected boolean exist(Integer idx) {
        return idx >= 0;
    }

    @Override
    protected void updateImpl(Integer idx, Resume r) {
        storage[idx] = r;
    }

    @Override
    protected void saveImpl(Integer idx, Resume r) {
        if (STORAGE_LIMIT == size) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertElem(idx, r);
        size++;
    }

    protected abstract void insertElem(int idx, Resume r);

    @Override
    protected Resume getImpl(Integer idx) {
        return storage[idx];
    }

    @Override
    protected void deleteImpl(Integer idx) {
        fillDeletedElem(idx);
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
