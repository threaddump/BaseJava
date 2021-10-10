<%@ page import="com.basejava.webapp.model.Resume" %>
<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page import="com.basejava.webapp.web.HtmlSnippets" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <script src="js/script.js"></script>
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <jsp:useBean id="storageAction" type="java.lang.String" scope="request"/>

    <%if (storageAction.equals("update")) {%>
        <title>Редактирование резюме ${resume.fullName}</title>
    <%} else if (storageAction.equals("save")) {%>
        <title>Создание нового резюме</title>
    <%} else {
        throw new IllegalStateException("Invalid attribute: storageAction");
    }%>

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
            <%if (storageAction.equals("update")) {%>
                <p><%=HtmlSnippets.getViewLink(resume.getUuid())%></p>
                <p><%=HtmlSnippets.getAckDeleteLink(resume.getUuid())%></p>
            <%} %>
        </td>

        <!-- content -->
        <td class="td_main_section_content">

            <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="uuid" value="${resume.uuid}">
                <input type="hidden" name="action" value="${storageAction}">

                <div class="form_div">
                    <label for="fullName" class="form_label">Имя:</label>
                    <span class="form_span">
                        <input type="text" name="fullName" id="fullName" value="${resume.fullName}" />
                    </span>
                </div>

                <br />
                <h3 style="padding-left: 5px;">Контакты:</h3>
                <p>
                    <c:forEach var="contactType" items="<%=ContactType.values()%>">
                        <jsp:useBean id="contactType" type="com.basejava.webapp.model.ContactType" />
                        <div class="form_div">
                            <label for="${contactType.name()}" class="form_label">${contactType.title}:</label>
                            <span class="form_span">
                                <input type="text" name="${contactType.name()}" id="${contactType.name()}" value="${resume.getContact(contactType)}" />
                            </span>
                        </div>
                    </c:forEach>
                </p>

                <hr />

                <br />
                <h3 style="padding-left: 5px;">Секции:</h3>
                <p>
                    <c:forEach var="sectionType" items="<%=SectionType.values()%>">
                        <jsp:useBean id="sectionType" type="com.basejava.webapp.model.SectionType" />
                        <c:choose>

                            <c:when test="${(sectionType == SectionType.OBJECTIVE) || (sectionType == SectionType.PERSONAL)}">
                                <div class="form_div">
                                    <label for="${sectionType.name()}" class="form_label">${sectionType.title}:</label>
                                    <span class="form_span">
                                        <!-- TODO: not really clear how to initialize value, especially if section can be missing.
                                        ensure that sections are non-null? will require changes in viewing...
                                        maybe add isEmpty() method to Section interface? -->
                                        <input type="text" name="${sectionType.name()}" id="${sectionType.name()}" value="TODO" />
                                    </span>
                                </div>
                            </c:when>

                            <c:when test="${(sectionType == SectionType.ACHIEVEMENT) || (sectionType == SectionType.QUALIFICATIONS)}">
                                <div class="form_div">
                                    <label for="${sectionType.name()}" class="form_label">${sectionType.title}:</label>
                                    <span class="form_span">
                                        <textarea name="${sectionType.name()}" id="${sectionType.name()}"
                                                  oninput="auto_height(this);">TODO</textarea>
                                    </span>
                                </div>
                            </c:when>

                            <c:when test="${(sectionType == SectionType.EXPERIENCE) || (sectionType == SectionType.EDUCATION)}">
                                <p>${sectionType.title} is not supported yet</p>
                            </c:when>

                        </c:choose>
                    </c:forEach>
                </p>

                <!--
                <br>
                <dl>
                    <dt>поле раз:</dt>
                    <dd><input type="text" name="TESTFIELD" size=50 value="${resume.fullName}"></dd>
                </dl>
                <dl>
                    <dt>поле два-с:</dt>
                    <dd><input type="text" name="TESTFIELD" size=50 value="${resume.fullName}"></dd>
                </dl>
                -->
                <!-- params with the same name can be retrieved using request.getParameterValues(name) -->

                <hr />
                <button type="submit">Сохранить</button>
            </form>

        </td>
    </tr>
    </tbody>
</table>

<jsp:include page="inc/footer.jsp"/>
</body>
</html>