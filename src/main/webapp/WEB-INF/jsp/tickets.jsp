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
    <fmt:setBundle basename="ticketsPage" var="ticketsPage"/>
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
<div class="col-md-1"></div>
<div class="col-md-10">
    <form action="/cancel" method="post" class="text-center">
        <button type="submit" name="command" value="cancel" class="btn btn-primary btn-md"><fmt:message
                key="tickets.cancelButton" bundle="${ticketsPage}"/></button>
        <button type="submit" name="command" value="cancelAll" class="btn btn-primary btn-md"><fmt:message
                key="tickets.cancelAllButton" bundle="${ticketsPage}"/></button>
        <table class="table" style="margin-top: 15px">
            <tr>
                <th><fmt:message key="tickets.application" bundle="${ticketsPage}"/></th>
                <th><fmt:message key="tickets.trainNumber" bundle="${ticketsPage}"/></th>
                <th><fmt:message key="tickets.name" bundle="${ticketsPage}"/></th>
                <th><fmt:message key="tickets.surname" bundle="${ticketsPage}"/></th>
                <th><fmt:message key="tickets.departure" bundle="${ticketsPage}"/></th>
                <th><fmt:message key="tickets.arrival" bundle="${ticketsPage}"/></th>
                <th><fmt:message key="tickets.from" bundle="${ticketsPage}"/></th>
                <th><fmt:message key="tickets.to" bundle="${ticketsPage}"/></th>
                <th><fmt:message key="tickets.type" bundle="${ticketsPage}"/></th>
                <th><fmt:message key="tickets.price" bundle="${ticketsPage}"/></th>
                <th><fmt:message key="tickets.cancel" bundle="${ticketsPage}"/></th>
            </tr>
            <tr>
                <c:forEach items="${tickets}" var="ticket">
            <tr>
                <td>#${ticket.requestId}</td>
                <td>${ticket.trainId}</td>
                <td>${ticket.name}</td>
                <td>${ticket.surname}</td>
                <td>${ticket.fromDate}</td>
                <td>${ticket.toDate}</td>
                <td>${ticket.fromCity}</td>
                <td>${ticket.toCity}</td>
                <td>${ticket.typePlace}</td>
                <td>${ticket.price}</td>
                <td><input type="checkbox" name="${ticket.requestId}" value="cancel"></td>
            </tr>
            </c:forEach>
            </tr>
        </table>
    </form>

</div>
<div class="col-md-1"></div>
</body>
</html>
