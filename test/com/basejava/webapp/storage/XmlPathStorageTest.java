package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.factory.StorageConfig;
import com.basejava.webapp.storage.factory.StorageFactory;

public class XmlPathStorageTest extends AbstractStorageTest {
    private static final StorageConfig cfg = new StorageConfig(
            "PATH",
            Config.get().getStorageConfig().getStorageDir(),
            "XML",
            null,
            null,
            null,
            "FALSE"
    );

    public XmlPathStorageTest() {
        super(StorageFactory.getStorage(cfg));
    }
}