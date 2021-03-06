package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElem(int idx, Resume r) {
        // Arrays.binarySearch returns (-insertionPoint - 1) when key is not found.
        int targetIdx = -idx - 1;
        System.arraycopy(storage, targetIdx, storage, targetIdx + 1, size - targetIdx);
        storage[targetIdx] = r;
    }

    @Override
    protected void fillDeletedElem(int idx) {
        int tailLen = size - (idx + 1);
        if (tailLen > 0) {
            System.arraycopy(storage, idx + 1, storage, idx, tailLen);
        }
    }

    @Override
    protected Integer findKey(String uuid) {
        Resume searchKey = new Resume(uuid, "ignored");
        return Arrays.binarySearch(
                storage, 0, size, searchKey,
                Comparator.comparing(Resume::getUuid)
                );
    }
}
