package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Integer findKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean exist(Object key) {
        return ((Integer) key) >= 0;
    }

    @Override
    protected void updateImpl(Object key, Resume r) {
        storage.set((Integer) key, r);
    }

    @Override
    protected void saveImpl(Object key, Resume r) {
        storage.add(r);
    }

    @Override
    protected Resume getImpl(Object key) {
        return storage.get((Integer) key);
    }

    @Override
    protected void deleteImpl(Object key) {
        // NOTE (EL): remove((Integer) key) calls remove(Object), but we need remove(int)
        final int idx = (Integer) key;
        storage.remove(idx);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
