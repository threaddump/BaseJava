import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int elemCount;

    void clear() {
        Arrays.fill(storage, 0, elemCount, null);
        elemCount = 0;
    }

    void save(Resume r) {
        // storage overflow if (elemCount == storage.length)
        storage[elemCount++] = r;
    }

    Resume get(String uuid) {
        final int idx = lookupByUUID(uuid);
        return (idx != -1) ? storage[idx] : null;
    }

    void delete(String uuid) {
        final int targetIdx = lookupByUUID(uuid);
        if (targetIdx != -1) {
            if (targetIdx < (elemCount - 1)) {
                System.arraycopy(storage, targetIdx + 1, storage, targetIdx, elemCount - (targetIdx + 1));
            }
            storage[--elemCount] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, elemCount);
    }

    int size() {
        return elemCount;
    }

    /**
     * @return Index of matching element of storage[], or -1 (not found)
     */
    private int lookupByUUID(String uuid) {
        for (int i = 0; i < elemCount; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
