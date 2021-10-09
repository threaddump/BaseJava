package com.basejava.webapp.web;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.storage.factory.StorageFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {

    // TODO: any thread-safety issues?
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = StorageFactory.getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writePageHeader(writer);

        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            writeResume(writer, uuid);
        } else {
            writeResumeTable(writer);
        }
        writePageFooter(writer);
    }

    private void writePageHeader(PrintWriter writer) {
        writer.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <link rel=\"stylesheet\" href=\"css/style.css\" type=\"text/css\">\n" +
                "    <title>База данных резюме</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<!-- main section -->\n" +
                "<table class=\"table_main_section\">\n" +
                "    <tbody>\n" +
                "    <tr>\n" +
                "        <!-- content -->\n" +
                "        <td class=\"td_main_section_content\">");
    }

    private void writeResume(PrintWriter writer, String uuid) {
        Resume r = null;
        try {
            r = storage.get(uuid);
        } catch (NotExistStorageException e) {
            writer.write("<p>Резюме с UUID=" + uuid + " отсутствует в базе данных</p>");
            writer.write("<a href=\"/resumes/resume\">Обратно к списку резюме</a>");
            return;
        }

        writer.write("<h1>" + r.getFullName() + "</h1>");

        writer.write("<p>");
        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
            writer.write(e.getKey().getTitle() + ": " + e.getValue() + "<br>");
        }
        writer.write("</p><hr>");

        {
            TextSection s = (TextSection) r.getSection(SectionType.OBJECTIVE);
            if (s != null) {
                writer.write("<h2>" + SectionType.OBJECTIVE.getTitle() + "</h2>");
                writer.write("<b>" + s.getContent() + "</b>");
            }
        }

        {
            TextSection s = (TextSection) r.getSection(SectionType.PERSONAL);
            if (s != null) {
                writer.write("<h2>" + SectionType.PERSONAL.getTitle() + "</h2>");
                writer.write(s.getContent() + "<br>");
            }
        }

        {
            ListSection s = (ListSection) r.getSection(SectionType.ACHIEVEMENT);
            if (s != null) {
                writer.write("<h2>" + SectionType.ACHIEVEMENT.getTitle() + "</h2><ul>");
                for (String line : s.getItems()) {
                    writer.write("<li>" + line + "</li>");
                }
                writer.write("</ul>");
            }
        }

        {
            ListSection s = (ListSection) r.getSection(SectionType.QUALIFICATIONS);
            if (s != null) {
                writer.write("<h2>" + SectionType.QUALIFICATIONS.getTitle() + "</h2><ul>");
                for (String line : s.getItems()) {
                    writer.write("<li>" + line + "</li>");
                }
                writer.write("</ul>");
            }
        }
    }

    private void writeResumeTable(PrintWriter writer) {
        // write table header
        writer.write("<table class=\"table_output\">\n" +
                "                <thead class=\"thead_output\">\n" +
                "                <tr>\n" +
                "                    <th class=\"th_td_output\">UUID</th>\n" +
                "                    <th class=\"th_td_output\" style=\"width: 50%;\">Полное имя</th>\n" +
                "                </tr>\n" +
                "                </thead>\n" +
                "                <tbody class=\"tbody_output\">");
        // write table content
        List<Resume> resumes = storage.getAllSorted();
        for (Resume r : resumes) {
            writer.write("<tr><td class=\"th_td_output\"><a href=\"/resumes/resume?uuid=" + r.getUuid() + "\">" + r.getUuid());
            writer.write("</a></td><td class=\"th_td_output\">" + r.getFullName() + "</td></tr>");
        }
        // write table footer
        writer.write("                </tbody>\n" +
                "            </table>");
    }

    private void writePageFooter(PrintWriter writer) {
        writer.write("        </td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "\n" +
                "<!-- footer -->\n" +
                "<table class=\"table_footer\">\n" +
                "    <tbody>\n" +
                "    <tr>\n" +
                "        <td class=\"td_footer\">\n" +
                "            Учебный проект по курсу <a href=\"https://javaops.ru/view/basejava\" target=\"_blank\">BaseJava</a> (Java SE + Web);\n" +
                "            <a href=\"https://github.com/threaddump/BaseJava\" target=\"_blank\">исходный код</a> проекта на GitHub\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
