<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="css/forms.css">
    <title><fmt:message key="label.edit"/></title>
</head>
</title>
</head>
<body>
<c:import url="header.jsp"/>
<div class="container-fluid bg">
    <h4><fmt:message key="label.edit"/></h4>
    <form action="controller" method="POST">
        <div class="form-row">
            <div class="form-group col-md-5">
                <input type="text" name="topic_id" value="${selected.topic.id}" pattern=".*[^<>]"
                       class="form-control"
                       placeholder=<fmt:message key="label.topic_id"/>>
            </div>
            <div class="form-group col-md-5">
                <input type="text" name="name" value="${selected.name}" pattern=".*[^<>]"
                       class="form-control"
                       placeholder=<fmt:message key="label.name"/>>
            </div>
            <div class="form-group col-md-5">
                <input type="text" name="description" value="${selected.description}" pattern=".*[^<>]"
                       class="form-control"
                       placeholder=<fmt:message key="label.description"/>>
            </div>
            <div class="form-group col-md-3">
                <input type="date" name="date" value="${selected.date}" pattern=".*[^<>]"
                       class="form-control"
                       placeholder=<fmt:message key="label.date"/>>
            </div>
        </div>
        <div class="form-row">
            <input type="hidden" name="command" value="edit_conference">
        </div>
        <button type="submit" class="btn btn-primary">
            <fmt:message key="label.change"/>
        </button>
    </form>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
