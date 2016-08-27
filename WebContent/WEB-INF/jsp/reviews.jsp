<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" type="text/css" href="./presentationResources/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./presentationResources/css/myStyles.css">

<script type="text/javascript" src="./presentationResources/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery-ui.js"></script>
<script type="text/javascript" src="./presentationResources/js/jsCustomScript.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery.jscroll.js"></script>
<script type="text/javascript" src="./presentationResources/js/jquery.jscroll.min.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Reviews</title>
<head>
<script language="javascript" type="text/javascript">
  function resizeIframe(obj) {
    obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
  }

  $(document).ready(function(){
	  
	  document.getElementById("activeSel").style.backgroundColor="#f6f6f6";
	  document.getElementById("activeSel").style.backgroundImage="url(./presentationResources/images/arrow-up.gif)";
      document.getElementById("activeSel").style.backgroundRepeat="no-repeat";
	  document.getElementById("activeSel").style.backgroundPosition="center bottom";	
	  
	    $("topnav li a").click(function(e) {
	        e.preventDefault();
	        $("#theI_Frame").attr("src", $(this).attr("href"));
	    })

	});

	$("#theI_Frame").jscroll();
	
	
	
	$('.scroll').jscroll();



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
	
	<div class="col-12" >
		
		<ul class="topnav responsive">
		
		  <li id="activeSel"  onclick="switchActive($(this));"><a href="reviewsSearchBook" target="theI_Frame">&nbsp; Search Book </a></li>
		  <li id="activeSel0"  onclick="switchActive($(this));"><a href="reviewsSearchDocs" target="theI_Frame"> Search Docs &nbsp;</a></li>
		  <li id="activeSel1"  onclick="switchActive($(this));"><a href="searchNASA" target="theI_Frame">Search NASA &nbsp;</a></li>
		  <li id="activeSel2" onclick="switchActive($(this));"><a href="reviewsAddBook" target="theI_Frame"> &nbsp; Add a book &nbsp;&nbsp;</a></li>
		  <li id="activeSel3" ><a href="/reviewsReviewBookNoneAdded" target="theI_Frame">Review book &nbsp;</a></li>
		  <li id="logout"><a href="logout"> &nbsp;&nbsp;Logout <span style="color:transparent; text-shadow:none;">.......... </span></a></li>
		 </ul>			 
		 	</div>
		 
	
	
		
		
<br/>
<div class="frameContainer responsive">
  <iframe name="theI_Frame" id="theI_Frame" src="reviewsSearchBook" frameborder="0" scrolling="yes" onload="resizeIframe(this)">
  </iframe>
</div>



</body>
</html> 