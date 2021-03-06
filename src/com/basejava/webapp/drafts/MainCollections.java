package com.basejava.webapp.drafts;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MainCollections {
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1, "Name1");

    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2, "Name2");

    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3, "Name3");

    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4, "Name4");

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();
        collection.add(RESUME_1);
        collection.add(RESUME_2);
        collection.add(RESUME_3);

        /*
        for (Resume r : collection) {
            System.out.println(r);

            if (r.getUuid().equals(UUID_1)) {
                // ConcurrentModificationException!
                collection.remove(r);
            }
        }
        */

        Iterator<Resume> it = collection.iterator();
        while (it.hasNext()) {
            Resume r = it.next();
            System.out.println(r);

            if (r.getUuid().equals(UUID_1)) {
                // no exception!
                it.remove();
            }
        }

        System.out.println(collection.toString());

        Map<String, Resume> map = new HashMap<>();
        map.put(UUID_1, RESUME_1);
        map.put(UUID_2, RESUME_2);
        map.put(UUID_3, RESUME_3);
        // bad approach
        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        }
        // good approach
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }

        /*
        List<Resume> resumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        resumes.remove(1); // UnsupportedOperationException
        System.out.println(resumes);
         */
    }
}
