<%@ page import="com.basejava.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <title>База данных резюме</title>
</head>
<body>
<!-- header -->
<table class="table_header_outer">
    <tbody>
    <tr>
        <td class="td_header_outer">
            <table class="table_header_inner">
                <tbody>
                <tr>
                    <td class="td_header_inner_title">
                        <table>
                            <tbody>
                            <tr>
                                <td style="font-size: 30pt; vertical-align: center;">Резюме</td>
                                <td style="width: 2px;"></td>
                                <td style="font-size: 15pt; vertical-align: bottom;">:: база данных ::</td>
                            </tr>
                            </tbody>
                        </table>
                    </td>

                    <td class="td_header_inner_logo">
                        <a href="https://www.oracle.com/java/" target="_blank"
                           onmouseover="document.getElementById('bubble_0').style.display='block'"
                           onmouseout="document.getElementById('bubble_0').style.display='none'">
                            <img src="img/logo/javase.svg" class="img_logo" alt="Java SE 8">
                        </a>
                        <div class="float_bubble" id="bubble_0" style="display: none;">Платформа - Java Standard Edition</div>
                        <div class="float_bubble" id="bubble_1" style="display: none;">Система контроля версий - Git</div>
                        <div class="float_bubble" id="bubble_2" style="display: none;">Среда разработки - IntelliJ IDEA</div>
                        <div class="float_bubble" id="bubble_3" style="display: none;">База данных - PostgreSQL</div>
                        <div class="float_bubble" id="bubble_4" style="display: none;">Контейнер сервлетов - Apache Tomcat</div>
                    </td>
                    <td class="td_header_inner_logo">
                        <a href="https://git-scm.com/" target="_blank"
                           onmouseover="document.getElementById('bubble_1').style.display='block'"
                           onmouseout="document.getElementById('bubble_1').style.display='none'">
                            <img src="img/logo/git.svg" class="img_logo" alt="Git">
                        </a>
                    </td>
                    <td class="td_header_inner_logo">
                        <a href="https://www.jetbrains.com/ru-ru/idea/" target="_blank"
                           onmouseover="document.getElementById('bubble_2').style.display='block'"
                           onmouseout="document.getElementById('bubble_2').style.display='none'">
                            <img src="img/logo/intellijidea.svg" class="img_logo" alt="IntelliJ IDEA">
                        </a>
                    </td>
                    <td class="td_header_inner_logo">
                        <a href="https://www.postgresql.org/" target="_blank"
                           onmouseover="document.getElementById('bubble_3').style.display='block'"
                           onmouseout="document.getElementById('bubble_3').style.display='none'">
                            <img src="img/logo/postgresql.svg" class="img_logo" alt="PostgreSQL">
                        </a>
                    </td>
                    <td class="td_header_inner_logo">
                        <a href="https://tomcat.apache.org/" target="_blank"
                           onmouseover="document.getElementById('bubble_4').style.display='block'"
                           onmouseout="document.getElementById('bubble_4').style.display='none'">
                            <img src="img/logo/apachetomcat.svg" class="img_logo" alt="Apache Tomcat">
                        </a>
                    </td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>
    </tbody>
</table>

<!-- main section -->
<table class="table_main_section">
    <tbody>
    <tr>
        <!-- content -->
        <td class="td_main_section_content">

            <table class="table_output">
                <thead class="thead_output">
                <tr>
                    <th class="th_td_output">UUID</th>
                    <th class="th_td_output">Полное имя</th>
                </tr>
                </thead>
                <tbody class="tbody_output">
                <%
                    List<Resume> resumes = (List<Resume>) request.getAttribute("resumes");
                    for (Resume resume : resumes) {
                %>
                <tr>
                    <td class="th_td_output"><a href="resume?uuid=<%=resume.getUuid()%>"><%=resume.getUuid()%></a></td>
                    <td class="th_td_output"><%=resume.getFullName()%></td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>

        </td>
    </tr>
    </tbody>
</table>

<!-- footer -->
<table class="table_footer">
    <tbody>
    <tr>
        <td class="td_footer">
            Учебный проект по курсу <a href="https://javaops.ru/view/basejava" target="_blank">BaseJava</a> (Java SE + Web);
            <a href="https://github.com/threaddump/BaseJava" target="_blank">исходный код</a> проекта на GitHub
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>