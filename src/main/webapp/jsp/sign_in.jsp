<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<!doctype html>
<html>
<head>
    <link rel="stylesheet" href="css/forms.css">
    <title><fmt:message key="label.sign-in"/></title>
</head>
<c:import url="header.jsp"/>
<div class="container-fluid">
    <h4>
        <fmt:message key="label.sign-in"/>
    </h4>
    <c:if test="${message ne null}">
        <div>${message}</div>
    </c:if>
    <form action="controller" method="POST">
        <div class="form-group col-md-3">
            <input class="form-control" name="email" required pattern=".*[^<>]"
                   placeholder=<fmt:message key="label.email"/>>
        </div>
        <div class="form-group col-md-3">
            <input type="password" class="form-control" name="password" required
                   pattern=".*[^<>]" placeholder=<fmt:message key="label.password"/>>
        </div>
        <input type="hidden" name="command" value="log_in">
        <button type="submit" class="btn btn-primary btn-block">
            <fmt:message key="label.submit"/>
        </button>
    </form>
    <form action="controller" method="POST">
        <input type="hidden" name="command" value="to_sign_up">
        <button type="submit" class="btn btn-outline-primary btn-block">
            <fmt:message key="label.signup"/>
        </button>
    </form>
</div>
<div class="container-fluid">
    <c:import url="footer.jsp"/>
</div>
</body>
</html>
