package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.factory.StorageConfig;
import com.basejava.webapp.storage.factory.StorageFactory;

public class JsonPathStorageTest extends AbstractStorageTest {
    private static final StorageConfig cfg = new StorageConfig(
            "PATH",
            Config.get().getStorageConfig().getStorageDir(),
            "JSON",
            null,
            null,
            null
    );

    public JsonPathStorageTest() {
        super(StorageFactory.getStorage(cfg));
    }
}