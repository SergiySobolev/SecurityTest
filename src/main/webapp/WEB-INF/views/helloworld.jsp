<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Acl Example</title>
    <link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>">
</head>
<body>
    <div>
        <sec:authorize access="isAnonymous()">
            <a href="loginForm">Login</a>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <a href="logout">Logout</a>
        </sec:authorize>
    </div>
    <sec:authorize access="isAuthenticated()">
        <div><a href="all/view">View All</a></div>
    </sec:authorize>
</body>
</html>