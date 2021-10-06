package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage {

    protected ObjectStreamStorage(File baseDir) {
        super(baseDir);
    }

    @Override
    protected void writeFile(OutputStream os, Resume r) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }

    @Override
    protected Resume readFile(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Unable to deserialize Resume", null, e);
        }
    }
}
