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
    <title>Participant</title>
</head>
<body>
<c:import url="header.jsp"/>
<%--Jumbotron--%>
<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <h2 class="display-4"><fmt:message key="label.welcome"/>,
            ${sessionScope.user.name } ${sessionScope.user.patronymic}</h2>
        <br>
        <p class="lead">${message}</p>
        <hr class="my-4">
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
                                value="to_edit_user">
                            <fmt:message key="label.user_profile"/>
                        </button>
                    </li>
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
                                value="find_user_applications">
                            <fmt:message key="label.find_user_applications"/>
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
                <th><fmt:message key="label.table_email"/></th>
                <th><fmt:message key="label.role"/></th>
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
                        <c:choose>
                            <c:when test="${user.role eq 'ADMIN'}">
                                <button class="btn btn-secondary">
                                    <fmt:message key="label.user_admin"/>
                                </button>
                            </c:when>
                            <c:when test="${user.role eq 'PARTICIPANT'}">
                                <button class="btn btn-info">
                                    <fmt:message key="label.user_participant"/>
                                </button>
                            </c:when>
                            <c:when test="${user.role eq 'OBSERVER'}">
                                <button class="btn btn-info">
                                    <fmt:message key="label.user_observer"/>
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-danger">
                                    <c:out value="Invalid role"/>
                                </button>
                            </c:otherwise>
                        </c:choose>
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
            </tr>
            </thead>
            <tbody>
            <c:forEach var="topic" items="${topic_list}">
                <tr>
                    <td><c:out value="${topic.id}"/></td>
                    <td><c:out value="${topic.name}"/></td>
                    <td><c:out value="${topic.description}"/></td>
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
                        <c:choose>
                            <c:when test="${conference.status eq 'PENDING'}">
                                <button class="btn btn-info">
                                    <fmt:message key="label.conference_pending"/>
                                </button>
                            </c:when>
                            <c:when test="${conference.status eq 'CANCELED'}">
                                <button class="btn btn-dark">
                                    <fmt:message key="label.conference_canceled"/>
                                </button>
                            </c:when>
                            <c:when test="${conference.status eq 'ENDED'}">
                                <button class="btn btn-outline-dark">
                                    <fmt:message key="label.conference_ended"/>
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-danger">
                                    <c:out value="Invalid status"/>
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${conference.status eq 'PENDING'}">
                                <form action="controller" method="post">
                                    <button type="submit" class="btn btn-danger" name="command"
                                            value="to_add_application">
                                        <fmt:message key="label.add_application"/>
                                    </button>
                                    <input type="hidden" name="id" value="${conference.id}">
                                </form>
                            </c:when>
                        </c:choose>
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
                    <td><c:out value="${application.description}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${application.status eq 'CLAIMED'}">
                                <button class="btn btn-info">
                                    <fmt:message key="label.application_claimed"/>
                                </button>
                            </c:when>
                            <c:when test="${application.status eq 'CONFIRMED'}">
                                <button class="btn btn-success">
                                    <fmt:message key="label.application_confirmed"/>
                                </button>
                            </c:when>
                            <c:when test="${application.status eq 'REJECTED'}">
                                <button class="btn btn-dark">
                                    <fmt:message key="label.application_rejected"/>
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-danger">
                                    <c:out value="INVALID"/>
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <form action="controller" method="post">
                            <button type="submit" class="btn btn-danger" name="command"
                                    value="remove_application">
                                <fmt:message key="label.remove_application"/>
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
