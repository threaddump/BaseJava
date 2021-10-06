package com.basejava.webapp.storage;

import org.junit.After;

public abstract class FileStorageTest extends AbstractStorageTest {

    protected static final String STORAGE_DIR = "storage/";

    protected FileStorageTest(Storage storage) {
        super(storage);
    }
    
    @After
    public void tearDown() throws Exception {
        storage.clear();
    }
}
