package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    public final void update(Resume r) {
        final Object key = findKeyChecked(r.getUuid(), true);
        updateImpl(key, r);
    }

    public final void save(Resume r) {
        final Object key = findKeyChecked(r.getUuid(), false);
        saveImpl(key, r);
    }

    public final Resume get(String uuid) {
        final Object key = findKeyChecked(uuid, true);
        return getImpl(key);
    }

    public final void delete(String uuid) {
        final Object key = findKeyChecked(uuid, true);
        deleteImpl(key);
    }

    private Object findKeyChecked(String uuid, boolean shouldExist) {
        final Object key = findKey(uuid);
        final boolean exist = exist(key);
        if (shouldExist && !exist) {
            throw new NotExistStorageException(uuid);
        } else if (!shouldExist && exist) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    public List<Resume> getAllSorted() {
        List<Resume> resumes = getAllImpl();
        Collections.sort(resumes);
        return resumes;
    }

    protected abstract Object findKey(String uuid);

    protected abstract boolean exist(Object key);

    protected abstract void updateImpl(Object key, Resume r);

    protected abstract void saveImpl(Object key, Resume r);

    protected abstract Resume getImpl(Object key);

    protected abstract void deleteImpl(Object key);

    protected abstract List<Resume> getAllImpl();
}
