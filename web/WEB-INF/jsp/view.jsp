<%@ page import="com.basejava.webapp.model.Resume" %>
<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
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
                <jsp:useBean id="contactEntry" type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
                <%=ContactRenderer.toHtml(contactEntry.getKey(), contactEntry.getValue())%><br/>
              </c:forEach>
            </p>

            <hr/>

            <c:forEach var="sectionEntry" items="${resume.sections}">
                <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.Section>"/>
                <h2><%=sectionEntry.getKey().getTitle()%></h2><br/>
                <!-- TODO -->
            </c:forEach>

        </td>
    </tr>
    </tbody>
</table>

<jsp:include page="inc/footer.jsp"/>
</body>
</html>