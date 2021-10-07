package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.factory.StorageFactory;
import com.basejava.webapp.storage.factory.StorageType;
import org.junit.After;

import java.util.Properties;

public class SqlStorageTest extends AbstractStorageTest {
    private static final Properties props = new Properties();
    static {
        props.setProperty("storage.type", StorageType.SQL.name());
        props.setProperty("db.url", Config.get().getProps().getProperty("db.url"));
        props.setProperty("db.user", Config.get().getProps().getProperty("db.user"));
        props.setProperty("db.password", Config.get().getProps().getProperty("db.password"));
    }

    public SqlStorageTest() {
        super(StorageFactory.getStorage(props));
    }

    @After
    public void tearDown() throws Exception {
        storage.clear();
    }
}