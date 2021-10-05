package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<KeyType> implements Storage {

    public final void update(Resume r) {
        final KeyType key = findKeyChecked(r.getUuid(), true);
        updateImpl(key, r);
    }

    public final void save(Resume r) {
        final KeyType key = findKeyChecked(r.getUuid(), false);
        saveImpl(key, r);
    }

    public final Resume get(String uuid) {
        final KeyType key = findKeyChecked(uuid, true);
        return getImpl(key);
    }

    public final void delete(String uuid) {
        final KeyType key = findKeyChecked(uuid, true);
        deleteImpl(key);
    }

    private KeyType findKeyChecked(String uuid, boolean shouldExist) {
        final KeyType key = findKey(uuid);
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

    protected abstract KeyType findKey(String uuid);

    protected abstract boolean exist(KeyType key);

    protected abstract void updateImpl(KeyType key, Resume r);

    protected abstract void saveImpl(KeyType key, Resume r);

    protected abstract Resume getImpl(KeyType key);

    protected abstract void deleteImpl(KeyType key);

    protected abstract List<Resume> getAllImpl();
}
