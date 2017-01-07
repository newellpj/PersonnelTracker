<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="searchDocsApp" >
<head>

<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap-custom.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/myStyles.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/docs.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/font-awesome.css">

<script type="text/javascript" src="./presentationResources/js/angular.js"></script>
<script type="text/javascript" src="./presentationResources/js/searchDocs.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery-ui.js"></script>
<script type="text/javascript" src="./presentationResources/js/jsCustomScript.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery.jscroll.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery.jscroll.min.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery.ellipsis.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Reviews</title>
<head>

<script>
	$(document).ready(function() {

		if(navigator.userAgent.toLowerCase().indexOf('firefox') > -1){
			user_pref("capability.policy.policynames", "localfilelinks");
			user_pref("capability.policy.localfilelinks.sites", "file:///C:/");
			user_pref("capability.policy.localfilelinks.checkloaduri.enabled",  "allAccess");
		}

		
	
		var html = $(".bookRevList").html();
		 
	//  if(html != 'undefined' && html != null){
	//		
		//  $('.search-box').jscroll({		  
		//	loadingHtml: "<center><div class='ajax-loader-2'> </div></center>"     
		//  });
	//	  
		 // $('.search').jscroll();
	//	  
	//   }

	
	
		searchDocsPageInit();
		var acc = document.getElementsByClassName("accordion");
		var i;

		for (i = 0; i < acc.length; i++) {
			acc[i].onclick = function(){
				this.classList.toggle("active");
				this.nextElementSibling.classList.toggle("hide");
			}
		}
		
		   
	});
	
	(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
     
</script>

<style>

</style>
</head>
<body background="./presentationResources/images/bgimg.jpg">

<br/>
<button class="glyphicon glyphicon-search accordion responsive"> <span style="font-family:Arial;">Show or Hide Search Documents </span></button>
	<div id="search-box" class="search-box responsive" ng-controller="searchDocsController" ng-init="titleText = ''; authorText = ''; keywordsText= 'PHP';" >



		<c:if test="${not empty error}">
			<div class="error alert alert-error">${error}</div>
		</c:if>
		<c:if test="${not empty message}">
			<div class="message">${message}</div>
		</c:if>

		
		<p><span style="align-center; font-style:italic;">Please type in the criteria to find documents to view</span></p>
		
			<form:form id="reviewsForm"  commandName="bookReviewsModel">
	
			<div class="titleFields responsive">
			
					Title:
					<input ng-model="titleText" id="titleText" ng-blur="lostFocus('titleSearchPossibles')"  type='text' name='titleText'><span class="glyphicon glyphicon-book iconspanSearchDocs"></span>		
						<span style="visibility:hidden;"> hidden text for placeholder hidde text .</span>
				<ul class="titleSearchPossibles" ng-mouseleave="mouseLeave('titleSearchPossibles')">
							<li ng-repeat="d in data track by $index" style="margin-left:2em;">
							 <span ng-mousedown="displayTitles(d)">{{d}}</span>
							</li>
					  </ul>
			</div>
						
					 
			<div class="authorFields">
					Author:
					<input ng-model="authorText"   id="authorText" type='text' name='authorText' /><span class="glyphicon glyphicon-pencil iconspanSearchDocs"></span>
					<span style="visibility:hidden;"> hidden text for placeholder hidden text .</span>
				<ul class="authorSearchPossibles"  ng-mouseleave="mouseLeave('authorSearchPossibles')">
							<li ng-repeat="d in data track by $index" style="width:100%;  margin-left:2em;">
							 <span ng-mousedown="displayAuthors(d)">{{d}}</span>
							</li>
					  </ul>
			</div>
						
					 
			
			
			<div class="tagFields">	
					Keywords (comma separated): </br></br>
					<div id="tags" ><input id="keywordsText" ng-model="keywordsText" type='text' value='' placeholder="Add a tag" name='keywordsText' /><span class="glyphicon glyphicon-tag iconspan2"></span>
							<span>Java</span>
							<span>Javascript</span>
							<span>JQuery</span>
							<span>Angular</span>
						</div>
			</div>
		
			  <div class="tagSearches responsive"> 
					<button id="searchBook" class="searchBook" name="searchBook" type="button" ng-click="performDocSearch();" 
							ng-disabled="titleText == '' && authorText == '' && keywordsText == '' " value="Search.." > 
					<span class="glyphicon glyphicon-eye-open" style="padding-right:5px;"  ></span>Search...
					</button>
					<button id="resetSearch" class="resetSearch"  name="resetSearch" type="button" onclick="resetDocSearch();"  value="Reset" >
							<span class="glyphicon glyphicon-erase" style="padding-right:5px;" ></span>Reset
					</button>	
			   </div>		
			<br/>		
		</form:form>
</div>	

<div id="resultsSectionDocs" class="resultsSectionDocs responsive">
		<form:form id="searchResults" class="searchResults">	
			<div id="search" class="search" >
				<ul id="bookRevList" class="bookRevList" >				
				</ul>
			</div>
		
		</form:form>
</div>		
		
	

<div class="facebookFooter" >
	<div id="fb-root" ></div>
<div class="fb-like" data-href="http://www.w3schools.com/" data-layout="standard" data-action="like" data-size="small" data-show-faces="true" data-share="true"></div>

</body>
</html>