<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<c:import url="header.jsp"/>
<div>
    <h1>ERROR</h1>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
