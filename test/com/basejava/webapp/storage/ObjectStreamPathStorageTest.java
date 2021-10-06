package com.basejava.webapp.storage;

import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectStreamPathStorageTest extends AbstractFileStorageTest {
    public ObjectStreamPathStorageTest() { super(new ObjectStreamPathStorage(STORAGE_DIR.getAbsolutePath())); }
}