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
        return uuid;
    }

    @Override
    protected boolean exist(Object uuid) {
        return storage.containsKey((String) uuid);
    }

    @Override
    protected void updateImpl(Object uuid, Resume r) {
        storage.put((String) uuid, r);
    }

    @Override
    protected void saveImpl(Object uuid, Resume r) {
        storage.put((String) uuid, r);
    }

    @Override
    protected Resume getImpl(Object uuid) {
        return storage.get((String) uuid);
    }

    @Override
    protected void deleteImpl(Object uuid) {
        storage.remove((String) uuid);
    }

    @Override
    protected List<Resume> getAllImpl() {
        return new ArrayList(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
