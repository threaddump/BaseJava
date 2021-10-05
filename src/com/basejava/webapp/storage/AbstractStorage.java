package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<KeyType> implements Storage {

    private static final Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());

    public final void update(Resume r) {
        LOGGER.info("update(): r=" + r);
        final KeyType key = findKeyChecked(r.getUuid(), true);
        updateImpl(key, r);
    }

    public final void save(Resume r) {
        LOGGER.info("save(): r=" + r);
        final KeyType key = findKeyChecked(r.getUuid(), false);
        saveImpl(key, r);
    }

    public final Resume get(String uuid) {
        LOGGER.info("get(): uuid=" + uuid);
        final KeyType key = findKeyChecked(uuid, true);
        return getImpl(key);
    }

    public final void delete(String uuid) {
        LOGGER.info("delete(): uuid=" + uuid);
        final KeyType key = findKeyChecked(uuid, true);
        deleteImpl(key);
    }

    private KeyType findKeyChecked(String uuid, boolean shouldExist) {
        final KeyType key = findKey(uuid);
        final boolean doesExist = exist(key);
        if (doesExist == shouldExist) {
            return key;
        }
        LOGGER.warning("findKeyChecked(): uuid=" + uuid +
                "; doesExist=" + doesExist + "; shouldExist=" + shouldExist);
        if (shouldExist && !doesExist) {
            throw new NotExistStorageException(uuid);
        } else /* if (!shouldExist && doesExist) */ {
            throw new ExistStorageException(uuid);
        }
    }

    public List<Resume> getAllSorted() {
        LOGGER.info("getAllSorted()");
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
