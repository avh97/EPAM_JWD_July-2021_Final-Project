<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="css/forms.css">
    <title>Add conference</title>
</head>
<body>
<c:import url="header.jsp"/>
<div class="container-fluid">
    <h4>Add conference</h4>
    <div class="form-inline">
        <form action="controller" method="post">
            <input type="text" name="topic_id" value="" pattern=".*[^<>]" class="form-control"
                   placeholder=<fmt:message key="label.topic_id"/>>
            <input type="text" name="name" value="" required pattern=".*[^<>]" class="form-control"
                   placeholder=<fmt:message key="label.conference_name"/>>
            <input type="text" name="description" value="" required pattern=".*[^<>]" class="form-control"
                   placeholder=<fmt:message key="label.description"/>>
            <input type="date" min="2021-01-12" name="date" value="" required class="form-control"
                   placeholder=<fmt:message key="label.date"/>>
            <input type="hidden" name="command" value="add_conference">
            <button type="submit" class="btn btn-primary">
                <fmt:message key="label.add"/>
            </button>
        </form>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
