package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected String findKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean exist(String uuid) {
        return storage.containsKey(uuid);
    }

    @Override
    protected void updateImpl(String uuid, Resume r) {
        storage.put(uuid, r);
    }

    @Override
    protected void saveImpl(String uuid, Resume r) {
        storage.put(uuid, r);
    }

    @Override
    protected Resume getImpl(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteImpl(String uuid) {
        storage.remove(uuid);
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
