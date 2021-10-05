package com.basejava.webapp.drafts;

public class MainString {
    public static void main(String[] args) {
        String[] strArray = new String[]{"1", "2", "3", "4", "5"};
        /*
        String result = "";
        for (String str : strArray) {
            result += str + ", ";
        }
        System.out.println(result);
        */

        // more efficient approach
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str).append(", ");
        }
        System.out.println(sb.toString());
        // p.s. StringBuffer is similar to StringBuilder, but older, and (!) thread-safe

        // string pooling effect on string comparison
        String s1 = "abc";
        String s2 = "abc";
        String s3 = "ab" + "c"; // can't trick optimizer this way
        String s4 = "c";
        String s5 = "ab" + s4; // but this way - we can
        String s6 = ("ab" + s4).intern(); // runtime search in pool
        System.out.println(s1 == s2); // true
        System.out.println(s1 == s3); // true
        System.out.println(s1 == s5); // false
        System.out.println(s1 == s6); // true
    }
}
