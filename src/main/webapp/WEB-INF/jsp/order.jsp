<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="hello" uri="/WEB-INF/lib/hello.tld" %>
<html>
<head>
    <title>Railway System</title>
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
    <fmt:setBundle basename="order" var="order"/>
    <fmt:setBundle basename="navbar" var="navbar"/>
    <fmt:setBundle basename="message" var="message"/>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">Railway System</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="/main"><fmt:message key="navbar.main" bundle="${navbar}"/></a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a class="navbar-brand"><hello:user name="${username}"/></a></li>
            <li><a href="/EN">EN</a></li>
            <li><a href="/UKR">UKR</a></li>
            <li><a href="/logout"><fmt:message key="navbar.logout" bundle="${navbar}"/></a></li>
        </ul>
    </div>
</nav>

<div class="col-md-2"></div>
<div class="col-md-8">
    <c:if test="${not empty noTickets}">
        <h1><fmt:message key="message.noTickets" bundle="${message}"/></h1>
    </c:if>
    <c:if test="${not empty tickets}">
        <form action="/book" method="post" class="text-center">
            <table class="table">
                <tr>
                    <th><fmt:message key="order.trainNumber" bundle="${order}"/></th>
                    <th><fmt:message key="order.name" bundle="${order}"/></th>
                    <th><fmt:message key="order.surname" bundle="${order}"/></th>
                    <th><fmt:message key="order.departure" bundle="${order}"/></th>
                    <th><fmt:message key="order.arrival" bundle="${order}"/></th>
                    <th><fmt:message key="order.from" bundle="${order}"/></th>
                    <th><fmt:message key="order.to" bundle="${order}"/></th>
                    <th><fmt:message key="order.type" bundle="${order}"/></th>
                    <th><fmt:message key="order.price" bundle="${order}"/></th>
                    <th><fmt:message key="order.count" bundle="${order}"/></th>
                </tr>
                <tr>
                    <c:forEach items="${tickets}" var="ticket">
                <tr>
                    <td>${ticket.trainId}</td>
                    <td>${ticket.name}</td>
                    <td>${ticket.surname}</td>
                    <td>${ticket.fromDate}</td>
                    <td>${ticket.toDate}</td>
                    <td>${ticket.fromCity}</td>
                    <td>${ticket.toCity}</td>
                    <td>${ticket.typePlace} (${ticket.max})</td>
                    <td>${ticket.price}</td>
                    <td style="width: 80px">x<input type="number" name="${ticket.trainId}" max="${ticket.max}" min="0"
                                                    value="1"></td>
                </tr>
                </c:forEach>
                </tr>
            </table>
            <input type="hidden" name="tickets" value="${tickets}">
            <button type="submit" name="command" value="book" class="btn btn-primary btn-lg "><fmt:message
                    key="order.bookTickets" bundle="${order}"/></button>
        </form>
    </c:if>
</div>
<div class="col-md-2"></div>
</body>
</html>
