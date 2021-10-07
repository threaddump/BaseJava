package com.basejava.webapp.storage;

import com.basejava.webapp.storage.factory.StorageFactory;
import com.basejava.webapp.storage.factory.StorageType;

import java.util.Properties;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private static final Properties props = new Properties();
    static {
        props.setProperty("storage.type", StorageType.SORTED_ARRAY.name());
    }

    public SortedArrayStorageTest() {
        super(StorageFactory.getStorage(props));
    }
}