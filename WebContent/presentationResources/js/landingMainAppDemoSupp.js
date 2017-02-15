
var thisGroupData = [];

var initialemployeeModelData = []

var employeeSearchData = [];

facetChecked = false;

function displayFacetCheckboxSelection(){
   console.log($("input[type='checkbox']:checked").val()+' :  BULLSHIT! : '+thisGroupData.length);
   //console.log($("input[type='checkbox']:checked").attr("id"));

      duplicatesRemovedList = [];

      var checkedFound = false;

       $("input[type='checkbox']:checked").each(function(){

           facetChecked = $(this).attr("id").includes("facet");

           if(facetChecked){
                 console.log("checked label id found :: "+$(this).attr("id"));//this is the checked checkbox
                 clearSearchList(); //don't clear - but need to check if search item already exists
                 checkedFound = true;
                 var facetLabelID = $(this).attr("id");
                  facetLabelID = facetLabelID.replace("facet", "");
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
             }
        });


    if(!checkedFound){  //re-display original search set before any facets were selected
        facetChecked = false;
        /* for(var i = 0; i <  thisGroupData.length; i++){
           for(var j = 0; j < facetList.length; j++){
             if(facetList[j]['facetCount'] > 0){
                 console.log("employees matched against facet list : "+facetList[j]['employeesMatchedAgainstFacetCategory'].length+" :: "+
                 facetList[j]['employeesMatchedAgainstFacetCategory'][0]['employeeSurname']);
                 duplicatesRemovedList = duplicatesRemovedList.concat(facetList[j]['employeesMatchedAgainstFacetCategory']);
                 console.log("duplicate list before : "+duplicatesRemovedList.length);
                   duplicatesRemovedList = testForDuplicates(duplicatesRemovedList);
                 console.log("duplicate list after : "+duplicatesRemovedList.length);
             }
           }
         }*/
         duplicatesRemovedList =initialemployeeModelData;

      }


            document.getElementById("search").style.display = "inline";
            //now format the search list to display to user

            $(".bookRevList").empty();

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
            for(var i = 0; i < keys.length; i++){
                console.log("key value : "+keys[i]);
            }
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
      console.log(key +" :: contains::: "+hash.contains(key))
      if (!hash.contains(key)){
          result.push(arr[i]);
          hash.add(key);
      }
  }


    console.log("result size : "+result.length);
    return result;
}


function clearSearchList(){
    var html = document.getElementById("bookRevList").html;
    var innerHTML = document.getElementById("bookRevList").innerHTML;

    document.getElementById("resultsSection").style.display = "block";
    document.getElementById("bookRevList").innerHTML = ""; //this is the original search results div that gets displayed

    console.log("inner html of  book rev list : "+document.getElementById("bookRevList").innerHTML);

}


function formatFacetContent(groupData, initialemployeeModelDataReturned){

  var formattedContent = '';

  initialemployeeModelData = initialemployeeModelDataReturned; //this will contain the number of records equal to or less than pagaination value

  thisGroupData = groupData;

  for(var i = 0; i <  groupData.length; i++){

			facetList = groupData[i]['facetModelsMatchingGroupItems'];
      formattedContent = formattedContent +" <ul class='groupData responsive'><span class='facetGroupLabel'> "
			+ groupData[i]['groupLabel']+"</span>";

			for(var j = 0; j < facetList.length; j++){
        if(facetList[j]['facetCount'] > 0){
				    var theLabel = facetList[j]['facetLabel'];
            console.log("building html with this label ::: "+theLabel);
            formattedContent = formattedContent +  "<li><span><input type='checkbox' id='"+"facet"+theLabel+"'"
               +"onclick='displayFacetCheckboxSelection()'>"+ theLabel+" </input> </span><span>("
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

function matchSideBarToSearchResultsSection(){
  var resultsSectionHeight = $('#resultsSection').height();
  var sideBarHeight = $('.facetSidebar').height();

  if(sideBarHeight > resultsSectionHeight){
     var diff = sideBarHeight - resultsSectionHeight;
     diff = diff + 50;
     document.getElementById("resultsSection").style.marginBottom = diff+"px";
  }else{
     document.getElementById("resultsSection").style.marginBottom = "2%";
  }

}


	function formatSearchContent(searchData, $log){
	//		$log.info("formatting");

			var formattedMarkup = "";

	//		$log.info("formatBooksSearchContent "+JSON.stringify(searchData));

			if(undefined != searchData){


				formattedMarkup = "<div class='profilePicImg responsive' style='float:left; margin-right:1.5em;' ><img alt='book thumb' width='"+searchData['imageWidth']+"' height='"+searchData['imageHeight']
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

      matchSideBarToSearchResultsSection();

			return formattedMarkup;
	 }

   function paginateHere(){
     //console.log('7777777777 paginate here method are here at all?!!?!?!?');


     $.ajax('retrieveNextPaginatedResults', {
           success: function(data) {

         initialemployeeModelData = initialemployeeModelData.concat(data); //add the returned paginated data
                                                                           //to initial search employee data so
                                                                           //if user selects facets we can return the last paginated data
          console.log("data length returned ::: "+data.length);

            employeeSearchData = data;
            for(var i = 0; i < data.length; i++){


                console.log(data[i]);
                var formattedContent = "<div class='searchSegment responsive'>"+formatSearchContent(data[i])+"</div>"

                $('.bookRevList').append(formattedContent);

            }

            if(data == undefined || data == null || data.length < 1){
            //    detachScroll();
                $('.ajax-loader-2').remove();
            }else{
              $('.ajax-loader-2').remove();
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


      console.log($(window).scrollTop()+' R: '+$(window).height());
      console.log(getDocHeight()+": "+document.body.scrollHeight);

        if(!facetChecked){
            if(($(window).scrollTop() + $(window).height()) >= getDocHeight()) {
                  console.log("1 bottom bitch : "+employeeSearchData);
               if(employeeSearchData != undefined && employeeSearchData.length > 0){
                        console.log("2 bottom bitch : ");
                   if($('.ajax-loader-2').html() == undefined || $('.ajax-loader-2').html() == ''){
                          console.log("3 bottom bitch : ");
                          $('.resultsSection').append("<center><div class='ajax-loader-2'> </div></center>");
                          paginateHere();
                   }
                }
             }
         }



   });

 }

 function detachScroll(){
      $( window ).scroll(function(){

      });
 }
