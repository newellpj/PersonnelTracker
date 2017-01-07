<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>


<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap-custom.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/myStyles.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/login.css">

<title>Logout Page</title>
<style>
	@media screen and (max-width:1200px){
	  .logoImg2{display:none;}
	}


</style>
</head>
<body background="./presentationResources/images/bgimg.jpg" >

	

	<div id="login-box" class="login-box responsive">
	<div><h3>Thank you for visiting</h3></div>
<div class="logoImg2">
		<img alt="company logo" width='426' height='400' src='./presentationResources/images/tornCardboard.png' style='float:right; margin-right:10%; margin-top:3%;' />
	</div>

	
<br/>

<p>
		<div >
		<button name="returnToLogin" type="button" class="retLoginButton responsive" onclick="location.href='login'"
						value="Return to Login Page" >	 <span class="glyphicon glyphicon-log-in"></span> &nbsp;  Return to Login Page </button>
			</div>					

</p>
<br/>	
	</div>

</body>
</html>