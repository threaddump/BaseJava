<%@ page import="com.basejava.webapp.model.*" %>
<%@ page import="com.basejava.webapp.web.HtmlSnippets" %>
<%@ page import="com.basejava.webapp.util.HtmlUtils" %>
<%@ page import="com.basejava.webapp.util.DateUtils" %>
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
            <p><a href="resume?action=create"><img src="img/action/plus.svg" class="img_action">Новое резюме</a></p>
            <p><a href="resume?action=list"><img src="img/action/back.svg" class="img_action">Назад к списку</a></p>
            <p><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/action/edit.svg" class="img_action">Изменить</a></p>
            <p><a href="resume?uuid=${resume.uuid}&action=ack_delete"><img src="img/action/cross.svg" class="img_action">Удалить</a></p>
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
                            <h3><%=HtmlUtils.makeHref(link)%></h3>

                            <table class="table_list_positions">
                                <tbody>
                                    <c:forEach var="position" items="${organization.positions}">
                                        <jsp:useBean id="position" type="com.basejava.webapp.model.Position" />
                                        <tr>
                                            <td class="td_list_positions_1"><%=DateUtils.format(position.getTimeSpan())%></td>
                                            <td class="td_list_positions_2"><b>${position.title}</b></td>
                                        </tr>
                                        <c:set var="posDescription" value="${position.description}" />
                                        <c:if test="${not empty posDescription}">
                                            <tr>
                                                <td class="td_list_positions_1"></td>
                                                <td class="td_list_positions_2"><p>${position.description}</p></td>
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