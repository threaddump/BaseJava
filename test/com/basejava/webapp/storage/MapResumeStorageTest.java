package com.basejava.webapp.storage;

import com.basejava.webapp.storage.factory.StorageFactory;
import com.basejava.webapp.storage.factory.StorageType;

import java.util.Properties;

public class MapResumeStorageTest extends AbstractStorageTest {
    private static final Properties props = new Properties();
    static {
        props.setProperty("storage.type", StorageType.MAP_RESUME.name());
    }

    public MapResumeStorageTest() {
        super(StorageFactory.getStorage(props));
    }
}