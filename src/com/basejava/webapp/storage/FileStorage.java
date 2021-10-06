package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.strategy.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private final File baseDir;
    private final StreamSerializer serializer;

    protected FileStorage(File baseDir, StreamSerializer serializer) {
        Objects.requireNonNull(baseDir, "baseDir must not be null");
        Objects.requireNonNull(serializer, "serializer must not be null");

        if (!baseDir.isDirectory()) {
            throw new IllegalArgumentException(baseDir.getAbsolutePath() + " is not a directory");
        }
        if (!(baseDir.canRead() && baseDir.canWrite())) {
            throw new IllegalArgumentException(baseDir.getAbsolutePath() + " is not readable/writable");
        }

        this.baseDir = baseDir;
        this.serializer = serializer;
    }

    private String[] listFilesChecked() {
        final String[] files = baseDir.list();
        if (files == null) {
            throw new StorageException("Unable to read directory");
        }
        return files;
    }

    private File getFile(String fileName) {
        return new File(baseDir, fileName);
    }

    @Override
    public void clear() {
        for (String fileName : listFilesChecked()) {
            deleteImpl(getFile(fileName));
        }
    }

    @Override
    protected File findKey(String uuid) {
        return getFile(uuid);
    }

    @Override
    protected boolean exist(File file) {
        return file.exists();
    }

    @Override
    protected void updateImpl(File file, Resume r) {
        try {
            serializer.writeResume(new BufferedOutputStream(new FileOutputStream(file)), r);
        } catch (IOException e) {
            throw new StorageException("File write error", r.getUuid(), e);
        }
    }

    @Override
    protected void saveImpl(File file, Resume r) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("File create error " + file.getAbsolutePath(), r.getUuid(), e);
        }
        updateImpl(file, r);
    }

    @Override
    protected Resume getImpl(File file) {
        try {
            return serializer.readResume(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void deleteImpl(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected List<Resume> getAllImpl() {
        String[] fileNames = listFilesChecked();
        List<Resume> resumes = new ArrayList<>(fileNames.length);
        for (String fileName : fileNames) {
            resumes.add(getImpl(getFile(fileName)));
        }
        return resumes;
    }

    @Override
    public int size() {
        return listFilesChecked().length;
    }
}
