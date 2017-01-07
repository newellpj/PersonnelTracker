 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 

<%@ page language="java"  session="true" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="reviewsBookPageApp">
<head>

<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap-custom.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/myStyles.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/reviewBook.css">

<script type="text/javascript" src="./presentationResources/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery-ui.js"></script>
<script type="text/javascript" src="./presentationResources/js/jsCustomScript.js"></script>
<script type="text/javascript" src="./presentationResources/js/angular.js"></script>
<script type="text/javascript" src="./presentationResources/js/review.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery.jscroll.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery.jscroll.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Reviews</title>
<head>
<script>
$(document).ready(function() {
	
	//alert(parent.document.getElementById("activeSel3").style);
		
	switchActiveOnParentForReviews();
		
		
	  var html = $(".bookRevList").html();
		  
	  if(html != 'undefined' && html != null){
	
		  $('.resultsSection').jscroll({		  
				loadingHtml: "<center><div class='ajax-loader-2'> </div></center>"     
		  });
		  
		  document.getElementById("resultsSectionReview").style.visibility = "visible";
	   }
	 
	  
	 
	 var acc = document.getElementsByClassName("accordionReviews");
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


<button class="glyphicon glyphicon-search accordionReviews responsive"> <span style="font-family:Arial;">Show or Hide Review Books </span></button>

	<div id="add-reviews-box" class="add-reviews-box responsive" ng-controller="reviewPageController"  ng-init="titleText = ''; authorText= ''; reviewText = ''; starRating = '0';">

		<h3>Add a Book Review</h3>
	    <span style="font-weight: normal;font-style:italic; text-shadow: 0.5px 0.5px #a8a8a8; margin-top:8px; float:left;">Please rate this book :</span>	
			<fieldset class="rating">
			
			    <input ng-model="starRating" type="radio" id="star5" name="rating" value="5" /><label for="star5" title="The Best">5 stars</label>
			    <input ng-model="starRating" type="radio" id="star4" name="rating" value="4" /><label for="star4" title="Very good">4 stars</label>
			    <input ng-model="starRating" type="radio" id="star3" name="rating" value="3" /><label for="star3" title="Average">3 stars</label>
			    <input ng-model="starRating" type="radio" id="star2" name="rating" value="2" /><label for="star2" title="Didn't like it">2 stars</label>
			    <input ng-model="starRating" type="radio" id="star1" name="rating" value="1" /><label for="star1" title="Terrible">1 star</label>
			</fieldset>

		<c:if test="${not empty error}">
			<div class="error alert alert-error">${error}</div>
		</c:if>
		<c:if test="${not empty message}">
			<div class="message">${message}</div>
		</c:if>

<br/>
			<form:form id="reviewsForm"  method="post" commandName="bookReviewsModel">
	
	<div ng-controller="bookReviewSubmitter" >
	
		<table style="width:100%;">

				<tr>
					<td>Title:</td>
					<td><input type='text' style="width:523px;" id="bookTitleReview" class="responsive" name='bookTitleReview' disabled="true" 
					value='<%=session.getAttribute("bookTitleFound")%>' /><span class="glyphicon glyphicon-book iconspanTitleAuth"></span></td>
					<td><span style="visibility:hidden;" class="dummyTD">quick  brown</span></td>
				</tr>
				<tr>
					<td>Author:</td>
					<td><input type='text' style="width:523px;" id="bookAuthorReview" name='bookAuthorReview' class="responsive"
					disabled="true" value='<%=session.getAttribute("bookAuthorFound")%>' /><span class="glyphicon glyphicon-pencil iconspanTitleAuth"></span></td>
					<td><span style="visibility:hidden;" class="dummyTD">quick  brown</span></td>
				</tr>
				<tr>
					<td>Review:</td>
					<td><textarea ng-model="reviewText" class="responsive" style="resize: none; width:523px; height:212px;" id="reviewText" name="reviewText" rows="10" cols="70" maxlength="1000" ></textarea>
					<span class="glyphicon glyphicon-comment iconspanReview responsive"></span></td>
				</tr>
				<tr>
					<td colspan="1"></td><td colspan='2'>
					
					<button id="addReview" class="responsive" name="addReview" type="button"
						value="Add Review.." ng-click="addBookReview();" 
						ng-disabled=" reviewText == '' || starRating == '0' "><span class="glyphicon glyphicon-star glyphicon-star-empty"></span> Add Review..  </button> </td>
				</tr>
			</table>
	</div>			
		
			
			
		</form:form>
	</div>
			
	<div id="resultsSectionReview" class="resultsSection responsive">
		
				
		<c:if test="${not empty formattedHTML}">
						<div class='reviewedBookDetails'>${formattedHTML}</div>
					</c:if>
		
				<c:if test="${not empty reviewLists}">
					<span style='font-family:courier; font-style:italic; font-size:medium;' >Book Reviews for <%=session.getAttribute("bookTitleFound")%></span>
									
					<ul id="bookRevList" class="bookRevList">
						<c:forEach var="listValue" items="${reviewLists}">
							<li><div class="reviewSegment">${listValue}</div></li>
						</c:forEach>
					</ul>
					
				 <div class="next"><a href="retrieveNextReviewsSegment">next</a> </div>

				</c:if>
		
		</div>

	<div class="facebookFooter" >
	<div id="fb-root" ></div>
<div class="fb-like" data-href="http://www.w3schools.com/" data-layout="standard" data-action="like" data-size="small" data-show-faces="true" data-share="true"></div>
</div>

</body>
</html>