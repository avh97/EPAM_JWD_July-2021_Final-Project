<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<!doctype html>
<html>
<head>
    <link rel="stylesheet" href="css/styles.css">
    <title>Admin</title>
</head>
<body>
<c:import url="header.jsp"/>
<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <h1 class="display-4"><fmt:message key="label.welcome"/>, ${name } ${patronymic}</h1>
        <p class="lead">Это простой пример блока с компонентом в стиле jumbotron для привлечения дополнительного внимания к содержанию или информации.</p>
        <hr class="my-4">
        <p>${message}</p>
        <p class="lead">
        <div class="dropdown">
            <form action="controller" method="post">
                <button class="btn btn-primary dropdown-toggle" type="button"
                        id="dropdownMenuButton0" data-bs-toggle="dropdown" aria-haspopup="true"
                        aria-expanded="false">
                    <fmt:message key="label.menu"/>
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <button type="submit" class="dropdown-item" name="command"
                            value="find_all_users">
                        <fmt:message key="label.find_all_users"/>
                    </button>
                    <button type="submit" class="dropdown-item" name="command"
                            value="find_all_topics">
                        <fmt:message key="label.find_all_topics"/>
                    </button>
                    <button type="submit" class="dropdown-item" name="command"
                            value="find_all_conferences">
                        <fmt:message key="label.find_all_conferences"/>
                    </button>
                    <button type="submit" class="dropdown-item" name="command"
                            value="find_all_applications">
                        <fmt:message key="label.find_all_applications"/>
                    </button>
                    <button type="submit" class="dropdown-item" name="command"
                            value="to_add_topic">
                        <fmt:message key="label.add_topic"/>
                    </button>
                    <button type="submit" class="dropdown-item" name="command"
                            value="to_add_conference">
                        <fmt:message key="label.add_conference"/>
                    </button>
                </div>
            </form>
        </div>
        </p>
    </div>
