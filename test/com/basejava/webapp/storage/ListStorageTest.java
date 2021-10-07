package com.basejava.webapp.storage;

import com.basejava.webapp.storage.factory.StorageFactory;
import com.basejava.webapp.storage.factory.StorageType;

import java.util.Properties;

public class ListStorageTest extends AbstractStorageTest {
    private static final Properties props = new Properties();
    static {
        props.setProperty("storage.type", StorageType.LIST.name());
    }

    public ListStorageTest() {
        super(StorageFactory.getStorage(props));
    }
}