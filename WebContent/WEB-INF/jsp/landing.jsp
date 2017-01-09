<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html  ng-app="loginSignupApp" >
<head>

<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap-custom.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/myStyles.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/landing.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/jquery.bxslider.css">

<link href="https://fonts.googleapis.com/css?family=Lobster|Open+Sans+Condensed:300" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Playfair+Display" rel="stylesheet">

<!-- <link rel="shortcut icon" href="./presentationResources/images/favicon.ico" type="image/x-icon"> -->

<script type="text/javascript" src="./presentationResources/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery-ui.js"></script>
<script type="text/javascript" src="./presentationResources/js/angular.js"></script>
<script type="text/javascript" src="./presentationResources/js/landing.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery.bxslider.min.js"></script>
<!--<script type="text/javascript" src="./presentationResources/js/landingWelcome.js"></script>
<script type="text/javascript" src="./presentationResources/js/landingApp.js"></script>
<script type="text/javascript" src="./presentationResources/js/landingAboutUs.js"></script> -->

<title>Personnel Tracker 360</title>
<style>



</style>

<script>

	 if (top.location!= self.location) {

		var href = self.location.href;

	    top.location = self.location.href;
	 }

	 $(document).ready(function(){

					 document.getElementById("openByDefault").click();
					 $('.bxslider').bxSlider({
							captions: true
					 });
    });


</script>

</head>
<body>


	<div id="main-box" class="main-box responsive">

<h1 class="pageHeaderLogo"> Personnel Tracker <span>360</span> </h1>
<ul class="tab">
  <li><a href="javascript:void(0)" class="tablinks defaultTab" onclick="openTab(event, 'welcomeTab')" id="openByDefault"  >Welcome</a></li>
  <li><a href="javascript:void(0)" class="tablinks" onclick="openTab(event, 'appTab')">The App</a></li>
  <li><a href="javascript:void(0)" class="tablinks" onclick="openTab(event, 'aboutTab')">About Us</a></li>
</ul>




<div id="welcomeTab" class="tabcontent">
    <welcome-page></welcome-page>
</div>
<div id="appTab" class="tabcontent">
    <app-form></app-form>
</div>
<div id="aboutTab" class="tabcontent">
    <about-form></about-form>
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
