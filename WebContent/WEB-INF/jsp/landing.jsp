<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html  ng-app="loginSignupApp" >
<head>
<link rel="stylesheet" type="text/css" href="./presentationResources/css/myStyles.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap-custom.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/font-awesome.css">

<link rel="stylesheet" type="text/css" href="./presentationResources/css/landing.css">
<script type="text/javascript" src="./presentationResources/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery-ui.js"></script>
<script type="text/javascript" src="./presentationResources/js/angular.js"></script>
<script type="text/javascript" src="./presentationResources/js/landing.js"></script>


<title>Login Page</title>
<style>



</style>

<script>

	 if (top.location!= self.location) {

		var href = self.location.href;

		//alert("href is : "+href);

	    top.location = self.location.href;
	 }

	 $(document).ready(function(){
				 document.getElementById("openByDefault").click();
    });



	// window.addEventListener('beforeinstallprompt', function(e) {
	//	  console.log('beforeinstallprompt Event fired');
	//	  e.preventDefault();
	//	  return false;
	//	});



</script>

</head>
<body background="./presentationResources/images/bgimg.jpg">



	<div id="login-box" class="login-box responsive">


		<div  style= "display:none;" class="logoimg responsive"><img  width='560' height='430' src='./presentationResources/images/paperandglassFoldedCoffeeStain2.png'/></div>



		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty message}">
			<div class="message">${message}</div>
		</c:if>
			<br/><br/>


						<p>Not Registered? Click Sign up tab otherwise login below</p>


<ul class="tab">
  <li><a href="javascript:void(0)" class="tablinks defaultTab" onclick="openTab(event, 'loginTab')" id="openByDefault"  >Login</a></li>
  <li><a href="javascript:void(0)" class="tablinks" onclick="openTab(event, 'signupTab')">Signup</a></li>

</ul>




<div id="loginTab" class="tabcontent">
		<form
			action="<c:url value='/j_spring_security_check' />" method='POST'>

			<div id="loginFields" class="loginFields responsive">


				<div id="users" class="userFields responsive">

					<input class="usernameInput responsive" type='text' name='username'
					    placeholder="Username" style="width:60%;">
				 </div>
				<div id="pass"  class="passFields responsive">
					<input class="usernameInput responsive"
					placeholder="Password" type='password' name='password'
					           style="width:60%; margin-left:0.3em;"/>
				</div>


			</div>
			<div >
		<button name="submit" type="submit" class="loginButton responsive" value="Login" >
			<span class="glyphicon glyphicon-log-in"></span> &nbsp; Login </button>
			</div>
		</form>
</div>
<div id="signupTab" class="tabcontent">
    <signup-form></signup-form>
</div>



		<!--
			<button class="js-push-button" onclick="subscribe();">
		  		Enable Push Messages
			</button>
		 -->

	</div>

		<!-- 	<iframe style="float:right" src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3151.758257044112!2d144.98990895113457!3d-37.81913097965206!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x6ad642edb9cfc8a5%3A0xf3eebc93fb3f537c!2s25+Rotherwood+St%2C+Richmond+VIC+3121!5e0!3m2!1sen!2sau!4v1477837138064"
		            width="600" height="450" frameborder="0" style="border:0" allowfullscreen></iframe>   -->

</body>
</html>
