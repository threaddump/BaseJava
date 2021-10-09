<%@ page import="com.basejava.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <title>База данных резюме</title>
</head>
<body>
<jsp:include page="inc/header.jsp" />

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

<jsp:include page="inc/footer.jsp" />
</body>
</html>