package com.basejava.webapp.web;

import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.storage.factory.StorageFactory;
import com.basejava.webapp.util.HtmlUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = StorageFactory.getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action == null) ? "list" : action;

        switch (action) {
            case "list":
                handleList(request, response);
                break;
            case "view":
                handleView(request, response);
                break;
            case "create":
                handleCreate(request, response);
                break;
            case "edit":
                handleEdit(request, response);
                break;
            case "ack_delete":
                handleAcknowledgeDelete(request, response);
                break;
            case "delete":
                handleDelete(request, response);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " (get) is illegal");
        }
    }

    private void handleList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Resume> resumes = storage.getAllSorted();
        request.setAttribute("resumes", resumes);
        String targetJsp = (resumes.size() > 0) ? "/WEB-INF/jsp/list.jsp" : "/WEB-INF/jsp/empty.jsp";
        request.getRequestDispatcher(targetJsp).forward(request, response);
    }

    private void handleView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        Resume r = storage.get(uuid);
        request.setAttribute("resume", r);
        request.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(request, response);
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        Resume r = storage.get(uuid);
        populateEmptySections(r);
        request.setAttribute("resume", r);
        request.setAttribute("storageAction", "update");
        request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
    }

    private void handleCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = UUID.randomUUID().toString();
        Resume r = new Resume("Имя Фамилия");
        populateEmptySections(r);
        request.setAttribute("resume", r);
        request.setAttribute("storageAction", "save");
        request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
    }

    private void handleAcknowledgeDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        Resume r = storage.get(uuid);
        request.setAttribute("resume", r);
        request.getRequestDispatcher("/WEB-INF/jsp/ack_delete.jsp").forward(request, response);
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uuid = request.getParameter("uuid");
        storage.delete(uuid);
        response.sendRedirect("resume");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            throw new IllegalArgumentException("Action must not be null");
        } else if (!(action.equals("update") || action.equals("save"))) {
            throw new IllegalArgumentException("Action " + action + " must not be null");
        }

        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        fullName = HtmlUtils.escapeHtml(fullName);

        Resume r = null;
        switch (action) {
            case "update":
                r = storage.get(uuid);
                r.setFullName(fullName);
                break;
            case "save":
                r = new Resume(uuid, fullName);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " (post) is illegal");
        }

        for (ContactType contactType : ContactType.values()) {
            String contactValue = request.getParameter(contactType.name());
            if (!HtmlSnippets.isNullOrEmpty(contactValue)) {
                contactValue = HtmlUtils.escapeHtml(contactValue);
                r.setContact(contactType, contactValue);
            } else {
                r.getContacts().remove(contactType);
            }
        }

        for (SectionType sectionType : SectionType.values()) {
            switch (sectionType) {
                case OBJECTIVE: case PERSONAL:
                    String textSectionValue = request.getParameter(sectionType.name());
                    if (!HtmlSnippets.isNullOrEmpty(textSectionValue)) {
                        textSectionValue = HtmlUtils.escapeHtml(textSectionValue);
                        r.setSection(sectionType, new TextSection(textSectionValue));
                    } else {
                        r.getSections().remove(sectionType);
                    }
                    break;
                case ACHIEVEMENT: case QUALIFICATIONS:
                    String listSectionValueRaw = request.getParameter(sectionType.name());
                    // Different combinations of CR / CR+LF / LF are possible in input, all of them are line breaks.
                    // java.util.regex.Pattern supports "Linebreak matcher" - "\R", which is exactly what we need.
                    // https://stackoverflow.com/questions/454908/split-java-string-by-new-line/31060125#31060125
                    String[] listSectionValue = listSectionValueRaw.split("\\R");
                    // drop empty values
                    listSectionValue = Arrays.stream(listSectionValue)
                                            .filter(s -> !HtmlSnippets.isNullOrEmpty(s))
                                            .toArray(String[]::new);
                    if (listSectionValue.length > 0) {
                        listSectionValue = Arrays.stream(listSectionValue)
                                                .map(HtmlUtils::escapeHtml)
                                                .toArray(String[]::new);
                        r.setSection(sectionType, new ListSection(listSectionValue));
                    } else {
                        r.getSections().remove(sectionType);
                    }
                    break;
                case EXPERIENCE: case EDUCATION:
                    // TODO
                    break;
            }
        }

        // TODO: parse resume from params; don't forget to escape user input

        switch (action) {
            case "update":
                storage.update(r);
                break;
            case "save":
                storage.save(r);
                break;
        }

        request.setAttribute("resume", r);
        request.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(request, response);
    }

    private void populateEmptySections(Resume r) {
        // replace null sections with empty objects
        // nulls lead to "java.lang.InstantiationException: bean section not found within scope" in edit.jsp (WTF?!)
        for (SectionType sectionType : SectionType.values()) {
            Section section = r.getSection(sectionType);
            if (section == null) {
                switch (sectionType) {
                    case OBJECTIVE: case PERSONAL:
                        section = TextSection.EMPTY;
                        break;
                    case ACHIEVEMENT: case QUALIFICATIONS:
                        section = ListSection.EMPTY;
                        break;
                    case EXPERIENCE: case EDUCATION:
                        // TODO:
                        section = TextSection.EMPTY;
                        //section = OrgSection.EMPTY;
                        break;
                }
            }
            r.setSection(sectionType, section);
        }
    }
}
