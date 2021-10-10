<%@ page import="com.basejava.webapp.model.Resume" %>
<%@ page import="com.basejava.webapp.web.HtmlSnippets" %>
<%@ page import="com.basejava.webapp.web.ContactRenderer" %>
<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <title>Список всех резюме</title>
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
        </td>

        <!-- content -->
        <td class="td_main_section_content">

            <table class="table_output">
                <thead class="thead_output">
                <tr>
                    <th class="th_td_output" style="width: 50px;">Действия</th>
                    <th class="th_td_output">Имя</th>
                    <th class="th_td_output">E-mail</th>
                </tr>
                </thead>
                <tbody class="tbody_output">
                <c:forEach items="${resumes}" var="resume">
                    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume"/>
                    <tr>
                        <td class="th_td_output" style="width: 50px;">
                            <%=HtmlSnippets.getAckDeleteButton(resume.getUuid())%>&nbsp;<%=HtmlSnippets.getEditButton(resume.getUuid())%>
                        </td>
                        <td class="th_td_output"><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                        <td class="th_td_output"><%=ContactRenderer.getEmailHref(resume.getContact(ContactType.EMAIL))%></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </td>
    </tr>
    </tbody>
</table>

<jsp:include page="inc/footer.jsp"/>
</body>
</html>