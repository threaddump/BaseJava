package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
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
    protected List<Resume> getAllImpl() {
        List<Resume> resumes = new ArrayList<>();
        resumes.addAll(storage.values());
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
