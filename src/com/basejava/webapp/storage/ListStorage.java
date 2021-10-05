package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage implements Storage {
    private List<Resume> storage = new LinkedList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        final String uuid = r.getUuid();
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                storage.set(i, r);
                return;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void save(Resume r) {
        final String uuid = r.getUuid();
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                throw new ExistStorageException(uuid);
            }
        }
        storage.add(r);
    }

    @Override
    public Resume get(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            Resume r = storage.get(i);
            if (r.getUuid().equals(uuid)) {
                return r;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                storage.remove(i);
                return;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
