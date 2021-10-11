package com.basejava.webapp.util;

import com.basejava.webapp.model.Link;
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

    public static boolean isNullOrEmpty(String str) {
        return (str == null) || (str.trim().length() == 0);
    }

    /**
     * Returns html code for a href, or just a plain text string if url is empty.
     */
    public static String makeHref(String title, String url) {
        return isNullOrEmpty(url) ? title : ("<a href=\"" + url + "\">" + title + "</a>");
    }

    public static String makeHref(Link link) {
        return makeHref(link.getTitle(), link.getUrl());
    }
}