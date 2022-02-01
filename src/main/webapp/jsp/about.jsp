<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="css/forms.css">
    <title><fmt:message key="label.conference_name"/></title>
</head>
<body>
<c:import url="header.jsp"/>
<div class="container-fluid">
    <h4>Final project for EPAM Java Web Development online course</h4>
    <h5>Topic №20</h5>
    <h6>Conference organization system</h6>
    <p>The administrator creates topics, conferences. A participant registers and submits an application to one or more
        conferences.
        The administrator checks the application for compliance with the conference topic and confirms/rejects the
        application.
        The participant can withdraw the application, ask the Administrator a question.</p>
    <h6>Система организации конференций</h6>
    <p>Администратор создает темы, конференции. Участник регистрируется и подает заявку в одну или несколько
        конференций.
        Администратор проверяет заявку на соответствие тематике конференции и подтверждает\отклоняет заявку.
        Участник может снять заявку, задать вопрос Администратору.</p>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
