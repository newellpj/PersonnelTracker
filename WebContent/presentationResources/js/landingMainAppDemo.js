(function(angular) {


    var appDemoModule = angular.module('appTabPage', []);



    appDemoModule.component('appPageDemo', {
      template: `<div ng-controller="searchPageController">
      	<form id="searchForm" name="searchForm"  commandName="bookReviewsModel">


				<div class="surnameAndDept responsive" >

					<input ng-model="employeeName" id="employeeName"  placeholder="Employee surname..."  type='text' name='employeeName' style="width:40%;">

					<input id="deptCheck" type="checkbox"  ng-model="deptCheck" class="checks responsive" name="deptCheck" value="deptCheck" ng-click="deptHide = !deptHide" />Department
					<select ng-model="deptSelect.selectedOption" class="depSelect responsive" ng-hide="deptHide" style="width:30%; margin-left:1.7em; " id="departSelect"
							ng-options="option.name for option in deptSelect.availableOptions track by option.value">

					</select>

						 <ul class="nameSearchPossibles" ng-mouseleave="mouseLeave('nameSearchPossibles')">
							<li ng-repeat="d in data | filter: employeeName track by $index" style="margin-left:2em;">
							 <span ng-mousedown="displayNames(d)">{{d.employeeName}}</span>
							</li>
						   </ul>


				</div>

				<div class="firstnamePosition responsive">

					<input ng-model="empFirstName" id="empFirstName"  placeholder="Employee first name..." style="width:40%;" type='text' name='empFirstName' />

					<input id="positionCheck" type="checkbox" class="checks responsive" ng-model="positionCheck"  name="positionCheck" value="positionCheck" ng-click="positionHide = !positionHide" />Position
					<select  ng-model="positionSelect.selectedOption" name="posText" ng-hide="positionHide" class="positionSelect responsive" style="width:30%; margin-left:0.4em;"
          id="positionSelect"
					    ng-options="option.name for option in positionSelect.availableOptions track by option.value" >
					</select>

					 <ul class="firstNameSearchPossibles" ng-mouseleave="mouseLeave('firstNameSearchPossibles')">
							<li ng-repeat="d in data | unique: empFirstName" style="width:100%; padding-left:-2em !important; margin-left:2em;">
							 <span ng-mousedown="displayFirstNames(d)">{{d.empFirstName}}</span>
							</li>
					  </ul>

				</div>

				<div class="givenNamesSkillset responsive">

					<input ng-model="empGivenNames" id="empGivenNames"  placeholder="Employee given names..." style="width:40%;" type='text' name='empGivenNames' />

					<input id="skillsetCheck" ng-model="skillCheck" class="checks responsive" type="checkbox" name="skillsetCheck" value="skillsetCheck" ng-click="skillsetHide = !skillsetHide" />Skillset
					<select ng-model="skillsetSelect.selectedOption" ng-hide="skillsetHide" name="skillsetSelect" class="skillsetSelect responsive" style="width:30%;" id="skillsetSelect"
							ng-options="option.name for option in skillsetSelect.availableOptions track by option.value" >
					</select>
					 <ul class="givenNamesSearchPossibles"  ng-mouseleave="mouseLeave('givenNamesSearchPossibles')">
							<li ng-repeat="d in data | unique: empGivenNames " style="margin-left:2em;">
							 <span ng-mousedown="displayGivenNames(d)">{{d.empGivenNames}}</span>
							</li>
					  </ul>
		          </div>

			  <div class="tagSearches responsive" ng-controller="searchSubmitter">


								<button id="searchBook" class="searchBook responsive" name="searchBook" type="button"
								 ng-disabled="employeeName == '' && empFirstName == '' && empGivenNames == '' && skillsetSelect.selectedOption.value == ''
											&& positionSelect.selectedOption.value == '' && deptSelect.selectedOption.value == '' "  ng-click="performBookSearch();" value="Search.." >
								<span class="glyphicon glyphicon-eye-open" style="padding-right:0.5em;" ></span>Search...
								</button>
								<button id="resetSearch" class="resetSearch responsive" name="resetSearch" type="button" onclick="resetTheSearch();"  value="Reset" >
										<span class="glyphicon glyphicon-refresh" style="padding-right:0.5em;" ></span> Reset...
								</button>


					</div>
			<br/>


		</form>

</div>

<div id="resultsSection" class="resultsSection responsive" >
		<form id="searchResults" class="searchResults">

			<div id="search" class="search" style="display:none; width:1000px !important;">
				<ul id="bookRevList" class="bookRevList">
				</ul>
			</div>

		</form>
</div></div>		`
    });

    var searchedDataSet;


appDemoModule.filter('unique', function() {
// we will return a function which will take in a collection
// and a keyname
 return function(collection, keyname) {
  // we define our output and keys array;
  var output = [],
    keys = [];

  // we utilize angular's foreach function
  // this takes in our original collection and an iterator function
  angular.forEach(collection, function(item) {
    // we check to see whether our object exists
    var key = item[keyname];
    // if it's not already part of our keys array
    if(keys.indexOf(key) === -1) {
      // add it to our keys array
      keys.push(key);
      // push this item to our final output array
      output.push(item);
    }
  });
  // return our array which should be devoid of
  // any duplicates
  return output;
 };
});


appDemoModule.service('constructInstantSearchService', function($log, $http){

  this.runInstantSearch = function(valueArrayKey, searchValueKey, valueSelected, allDataReturned){
    var displayItems = [];

    var matchedCount = 0;


    for(var i = 0; i < allDataReturned.length; i++){
      if(allDataReturned[i][valueArrayKey] == valueSelected){
        displayItems[matchedCount] = allDataReturned[i];
        matchedCount++;
      }
    }

    console.log('hello display items there : '+displayItems[0][valueArrayKey]);

     $('.'+searchValueKey+'SearchPossibles').css("display", "none");

      var searchCriteriaUpdate =  searchValueKey+"-"+valueSelected;

      $http({
          url : 'updateSearchCriteriaAndPaginationOffset',
          method : 'POST',
          headers: {'Content-Type' : 'application/json'},
          dataType: "JSON",
          params: {
            searchCriteriaUpdate: searchCriteriaUpdate,
            currentInstantSearchNumberDisplayed: matchedCount
          }
        }).then(function successCallback(data) {
            console.log("success update");
          }, function errorCallback(response) {
             console.log("error update");
        });

     return displayItems;
  }
});

appDemoModule.service('searchDisplayInitService', function($log){

this.searchDisplayInit = function(){
    var html = document.getElementById("bookRevList").html;
    var innerHTML = document.getElementById("bookRevList").innerHTML;


    $log.info("results section");

    document.getElementById("resultsSection").style.visibility = "visible";
    document.getElementById("bookRevList").innerHTML = ""; //this is the original search results div that gets displayed

    $log.info("inner html of  book rev list : "+document.getElementById("bookRevList").innerHTML);

    if(document.getElementById("bookRevList2") != null && document.getElementById("bookRevList2") != 'undefined'){

      document.getElementById("bookRevList2").innerHTML = "";

       $( ".bookRevList2" ).each(function( ) { //these are the search result divs that get added upon pagination of search results
          this.innerHTML = "";
        });

      $( ".searchSegment" ).remove();
    }
}

});


appDemoModule.service('formatSearchService', function($log, searchDisplayInitService) {



  this.formatContent = function(dataToFormat){

    searchDisplayInitService.searchDisplayInit();

    document.getElementById("search").style.display = "inline";

    $log.info("data to format length :::: "+dataToFormat.length);

    for(var i = 0; i < dataToFormat.length; i++){
      var formattedContent = "<div class='searchSegment'>"+formatBooksSearchContent(dataToFormat[i], $log)+"</div>"
      $('.bookRevList').append(formattedContent);
    }



    $(".search").append("<div class='next'><a href='retrieveNextSearchSegment'>"+""+"</a> </div>");

    $('.resultsSection').jscroll({
      loadingHtml: "<center><div class='ajax-loader-2'> </div></center>"
    });
  }
});

appDemoModule.controller('searchPageController', function($scope, $log, $timeout, $http, formatSearchService, constructInstantSearchService) {
 $log.info("11 title text from search page controller : "+$scope.employeeName);

   $scope.deptHide = true;
   $scope.positionHide = true;
   $scope.skillsetHide = true;
   $scope.lastSelectedSurnameItem = '';
   $scope.lastSelectedFirstNameItem = '';
   $scope.lastSelectedGivenNamesItem = '';

  $scope.deptSelect = {
    model: null,
    availableOptions: [
       {value: '', name: 'Please select..'},
       {value: 'Drama', name: 'Drama'},
       {value: 'Thriller', name: 'Thriller'},
       {value: 'Crime', name: 'Crime'},
       {value: 'Biography', name: 'Biography'},
       {value: 'Philosophy', name: 'Philosophy'},
       {value: 'Mystery', name: 'Mystery'},
       {value: 'Non-fiction', name: 'Non-fiction'},
       {value: 'Romance', name: 'Romance'},
       {value: 'Sci-fi', name: 'Sci-fi'},
       {value: 'Human Interest', name: 'Human Interest'}
     ],
     selectedOption: {value: '', name: 'Please select..'}
  };

  $scope.positionSelect = {
    model: null,
    availableOptions: [
       {value: '', name: 'Please select..'},
       {value: 'Fiction', name: 'Fiction'},
       {value: 'Non-fiction', name: 'Non-fiction'}
     ],
     selectedOption: {value: '', name: 'Please select..'}
  };

//  $scope.skillsetSelect = {
  //  availableOptions [
    /* {value: '', name: 'Please select..'},
       {value: 'English', name: 'English'},
       {value: 'French', name: 'French'},
       {value: 'Mandarin', name: 'Mandarin'},
         {value: 'Hindi', name: 'Hindi'},
       {value: 'Latin', name: 'Latin'},
       {value: 'Spanish', name: 'Spanish'}  */
    // ]
    // selectedOption: {value: '', name: 'Please select..'}
    console.log("dsfdsfdsfdsfdsfdsfdsf");

$scope.skillsetSelect = [];

     $http({
                method: 'GET',
                url: 'getSkillsets',
                data: { }
            }).then(function successCallback(skillsetDataList) {
               $scope.skillsetSelect = skillsetDataList;
        },  function errorCallback(response) {

             console.log('error retrieving data : '+response);
        });

//  };

  $scope.testValue = function(searchType, tmpStr){

    if(searchType === 'surname'){
      if (tmpStr === $scope.employeeName && $scope.lastSelectedSurnameItem != $scope.employeeName){
        return true;
      }
    }else if(searchType === 'firstName'){
      if (tmpStr === $scope.empFirstName && $scope.lastSelectedFirstNameItem != $scope.empFirstName){
        return true;
      }
    }else if(searchType === 'givenNames'){
      if (tmpStr === $scope.empGivenNames && $scope.lastSelectedGivenNamesItem != $scope.empGivenNames){
        return true;
      }
    }

    return false;

  }

  $scope.performInstantSearch = function(tmpStr, objClass, searchType){

    if (!tmpStr || tmpStr.length == 0) {
                 console.log("within the null empty text "+tmpStr);
       $scope.data = "";
       $(objClass).css("display", "none");
       return 0;
    }


    $timeout(function() {

      // if searchStr is still the same..
      // go ahead and retrieve the data
        if ($scope.testValue(searchType, tmpStr)){

          console.log("within the title text "+tmpStr);

          $http({
            url : 'partialSearchForName',
            method : 'GET',
            headers: {'Content-Type' : 'application/json'},
            dataType: "JSON",
            params: {
              partialSearch: searchType+'-'+tmpStr
            }
          }).then(function successCallback(data) {
            //$scope.responseData = data;
            console.log("data returned "+data[0]['employeeName']);
            console.log("data length returned "+data.length);


             $scope.data = data;
             searchedDataSet = data;
           //  $(objClass).css("display", "table");

            if(data.length == 0 || data[0]['employeeName'] == null){
               $(objClass).css("display", "none");
                $scope.data = "";
              searchedDataSet = "";
             }else{
               $(objClass).css("display", "table");
             }


          }, function errorCallback(response) {
             $(objClass).css("display", "none");
            console.log('error retrieving data');
          });
        }
      }, 250);
  }


  $scope.$watch('positionCheck', function(newVal, oldVal, scope) {
    $log.info("newVal : "+newVal);

      $scope.positionSelect.selectedOption = $scope.positionSelect.availableOptions[0];


  });

  $scope.$watch('skillsetCheck', function(newVal, oldVal, scope) {
    $log.info("new val skill : "+newVal);

      $scope.skillsetSelect.selectedOption = $scope.skillsetSelect[0];

  });

  $scope.$watch('deptCheck', function(newVal, oldVal, scope) {
    $log.info("new value for genre check :: "+newVal);

      $scope.deptSelect.selectedOption = $scope.deptSelect.availableOptions[0];
  });


  //this has to be after the onclock
  $scope.mouseLeave = function(objToHide){
    console.log('lost focus');
    $("."+objToHide).css('display', 'none');
  }

  $scope.displayNames = function(data){
    console.log('hello there : '+data);
    $scope.employeeName = data.employeeName;
    //$scope.data = "";
    $scope.lastSelectedSurnameItem = data.employeeName;
     $('.nameSearchPossibles').css("display", "none");
    var dataToFormat = [];
    dataToFormat[0] = data;
     formatSearchService.formatContent(dataToFormat);

  }

  $scope.displayFirstNames = function(data){
    console.log('hello there : '+data);
    $scope.employeeName = data.employeeName;
    //$scope.data = "";
    $scope.lastSelectedSurnameItem = data.employeeName;
     $('.nameSearchPossibles').css("display", "none");
    var dataToFormat = [];
    dataToFormat[0] = data;
     formatSearchService.formatContent(dataToFormat);

  }

  $scope.displayGivenNames = function(data){
    console.log('hello there : '+data);
    $scope.employeeName = data.employeeName;
    //$scope.data = "";
    $scope.lastSelectedSurnameItem = data.employeeName;
     $('.nameSearchPossibles').css("display", "none");
    var dataToFormat = [];
    dataToFormat[0] = data;
     formatSearchService.formatContent(dataToFormat);

  }



  $scope.$watch('employeeName', function (tmpStr){
      console.log("tmpStr : "+tmpStr);
     $scope.performInstantSearch(tmpStr, '.nameSearchPossibles', 'surname');
  });

  $scope.$watch('empFirstName', function (tmpStr){
      console.log("tmpStr : "+tmpStr);
     $scope.performInstantSearch(tmpStr, '.firstNameSearchPossibles', 'firstName');
  });


  $scope.$watch('empGivenNames', function (tmpStr){
      console.log("tmpStr : "+tmpStr);
     $scope.performInstantSearch(tmpStr, '.givenNamesSearchPossibles', 'givenNames');
  });


});


//searchBookApp.factory('titleVal', function(){
//return { employeeName: '' };
//});


appDemoModule.controller('searchSubmitter', function($scope, $http, $log) {

   $scope.performBookSearch = function () {

    var html = document.getElementById("bookRevList").html;
    var innerHTML = document.getElementById("bookRevList").innerHTML;

    document.getElementById("resultsSection").style.visibility = "visible";
    document.getElementById("bookRevList").innerHTML = ""; //this is the original search results div that gets displayed

    $log.info("inner html of  book rev list : "+document.getElementById("bookRevList").innerHTML);

    if(document.getElementById("bookRevList2") != null && document.getElementById("bookRevList2") != 'undefined'){

      document.getElementById("bookRevList2").innerHTML = "";

       $( ".bookRevList2" ).each(function( ) { //these are the search result divs that get added upon pagination of search results
          this.innerHTML = "";
        });

      $( ".searchSegment" ).remove();



    }

    //as search segment can get placed outside the book list by the jscroll function we should
    //remove all searchSegments - they will be re-added by javascript or the controllers dynamically
    //anyway so no damage is done

    $log.info("we are titleVal 323 : "+$scope.employeeName);

      var employeeName = $scope.employeeName;
      var empGivenNames = $scope.empGivenNames;
      var empFirstName = $scope.empFirstName;
      var deptSelect = $scope.deptSelect.selectedOption.value;
      var positionSelect = $scope.positionSelect.selectedOption.value;
      var skillsetSelect = $scope.skillsetSelect.selectedOption.value;

      $log.info("publisher text ::: "+empGivenNames);
      $log.info("skillsetSelect text ::: "+skillsetSelect);

       var dlg = $("<div></div>").dialog({
        hide: 'fade',
        maxWidth: 600,
        modal: true,
        height: 200,
        show: 'fade',
        title: 'Searching Books....',
        width: ( ('__proto__' in {}) ? '600' : 600 )
      });

      $(dlg).parent().find('button').remove();

      $(dlg).html("<div class='ajax-loader-2 help-inline pull-right'></div><div><p>Searching books...</p></div>");

      $(dlg).dialog("open");


    $http({
        url : 'searchForBook',
        method : 'GET',
        headers: {'Content-Type' : 'application/json'},
        dataType: "JSON",
        params: {
          surname: employeeName,
          givenNames: empGivenNames,
          firstName: empFirstName,
          deptSelect: deptSelect,
          positionSelect: positionSelect,
          skillsetSelect: skillsetSelect
        }
      }).then(function successCallback(successErrorCode) {

        $log.info("we are here : "+bookReviewsModelArray.length);
        $log.info("we are here : "+bookReviewsModelArray);

        $log.info("index of ::: "+bookReviewsModelArray.indexOf("html"));

        if( bookReviewsModelArray.indexOf("html") > -1 && bookReviewsModelArray.indexOf("body") >  -1){
          $(dlg).dialog("close");
          window.parent.location.href = 'logout';
        }else{

            document.getElementById("search").style.display = "inline";


            $scope.formattedSearchData = '';

            var testFirstElement = bookReviewsModelArray[0]['booksList'];

            $log.info('testFirstElement : '+testFirstElement);
            $log.info('bookReviewsModelArray : '+bookReviewsModelArray.length);

            if("No Books Found!!" != testFirstElement){
              $log.info("we here again");
              for(var i = 0; i < bookReviewsModelArray.length; i++){

                //$log.info("first book in array : "+$('.bookRevList').html());

                //$('.bookRevList').append("<div class='searchSegment'>");

                var formattedContent = "<div class='searchSegment'>"+formatBooksSearchContent(bookReviewsModelArray[i], $log)+"</div>"

                $('.bookRevList').append(formattedContent);
              //	$('.bookRevList').append("</div>");

              }

              $(".search").append("<div class='next'><a href='retrieveNextSearchSegment'>"+""+"</a> </div>");

              $('.resultsSection').jscroll({
                loadingHtml: "<center><div class='ajax-loader-2'> </div></center>"
              });

            }else{
              $('.bookRevList').append("<span style='text-shadow: 0.5px 0.5px #a8a8a8; '>No Books Found!! </span>");
            }

            $(dlg).dialog("close");
         }

      }, function errorCallback(response) {
        $log.error("we errored here");

        $(dlg).dialog("close");

        var errorDialog = $("<div></div>").dialog({
            hide: 'fade',
            maxWidth: 300,
            modal: true,
            show: 'fade',
            open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
              buttons: [
                {
                  'class': 'btn btn-primary',
                  click: function(e) {
                    $(this).dialog("close");
                  },
                  text: 'OK'
                }

              ],

            create: function (event, ui) {
              //$(event.target).parent().css('position', 'fixed'); - this stops the error message jumping around after initial popup
            },
            title: 'Could NOT find book!',
            width: ( 300 )
          });

          msg = "There was an error retrieving book";

          $(errorDialog).html('<p>'+msg+'</p>');
          $('.ui-dialog-buttonset').css("backgroundImage", "url('')");
          $('.ui-dialog-buttonset').css("backgroundColor", "#c3c3c3");


           $(errorDialog).dialog("open");
            window.parent.location.href = 'logout';
          });

      }


  });







})(angular);





	function formatBooksSearchContent(searchData, $log){
			$log.info("formatting");
			var bookDetails =  searchData['booksList'];
			var formattedMarkup = "";

			$log.info("formatBooksSearchContent "+bookDetails);

			if("No books found" != bookDetails){

				bookDetails = encodeURI(bookDetails);//bookDetails.replace(/ /g, "-");

				formattedMarkup = "<div style='float:left; margin-right:1.5em;' ><img alt='book thumb' width='"+searchData['imageWidth']+"' height='"+searchData['imageHeight']
				+"' src='"+searchData['thumbnailLocation']+"' /></div>"+
				"<span style='font-family:courier;'><b>Title : </b>"+searchData['employeeName']+"<b> Author : </b> "+searchData['employeeFirstName']+" &nbsp; <b>Publisher: </b>"
				+searchData['employeeGivenNames']+"</span>"+
				" <p style='font-size:x-small;!important'>"+searchData['excerpt']+

				"&nbsp; <a style='font-size:x-small;!important; font-style:italic !important;' href='reviewsReviewBook?titleAuthorText="+bookDetails
				+"&imageHeight="+searchData['imageHeight']+"&imageWidth="+searchData['imageWidth']+"&thumbnailLocation="+searchData['thumbnailLocation']+"'> Review this </p>";
			}

			return formattedMarkup;
	 }
