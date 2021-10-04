package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveInternal(Resume r) {
        // Arrays.binarySearch returns (-insertionPoint - 1) when key is not found.
        int idx = -findIndex(r.getUuid()) - 1;
        System.arraycopy(storage, idx, storage, idx + 1, size - idx);
        storage[idx] = r;
        size++;
    }

    @Override
    protected void deleteInternal(int idx) {
        if (idx < (size - 1)) {
            System.arraycopy(storage, idx + 1, storage, idx, size - (idx + 1));
        }
        storage[--size] = null;
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
