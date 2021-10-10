<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page import="com.basejava.webapp.model.TextSection" %>
<%@ page import="com.basejava.webapp.model.ListSection" %>
<%@ page import="com.basejava.webapp.model.OrgSection" %>
<%@ page import="com.basejava.webapp.model.Position" %>
<%@ page import="com.basejava.webapp.web.ContactRenderer" %>
<%@ page import="com.basejava.webapp.web.HtmlSnippets" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Просмотр резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="inc/header.jsp"/>

<!-- main section -->
<table class="table_main_section">
    <tbody>
    <tr>
        <!-- menu; sorry for the div -->
        <td class="td_main_section_menu">
            <div class="rasporka"></div>
            <p><%=HtmlSnippets.getCreateLink()%></p>
            <p><%=HtmlSnippets.getListLink()%></p>
            <p><%=HtmlSnippets.getEditLink(resume.getUuid())%></p>
            <p><%=HtmlSnippets.getAckDeleteLink(resume.getUuid())%></p>
        </td>

        <!-- content -->
        <td class="td_main_section_content">

            <h1>${resume.fullName}</h1>

            <p>
                <c:forEach var="contactEntry" items="${resume.contacts}">
                    <jsp:useBean id="contactEntry" type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>" />
                    <%=ContactRenderer.toHtml(contactEntry.getKey(), contactEntry.getValue())%><br>
                </c:forEach>
            </p>

            <hr/>

            <c:forEach var="sectionEntry" items="${resume.sections}">
                <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.Section>" />

                <c:set var="sectionType" value="${sectionEntry.key}" />
                <jsp:useBean id="sectionType" type="com.basejava.webapp.model.SectionType" />
                <h2>${sectionType.title}</h2>

                <c:set var="section" value="${sectionEntry.value}" />
                <jsp:useBean id="section" type="com.basejava.webapp.model.Section" />

                <c:choose>
                    <c:when test="${sectionType == SectionType.OBJECTIVE}">
                        <p><h3><%=((TextSection) section).getContent()%></h3></p>
                    </c:when>
                    <c:when test="${sectionType == SectionType.PERSONAL}">
                        <p><%=((TextSection) section).getContent()%></p>
                    </c:when>

                    <c:when test="${(sectionType == SectionType.ACHIEVEMENT) || (sectionType == SectionType.QUALIFICATIONS)}">
                        <p>
                            <ul>
                                <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </p>
                    </c:when>

                    <c:when test="${(sectionType == SectionType.EXPERIENCE) || (sectionType == SectionType.EDUCATION)}">
                        <c:forEach var="organization" items="<%=((OrgSection) section).getOrgs()%>">
                            <jsp:useBean id="organization" type="com.basejava.webapp.model.Organization" />

                            <c:set var="link" value="${organization.link}" />
                            <jsp:useBean id="link" type="com.basejava.webapp.model.Link" />
                            <c:choose>
                                <c:when test="${empty link.url}">
                                    <h3>${link.title}</h3>
                                </c:when>
                                <c:otherwise>
                                    <h3><a href="${link.url}" target="_blank">${link.title}</a></h3>
                                </c:otherwise>
                            </c:choose>

                            <table class="table_list_positions">
                                <tbody>
                                    <c:forEach var="position" items="${organization.positions}">
                                        <jsp:useBean id="position" type="com.basejava.webapp.model.Position" />
                                        <tr>
                                            <td class="td_list_positions_1"><%=HtmlSnippets.getTimeSpanStr(position.getTimeSpan())%></td>
                                            <td class="td_list_positions_2"><b>${position.title}</b></td>
                                        </tr>
                                        <c:set var="posDescription" value="${position.description}" />
                                        <c:if test="${not empty posDescription}">
                                            <tr>
                                                <td class="td_list_positions_1"></td>
                                                <td class="td_list_positions_2">${position.description}</td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>

        </td>
    </tr>
    </tbody>
</table>

<jsp:include page="inc/footer.jsp"/>
</body>
</html>