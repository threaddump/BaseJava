package com.basejava.webapp.storage;

import com.basejava.webapp.storage.factory.StorageFactory;
import com.basejava.webapp.storage.factory.StorageType;

import java.util.Properties;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    private static final Properties props = new Properties();
    static {
        props.setProperty("storage.type", StorageType.ARRAY.name());
    }

    public ArrayStorageTest() {
        super(StorageFactory.getStorage(props));
    }
}