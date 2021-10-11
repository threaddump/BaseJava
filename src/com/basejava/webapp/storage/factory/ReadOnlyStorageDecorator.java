package com.basejava.webapp.storage.factory;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;

import java.util.List;

public final class ReadOnlyStorageDecorator implements Storage {
    private final Storage backingStorage;

    public ReadOnlyStorageDecorator(Storage storage) { this.backingStorage = storage; }

    private void readOnly() {
        throw new StorageException("Storage is currently in read-only mode");
    }

    @Override
    public void clear() { readOnly(); }

    @Override
    public void update(Resume r) { readOnly(); }

    @Override
    public void save(Resume r) { readOnly(); }

    @Override
    public void delete(String uuid) { readOnly(); }

    @Override
    public Resume get(String uuid) { return backingStorage.get(uuid); }

    @Override
    public List<Resume> getAllSorted() { return backingStorage.getAllSorted(); }

    @Override
    public int size() { return backingStorage.size(); }
}
