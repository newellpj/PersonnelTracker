
var thisGroupData = [];

var employeeSearchData = [];

function displayFacetCheckboxSelection(){
   console.log($("input[type='checkbox']:checked").val()+' :  BULLSHIT! : '+thisGroupData.length);
   //console.log($("input[type='checkbox']:checked").attr("id"));

    duplicatesRemovedList = [];


   $("input[type='checkbox']:checked").each(function(){
         console.log($(this).attr("id"));//this is the checked checkbox
         clearSearchList(); //don't clear - but need to check if search item already exists

         var facetLabelID = $(this).attr("id");



         for(var i = 0; i <  thisGroupData.length; i++){
            facetList = thisGroupData[i]['facetModelsMatchingGroupItems'];
            console.log("facet list : "+facetList.length);
            for(var j = 0; j < facetList.length; j++){
              if(facetLabelID == facetList[j]['facetLabel']  && facetList[j]['facetCount'] > 0){
                  console.log("employees matched against facet list : "+facetList[j]['employeesMatchedAgainstFacetCategory'].length+" :: "+
                facetList[j]['employeesMatchedAgainstFacetCategory'][0]['employeeSurname']);
                  duplicatesRemovedList = duplicatesRemovedList.concat(facetList[j]['employeesMatchedAgainstFacetCategory']);
                  console.log("duplicate list before : "+duplicatesRemovedList.length);
                  duplicatesRemovedList = testForDuplicates(duplicatesRemovedList);
                  console.log("duplicate list after : "+duplicatesRemovedList.length);
              }
            }

         }

    });



      // if(duplicatesRemovedAllList.length <= 0){
      //       $('.bookRevList').append("<span style='font-size:1.5em;'>No Facets selected Found </span>");
      //
      // }else{

            document.getElementById("search").style.display = "inline";
            //now format the search list to display to user
            for(var k = 0; k < duplicatesRemovedList.length; k++){

                 console.log("employee data model object :: "+duplicatesRemovedList[k]['employeeSurname']);
                 var formattedContent = "<div class='searchSegment'>"+formatSearchContent(duplicatesRemovedList[k], null)+"</div>";
                 $('.bookRevList').append(formattedContent);
                // attachScroll();
            }


}



function testForDuplicates(arr){

      var hash = (function() {
      var keys = {};
      return {
          contains: function(key) {
              return keys[key] === true;
          },
          add: function(key) {
              if (keys[key] !== true){
                  keys[key] = true;
              }
          }
      };
  })();

  var key = null;
  var result = [];
  for (var i = 0; i < arr.length; i++){

      key = arr[i]['idemployee'];

      if (!hash.contains(key)){
          result.push(arr[i]);
          hash.add(key);
      }
  }

    return result;
}


function clearSearchList(){
    var html = document.getElementById("bookRevList").html;
    var innerHTML = document.getElementById("bookRevList").innerHTML;

    document.getElementById("resultsSection").style.display = "block";
    document.getElementById("bookRevList").innerHTML = ""; //this is the original search results div that gets displayed

    console.log("inner html of  book rev list : "+document.getElementById("bookRevList").innerHTML);

    if(document.getElementById("bookRevList2") != null && document.getElementById("bookRevList2") != 'undefined'){

          document.getElementById("bookRevList2").innerHTML = "";

           $( ".bookRevList2" ).each(function( ) { //these are the search result divs that get added upon pagination of search results
              this.innerHTML = "";
            });

          $( ".searchSegment" ).remove();

    }
}


function formatFacetContent(groupData){

  var formattedContent = '';

  thisGroupData = groupData;

  for(var i = 0; i <  groupData.length; i++){

			facetList = groupData[i]['facetModelsMatchingGroupItems'];
      formattedContent = formattedContent +" <ul class='groupData'><span class='facetGroupLabel'> "
			+ groupData[i]['groupLabel']+"</span>";

			for(var j = 0; j < facetList.length; j++){
        if(facetList[j]['facetCount'] > 0){
				    var theLabel = facetList[j]['facetLabel'];
            console.log(theLabel);
            formattedContent = formattedContent +  "<li><span><input type='checkbox' id='"+theLabel+"'"+"onclick='displayFacetCheckboxSelection()'>"+ theLabel+" </input> </span><span>("
						+facetList[j]['facetCount']+")</span> </li>" ;
          }
			}

			formattedContent = formattedContent + "</ul>"

	}

	return formattedContent;

}


