<%@ page import="com.basejava.webapp.model.Resume" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Удаление резюме ${resume.fullName}</title>
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
            <p><a href="resume?uuid=${resume.uuid}&action=view"><img src="img/action/eye.svg" class="img_action">Назад к резюме</a></p>
        </td>

        <!-- content -->
        <td class="td_main_section_content">

            <h2>Подтверждение операции</h2>
            <p>Вы действительно хотите удалить резюме <b>${resume.fullName}</b>? Эта операция необратима.</p>
            <hr>
            <a href="resume?action=list">Нет, вернуться к списку резюме</a><br />
            <a href="resume?uuid=${resume.uuid}&action=view">Нет, вернуться к просмотру этого резюме</a><br />
            <br />
            <a href="resume?uuid=${resume.uuid}&action=delete" style="color: #ff0000;">Да, удалить резюме безвозвратно</a>

        </td>
    </tr>
    </tbody>
</table>

<jsp:include page="inc/footer.jsp"/>
</body>
</html>