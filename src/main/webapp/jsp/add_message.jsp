<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="css/forms.css">
    <title>Ask question</title>
</head>
<body>
<c:import url="header.jsp"/>
<div class="container-fluid">
    <h4>Ask question</h4>
    <div class="form-inline">
        <form action="controller" method="POST">
            <input type="text" name="question" value="" required pattern=".*[^<>]" class="form-control"
                   placeholder=<fmt:message key="label.description"/>>
            <input type="hidden" name="command" value="add_message">
            <button type="submit" class="btn btn-primary">
                <fmt:message key="label.submit"/>
            </button>
        </form>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
