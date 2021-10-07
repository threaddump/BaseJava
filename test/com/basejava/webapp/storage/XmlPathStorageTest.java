package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.factory.StorageFactory;
import com.basejava.webapp.storage.factory.StorageType;
import com.basejava.webapp.storage.factory.StreamSerializerType;

import java.util.Properties;

public class XmlPathStorageTest extends AbstractStorageTest {
    private static final Properties props = new Properties();
    static {
        props.setProperty("storage.type", StorageType.PATH.name());
        props.setProperty("storage.dir", Config.get().getProps().getProperty("storage.dir"));
        props.setProperty("serializer.type", StreamSerializerType.XML.name());
    }

    public XmlPathStorageTest() {
        super(StorageFactory.getStorage(props));
    }
}