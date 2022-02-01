<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<!doctype html>
<html>
<head>
    <title>Conference</title>
    <!-- Required meta tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
</head>
<body>
<div class="btn-toolbar justify-content-between" role="toolbar" aria-label="toolbar with button groups">
    <div class="btn-group" role="group" aria-label="First group">
        <form action="controller" method="GET">
            <input type="submit" value=
            <fmt:message key="label.home"/>
                    class="btn"> <input type="hidden" name="command"
                                        value="to_main">
        </form>
        <form action="controller" method="GET">
            <input type="submit" value=
            <fmt:message key="label.about"/>
                    class="btn"> <input type="hidden" name="command"
                                        value="to_about_page">
        </form>
        <c:if test="${user.getRole().toString() eq null}">
            <form action="controller" method="GET">
                <input type="submit" value=
                    <fmt:message key="label.account"/>
                        class="btn"> <input type="hidden" name="command" value="to_sign_in">
            </form>
        </c:if>
        <c:if test="${user.getRole().toString() ne null}">
            <form action="controller" method="GET">
                <input type="submit" value=
                    <fmt:message key="label.account"/>
                        class="btn"> <input
                    type="hidden" name="command" value="to_personal_page">
            </form>
        </c:if>
        <div class="dropdown">
            <form action="controller" method="POST">
                <button class="btn dropdown-toggle" type="button"
                        id="locale-dropdown-menu-button" data-bs-toggle="dropdown"
                        aria-haspopup="true"
                        aria-expanded="false">
                    <fmt:message key="label.language"/>
                </button>
                <div class="dropdown-menu" aria-labelledby="locale-dropdown-menu-button">
                    <button class="dropdown-item" type="submit" name="language"
                            value="ru_RU">Русский
                    </button>
                    <button class="dropdown-item" type="submit" name="language"
                            value="en_US">English (US)
                    </button>
                    <input type="hidden" name="command" value="select_locale">
                </div>
            </form>
        </div>
    </div>
    <c:if test="${user.getRole().toString() eq null}">
        <div class="header_button">
            <form action="controller" method="GET">
                <input type="submit" value=
                    <fmt:message key="label.login"/>
                        class="btn"> <input type="hidden" name="command" value="to_sign_in">
            </form>
        </div>
    </c:if>
    <c:if test="${user.getRole().toString() ne null}">
        <div class="header_button">
            <form action="controller" method="GET">
                <input type="submit" value=
                    <fmt:message key="label.logout"/>
                        class="btn"> <input
                    type="hidden" name="command" value="log_out">
            </form>
        </div>
    </c:if>
</div>
</body>
</html>