</div>
<div>
    <c:if test="${user_list ne null}">
        <table class="table table-striped">
            <tr>
                <th><fmt:message key="label.id"/></th>
                <th><fmt:message key="label.user_name"/></th>
                <th><fmt:message key="label.user_patronymic"/></th>
                <th><fmt:message key="label.user_surname"/></th>
                <th><fmt:message key="label.email"/></th>
                <th><fmt:message key="label.role"/></th>
                <th colspan="2"><fmt:message key="label.action"/></th>
            </tr>
            <c:forEach var="user" items="${user_list}">
                <tr>
                    <td><c:out value="${user.id }"/></td>
                    <td><c:out value="${user.name }"/></td>
                    <td><c:out value="${user.patronymic }"/></td>
                    <td><c:out value="${user.surname }"/></td>
                    <td><c:out value="${user.email }"/></td>
                    <td>
                        <div class="dropdown">
                            <form action="controller" method="post">
                                <button class="btn btn-outline-danger dropdown-toggle" type="button"
                                        id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true"
                                        aria-expanded="false">
                                    <c:out value="${user.role}"/>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <c:if test="${user.role ne 'ADMIN'}">
                                        <button type="submit" class="dropdown-item" name="command"
                                                value="change_user_role_to_admin">
                                            <c:out value="ADMIN"/>
                                        </button>
                                    </c:if>
                                    <c:if test="${user.role ne 'PARTICIPANT'}">
                                        <button type="submit" class="dropdown-item" name="command"
                                                value="change_user_role_to_participant">
                                            <c:out value="PARTICIPANT"/>
                                        </button>
                                    </c:if>
                                    <c:if test="${user.role ne 'OBSERVER'}">
                                        <button type="submit" class="dropdown-item" name="command"
                                                value="change_user_role_to_observer">
                                            <c:out value="OBSERVER"/>
                                        </button>
                                    </c:if>
                                    <input type="hidden" name="id" value="${user.id}">
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
                    </td>
                    <td>
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
        </table>
    </c:if>
    <c:if test="${topic_list ne null}">
        <table class="table table-striped">
            <tr>
                <th><fmt:message key="label.id"/></th>
                <th><fmt:message key="label.topic_name"/></th>
                <th><fmt:message key="label.description"/></th>
                <th colspan="2"><fmt:message key="label.action"/></th>
            </tr>
            <c:forEach var="topic" items="${topic_list}">
                <tr>
                    <td><c:out value="${topic.id }"/></td>
                    <td><c:out value="${topic.name }"/></td>
                    <td><c:out value="${topic.description }"/></td>
                    <td>
                        <form action="controller" method="post">
                            <button type="submit" class="btn btn-danger" name="command"
                                    value="remove_topic">
                                <fmt:message key="label.remove"/>
                            </button>
                            <input type="hidden" name="id" value="${topic.id}">
                        </form>
                    </td>
                    <td>
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
        </table>
    </c:if>
    <c:if test="${conference_list ne null}">
        <table class="table table-striped">
            <tr>
                <th><fmt:message key="label.id"/></th>
                <th><fmt:message key="label.topic_name"/></th>
                <th><fmt:message key="label.conference_name"/></th>
                <th><fmt:message key="label.description"/></th>
                <th><fmt:message key="label.date"/></th>
                <th><fmt:message key="label.status"/></th>
                <th colspan="3"><fmt:message key="label.action"/></th>
            </tr>
            <c:forEach var="conference" items="${conference_list}">
                <tr>
                    <td><c:out value="${conference.id}"/></td>
                    <td><c:out value="${conference.topic.name}"/></td>
                    <td><c:out value="${conference.name}"/></td>
                    <td><c:out value="${conference.description}"/></td>
                    <td><c:out value="${conference.date}"/></td>
                    <td><c:out value="${conference.status}"/></td>
                    <td>
                        <div class="dropdown">
                            <form action="controller" method="post">
                                <button class="btn btn-danger dropdown-toggle" type="button"
                                        id="dropdownMenuButton2" data-bs-toggle="dropdown" aria-haspopup="true"
                                        aria-expanded="false">
                                    <fmt:message key="label.change_status"/>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <button type="submit" class="dropdown-item" name="command"
                                            value="change_conference_status_to_pending">
                                        <fmt:message key="label.change_status_to_pending"/>
                                    </button>
                                    <button type="submit" class="dropdown-item" name="command"
                                            value="change_conference_status_to_canceled">
                                        <fmt:message key="label.change_status_to_canceled"/>
                                    </button>

                                    <button type="submit" class="dropdown-item" name="command"
                                            value="change_conference_status_to_ended">
                                        <fmt:message key="label.change_status_to_ended"/>
                                    </button>
                                    <input type="hidden" name="id" value="${conference.id}">
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
                    </td>
                    <td>
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
        </table>
    </c:if>
    <c:if test="${application_list ne null}">
        <table class="table table-striped">
            <tr>
                <th><fmt:message key="label.id"/></th>
                <th><fmt:message key="label.conference_name"/></th>
                <th><fmt:message key="label.topic_name"/></th>
                <th><fmt:message key="label.conference_status"/></th>
                <th><fmt:message key="label.date"/></th>
                <th><fmt:message key="label.user_name"/></th>
                <th><fmt:message key="label.user_surname"/></th>
                <th><fmt:message key="label.role"/></th>
                <th><fmt:message key="label.application_description"/></th>
                <th><fmt:message key="label.application_status"/></th>
                <th colspan="3"><fmt:message key="label.action"/></th>
            </tr>
            <c:forEach var="application" items="${application_list}">
                <tr>
                    <td><c:out value="${application.id}"/></td>
                    <td><c:out value="${application.conference.name}"/></td>
                    <td><c:out value="${application.conference.topic.name}"/></td>
                    <td><c:out value="${application.conference.status}"/></td>
                    <td><c:out value="${application.conference.date}"/></td>
                    <td><c:out value="${application.user.name}"/></td>
                    <td><c:out value="${application.user.surname}"/></td>
                    <td><c:out value="${application.user.role}"/></td>
                    <td><c:out value="${application.description}"/></td>
                    <td><c:out value="${application.status}"/></td>
                    <td>
                        <div class="dropdown">
                            <form action="controller" method="post">
                                <button class="btn btn-danger dropdown-toggle" type="button"
                                        id="dropdownMenuButton3" data-bs-toggle="dropdown" aria-haspopup="true"
                                        aria-expanded="false">
                                    <fmt:message key="label.change_status"/>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <button type="submit" class="dropdown-item" name="command"
                                            value="change_application_status_to_claimed">
                                        <fmt:message key="label.change_status_to_claimed"/>
                                    </button>
                                    <button type="submit" class="dropdown-item" name="command"
                                            value="change_application_status_to_confirmed">
                                        <fmt:message key="label.change_status_to_confirmed"/>
                                    </button>
                                    <button type="submit" class="dropdown-item" name="command"
                                            value="change_application_status_to_rejected">
                                        <fmt:message key="label.change_status_to_rejected"/>
                                    </button>
                                    <input type="hidden" name="id" value="${application.id}">
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
        </table>
    </c:if>
</div>
<c:import url="footer.jsp"/>
</body>
</html>