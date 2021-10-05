package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 0; i < (AbstractArrayStorage.STORAGE_LIMIT - 3); i++) {
                storage.save(new Resume("Name" + Integer.toHexString(i)));
            }
        } catch (StorageException e) {
            Assert.fail("Early overflow");
        }
        assertSize(AbstractArrayStorage.STORAGE_LIMIT);
        storage.save(new Resume("Here comes the Exception"));
    }
}
