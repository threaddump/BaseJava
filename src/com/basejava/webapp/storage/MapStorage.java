package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Object findKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected boolean exist(Object key) {
        return key != null;
    }

    @Override
    protected void updateImpl(Object key, Resume r) {
        storage.put((String) key, r);
    }

    @Override
    protected void saveImpl(Object key, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getImpl(Object key) {
        return storage.get((String) key);
    }

    @Override
    protected void deleteImpl(Object key) {
        storage.remove((String) key);
    }

    @Override
    public Resume[] getAll() {
        Resume[] result = new Resume[size()];
        int idx = 0;
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            result[idx++] = entry.getValue();
        }
        return result;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
