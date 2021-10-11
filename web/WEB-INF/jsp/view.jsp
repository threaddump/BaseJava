<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page import="com.basejava.webapp.model.TextSection" %>
<%@ page import="com.basejava.webapp.model.ListSection" %>
<%@ page import="com.basejava.webapp.model.OrgSection" %>
<%@ page import="com.basejava.webapp.model.Position" %>
<%@ page import="com.basejava.webapp.web.HtmlSnippets" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <script src="js/script.js"></script>
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
                    <c:set var="contactType" value="${contactEntry.key}" />
                    <jsp:useBean id="contactType" type="com.basejava.webapp.model.ContactType" />
                    <c:set var="contactValue" value="${contactEntry.value}" />

                    <c:choose>
                        <c:when test="${(contactType == ContactType.PHONE) || (contactType == ContactType.HOME_PHONE)}">
                            ${contactType.title}: <img src="img/icon/phone.svg" class="img_icon">${contactValue}
                        </c:when>
                        <c:when test="${contactType == ContactType.MOBILE_PHONE}">
                            ${contactType.title}: <img src="img/icon/mobile.svg" class="img_icon">${contactValue}
                        </c:when>
                        <c:when test="${contactType == ContactType.SKYPE}">
                            ${contactType.title}: <a href="skype:${contactValue}"><img src="img/icon/skype.svg" class="img_icon">${contactValue}</a>
                        </c:when>
                        <c:when test="${contactType == ContactType.EMAIL}">
                            ${contactType.title}: <a href="mailto:${contactValue}"><img src="img/icon/email.svg" class="img_icon">${contactValue}</a>
                        </c:when>
                        <c:when test="${contactType == ContactType.LINKEDIN}">
                            <a href="https://www.linkedin.com/in/${contactValue}"><img src="img/icon/linkedin.svg" class="img_icon">Профиль LinkedIn</a>
                        </c:when>
                        <c:when test="${contactType == ContactType.GITHUB}">
                            <a href="https://github.com/${contactValue}"><img src="img/icon/github.svg" class="img_icon">Профиль GitHub</a>
                        </c:when>
                        <c:when test="${contactType == ContactType.STACKOVERFLOW}">
                            <a href="https://stackoverflow.com/users/${contactValue}"><img src="img/icon/stackoverflow.svg" class="img_icon">Профиль StackOverflow</a>
                        </c:when>
                        <c:when test="${contactType == ContactType.HOME_PAGE}">
                            <a href="${contactValue}">Домашняя страница</a>
                        </c:when>
                        <c:otherwise>
                            (Неизвестный тип контакта: ${contactType.title})
                        </c:otherwise>
                    </c:choose>
                    <br />
                </c:forEach>
            </p>

            <c:if test="<%=resume.getSections().size() > 0%>">
                <hr />
            </c:if>

            <c:forEach var="sectionEntry" items="${resume.sections}">
                <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.Section>" />

                <c:set var="sectionType" value="${sectionEntry.key}" />
                <jsp:useBean id="sectionType" type="com.basejava.webapp.model.SectionType" />
                <h2>${sectionType.title}</h2>

                <c:set var="section" value="${sectionEntry.value}" />
                <jsp:useBean id="section" type="com.basejava.webapp.model.Section" />

                <c:choose>
                    <c:when test="${sectionType == SectionType.OBJECTIVE}">
                        <p><b><%=((TextSection) section).getContent()%></b></p>
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

                    <c:otherwise>
                        (Неизвестный тип секции: ${sectionType.title})<br />
                    </c:otherwise>
                </c:choose>
            </c:forEach>

        </td>
    </tr>
    </tbody>
</table>

<jsp:include page="inc/footer.jsp"/>
</body>
</html>