package com.basejava.webapp;

public class MainInteger {
    public static void main(String[] args) {
        System.out.println(Integer.valueOf(-1) == Integer.valueOf(-1)); // true
        System.out.println(Integer.valueOf(-1) == new Integer(-1)); // false

        int result = getInt(); // auto-unboxing (Integer --> Int)
        System.out.println(result);

        System.out.println(Integer.valueOf(-1) == getInt()); // true; auto-boxing uses cache!
    }

    private static Integer getInt() {
        // return null; // NullPointerException
        return -1; // auto-boxing (int --> Integer)
    }
}
