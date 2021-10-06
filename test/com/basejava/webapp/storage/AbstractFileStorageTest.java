package com.basejava.webapp.storage;

import org.junit.After;

import java.io.File;

public abstract class AbstractFileStorageTest extends AbstractStorageTest {

    protected static final File STORAGE_DIR = new File("storage/");

    protected AbstractFileStorageTest(Storage storage) {
        super(storage);
    }

    @After
    public void tearDown() throws Exception {
        storage.clear();
    }
}
