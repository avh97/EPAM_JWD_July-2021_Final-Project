<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="css/forms.css">
    <title>Add topic</title>
</head>
<body>
<c:import url="header.jsp"/>
<div class="container-fluid">
    <h4>Add topic</h4>
    <div class="form-inline">
        <form action="controller" method="POST">
            <input type="text" name="topic_name" value="" pattern=".*[^<>]" class="form-control"
                   placeholder=<fmt:message key="label.name"/>>
            <input type="text" name="topic_description" value="" required pattern=".*[^<>]" class="form-control"
                   placeholder=<fmt:message key="label.description"/>>
            <input type="hidden" name="command" value="add_topic">
            <button type="submit" class="btn btn-primary">
                <fmt:message key="label.add"/>
            </button>
        </form>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
