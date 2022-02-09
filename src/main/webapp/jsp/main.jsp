<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<!doctype html>
<html>
<head>
    <title><fmt:message key="label.conference_name"/></title>
</head>
<body>
<c:import url="header.jsp"/>
<div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-indicators">
        <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active"
                aria-current="true" aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1"
                aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2"
                aria-label="Slide 3"></button>
    </div>
    <div class="carousel-inner">
        <div class="carousel-item active">
            <img src="images/1.jpg" class="d-block w-100" alt="Technology">
        </div>
        <div class="carousel-item">
            <img src="images/2.jpg" class="d-block w-100" alt="Economics">
        </div>
        <div class="carousel-item">
            <img src="images/3.jpg" class="d-block w-100" alt="History">
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>
<div>
    <table style="text-align: center" class="table table-striped">
        <tr>
            <td colspan="2"><strong><fmt:message key="label.conference_topics"/></strong></td>
        </tr>
        <tr>
            <th style="text-align: center;"><fmt:message key="label.topic_name"/></th>
            <th style="text-align: center;"><fmt:message key="label.description"/></th>
        </tr>
        <c:forEach var="element" items="${main_list}">
            <tr>
                <td><c:out value="${element.name}"/></td>
                <td><c:out value="${element.description}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
<div class="container-fluid">
    <c:import url="footer.jsp"/>
</div>
</body>
</html>
