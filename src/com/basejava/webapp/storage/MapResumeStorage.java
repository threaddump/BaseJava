package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Resume findKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean exist(Object resume) {
        return resume != null;
    }

    @Override
    protected void updateImpl(Object resume, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void saveImpl(Object resume, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getImpl(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void deleteImpl(Object resume) {
        storage.remove(((Resume) resume).getUuid());
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
