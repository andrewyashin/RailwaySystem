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
    <fmt:setBundle basename="date" var="date"/>
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
    <form action="/date" method="post" class="form-horizontal">
        <div class="form-group">
            <div class="col-md-6">
                <select name="from" class="form-control pull-right" id="from">
                    <c:forEach items="${cityFrom}" var="fromItem">
                        <option value="${fromItem.id}" ${from == fromItem.id ? 'selected' : ''}>${fromItem.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-6">
                <select name="to" class="form-control" id="to">
                    <c:forEach items="${cityTo}" var="toItem">
                        <option value="${toItem.id}" ${to == toItem.id ? 'selected' : ''}>${toItem.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-6">
                <div class="pull-right" style="text-align: right">
                    <label for="date"><fmt:message key="date.depatureDateFrom" bundle="${date}"/></label>
                    <input id="date" type="date" name="date" value="${dateNow}" class="form-control" required/>
                </div>
            </div>


            <div class="col-md-6">
                <label for="time"><fmt:message key="date.departureTimeFrom" bundle="${date}"/></label>
                <select name="time" class="form-control" id="time">
                    <option name="0" value="0" ${time == 0 ? 'selected' : ''}>00:00</option>
                    <option name="1" value="1" ${time == 1 ? 'selected' : ''}>01:00</option>
                    <option name="2" value="2" ${time == 2 ? 'selected' : ''}>02:00</option>
                    <option name="3" value="3" ${time == 3 ? 'selected' : ''}>03:00</option>
                    <option name="4" value="4" ${time == 4 ? 'selected' : ''}>04:00</option>
                    <option name="5" value="5" ${time == 5 ? 'selected' : ''}>05:00</option>
                    <option name="6" value="6" ${time == 6 ? 'selected' : ''}>06:00</option>
                    <option name="7" value="7" ${time == 7 ? 'selected' : ''}>07:00</option>
                    <option name="8" value="8" ${time == 8 ? 'selected' : ''}>08:00</option>
                    <option name="9" value="9" ${time == 9 ? 'selected' : ''}>09:00</option>
                    <option name="10" value="10" ${time == 10 ? 'selected' : ''}>10:00</option>
                    <option name="11" value="11" ${time == 11 ? 'selected' : ''}>11:00</option>
                    <option name="12" value="12" ${time == 12 ? 'selected' : ''}>12:00</option>
                    <option name="13" value="13" ${time == 13 ? 'selected' : ''}>13:00</option>
                    <option name="14" value="14" ${time == 14 ? 'selected' : ''}>14:00</option>
                    <option name="15" value="15" ${time == 15 ? 'selected' : ''}>15:00</option>
                    <option name="16" value="16" ${time == 16 ? 'selected' : ''}>16:00</option>
                    <option name="17" value="17" ${time == 17 ? 'selected' : ''}>17:00</option>
                    <option name="18" value="18" ${time == 18 ? 'selected' : ''}>18:00</option>
                    <option name="19" value="19" ${time == 19 ? 'selected' : ''}>19:00</option>
                    <option name="20" value="20" ${time == 20 ? 'selected' : ''}>20:00</option>
                    <option name="21" value="21" ${time == 21 ? 'selected' : ''}>21:00</option>
                    <option name="22" value="22" ${time == 22 ? 'selected' : ''}>22:00</option>
                    <option name="23" value="23" ${time == 23 ? 'selected' : ''}>23:00</option>
                </select>
            </div>
        </div>
        <div class="form-group text-center">
            <button type="submit" name="command" value="selectDateTime" class="btn btn-primary btn-lg"><fmt:message key="date.searchTrains" bundle="${date}"/></button>
        </div>

        <div class="form-group text-center">
            <c:if test="${not empty noTrain}">
                <h1><fmt:message key="message.noTrains" bundle="${message}"/></h1>
            </c:if>
            <c:if test="${not empty trains}">

                <table class="table">
                    <tr>
                        <th><fmt:message key="date.number" bundle="${date}"/></th>
                        <th><fmt:message key="date.from" bundle="${date}"/></th>
                        <th><fmt:message key="date.departure" bundle="${date}"/></th>
                        <th><fmt:message key="date.arrival" bundle="${date}"/></th>
                        <th><fmt:message key="date.seatsAvailable" bundle="${date}"/></th>
                    </tr>
                    <c:forEach items="${trains}" var="train">
                        <tr>
                            <td>${train.trainId}</td>
                            <td>${train.fromCity} / ${train.toCity}</td>
                            <td>${train.fromDate}</td>
                            <td>${train.toDate}</td>
                            <td>
                                <select name="train${train.trainId}" class="form-control" style="width: 180px;">
                                    <option value="none"></option>
                                    <c:if test="${train.compartmentFree gt 0}">
                                        <option value="C">C (${train.compartmentFree}) = ${train.compartmentPrice}
                                            <fmt:message key="date.currency" bundle="${date}"/>
                                        </option>
                                    </c:if>

                                    <c:if test="${train.berthFree gt 0}">
                                        <option value="B">B (${train.berthFree}) = ${train.berthPrice} <fmt:message key="date.currency" bundle="${date}"/></option>
                                    </c:if>

                                    <c:if test="${train.deluxeFree gt 0}">
                                        <option value="L">L (${train.deluxeFree}) = ${train.deluxePrice} <fmt:message key="date.currency" bundle="${date}"/></option>
                                    </c:if>
                                </select>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <button type="submit" name="command" value="make" class="btn btn-primary btn-lg"><fmt:message key="date.preOrderTickets" bundle="${date}"/></button>
            </c:if>
        </div>
    </form>
</div>
<div class="col-md-2"></div>
</body>
</div>
</html>
