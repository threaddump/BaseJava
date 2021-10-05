package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator RESUME_COMPARATOR = new Comparator<Resume>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    };

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
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid), RESUME_COMPARATOR);
    }
}
