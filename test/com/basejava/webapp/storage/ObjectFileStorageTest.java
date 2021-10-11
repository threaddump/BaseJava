package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.factory.StorageConfig;
import com.basejava.webapp.storage.factory.StorageFactory;

public class ObjectFileStorageTest extends AbstractStorageTest {
    private static final StorageConfig cfg = new StorageConfig(
            "FILE",
            Config.get().getStorageConfig().getStorageDir(),
            "OBJECT_STREAM",
            null,
            null,
            null,
            "FALSE"
    );

    public ObjectFileStorageTest() {
        super(StorageFactory.getStorage(cfg));
    }
}
