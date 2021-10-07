package com.basejava.webapp.storage;

import com.basejava.webapp.storage.factory.StorageFactory;
import com.basejava.webapp.storage.factory.StorageType;

import java.util.Properties;

public class MapUuidStorageTest extends AbstractStorageTest {
    private static final Properties props = new Properties();
    static {
        props.setProperty("storage.type", StorageType.MAP_UUID.name());
    }

    public MapUuidStorageTest() {
        super(StorageFactory.getStorage(props));
    }
}