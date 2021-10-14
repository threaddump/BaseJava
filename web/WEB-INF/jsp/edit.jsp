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

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script src="js/script.js"></script>

    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request" />
    <jsp:useBean id="storageAction" type="java.lang.String" scope="request" />

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
            <p><a href="resume?action=create"><img src="img/action/plus.svg" class="img_action">Новое резюме</a></p>
            <p><a href="resume?action=list"><img src="img/action/back.svg" class="img_action">Назад к списку</a></p>
            <%if (storageAction.equals("update")) {%>
                <p><a href="resume?uuid=${resume.uuid}&action=view"><img src="img/action/eye.svg" class="img_action">Назад к резюме</a></p>
                <p><a href="resume?uuid=${resume.uuid}&action=ack_delete"><img src="img/action/cross.svg" class="img_action">Удалить</a></p>
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
                        <input type="text" name="fullName" id="fullName" value="${resume.fullName}" placeholder="Имя Фамилия" required />
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
                                <input type="text" name="${contactType.name()}"
                                       id="${contactType.name()}" value="${resume.getContact(contactType)}" />
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
                        <c:set var="section" value="<%=resume.getSection(sectionType)%>" />
                        <jsp:useBean id="section" type="com.basejava.webapp.model.Section" />

                        <c:choose>
                            <c:when test="${(sectionType == SectionType.OBJECTIVE) || (sectionType == SectionType.PERSONAL)}">
                                <div class="form_div">
                                    <label for="${sectionType.name()}" class="form_label">${sectionType.title}:</label>
                                    <span class="form_span">
                                        <textarea name="${sectionType.name()}" id="${sectionType.name()}"
                                                  class="textarea_single_line"><%=((TextSection) section).getContent()%></textarea>
                                    </span>
                                </div>
                            </c:when>

                            <c:when test="${(sectionType == SectionType.ACHIEVEMENT) || (sectionType == SectionType.QUALIFICATIONS)}">
                                <div class="form_div">
                                    <label for="${sectionType.name()}" class="form_label">${sectionType.title}:</label>
                                    <span class="form_span">
                                        <textarea name="${sectionType.name()}" id="${sectionType.name()}"
                                                ><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                                    </span>
                                </div>
                            </c:when>

                            <c:when test="${(sectionType == SectionType.EXPERIENCE) || (sectionType == SectionType.EDUCATION)}">
                                <c:set var="orgSection" value="<%=(OrgSection) section%>" />
                                <jsp:useBean id="orgSection" type="com.basejava.webapp.model.OrgSection" />

                                <!-- header with an org addition button -->
                                <c:set var="orgPrefix" value="${sectionType.name()}_org" />
                                <div class="form_div ${orgPrefix}_header" style="">
                                    <label for="${orgPrefix}_add" class="form_label">${sectionType.title}:</label>
                                    <span class="form_span">
                                        <button id="${orgPrefix}_add" class="button_add org_add">
                                            <img src="img/action/plus.svg" class="img_action">Добавить организацию</button>
                                    </span>
                                    <input type="hidden" name="${orgPrefix}_counter"
                                           class="editor_counter" value="${orgSection.orgs.size()}" />
                                    <input type="hidden" name="${orgPrefix}_prefix"
                                           class="editor_prefix" value="${orgPrefix}" />
                                </div>

                                <!-- organizations wrapped in fieldset tag -->
                                <div class="${orgPrefix}_container" style="">
                                    <c:forEach var="organization" items="${orgSection.orgs}" varStatus="orgCounter">
                                        <jsp:useBean id="organization" type="com.basejava.webapp.model.Organization" />
                                        <c:set var="orgIndexedPrefix" value="${orgPrefix}_${orgCounter.index}" />

                                        <fieldset id="${orgIndexedPrefix}_fieldset">
                                            <!-- org removal button -->
                                            <div class="form_div">
                                                <button id="${orgIndexedPrefix}_remove" class="button_remove">
                                                    <img src="img/action/cross.svg" class="img_action">Удалить организацию</button>
                                            </div>

                                            <!-- display inputs for link (title and url) -->
                                            <c:set var="link" value="${organization.link}" />
                                            <jsp:useBean id="link" type="com.basejava.webapp.model.Link" />

                                            <div class="form_div">
                                                <label for="${orgIndexedPrefix}_title" class="form_label">Название:</label>
                                                <span class="form_span">
                                                    <input type="text" name="${orgIndexedPrefix}_title"
                                                           id="${orgIndexedPrefix}_title" value="${link.title}" />
                                                </span>
                                            </div>

                                            <div class="form_div">
                                                <label for="${orgIndexedPrefix}_url" class="form_label">URL сайта:</label>
                                                <span class="form_span">
                                                    <input type="text" name="${orgIndexedPrefix}_url"
                                                           id="${orgIndexedPrefix}_url" value="${link.url}" />
                                                </span>
                                            </div>

                                            <!-- header with a pos addition button -->
                                            <c:set var="posPrefix" value="${orgIndexedPrefix}_pos" />
                                            <div class="form_div ${posPrefix}_header" style="">
                                                <label class="form_label"></label>
                                                <span class="form_span">
                                                    <button id="${posPrefix}_add" class="button_add pos_add">
                                                        <img src="img/action/plus.svg" class="img_action">Добавить позицию</button>
                                                </span>
                                                <input type="hidden" name="${posPrefix}_counter"
                                                       class="editor_counter" value="${organization.positions.size()}" />
                                                <input type="hidden" name="${posPrefix}_prefix"
                                                       class="editor_prefix" value="${posPrefix}" />
                                            </div>

                                            <!-- positions wrapped in a fieldset tag -->
                                            <div class="${posPrefix}_container" style="">
                                                <c:forEach var="position" items="${organization.positions}" varStatus="posCounter">
                                                    <jsp:useBean id="position" type="com.basejava.webapp.model.Position" />
                                                    <c:set var="posIndexedPrefix" value="${posPrefix}_${posCounter.index}" />

                                                    <fieldset id="${posIndexedPrefix}_fieldset">

                                                        <!-- pos removal button -->
                                                        <div class="form_div">
                                                            <button id="${posIndexedPrefix}_remove" class="button_remove">
                                                                <img src="img/action/cross.svg" class="img_action">Удалить позицию</button>
                                                        </div>

                                                        <!-- display inputs for position (TimeSpan.begin, TimeSpan.end, title, description) -->
                                                        <div class="form_div">
                                                            <label for="${posIndexedPrefix}_begin" class="form_label">Дата начала:</label>
                                                            <span class="form_span">
                                                                <input type="text" name="${posIndexedPrefix}_begin"
                                                                       id="${posIndexedPrefix}_begin"
                                                                       value="<%=DateUtils.format(position.getTimeSpan().getBegin())%>"
                                                                       placeholder="Дата в формате MM/yyyy (например, 10/2001) или &quot;Сейчас&quot;" />
                                                            </span>
                                                        </div>

                                                        <div class="form_div">
                                                            <label for="${posIndexedPrefix}_end" class="form_label">Дата окончания:</label>
                                                            <span class="form_span">
                                                                <input type="text" name="${posIndexedPrefix}_end"
                                                                       id="${posIndexedPrefix}_end"
                                                                       value="<%=DateUtils.format(position.getTimeSpan().getEnd())%>"
                                                                       placeholder="Дата в формате MM/yyyy (например, 10/2001) или &quot;Сейчас&quot;" />
                                                            </span>
                                                        </div>

                                                        <div class="form_div">
                                                            <label for="${posIndexedPrefix}_title" class="form_label">Должность:</label>
                                                            <span class="form_span">
                                                                <textarea name="${posIndexedPrefix}_title" id="${posIndexedPrefix}_title"
                                                                          class="textarea_single_line">${position.title}</textarea>
                                                            </span>
                                                        </div>

                                                        <div class="form_div">
                                                            <label for="${posIndexedPrefix}_desc" class="form_label">Описание:</label>
                                                            <span class="form_span">
                                                                <textarea name="${posIndexedPrefix}_desc" id="${posIndexedPrefix}_desc"
                                                                        >${position.description}</textarea>
                                                            </span>
                                                        </div>

                                                    </fieldset>
                                                </c:forEach>
                                            </div>

                                        </fieldset>
                                    </c:forEach>
                                </div>

                                <!-- new line after org -->
                                <br />
                            </c:when>

                        </c:choose>
                    </c:forEach>
                </p>

                <hr />
                <button type="submit" class="button_submit">Сохранить</button>
            </form>

        </td>
    </tr>
    </tbody>
</table>

<jsp:include page="inc/footer.jsp"/>
</body>
</html>