package com.basejava.webapp.drafts;

import com.basejava.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("fullName");
        System.out.println(r);

        Field f = r.getClass().getDeclaredFields()[0];
        System.out.println(f.getName());

        // Reflection is a pathway to many abilities, some considered to be unnatural.
        // E.g. changing 'private final' fields.
        f.setAccessible(true);
        String s = (String) f.get(r);
        f.set(r, s.toUpperCase());
        System.out.println(f.get(r));

        // Homework task: invoke r.toString via reflection
        Method m = r.getClass().getDeclaredMethod("toString");
        System.out.println("Invoked Resume.toString() via reflection: " + ((String) m.invoke(r)));
    }
}
