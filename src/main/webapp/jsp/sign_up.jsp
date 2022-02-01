<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<!doctype html>
<html>
<head>
    <link rel="stylesheet" href="css/forms.css">
    <title><fmt:message key="label.signup"/></title>
</head>
<body>
<c:import url="header.jsp"/>
<div class="container-fluid">
    <h4>
        <fmt:message key="label.registration"/>
    </h4>
    <c:if test="${message ne null}">
        <div>${message}</div>
    </c:if>
    <form action="controller" method="POST">

        <div class="form-group col-md-4">
            <input type="email" class="form-control" name="email" required
                   placeholder=<fmt:message key="label.email"/>>
        </div>
        <div class="form-group col-md-4">
            <input type="password" class="form-control" name="password" required maxlength="20"
                   placeholder=<fmt:message key="label.new_password"/>>
        </div>
        <div class="form-group col-md-4">
            <input type="password" class="form-control"
                   name="confirmed_password" required
                   placeholder=<fmt:message key="label.confirm_password"/>>
        </div>
        <div class="form-group col-md-4">
            <input type="text" class="form-control" name="name" pattern=".*[^<>]" required
                   placeholder=<fmt:message key="label.new_name"/>>
        </div>
        <div class="form-group col-md-4">
            <input type="text" class="form-control" name="patronymic" pattern=".*[^<>]"
                   placeholder=<fmt:message key="label.new_patronymic"/>>
        </div>
        <div class="form-group col-md-4">
            <input type="text" class="form-control" name="surname" pattern=".*[^<>]"
                   placeholder=<fmt:message key="label.new_surname"/>>
        </div>
        <input type="hidden" name="command" value="sign_up">
        <button type="submit" class="btn btn-primary">
            <fmt:message key="label.submit"/>
        </button>
    </form>
</div>
<c:import url="footer.jsp"/>
</body>
</html>