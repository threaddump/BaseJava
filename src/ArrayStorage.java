import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int countResume;

    void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    void save(Resume r) {
        // storage overflow if (countResume == storage.length)
        storage[countResume++] = r;
    }

    Resume get(String uuid) {
        final int idx = findIndex(uuid);
        return (idx != -1) ? storage[idx] : null;
    }

    void delete(String uuid) {
        final int targetIdx = findIndex(uuid);
        if (targetIdx != -1) {
            if (targetIdx < (countResume - 1)) {
                System.arraycopy(storage, targetIdx + 1, storage, targetIdx, countResume - (targetIdx + 1));
            }
            storage[--countResume] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    int size() {
        return countResume;
    }

    /**
     * @return Index of matching element of storage[], or -1 (not found)
     */
    private int findIndex(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
