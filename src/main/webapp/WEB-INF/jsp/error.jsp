<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script src="<c:url value="/resources/js/jquery-3.2.1.js"/> "></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/> "></script>
</head>
<body>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">Railway System</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="/RailwaySystem?web.command=tickets">Main</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="/RailwaySystem?web.command=EN">EN</a></li>
            <li><a href="/RailwaySystem?web.command=UKR">UKR</a></li>
            <li class="right"><a href="/RailwaySystem?web.command=logout">Log out</a></li>
        </ul>
    </div>
</nav>
<div class="col-md-2"></div>
<div class="col-md-8">
    <h1>Ooops</h1>
    <h2>Something goes wrong :(</h2>
    <p><c:out value="${messageError}"/></p>
</div>
<div class="col-md-2"></div>

</body>
</html>
