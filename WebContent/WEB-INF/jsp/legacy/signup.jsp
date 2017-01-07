<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@page session="true"%>
<html>
<head>
<title>Sign-up to PJs Book Reviews</title>
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap-custom.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/myStyles.css">
</head>
<body onload='document.loginForm.username.focus();'>

	

	<div id="signup-box">

		<h3>PJs Book Reviews Sign Up Page</h3>

		<c:if test="${not empty message}">
			<div class="message">${message}</div>
		</c:if>

<br/><br/>
	
		<form:form  action="signupSubmitted" method="post" commandName="usersModel">
			<table width="100%">
				<tr>
					<td>Choose a Username:</td>
					<td><form:input path="username" name="username" class="usernameInput" style="width:257px !important;" type='text' /><span class="glyphicon glyphicon-user iconspan"></span></td>
				</tr>
				<tr>
					<td>Choose a Password:</td>
					<td><form:input path="password" name="password"  type="password" style="width:257px !important;" />  <span class="glyphicon glyphicon-lock iconspan"></span></td>
				</tr>
				<tr>
				<td><span style="padding:70px; visibility:hidden;"></span></td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit"
						value="Sign up!" /></td>
				</tr>
			</table>


		</form:form>
	</div>

</body>
</html>