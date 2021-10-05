package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
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
        return null;
    }

    @Override
    protected boolean exist(Integer key) {
        return key != null;
    }

    @Override
    protected void updateImpl(Integer key, Resume r) {
        storage.set(key, r);
    }

    @Override
    protected void saveImpl(Integer key, Resume r) {
        storage.add(r);
    }

    @Override
    protected Resume getImpl(Integer key) {
        return storage.get(key);
    }

    @Override
    protected void deleteImpl(Integer key) {
        // NOTE (EL): remove((Integer) key) calls remove(Object), but we need remove(int)
        storage.remove(key.intValue());
    }

    @Override
    protected List<Resume> getAllImpl() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
