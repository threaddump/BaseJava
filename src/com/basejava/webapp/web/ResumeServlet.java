package com.basejava.webapp.web;

import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.storage.factory.StorageFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        request.setAttribute("resume", r);
        request.setAttribute("storageAction", "update");
        request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
    }

    private void handleCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = UUID.randomUUID().toString();
        Resume r = new Resume("Имя Фамилия");
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
        action = (action == null) ? "" : action;

        String uuid = request.getParameter("uuid");
        String fullName = HtmlSnippets.escapeHTML(request.getParameter("fullName"));

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
            if (contactValue != null && contactValue.trim().length() != 0) {
                r.setContact(contactType, contactValue);
            } else {
                r.getContacts().remove(contactType);
            }
        }

        // TODO: parse resume from params

        switch (action) {
            case "update":
                storage.update(r);
                break;
            case "save":
                storage.save(r);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " (post) is illegal");
        }

        request.setAttribute("resume", r);
        request.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(request, response);
    }
}
