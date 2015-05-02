<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/common.css'/>">
</head>
<body onload='document.loginForm.username.focus();'>

<div id="login-box">

    <c:url value="/login" var="loginUrl"/>

    <form name='loginForm' action="${loginUrl}" method='POST' class="form-horizontal">
        <fieldset>
            <div class="form-group">
                <label for="username" class="col-lg-2 control-label">User:</label>

                <div class="col-lg-10">
                    <input type='text' id='username' name='username' value='' class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="username" class="col-lg-2 control-label">Password:</label>
                <div class="col-lg-10">
                    <input type='password' id='password' name='password' value='' class="form-control">
                </div>
            </div>
            <input name="submit" type="submit" value="Submit" class="btn btn-primary"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </fieldset>
    </form>
</div>

</body>
</html>
