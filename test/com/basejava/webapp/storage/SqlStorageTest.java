package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.factory.StorageConfig;
import com.basejava.webapp.storage.factory.StorageFactory;
import org.junit.After;

public class SqlStorageTest extends AbstractStorageTest {
    private static final StorageConfig cfg = new StorageConfig(
            "SQL",
            null,
            null,
            Config.get().getStorageConfig().getDbUrl(),
            Config.get().getStorageConfig().getDbUsername(),
            Config.get().getStorageConfig().getDbPassword(),
            "FALSE"
    );

    public SqlStorageTest() { super(StorageFactory.getStorage(cfg)); }

    @After
    public void tearDown() throws Exception {
        storage.clear();
    }
}