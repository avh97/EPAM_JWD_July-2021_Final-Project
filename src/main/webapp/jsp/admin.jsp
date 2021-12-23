<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<!doctype html>
<html>
<head>
    <link rel="stylesheet" href="css/styles.css">
    <title>Administrator</title>
</head>
<body>
<c:import url="header.jsp"/>
<%--Jumbotron--%>
<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <h2 class="display-4"><fmt:message key="label.welcome"/>, ${name } ${patronymic}</h2>
        <p class="lead">Здесь должно было быть какое-то приветствие. Но я его не придумал :(</p>
        <hr class="my-4">
        <p>${message}</p>
        <%--Dropdown main menu--%>
        <div class="btn-group dropdown">
            <form action="controller" method="post">
                <button class="btn btn-primary dropdown-toggle" type="button"
                        id="dropdownMenuButton0" data-bs-toggle="dropdown" aria-expanded="false">
                    <fmt:message key="label.menu"/>
                </button>
                <ul class="dropdown-menu">
                    <li>
                        <button type="submit" class="dropdown-item" name="command"
                                value="find_all_users">
                            <fmt:message key="label.find_all_users"/>
                        </button>
                    </li>
                    <li>
                        <button type="submit" class="dropdown-item" name="command"
                                value="find_all_topics">
                            <fmt:message key="label.find_all_topics"/>
                        </button>
                    </li>
                    <li>
                        <button type="submit" class="dropdown-item" name="command"
                                value="find_all_conferences">
                            <fmt:message key="label.find_all_conferences"/>
                        </button>
                    </li>
                    <li>
                        <button type="submit" class="dropdown-item" name="command"
                                value="find_all_applications">
                            <fmt:message key="label.find_all_applications"/>
                        </button>
                    </li>
                    <li>
                        <button type="submit" class="dropdown-item" name="command"
                                value="to_add_topic">
                            <fmt:message key="label.add_topic"/>
                        </button>
                    </li>
                    <li>
                        <button type="submit" class="dropdown-item" name="command"
                                value="to_add_conference">
                            <fmt:message key="label.add_conference"/>
                        </button>
                    </li>
                </ul>
            </form>
        </div>
    </div>
</div>
<%--Tables--%>
<div class="container">
    <%--Table 1: Users--%>
    <c:if test="${user_list ne null}">
        <h2><fmt:message key="label.users"/></h2>
        <table class="table table-fluid" id="myTable">
            <thead>
            <tr>
                <th><fmt:message key="label.id"/></th>
                <th><fmt:message key="label.user_name"/></th>
                <th><fmt:message key="label.user_patronymic"/></th>
                <th><fmt:message key="label.user_surname"/></th>
                <th><fmt:message key="label.email"/></th>
                <th><fmt:message key="label.role"/></th>
                <th><fmt:message key="label.action"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${user_list}">
                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.name }"/></td>
                    <td><c:out value="${user.patronymic}"/></td>
                    <td><c:out value="${user.surname}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td>
                        <div class="dropdown">
                            <form action="controller" method="post">
                                <button class="btn btn-outline-danger dropdown-toggle" type="button"
                                        id="user-role-dropdown-menu-button" data-bs-toggle="dropdown"
                                        aria-haspopup="true"
                                        aria-expanded="false">
                                    <c:out value="${user.role}"/>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="user-role-dropdown-menu-button">
                                    <c:if test="${user.role ne 'ADMIN'}">
                                        <button type="submit" class="dropdown-item" name="role"
                                                value="ADMIN">
                                            <c:out value="ADMIN"/>
                                        </button>
                                    </c:if>
                                    <c:if test="${user.role ne 'PARTICIPANT'}">
                                        <button type="submit" class="dropdown-item" name="role"
                                                value="PARTICIPANT">
                                            <c:out value="PARTICIPANT"/>
                                        </button>
                                    </c:if>
                                    <c:if test="${user.role ne 'OBSERVER'}">
                                        <button type="submit" class="dropdown-item" name="role"
                                                value="OBSERVER">
                                            <c:out value="OBSERVER"/>
                                        </button>
                                    </c:if>
                                    <input type="hidden" name="id" value="${user.id}">
                                    <input type="hidden" name="command" value="change_user_role">
                                </div>
                            </form>
                        </div>
                    </td>
                    <td>
                        <form action="controller" method="post">
                            <button type="submit" class="btn btn-danger" name="command"
                                    value="remove_user">
                                <fmt:message key="label.remove"/>
                            </button>
                            <input type="hidden" name="id" value="${user.id}">
                        </form>
                        <form action="controller" method="post">
                            <button type="submit" class="btn btn-danger" name="command"
                                    value="to_edit_user">
                                <fmt:message key="label.edit"/>
                            </button>
                            <input type="hidden" name="id" value="${user.id}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <%--Table 2: Topics--%>
    <c:if test="${topic_list ne null}">
        <h2><fmt:message key="label.topics"/></h2>
        <table class="table table-fluid" id="myTable">
            <thead>
            <tr>
                <th><fmt:message key="label.id"/></th>
                <th><fmt:message key="label.topic_name"/></th>
                <th><fmt:message key="label.description"/></th>
                <th><fmt:message key="label.action"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="topic" items="${topic_list}">
                <tr>
                    <td><c:out value="${topic.id}"/></td>
                    <td><c:out value="${topic.name}"/></td>
                    <td><c:out value="${topic.description}"/></td>
                    <td>
                        <form action="controller" method="post">
                            <button type="submit" class="btn btn-danger" name="command"
                                    value="remove_topic">
                                <fmt:message key="label.remove"/>
                            </button>
                            <input type="hidden" name="id" value="${topic.id}">
                        </form>
                        <form action="controller" method="post">
                            <button type="submit" class="btn btn-danger" name="command"
                                    value="to_edit_topic">
                                <fmt:message key="label.edit"/>
                            </button>
                            <input type="hidden" name="id" value="${topic.id}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <%--Table 3: Conferences--%>
    <c:if test="${conference_list ne null}">
        <h2><fmt:message key="label.conferences"/></h2>
        <table class="table table-fluid" id="myTable">
            <thead>
            <tr>
                <th><fmt:message key="label.id"/></th>
                <th><fmt:message key="label.topic_name"/></th>
                <th><fmt:message key="label.conference_name"/></th>
                <th><fmt:message key="label.description"/></th>
                <th><fmt:message key="label.date"/></th>
                <th><fmt:message key="label.status"/></th>
                <th><fmt:message key="label.action"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="conference" items="${conference_list}">
                <tr>
                    <td><c:out value="${conference.id}"/></td>
                    <td><c:out value="${conference.topic.name}"/></td>
                    <td><c:out value="${conference.name}"/></td>
                    <td><c:out value="${conference.description}"/></td>
                    <td><c:out value="${conference.date}"/></td>
                    <td>
                        <div class="dropdown">
                            <form action="controller" method="post">
                                <button class="btn btn-outline-danger dropdown-toggle" type="button"
                                        id="conference-status-dropdown-menu-button" data-bs-toggle="dropdown"
                                        aria-haspopup="true"
                                        aria-expanded="false">
                                    <c:out value="${conference.status}"/>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="conference-status-dropdown-menu-button">
                                    <c:if test="${conference.status ne 'PENDING'}">
                                        <button type="submit" class="dropdown-item" name="status"
                                                value="PENDING">
                                            <c:out value="PENDING"/>
                                        </button>
                                    </c:if>
                                    <c:if test="${conference.status ne 'CANCELED'}">
                                        <button type="submit" class="dropdown-item" name="status"
                                                value="CANCELED">
                                            <c:out value="CANCELED"/>
                                        </button>
                                    </c:if>
                                    <c:if test="${conference.status ne 'ENDED'}">
                                        <button type="submit" class="dropdown-item" name="status"
                                                value="ENDED">
                                            <c:out value="ENDED"/>
                                        </button>
                                    </c:if>
                                    <input type="hidden" name="id" value="${conference.id}">
                                    <input type="hidden" name="command" value="change_conference_status">
                                </div>
                            </form>
                        </div>
                    </td>
                    <td>
                        <form action="controller" method="post">
                            <button type="submit" class="btn btn-danger" name="command"
                                    value="remove_conference">
                                <fmt:message key="label.remove"/>
                            </button>
                            <input type="hidden" name="id" value="${conference.id}">
                        </form>
                        <form action="controller" method="post">
                            <button type="submit" class="btn btn-danger" name="command"
                                    value="to_edit_conference">
                                <fmt:message key="label.edit"/>
                            </button>
                            <input type="hidden" name="id" value="${conference.id}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <%--Table 4: Applications--%>
    <c:if test="${application_list ne null}">
        <h2><fmt:message key="label.applications"/></h2>
        <table class="table table-fluid" id="myTable">
            <thead>
            <tr>
                <th><fmt:message key="label.id"/></th>
                <th><fmt:message key="label.conference_name"/></th>
                <th><fmt:message key="label.topic_name"/></th>
                <th><fmt:message key="label.conference_status"/></th>
                <th><fmt:message key="label.date"/></th>
                <th><fmt:message key="label.user_name"/></th>
                <th><fmt:message key="label.role"/></th>
                <th><fmt:message key="label.application_description"/></th>
                <th><fmt:message key="label.application_status"/></th>
                <th><fmt:message key="label.action"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="application" items="${application_list}">
                <tr>
                    <td><c:out value="${application.id}"/></td>
                    <td><c:out value="${application.conference.name}"/></td>
                    <td><c:out value="${application.conference.topic.name}"/></td>
                    <td><c:out value="${application.conference.status}"/></td>
                    <td><c:out value="${application.conference.date}"/></td>
                    <td><c:out value="${application.user.surname}"/><br>
                        <c:out value="${fn:substring(application.user.name, 0, 1)}. "/>
                        <c:out value="${fn:substring(application.user.patronymic, 0, 1)}."/></td>
                    <td><c:out value="${application.user.role}"/></td>
                    <td><c:out value="${application.description}"/></td>
                    <td>
                        <div class="dropdown">
                            <form action="controller" method="post">
                                <button class="btn btn-outline-danger dropdown-toggle" type="button"
                                        id="application-status-dropdown-menu-button" data-bs-toggle="dropdown"
                                        aria-haspopup="true"
                                        aria-expanded="false">
                                    <c:out value="${application.status}"/>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="application-status-dropdown-menu-button">
                                    <c:if test="${application.status ne 'CLAIMED'}">
                                        <button type="submit" class="dropdown-item" name="status"
                                                value="CLAIMED">
                                            <c:out value="CLAIMED"/>
                                        </button>
                                    </c:if>
                                    <c:if test="${application.status ne 'CONFIRMED'}">
                                        <button type="submit" class="dropdown-item" name="status"
                                                value="CONFIRMED">
                                            <c:out value="CONFIRMED"/>
                                        </button>
                                    </c:if>
                                    <c:if test="${application.status ne 'REJECTED'}">
                                        <button type="submit" class="dropdown-item" name="status"
                                                value="REJECTED">
                                            <c:out value="REJECTED"/>
                                        </button>
                                    </c:if>
                                    <input type="hidden" name="id" value="${application.id}">
                                    <input type="hidden" name="command" value="change_application_status">
                                </div>
                            </form>
                        </div>
                    </td>
                    <td>
                        <form action="controller" method="post">
                            <button type="submit" class="btn btn-danger" name="command"
                                    value="remove_application">
                                <fmt:message key="label.remove"/>
                            </button>
                            <input type="hidden" name="id" value="${application.id}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
<%--JS script--%>
<script>
    $(document).ready(function () {
        $('#myTable').DataTable({
            language: {
                url: '//cdn.datatables.net/plug-ins/1.10.15/i18n/English.json'
            }
        });
    });
</script>
<c:import url="footer.jsp"/>
</body>
</html>