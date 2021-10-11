package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.storage.factory.StorageFactory;
import com.basejava.webapp.util.DateUtils;
import com.basejava.webapp.util.HtmlUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ResumeServlet extends HttpServlet {

    private Storage storage;
    private String lockedUuids;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = StorageFactory.getStorage();
        lockedUuids = Config.get().getLockedUuids();
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
            case "ack_delete":
                handleAcknowledgeDelete(request, response);
                break;
            case "delete":
                handleDelete(request, response);
                break;
            case "create":
                handleCreate(request, response);
                break;
            case "edit":
                handleEdit(request, response);
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

    private void handleAcknowledgeDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        Resume r = storage.get(uuid);
        request.setAttribute("resume", r);
        request.getRequestDispatcher("/WEB-INF/jsp/ack_delete.jsp").forward(request, response);
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uuid = request.getParameter("uuid");
        checkLockedUuid(uuid);
        storage.delete(uuid);
        response.sendRedirect("resume");
    }

    private void handleCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = UUID.randomUUID().toString();
        Resume r = new Resume("Имя Фамилия");
        populateEmptySections(r);
        request.setAttribute("resume", r);
        request.setAttribute("storageAction", "save");
        request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        checkLockedUuid(uuid);
        Resume r = storage.get(uuid);
        populateEmptySections(r);
        request.setAttribute("resume", r);
        request.setAttribute("storageAction", "update");
        request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
    }

    private void checkLockedUuid(String uuid) {
        if (uuid == null) return;
        for (String uuidToCheck : lockedUuids.split(",")) {
            if (uuid.equals(uuidToCheck)) {
                throw new IllegalStateException("Resume for uuid=" + uuid + " is locked from changes");
            }
        }
    }

    private void populateEmptySections(Resume r) {
        // replace null sections with empty objects
        // nulls lead to "java.lang.InstantiationException: bean section not found within scope" in edit.jsp
        for (SectionType sectionType : SectionType.values()) {
            Section section = r.getSection(sectionType);
            switch (sectionType) {
                case OBJECTIVE: case PERSONAL:
                    section = (section != null) ? section : TextSection.EMPTY;
                    break;
                case ACHIEVEMENT: case QUALIFICATIONS:
                    section = (section != null) ? section : ListSection.EMPTY;
                    break;
                case EXPERIENCE: case EDUCATION:
                    // Organizations and positions are sent to edit.jsp in reverse order,
                    // insertion of new fields in the beginning of the form and
                    // consequent parsing will be a mess otherwise.
                    // The order will be reversed back in parsing, so view.jsp
                    // is not affected.
                    LinkedList<Organization> nonEmptyOrgsList = new LinkedList<>();
                    if (section != null) {
                        for (Organization org : ((OrgSection) section).getOrgs()) {
                            LinkedList<Position> nonEmptyPosList = new LinkedList<>();
                            org.getPositions().stream().forEach(nonEmptyPosList::addFirst);
                            nonEmptyOrgsList.addFirst(new Organization(org.getLink(), nonEmptyPosList));
                        }
                    }
                    section = new OrgSection(nonEmptyOrgsList);
                    break;
            }
            r.setSection(sectionType, section);
        }
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
            if (!HtmlUtils.isNullOrEmpty(contactValue)) {
                contactValue = HtmlUtils.escapeHtml(contactValue);
                r.setContact(contactType, contactValue);
            } else {
                r.removeContact(contactType);
            }
        }

        for (SectionType sectionType : SectionType.values()) {
            Section section = null;
            switch (sectionType) {
                case OBJECTIVE: case PERSONAL:
                    String textSectionValue = request.getParameter(sectionType.name());
                    if (!HtmlUtils.isNullOrEmpty(textSectionValue)) {
                        textSectionValue = HtmlUtils.escapeHtml(textSectionValue);
                        section = new TextSection(textSectionValue);
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
                                            .filter(s -> !HtmlUtils.isNullOrEmpty(s))
                                            .toArray(String[]::new);
                    if (listSectionValue.length > 0) {
                        listSectionValue = Arrays.stream(listSectionValue)
                                                .map(HtmlUtils::escapeHtml)
                                                .toArray(String[]::new);
                        section = new ListSection(listSectionValue);
                    }
                    break;
                case EXPERIENCE: case EDUCATION:
                    // parse organizations
                    LinkedList<Organization> orgsList = new LinkedList<>();
                    final String orgPrefix = sectionType.name() + "_org";
                    int orgCounter = Integer.parseInt(request.getParameter(orgPrefix + "_counter"));
                    for (int orgIdx = 0; orgIdx < orgCounter; orgIdx++) {
                        final String orgIndexedPrefix = orgPrefix + "_" + orgIdx;

                        // parse link
                        String orgTitle = request.getParameter(orgIndexedPrefix + "_title");
                        if (HtmlUtils.isNullOrEmpty(orgTitle)) {
                            continue;
                        }
                        String orgUrl = request.getParameter(orgIndexedPrefix + "_url");
                        Link orgLink = new Link(HtmlUtils.escapeHtml(orgTitle), HtmlUtils.escapeHtml(orgUrl));

                        // parse positions
                        LinkedList<Position> orgPositions = new LinkedList<>();
                        final String posPrefix = orgIndexedPrefix + "_pos";
                        int posCounter = Integer.parseInt(request.getParameter(posPrefix + "_counter"));
                        for (int posIdx = 0; posIdx < posCounter; posIdx++) {
                            final String posIndexedPrefix = posPrefix + "_" + posIdx;

                            // parse time span
                            String posBegin = request.getParameter(posIndexedPrefix + "_begin");
                            String posEnd = request.getParameter(posIndexedPrefix + "_end");
                            TimeSpan timeSpan = new TimeSpan(DateUtils.parse(posBegin), DateUtils.parse(posEnd));

                            // parse other attributes
                            String posTitle = request.getParameter(posIndexedPrefix + "_title");
                            String posDesc = request.getParameter(posIndexedPrefix + "_desc");
                            if (HtmlUtils.isNullOrEmpty(posTitle)) {
                                continue;
                            }

                            posTitle = HtmlUtils.escapeHtml(posTitle);
                            posDesc = HtmlUtils.escapeHtml(posDesc);
                            // TODO: normalize linebreaks? also in other multiline fields?
                            orgPositions.addFirst(new Position(timeSpan, posTitle, posDesc));
                        }

                        orgsList.addFirst(new Organization(orgLink, orgPositions));
                    }

                    if (!orgsList.isEmpty()) {
                        section = new OrgSection(orgsList);
                    }
                    break; // (switch)
            }

            if (section != null) {
                r.setSection(sectionType, section);
            } else {
                r.removeSection(sectionType);;
            }
        }

        switch (action) {
            case "update":
                storage.update(r);
                break;
            case "save":
                storage.save(r);
                break;
        }

        request.setAttribute("resume", r);
        response.sendRedirect("resume?uuid=" + uuid + "&action=view");
    }
}
