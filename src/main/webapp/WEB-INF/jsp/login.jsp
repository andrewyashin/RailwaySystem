<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
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
    <fmt:setBundle basename="login" var="login"/>
    <fmt:setBundle basename="navbar" var="navbar"/>
    <fmt:setBundle basename="message" var="message"/>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">Railway System</a>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="/EN">EN</a></li>
            <li><a href="/UKR">UKR</a></li>
        </ul>
    </div>
</nav>
<div class="row">
    <div class="col-md-5"></div>
    <div class="col-md-2" style="text-align: center;">
        <form style="padding-top: 100%; text-align: center" method="post" action="/route"
              class="center-block">
            <input type="text" placeholder="<fmt:message key="login.placeholderEmail" bundle="${login}"/>" name="email"
                   id="email"
                   required
                   class="form-control"
                   style="width: 100%; margin-bottom: 10px"
            />
            <input type="password" placeholder="<fmt:message key="login.placeholderPassword" bundle="${login}"/>"
                   name="password" id="pwd"
                   required
                   class="form-control"
                   style="width: 100%; margin-bottom: 10px"
            />

            <button class="btn btn-primary" type="submit" value="login" name="command"><fmt:message key="login.signIn"
                                                                                                    bundle="${login}"/></button>
            </br>
            <c:if test="${requestScope.errorMessage != null}">
                <h4><fmt:message key="message.invalidEmailOrPassword" bundle="${message}"/></h4>
            </c:if>
        </form>
        <p class="message"><fmt:message key="login.notregistered" bundle="${login}"/>
            <a href="<c:url value="/resources/register.jsp"/>"><fmt:message
                    key="login.signUp" bundle="${login}"/></a></p>
    </div>
    <div class="col-md-5"></div>
</div>

</body>
</html>
