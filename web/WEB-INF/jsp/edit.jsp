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
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <jsp:useBean id="storageAction" type="java.lang.String" scope="request"/>

    <%if (storageAction.equals("update")) {%>
        <title>Редактирование резюме ${resume.fullName}</title>
    <%} else if (storageAction.equals("save")) {%>
        <title>Создание нового резюме</title>
    <%} else {
        throw new IllegalStateException("Unable to find attribute: storageAction");
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

                <dl>
                    <dt>Имя:</dt>
                    <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
                </dl>

                <br>
                <h3>Контакты:</h3>
                <p>
                    <c:forEach var="contactType" items="<%=ContactType.values()%>">
                    <jsp:useBean id="contactType" type="com.basejava.webapp.model.ContactType"/>
                    <dl>
                        <dt>${contactType.title}</dt>
                        <dd><input type="text" name="${contactType.name()}" size=30 value="${resume.getContact(contactType)}"></dd>
                    </dl>
                    </c:forEach>
                </p>

                <br>
                <h3>Секции:</h3>
                <p>
                    <c:forEach var="sectionType" items="<%=SectionType.values()%>">
                    <jsp:useBean id="sectionType" type="com.basejava.webapp.model.SectionType"/>
                        <dl>
                            <dt>${sectionType.title}</dt>
                            <dd><input type="text" name="${sectionType.name()}" size="30" value="TODO"></dd>
                        </dl>
                    </c:forEach>
                </p>

                <!-- TODO -->
                <br>
                <dl>
                    <dt>поле раз:</dt>
                    <dd><input type="text" name="TESTFIELD" size=50 value="${resume.fullName}"></dd>
                </dl>
                <dl>
                    <dt>поле два-с:</dt>
                    <dd><input type="text" name="TESTFIELD" size=50 value="${resume.fullName}"></dd>
                </dl>
                <!-- params with the same name can be retrieved using request.getParameterValues(name) -->

                <hr>
                <button type="submit">Сохранить</button>
                <button type="reset">Сбросить</button>
            </form>

        </td>
    </tr>
    </tbody>
</table>

<jsp:include page="inc/footer.jsp"/>
</body>
</html>