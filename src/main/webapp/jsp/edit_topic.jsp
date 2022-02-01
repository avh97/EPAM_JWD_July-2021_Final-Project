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
<body>
<c:import url="header.jsp"/>
<div class="container-fluid">
    <h4><fmt:message key="label.edit"/></h4>
    <form action="controller" method="POST">
        <div class="form-row">
            <div class="form-group col-md-5">
                <input type="text" name="name" value="${selected.name}" pattern=".*[^<>]"
                       class="form-control"
                       placeholder=<fmt:message key="label.name"/>>
            </div>
            <div class="form-group col-md-5">
                <input type="text" name="description" value="${selected.description}" pattern=".*[^<>]"
                       class="form-control"
                       placeholder=<fmt:message key="label.name"/>>
            </div>
        </div>
        <div class="form-row">
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
