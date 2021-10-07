package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.factory.StorageConfig;
import com.basejava.webapp.storage.factory.StorageFactory;

public class ObjectPathStorageTest extends AbstractStorageTest {
    private static final StorageConfig cfg = new StorageConfig(
            "PATH",
            Config.get().getStorageConfig().getStorageDir(),
            "OBJECT_STREAM",
            null,
            null,
            null
    );

    public ObjectPathStorageTest() {
        super(StorageFactory.getStorage(cfg));
    }
}