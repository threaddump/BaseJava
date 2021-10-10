package com.basejava.webapp.web;

import com.basejava.webapp.model.*;

import java.time.LocalDate;

public class SectionRenderer {

    public static String toHtml(SectionType type, Section section) {
        if (section == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<h2>").append(type.getTitle()).append("</h2>");

        switch (type) {
            case OBJECTIVE: case PERSONAL:
                renderTextSection(sb, (TextSection) section);
                break;
            case ACHIEVEMENT: case QUALIFICATIONS:
                renderListSection(sb, (ListSection) section);
                break;
            case EXPERIENCE: case EDUCATION:
                renderOrgSection(sb, (OrgSection) section);
                break;
            default:
                throw new IllegalArgumentException("Unsupported SectionType: " + type);
        }

        return sb.toString();
    }

    private static void renderTextSection(StringBuilder sb, TextSection section) {
        sb.append("<p>").append(section.getContent()).append("</p>");
    }

    private static void renderListSection(StringBuilder sb, ListSection section) {
        sb.append("<p><ul>");
        for (String line : section.getItems()) {
            sb.append("<li>").append(line).append("</li>");
        }
        sb.append("</ul></p>");
    }

    private static void renderOrgSection(StringBuilder sb, OrgSection section) {
        for (Organization org : section.getOrgs()) {
            renderLink(sb, org.getLink());
            sb.append("<table class=\"table_list_positions\"><tbody>");
            for (Position pos : org.getPositions()) {
                renderPosition(sb, pos);
            }
            sb.append("</tbody></table>");
        }
    }

    private static void renderLink(StringBuilder sb, Link link) {
        String url = link.getUrl();

        sb.append("<h3>");
        if (!url.equals("")) {
            sb.append("<a href=\"").append(url).append("\" target=\"_blank\">").append(link.getTitle()).append("</a>");
        } else {
            sb.append(link.getTitle());
        }
        sb.append("</h3>");
    }

    private static void renderPosition(StringBuilder sb, Position pos) {
        sb.append("<tr><td class=\"th_td_list_positions_1\">");
        renderTimeSpan(sb, pos.getTimeSpan());
        sb.append("</td><td class=\"th_td_list_positions_2\"><b>").append(pos.getTitle()).append("</b></td></tr>");

        String desc = pos.getDescription();
        if (!desc.equals("")) {
            sb.append("<tr><td class=\"th_td_list_positions_1\">&nbsp;</td><td class=\"th_td_list_positions_2\"><p>");
            sb.append(desc).append("</p></td></tr>");
        }
    }

    private static void renderTimeSpan(StringBuilder sb, TimeSpan ts) {
        renderLocalDate(sb, ts.getBegin());
        sb.append(" - ");
        renderLocalDate(sb, ts.getEnd());
    }

    private static void renderLocalDate(StringBuilder sb, LocalDate ld) {
        if (ld.equals(LocalDate.MAX)) {
            sb.append("Сейчас");
        } else {
            sb.append(ld.getMonthValue()).append('/').append(ld.getYear());
        }
    }
}
