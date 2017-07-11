<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="hello" uri="/WEB-INF/lib/hello.tld" %>
<html>
<head>
    <title>Railway System</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <link rel="stylesheet"
          type="text/css"
          href="<c:url value="/resources/css/bootstrap.css"/>"/>
    <link rel="stylesheet"
          type="text/css"
          href="<c:url value="/resources/css/bootstrap-theme.css"/>"/>
    <link rel="stylesheet"
          type="text/css"
          href="<c:url value="/resources/css/style.css"/>"/>
    <script src="<c:url value="/resources/js/jquery-3.2.1.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <fmt:setBundle basename="navbar" var="navbar"/>
    <fmt:setBundle basename="usersPage" var="usersPage"/>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">Railway System</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="/users"><fmt:message key="navbar.users" bundle="${navbar}"/></a></li>
            <li class="active"><a href="/tickets"><fmt:message key="navbar.tickets"
                                                               bundle="${navbar}"/></a>
            </li>
        </ul>

        <ul class="nav navbar-nav navbar-right">
            <li><a class="navbar-brand"><hello:user name="${username}"/></a></li>
            <li><a href="/EN">EN</a></li>
            <li><a href="/UKR">UKR</a></li>
            <li class="right"><a href="/logout"><fmt:message key="navbar.logout"
                                                             bundle="${navbar}"/></a></li>
        </ul>
    </div>
</nav>
<div class="col-md-2"></div>
<div class="col-md-8">

    <form method="post" action="/admin">
        <table class="table">
            <tr>
                <th><fmt:message key="usersPage.email" bundle="${usersPage}"/></th>
                <th><fmt:message key="usersPage.name" bundle="${usersPage}"/></th>
                <th><fmt:message key="usersPage.surname" bundle="${usersPage}"/></th>
                <th><fmt:message key="usersPage.tel" bundle="${usersPage}"/></th>
                <th><fmt:message key="usersPage.admin" bundle="${usersPage}"/></th>
                <th><fmt:message key="usersPage.action" bundle="${usersPage}"/></th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.email}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.phone}</td>
                    <td>${user.admin}</td>
                    <td>
                        <select name="${user.id}" class="form-control">
                            <option value="none"></option>
                            <option value="delete"><fmt:message key="usersPage.delete" bundle="${usersPage}"/></option>
                            <option value="admin"><fmt:message key="usersPage.makeAdmin"
                                                               bundle="${usersPage}"/></option>
                            <option value="user"><fmt:message key="usersPage.makeUser" bundle="${usersPage}"/></option>
                        </select>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div style="text-align: right; padding: 15px">
            <button type="submit" name="command" value="actionUsers" class="btn btn-primary btn-md"><fmt:message
                    key="usersPage.done" bundle="${usersPage}"/></button>
        </div>
    </form>
</div>
<div class="col-md-2"></div>
</body>
</html>
