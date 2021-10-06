package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class AbstractFileStorage extends AbstractStorage<String> {

    private static final Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());

    private final String basePath;

    public AbstractFileStorage(String basePath) {
        Objects.requireNonNull(basePath, "basePath must not be null");
        this.basePath = basePath;
        LOGGER.info("Initializing file storage; basePath=" + basePath);

        File storageDir = new File(basePath);
        if (!storageDir.exists()) {
            LOGGER.info("Storage directory not found, creating new");
            storageDir.mkdir();
        } else if (storageDir.isDirectory()) {
            LOGGER.info("Found existing storage directory");
        } else {
            throw new RuntimeException("Found file instead of data directory; basePath=" + basePath);
        }
    }

    private String getFullPathName(String fileName) {
        return basePath + "/" + fileName;
    }

    private void deleteFile(String pathName) {
        LOGGER.info("deleteFile(): pathName=" + pathName);
        final File f = new File(getFullPathName(pathName));
        f.delete();
    }

    @Override
    public void clear() {
        LOGGER.info("clear()");
        final File storageDir = new File(basePath);
        for (String pathName : storageDir.list()) {
            // TODO: decide if directories handling should be supported too
            deleteFile(pathName);
        }
    }

    protected String findKey(String uuid) {
        return getFullPathName(uuid + ".dat");
    }

    @Override
    protected boolean exist(String key) {
        final File f = new File(key);
        return f.exists();
    }

    @Override
    protected void updateImpl(String key, Resume r) {
        serializeResume(key, r);
    }

    @Override
    protected void saveImpl(String key, Resume r) {
        serializeResume(key, r);
    }

    @Override
    protected Resume getImpl(String key) {
        return unserializeResume(key);
    }

    @Override
    protected void deleteImpl(String key) {
        deleteFile(key);
    }

    @Override
    protected List<Resume> getAllImpl() {
        return null;
    }

    @Override
    public int size() {
        final File storageDir = new File(basePath);
        int size = 0;
        for (String ignored : storageDir.list()) {
            // TODO: decide if directories handling should be supported too
            size++;
        }
        return size;
    }

    protected abstract void serializeResume(String pathName, Resume r);

    protected abstract Resume unserializeResume(String pathName);
}
