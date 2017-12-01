<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c"         uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"    uri="http://www.springframework.org/tags"%>

<c:url value="/css" var="cssPath" scope="request" />
<c:url value="/js" var="jsPath" scope="request" />

<!DOCTYPE html>
<html lang="ru">
<head>
	<meta charset="utf-8">
	<title><spring:message code="login.title"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<link id="bs-css" href="${cssPath}/bootstrap.min.css" rel="stylesheet">
	<link href="${cssPath}/charisma-app.css" rel="stylesheet">

	<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>

<body>
<div class="ch-container">
	<div class="row">
		<div class="col-md-12 center login-header">
			<h2><spring:message code="login.welcome"/></h2>
		</div>
	</div>

	<div class="row">
		<div class="well col-md-5 center login-box">

			<c:choose>
				<c:when test="${param.error == true}">
					<div class="alert alert-danger">
						<spring:message code="login.invalid"/>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-info">
						<spring:message code="login.text"/>
					</div>
				</c:otherwise>
			</c:choose>

			<form class="form-horizontal" action="j_spring_security_check" method="post">
				<fieldset>
					<div class="input-group input-group-lg">
						<span class="input-group-addon"><i class="glyphicon glyphicon-user red"></i></span>
						<input name="j_username" type="text" class="form-control" placeholder="<spring:message code="login.user" />" autofocus="">
					</div>
					<div class="clearfix"></div><br>

					<div class="input-group input-group-lg">
						<span class="input-group-addon"><i class="glyphicon glyphicon-lock red"></i></span>
						<input name="j_password" type="password" class="form-control" placeholder="<spring:message code="login.password"/>">
					</div>
					<div class="clearfix"></div>

					<p class="center col-md-5">
						<button type="submit" class="btn btn-primary"><spring:message code="login"/></button>
					</p>
				</fieldset>
			</form>
		</div>
	</div>
</div>

</body>
</html>
