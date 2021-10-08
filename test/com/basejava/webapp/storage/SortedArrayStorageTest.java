package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test()
    public void testDeleteLast() {
        // delete last resume in SortedArrayStorage
        // (System.arraycopy is not invoked in this case)

        List<Resume> resumes = new ArrayList<>(storage.getAllSorted());
        resumes.sort(Comparator.comparing(Resume::getUuid));

        Resume resumeToDelete = resumes.get(resumes.size() - 1);
        storage.delete(resumeToDelete.getUuid());

        List<Resume> targetResumes = new ArrayList<>(resumes.subList(0, resumes.size() - 1));

        List<Resume> actualResumes = new ArrayList<>(storage.getAllSorted());
        actualResumes.sort(Comparator.comparing(Resume::getUuid));

        assertEquals(targetResumes, actualResumes);
    }

    @Test()
    public void testDeleteFirst() {
        // delete first resume in SortedArrayStorage
        // (System.arraycopy is not invoked in this case)

        List<Resume> resumes = new ArrayList<>(storage.getAllSorted());
        resumes.sort(Comparator.comparing(Resume::getUuid));

        Resume resumeToDelete = resumes.get(0);
        storage.delete(resumeToDelete.getUuid());

        List<Resume> targetResumes = new ArrayList<>(resumes.subList(1, resumes.size()));

        List<Resume> actualResumes = new ArrayList<>(storage.getAllSorted());
        actualResumes.sort(Comparator.comparing(Resume::getUuid));

        assertEquals(targetResumes, actualResumes);
    }
}