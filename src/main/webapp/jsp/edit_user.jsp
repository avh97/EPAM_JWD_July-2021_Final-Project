<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="css/forms.css">
    <title>Edit user</title>
</head>
<body>
<c:import url="header.jsp"/>
<div class="container-fluid">
    <h4><fmt:message key="label.user_profile"/></h4>
    <form action="controller" method="post">
        <div class="form-row">
            <div class="form-group col-md-3">
                <input type="text" name="email" value="${selected.email}" pattern=".*[^<>]"
                       class="form-control"
                       placeholder=<fmt:message key="label.email"/>>
            </div>
            <div class="form-group col-md-3">
                <input type="text" name="name" value="${selected.name}" pattern=".*[^<>]"
                       class="form-control"
                       placeholder=<fmt:message key="label.new_name"/>>
            </div>
            <div class="form-group col-md-3">
                <input type="text" name="patronymic" value="${selected.patronymic}" pattern=".*[^<>]"
                       class="form-control"
                       placeholder=<fmt:message key="label.new_patronymic"/>>
            </div>
            <div class="form-group col-md-3">
                <input type="text" name="surname" value="${selected.surname}" pattern=".*[^<>]"
                       class="form-control"
                       placeholder=<fmt:message key="label.new_surname"/>>
            </div>
        </div>
        <div class="form-row">
            <input type="hidden" name="command" value="edit_user">
        </div>
        <button type="submit" class="btn btn-primary">
            <fmt:message key="label.change"/>
        </button>
    </form>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
