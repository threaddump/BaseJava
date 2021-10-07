package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import org.junit.After;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword()));
    }

    @After
    public void tearDown() throws Exception {
        storage.clear();
    }
}