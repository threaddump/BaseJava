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
        // storage overflow if (count == storage.length)
        storage[elemCount++] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < elemCount; i++) {
            final Resume resume = storage[i];
            if (resume.uuid.equals(uuid)) {
                return resume;
            }
        }

        // not found
        return null;
    }

    void delete(String uuid) {
        // find array index by uuid
        int targetIdx = -1;
        for (int i = 0; i < elemCount; i++) {
            if (storage[i].uuid.equals(uuid)) {
                targetIdx = i;
                break;
            }
        }

        // not found, no action required
        if (targetIdx == -1) {
            return;
        }

        // shift elements, remove duplicated last element, shrink storage:
        // [ a b target d e ... ] -- > [ a b d e NULL ... ]
        if (targetIdx < (elemCount - 1)) {
            System.arraycopy(
                    storage, targetIdx + 1,
                    storage, targetIdx,
                    elemCount - (targetIdx + 1)
                    );
        }
        storage[--elemCount] = null;
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
}
