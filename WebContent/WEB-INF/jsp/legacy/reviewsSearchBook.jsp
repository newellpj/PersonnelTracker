<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="searchBookPageApp">
<head>

<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap-custom.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/myStyles.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/search.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/font-awesome.css">

<script type="text/javascript" src="./presentationResources/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery-ui.js"></script>
<script type="text/javascript" src="./presentationResources/js/jsCustomScript.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery.jscroll.min.js"></script>
<script type="text/javascript" src="./presentationResources/js/angular.js"></script>
<script type="text/javascript" src="./presentationResources/js/search.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Reviews</title>
<head>

<script>
	$(document).ready(function() {

	
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

	
	
		var acc = document.getElementsByClassName("accordion");
		var i;

		for (i = 0; i < acc.length; i++) {
			acc[i].onclick = function(){
				this.classList.toggle("active");
				$('.search-box').toggle("hide");
				//this.nextElementSibling.nextElementSibling.classList.toggle("hide");
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

<button class="glyphicon glyphicon-search accordion responsive"> <span style="font-family:Arial;">Show or Hide Search Books </span></button>

<div ng-controller="searchPageController" ng-init="titleText=''; authorText=''; publisherText=''" >
	<div class="logoImg">
		<img width='300' height='150' src='./presentationResources/images/eyeball.png' style='position:absolute; margin-left: 56%; margin-top:3%' />
	</div>

	<div id="se" class="search-box responsive" >

		<c:if test="${not empty error}">
			<div class="error alert alert-error">${error}</div>
		</c:if>
		<c:if test="${not empty message}">
			<div class="message">${message}</div>
		</c:if>
		
		<p><span style="align-center; font-style:italic;">Please type in the criteria to find a book to review</span></p>


			<form:form id="searchForm" name="searchForm"  commandName="bookReviewsModel">
	
	
				<div class="titleAndGenre responsive" >
					Title:
					<input ng-model="titleText" id="titleText"  placeholder="title search..."  type='text' name='titleText' style="width:40%;"><span class="glyphicon glyphicon-book iconspan2"></span>
					
					
					<input id="genre" type="checkbox"  ng-model="genreCheck" name="genre" value="genre" ng-click="genreHide = !genreHide" />Genre 
					<select ng-model="genreSelect.selectedOption" class="responsive" ng-hide="genreHide" style="width:30%; margin-left:1.7em; " id="genreSelect" 
							ng-options="option.name for option in genreSelect.availableOptions track by option.value">
					
					</select>
					   
						 <ul class="titleSearchPossibles" ng-mouseleave="mouseLeave('titleSearchPossibles')">
							<li ng-repeat="d in data | filter: titleText track by $index" style="margin-left:2em;">
							 <span ng-mousedown="displayTitles(d)">{{d.titleText}}</span>
							</li>
						   </ul>
						  
			
				</div>	
            		
				<div class="authorCategory responsive">
					Author:
					<input ng-model="authorText" id="authorText"  placeholder="author search..." style="width:40%;" type='text' name='authorText' /><span class="glyphicon glyphicon-pencil iconspan2"></span>
					
					<input id="category" type="checkbox" ng-model="catCheck"  name="category" value="category" ng-click="categoryHide = !categoryHide" />Category
					<select  ng-model="catSelect.selectedOption" name="catText" ng-hide="categoryHide" class="responsive" style="width:30%; margin-left:0.4em;" id="categorySelect" 
					    ng-options="option.name for option in catSelect.availableOptions track by option.value" >
					</select>
					
					 <ul class="authorSearchPossibles" ng-mouseleave="mouseLeave('authorSearchPossibles')">
							<li ng-repeat="d in data | unique: authorText" style="width:100%;   padding-left:-2em !important; margin-left:2em;">
							 <span ng-mousedown="displayAuthors(d)">{{d.authorText}}</span>
							</li>
					  </ul>
	
				</div>	
				
				<div class="publisherLang responsive">
					Publisher:  
					<input ng-model="publisherText" id="publisherText"  placeholder="publisher search..." style="width:40%;" type='text' name='publisherText' /><span class="glyphicon glyphicon-barcode iconspan2"></span>
					
					<input id="language" ng-model="langCheck" type="checkbox" name="language" value="language" ng-click="languageHide = !languageHide" />Language
					<select ng-model="langSelect.selectedOption" ng-hide="languageHide" name="langText" class="responsive" style="width:30%;" id="languageSelect" 
							ng-options="option.name for option in langSelect.availableOptions track by option.value" >	
					</select>
					 <ul class="publisherSearchPossibles"  ng-mouseleave="mouseLeave('publisherSearchPossibles')">
							<li ng-repeat="d in data | unique: publisherText " style="margin-left:2em;">
							 <span ng-mousedown="displayPublishers(d)">{{d.publisherText}}</span>
							</li>
					  </ul>
		          </div>	
			
			  <div class="tagSearches responsive" ng-controller="searchSubmitter"> 
					
							
								<button id="searchBook" class="searchBook responsive" name="searchBook" type="button" 
								 ng-disabled="titleText == '' && authorText == '' && publisherText == '' && langSelect.selectedOption.value == '' 
											&& catSelect.selectedOption.value == '' && genreSelect.selectedOption.value == '' "  ng-click="performBookSearch();" value="Search.." > 
								<span class="glyphicon glyphicon-eye-open" style="padding-right:0.5em;" ></span>Search...
								</button>
								<button id="resetSearch" class="resetSearch responsive" name="resetSearch" type="button" onclick="resetTheSearch();"  value="Reset" >
										<span class="glyphicon glyphicon-erase" style="padding-right:0.5em;" ></span> Reset...
								</button> 

					
					</div>		
			<br/>

			
		</form:form>
</div>	

<div id="resultsSection" class="resultsSection responsive" >
		<form:form id="searchResults" class="searchResults">
		
			<div id="search" class="search" style="display:none; width:1000px !important;">
				<ul id="bookRevList" class="bookRevList">				
				</ul>
			</div>
		
		</form:form>
</div>		
		
	

<div class="facebookFooter" >
	<div id="fb-root" ></div>
<div class="fb-like" data-href="http://www.w3schools.com/" data-layout="standard" data-action="like" data-size="small" data-show-faces="true" data-share="true"></div>
</div>

</div>
</body>
</html>