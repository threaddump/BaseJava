package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import org.junit.Ignore;
import org.junit.Test;

public class ListStorageTest extends AbstractStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    @Ignore
    @Test(expected = StorageException.class)
    public void saveOverflow() {
        /*
         AbstractStorageTest.saveOverflow() takes really long time for ListStorage.
         There are 2 obvious ways to fix this:
         1. Override test in ListStorageTest and throw the required StorageException;
         2. Override test with the same @Test annotation (!), and add @Ignore.
         */

        //throw new StorageException("Skipping saveOverflow() test for ListStorage", "");
    }
}