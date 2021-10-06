package com.basejava.webapp.storage;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectStreamStorageTest extends AbstractFileStorageTest {
    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}