package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.ResumeTestData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected Storage storage;

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final Resume RESUME_1 = ResumeTestData.makeResume(UUID_1, "Name1");

    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final Resume RESUME_2 = ResumeTestData.makeResume(UUID_2, "Name2");

    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final Resume RESUME_3 = ResumeTestData.makeResume(UUID_3, "Name3");

    private static final String UUID_4 = UUID.randomUUID().toString();
    private static final Resume RESUME_4 = ResumeTestData.makeResume(UUID_4, "Name4");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @After
    public void tearDown() throws Exception {
        storage.clear();
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);

        List<Resume> resumes = storage.getAllSorted();
        Assert.assertTrue(resumes.isEmpty());
    }

    @Test
    public void update() {
        final Resume r = ResumeTestData.makeResume(UUID_2, "Name2_new");
        storage.update(r);
        Assert.assertTrue(r.equals(storage.get(r.getUuid())));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.delete(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> resumes = storage.getAllSorted();
        assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), resumes);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertGet(Resume r) { assertEquals(r, storage.get(r.getUuid())); }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}