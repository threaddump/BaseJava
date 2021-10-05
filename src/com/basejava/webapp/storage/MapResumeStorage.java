package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected boolean exist(Resume resume) {
        return resume != null;
    }

    @Override
    protected void updateImpl(Resume resume, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void saveImpl(Resume resume, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getImpl(Resume resume) {
        return resume;
    }

    @Override
    protected void deleteImpl(Resume resume) {
        storage.remove(resume.getUuid());
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
