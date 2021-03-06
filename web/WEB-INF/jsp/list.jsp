<%@ page import="com.basejava.webapp.model.Resume" %>
<%@ page import="com.basejava.webapp.web.HtmlSnippets" %>
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
            <p><a href="resume?action=create"><img src="img/action/plus.svg" class="img_action">Новое резюме</a></p>
        </td>

        <!-- content -->
        <td class="td_main_section_content">

            <table class="table_list_resume">
                <thead class="thead_list_resume">
                <tr>
                    <th class="th_td_list_resume" style="width: 50px;">Действия</th>
                    <th class="th_td_list_resume">Имя</th>
                    <th class="th_td_list_resume">E-mail</th>
                </tr>
                </thead>
                <tbody class="tbody_list_resume">
                <c:forEach items="${resumes}" var="resume">
                    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume"/>
                    <tr>
                        <td class="th_td_list_resume" style="width: 50px;">
                            <%=HtmlSnippets.getAckDeleteButton(resume.getUuid())%>&nbsp;<%=HtmlSnippets.getEditButton(resume.getUuid())%>
                        </td>
                        <td class="th_td_list_resume"><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                        <td class="th_td_list_resume">
                            <c:set var="email" value="<%=resume.getContact(ContactType.EMAIL)%>" />
                            <c:if test="${not empty email}">
                                <a href="mailto:${email}">${email}</a>
                            </c:if>
                        </td>
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