function resetFacetMarkup(){
	$('.groupData').remove();
}


	function formatSearchContent(searchData, $log){
	//		$log.info("formatting");

			var formattedMarkup = "";

	//		$log.info("formatBooksSearchContent "+JSON.stringify(searchData));

			if(undefined != searchData){


				formattedMarkup = "<div style='float:left; margin-right:1.5em;' ><img alt='book thumb' width='"+searchData['imageWidth']+"' height='"+searchData['imageHeight']
				+"' src='"+searchData['profilePicURL']+"' /></div>"+
			"<span><b>Name : </b>"+searchData['employeeFirstName']+" "+
        searchData['employeeGivenNames'] +" "+searchData['employeeSurname']+"&nbsp;&nbsp;<b>Age : </b>"+searchData['employeeAge']+" <b>&nbsp;&nbsp;Gender : </b> "
        +searchData['employeeGender']+  "&nbsp;&nbsp;<b>Marital status : </b> " +searchData['employeeMaritalStatus']+" </span></br>"+

        "<span><b>Department : </b>"+searchData['empSkillsetsDataModel'][0]['departmentName']+" "+
          "&nbsp;&nbsp;<b>Current Position : </b>"+searchData['empSkillsetsDataModel'][0]['currentPostionName']+" <b>&nbsp;&nbsp;Skillsets : </b> "+
          "</br></br><div class='skillsetsSearchSummary'>";

          for(var i =0 ; i < searchData['empSkillsetsDataModel'].length; i++){
            formattedMarkup= formattedMarkup+" <i><b>Name</b></i> : "+searchData['empSkillsetsDataModel'][i]['skillsetName']+
            "&nbsp;&nbsp;<i> <b>Proficiency</b></i> : "
            +searchData['empSkillsetsDataModel'][i]['skillSetProficiency']+
            "&nbsp;&nbsp; <i><b>Relevance to position (1-10)</b></i> : "+searchData['empSkillsetsDataModel'][i]['skillsetToPositionRelevance']+
            "&nbsp;&nbsp;<i><b> Skillset Years Experience</b></i> : "+searchData['empSkillsetsDataModel'][i]['skillsetYearsExperience']+"</br>"
          }

          formattedMarkup= formattedMarkup+"</div>";

			}

			return formattedMarkup;
	 }

   function paginateHere(){
     //console.log('7777777777 paginate here method are here at all?!!?!?!?');


     $.ajax('retrieveNextPaginatedResults', {
           success: function(data) {
        //     console.log("success we are here : "+data.length);
        //     console.log("we are here : "+data);

        //    $('.bookRevList').append(formatSearchContent(data[i]));

          console.log("data length returned ::: "+data.length);

            employeeSearchData = data;
            for(var i = 0; i < data.length; i++){

              //$log.info("first book in array : "+$('.bookRevList').html());

              //$('.bookRevList').append("<div class='searchSegment'>");
                console.log(data[i]);
                var formattedContent = "<div class='searchSegment'>"+formatSearchContent(data[i])+"</div>"

                $('.bookRevList').append(formattedContent);

            //	$('.bookRevList').append("</div>");
                  //    $('.ajax-loader-2').remove();
            }

            if(data == undefined || data == null || data.length < 1){
                $('.ajax-loader-2').remove();
            }else{
                detachScroll();
            }


           },
           error: function() {
               console.log('An error occurred');
           }
        });



   }
   function getDocHeight() {
       var D = document;
       return Math.max(
           D.body.scrollHeight, D.documentElement.scrollHeight,
           D.body.offsetHeight, D.documentElement.offsetHeight,
           D.body.clientHeight, D.documentElement.clientHeight
       );
   }


function attachScroll(data){

   employeeSearchData = data;

   $( window ).scroll(function() {


      //console.log($(window).scrollTop()+' R: '+$(window).height());
      //console.log(getDocHeight()+": "+document.body.scrollHeight);
      if(employeeSearchData != undefined && employeeSearchData != null && employeeSearchData.length > 0){
          if(($(window).scrollTop() + $(window).height()) >= getDocHeight()) {
              // console.log("bottom bitch : "+$('.ajax-loader-2').html());

              if($('.ajax-loader-2').html() == undefined || $('.ajax-loader-2').html() == ''){

                    $('.resultsSection').append("<center><div class='ajax-loader-2'> </div></center>");
                    paginateHere();
              }
          }
      }



   });

 }

 function detachScroll(){
      $( window ).scroll(function(){

      });
 }
