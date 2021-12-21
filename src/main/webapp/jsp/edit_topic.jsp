<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="css/forms.css">
    <title>Edit topic</title>
</head>
<body>
<c:import url="header.jsp"/>
<div class="container-fluid">
    <h4>Edit topic</h4>
    <form action="controller" method="post">
        <div class="form-row">
            <div class="form-group col-md-3">
                <input type="text" name="topic_name" value="${topic.name}" pattern=".*[^<>]"
                       class="form-control"
                       placeholder=<fmt:message key="label.name"/>>
            </div>
            <div class="form-group col-md-3">
                <input type="text" name="topic_description" value="${topic.description}" pattern=".*[^<>]"
                       class="form-control"
                       placeholder=<fmt:message key="label.name"/>>
            </div>
        </div>
        <div class="form-row">
            <input type="hidden" name="id" value="${topic.id}">
            <input type="hidden" name="command" value="edit_topic">
        </div>
        <button type="submit" class="btn btn-primary">
            <fmt:message key="label.change"/>
        </button>
    </form>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
