package com.basejava.webapp.util;

import org.apache.commons.text.StringEscapeUtils;

public final class HtmlUtils {

    private HtmlUtils() {
    }

    /**
     * Replaces symbols, that are special in HTML, with safe-to-use character sequences.
     * E.g. "&" --> "&amp;". These sequences are safe-to-include into HTML code.
     *
     * Dependencies:
     *  org.apache.commons.text (~ v1.9)
     *  org.apache.commons.lang3 (~ v3.11)
     */
    public static String escapeHtml(String str) {
        return StringEscapeUtils.escapeHtml4(str);
    }
}