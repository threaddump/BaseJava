package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

// Test and tested class are in the same pkg, so protected members are available.
public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);

    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);

    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);

    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }
    
    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        Assert.assertEquals(3, storage.size());
        storage.clear();
        Assert.assertEquals(0, storage.size());
        Assert.assertArrayEquals(new Resume[0], storage.getAll());
    }

    @Test
    public void update() {
        final Resume r = new Resume(UUID_2);
        storage.update(r);
        Assert.assertTrue(r == storage.get(r.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 0; i < (AbstractArrayStorage.STORAGE_LIMIT - 3); i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Early overflow");
        }
        storage.save(new Resume());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void get() {
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
        Assert.assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.delete(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAll() {
        Resume[] targetResumes = new Resume[] {RESUME_1, RESUME_2, RESUME_3};
        Arrays.sort(targetResumes);
        Resume[] actualResumes = storage.getAll();
        Arrays.sort(actualResumes);
        Assert.assertArrayEquals(targetResumes, actualResumes);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}