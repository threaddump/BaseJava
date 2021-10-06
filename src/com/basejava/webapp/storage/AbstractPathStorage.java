package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private final Path basePath;

    protected AbstractPathStorage(String baseDir) {
        Objects.requireNonNull(baseDir, "baseDir must not be null");
        this.basePath = Paths.get(baseDir).toAbsolutePath().normalize();
        if (!(Files.isDirectory(basePath) && Files.isWritable(basePath))) {
            throw new IllegalArgumentException(baseDir + " is not a directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(basePath).forEach(this::deleteImpl);
        } catch (IOException e) {
            throw new StorageException("Unable to clear directory", null, e);
        }
    }

    @Override
    protected Path findKey(String uuid) {
        return Paths.get(basePath.toString(), uuid);
    }

    @Override
    protected boolean exist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void updateImpl(Path path, Resume r) {
        try {
            writeResume(new BufferedOutputStream(Files.newOutputStream(path)), r);
        } catch (IOException e) {
            throw new StorageException("File write error", r.getUuid(), e);
        }
    }

    @Override
    protected void saveImpl(Path path, Resume r) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("File create error " + path, r.getUuid(), e);
        }
        updateImpl(path, r);
    }

    @Override
    protected Resume getImpl(Path path) {
        try {
            return readResume(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteImpl(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File delete error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> getAllImpl() {
        try {
            return Files.list(basePath).map(this::getImpl).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Unable to read resume", null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(basePath).count();
        } catch (IOException e) {
            throw new StorageException("Unable to read directory", null, e);
        }
    }

    protected abstract void writeResume(OutputStream os, Resume r) throws IOException;

    protected abstract Resume readResume(InputStream is) throws IOException;
}
