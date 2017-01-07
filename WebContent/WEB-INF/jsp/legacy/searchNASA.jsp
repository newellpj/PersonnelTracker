<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap-custom.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/myStyles.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/searchFeeds.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/jquery.bxslider.css">

<script type="text/javascript" src="./presentationResources/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery-ui.js"></script>
<script type="text/javascript" src="./presentationResources/js/jsCustomScript.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery.jscroll.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery.jscroll.min.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery.bxslider.min.js"></script>
<script type="text/javascript" src="./presentationResources/js/highlighter.js"></script>






<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search all</title>
<head>

<script>
	$(document).ready(function() {

	
	   var html = $(".bookRevList").html();
		 
	
		populateSearchAllOptions();

		$('.bxslider').bxSlider({
			 captions: true
		});
		
		$('#searchAllSelect').change(function(){	
			var width = getSearchAllSelectOptionsToPixelsMapping($('select option:selected').text());			
			document.getElementById('searchAllSelect').style.width = width;
		});
		   
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
<body background="./presentationResources/images/bgimg.jpg" >

	<div id="all-search-box" class="all-search-box responsive">


		<c:if test="${not empty error}">
			<div class="error alert alert-error">${error}</div>
		</c:if>
		<c:if test="${not empty message}">
			<div class="message">${message}</div>
		</c:if>

			<form:form id="reviewsForm"  commandName="bookReviewsModel">
	<span style="font-weight: normal; font-size:small; margin-left:10px;"> Type keywords or phrases to be searched. You can also select the specific category in which you wish to search.</span>
		<table style="width:100%;">
		
				<tr>
					<td>
						<select style="width:40px; height:32px; color:white; background-color: #A2C5D0; border-radius: 0px 0px 0px 0px;" id="searchAllSelect"></select>
						<input id="searchText"  type='text' name='searchText' class="responsive">
						<button id="searchAllBtn" class="responsive" type="button" onclick="getRSSFeed(false, true);" > 
						<span class="glyphicon glyphicon-search" style="padding-top: 1px !important;"></span></button>
				
						
					</td>
		
				</tr>
			</table>
		
			<br/>

			
		</form:form>
	
	<div id="feedsSliderSegment" style="visibility:hidden;">
	   <ul id="feedsSlider" class="bxslider2" >
		 
	  </ul>
	</div>	
	
  <ul id="suggestedSlider" class="bxslider">
	  <li><img src="./presentationResources/images/pic1.jpg" width="500" title="Test picture caption 1" /></li>
	  <li><img src="./presentationResources/images/pic2.jpg" width="500" title="Test picture caption 2" /></li>
	  <li><img src="./presentationResources/images/pic3.jpg" width="500" title="Test picture caption 3" /></li>
	  <li><img src="./presentationResources/images/pic4.jpg" width="500" title="Test picture caption 4" /></li>
	  <li><img src="./presentationResources/images/pic5.jpg" width="500" title="Test picture caption 5" /></li>
	  <li><img src="./presentationResources/images/pic6.jpg" width="500" title="Test picture caption 6" /></li>
	  <li><img src="./presentationResources/images/pic7.jpg" width="500" title="Test picture caption 7" /></li>
	  <li><img src="./presentationResources/images/pic8.jpg" width="500" title="Test picture caption 8" /></li>
  </ul>

</div>	
<div id="resultsSection" class="resultsSection">
		<form:form id="searchResults" class="searchResults">
		
				<div id="search" class="search" style="display:none; width:1000px !important;">
					<ul id="bookRevList" class="bookRevList" >				
					</ul>
			</div>
		
		</form:form>
</div>		
		
	

<div class="facebookFooter" >
	<div id="fb-root" ></div>
<div class="fb-like" data-href="http://www.w3schools.com/" data-layout="standard" data-action="like" data-size="small" data-show-faces="true" data-share="true"></div>
</div>
</body>
</html>