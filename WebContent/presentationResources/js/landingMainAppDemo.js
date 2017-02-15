
(function(angular) {


    var appDemoModule = angular.module('appTabPage', []);



    appDemoModule.component('appPageDemo', {
      template: `<div ng-controller="searchPageController" class="demo responsive">
       <div class="searchingInProgressItems responsive"><button id="searchAgain" class="responsive resetSearch" ng-click="resetTheSearch()"> Search again? </button>
       <div id="waiter" class="ajax-loader-2a responsive"><div style="float:right; min-width:120px; margin-top:35%;">Searching...</div></div></div>
      	<form id="searchForm" name="searchForm"  commandName="bookReviewsModel" ng-hide="searchFormHide">


				<div class="surnameAndDept responsive" >
					<input ng-model="employeeName" id="employeeName"  placeholder="Employee surname..."  type='text' name='employeeName' style="width:40%;">
					<input id="deptCheck" type="checkbox"  ng-model="deptCheck" class="checks responsive" name="deptCheck" value="deptCheck" ng-click="deptHide = !deptHide" />Department
					<select ng-model="deptSelect.selectedOption" class="depSelect responsive" ng-hide="deptHide" style="width:30%; margin-left:1.7em; " id="departSelect"
							ng-options="option.name for option in deptSelect.availableOptions track by option.value">
					</select>
						 <ul class="nameSearchPossibles" ng-mouseleave="mouseLeave('nameSearchPossibles')" >
							<li ng-repeat="d in data | filter: employeeName track by $index" style="margin-left:2em;" >
							 <span ng-mousedown="displayNames(d)"><span style="font-style:italic;">{{d.employeeSurname}}</span>, {{d.employeeFirstName}} {{d.employeeGivenNames}}</span>
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
					 <ul class="firstNameSearchPossibles" ng-mouseleave="mouseLeave('firstNameSearchPossibles')" >
							<li ng-repeat="d in data | filter: empFirstName track by $index" style="width:100%; padding-left:-2em !important; margin-left:2em;" >
							 <span ng-mousedown="displayFirstNames(d)">{{d.employeeSurname}}, <span style="font-style:italic;">{{d.employeeFirstName}}</span> {{d.employeeGivenNames}}</span>
							</li>
					  </ul>
				</div>
				<div class="givenNamesSkillset responsive">
					<input ng-model="empGivenNames" id="empGivenNames"  placeholder="Employee given names..." style="width:40%;" type='text' name='empGivenNames' />
					<input id="skillsetCheck" ng-model="skillsetCheck" class="checks responsive" type="checkbox" name="skillsetCheck" value="skillsetCheck" ng-click="skillsetHide = !skillsetHide" />Skillset
					<select ng-model="skillsetSelect.selectedOption" ng-hide="skillsetHide" name="skillSelect" class="skillsetSelect responsive" style="width:30%;" id="skillsetSelect"
							ng-options="option.name for option in skillsetSelect.availableOptions track by option.value" >
					</select>
					 <ul class="givenNamesSearchPossibles"  ng-mouseleave="mouseLeave('givenNamesSearchPossibles')">
							<li ng-repeat="d in data | filter: empGivenNames track by $index" style="margin-left:2em;">
							 <span ng-mousedown="displayGivenNames(d)">{{d.employeeSurname}}, {{d.employeeFirstName}} <span style="font-style:italic;">{{d.employeeGivenNames}}</span></span> <!-- employeeGivenNames is the variable name in the employee data model returned from server-->
							</li>
					  </ul>
		          </div>
			  <div class="tagSearches responsive" ng-controller="searchSubmitter">
								<button id="searchBook" class="searchBook responsive" name="searchBook" type="button"
								  ng-click="performEmployeeSearch();" value="Search.." >
								<span class="glyphicon glyphicon-eye-open" style="padding-right:0.5em;" ></span>Search...
								</button>
								<button id="resetSearch" class="resetSearch responsive" name="resetSearch" type="button" ng-click="resetTheSearch()"  value="Reset" >
										<span class="glyphicon glyphicon-refresh" style="padding-right:0.5em;" ></span> Reset...
								</button>
					</div>
			<br/>
		</form>


    <div id="facetSidebar" class="facetSidebar responsive">
    </div>

      <div id="resultsSection" class="resultsSection responsive" >
      		<form id="searchResults" class="searchResults">
      			<div id="search" class="search" style="display:none; width:1000px !important;">
      				<ul id="bookRevList" class="bookRevList">
      				</ul>
      			</div>
      		</form>
      </div>

</div>`
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
      console.log("item to push : "+item);
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

    document.getElementById("resultsSection").style.display = "block";
    document.getElementById("facetSidebar").style.display = "block";
    document.getElementById("bookRevList").innerHTML = ""; //this is the original search results div that gets displayed

    $log.info("inner html of  book rev list : "+document.getElementById("bookRevList").innerHTML);

}

});


appDemoModule.service('formatSearchService', function($log, searchDisplayInitService) {



  this.formatContent = function(dataToFormat){

    searchDisplayInitService.searchDisplayInit();

    document.getElementById("search").style.display = "inline";

    $log.info("data to format length :::: "+dataToFormat.length);
    $log.info("data to format length :::: "+dataToFormat);

    for(var i = 0; i < dataToFormat.length; i++){
      var formattedContent = "<div class='searchSegment responsive'>"+formatSearchContent(dataToFormat[i], $log)+"</div>"
      $('.bookRevList').append(formattedContent);
    }


/*
    $(".search").append("<div class='next'><a href='thatone'>"+""+"</a> </div>");

    $('.resultsSection').jscroll({
      loadingHtml: "<center><div class='ajax-loader-2'> </div></center>
    }); */
  }
});





appDemoModule.controller('searchPageController', function($scope, $log, $timeout, $http, formatSearchService, constructInstantSearchService) {
 $log.info("11 title text from search page controller : "+$scope.employeeName);

   $scope.deptHide = true;
   $scope.searchFormHide = false;
   $scope.positionHide = true;
   $scope.skillsetHide = true;
   $scope.lastSelectedSurnameItem = '';
   $scope.lastSelectedFirstNameItem = '';
   $scope.lastSelectedGivenNamesItem = '';

  $scope.resetTheSearch = function(){
//      $scope.deptSelect.selectedOption = $scope.deptSelect.availableOptions[0];
//      $scope.skillsetSelect.selectedOption = $scope.skillsetSelect.availableOptions[0];
//      $scope.positionSelect.selectedOption = $scope.positionSelect.availableOptions[0];
      $scope.employeeName = '';
      $scope.empFirstName = '';
      $scope.empGivenNames = '';
      $scope.skillsetCheck = false;
      $scope.positionCheck = false;
      $scope.deptCheck = false;
      $scope.positionHide = true;
      $scope.skillsetHide = true;
      $scope.deptHide = true;
      $scope.groupData = '';
      $scope.searchFormHide = false;
      resetFacetMarkup();


      document.getElementById("searchAgain").style.display = "none";
      document.getElementById("resultsSection").style.display = "none";
      document.getElementById("facetSidebar").style.display = "none";

  }

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

  $scope.skillsetSelect = {
    availableOptions: [
     {value: '', name: 'Please select..'}
     ],
     selectedOption: {value: '', name: 'Please select..'}

  };

  $scope.deptSelect = [];

       $http({
                  method: 'GET',
                  url: 'getOrgDepts',
                  headers: {'Content-Type' : 'application/json'},
                  dataType: "JSON",
                  data: { }
              }).then(function successCallback(response) {
                console.log("positions ret "+response.data);

        //      response.data.push("name":"Please Select...", "value":"");
               //console.log(obj);

               $scope.deptSelect.availableOptions = response.data;

               $scope.deptSelect.availableOptions.unshift({value: '', name: 'Please select..'});
               $scope.deptSelect.selectedOption = {value: '', name: 'Please select..'};

          },  function errorCallback(response) {

               console.log('error retrieving data : '+response);
          });


  $scope.positionSelect = [];

       $http({
                  method: 'GET',
                  url: 'getCompanyPositions',
                  headers: {'Content-Type' : 'application/json'},
                  dataType: "JSON",
                  data: { }
              }).then(function successCallback(response) {
                console.log("positions ret "+response.data);

        //      response.data.push("name":"Please Select...", "value":"");
               //console.log(obj);

               $scope.positionSelect.availableOptions = response.data;

               $scope.positionSelect.availableOptions.unshift({value: '', name: 'Please select..'});
               $scope.positionSelect.selectedOption = {value: '', name: 'Please select..'};

          },  function errorCallback(response) {

               console.log('error retrieving data : '+response);
          });


$scope.skillsetSelect = [];

     $http({
                method: 'GET',
                url: 'getSkillsets',
                headers: {'Content-Type' : 'application/json'},
                dataType: "JSON",
                data: { }
            }).then(function successCallback(response) {
              console.log("skills ret "+response.data);

             $scope.skillsetSelect.availableOptions = response.data;
             $scope.skillsetSelect.availableOptions.unshift({value: '', name: 'Please select..'});
             $scope.skillsetSelect.selectedOption = {value: '', name: 'Please select..'};

        },  function errorCallback(response) {

             console.log('error retrieving data : '+response);
        });



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
          }).then(function successCallback(response) {
            //$scope.responseData = data;
          //  console.log("data returned "+data[0]['employeeName']);
            console.log("data length returned "+response.data.length);
            console.log("data at 0 "+response.data[0]['employeeFirstName']);



             $scope.data = response.data;
             searchedDataSet = response.data;
           //  $(objClass).css("display", "table");

            if(response.data.length == 0 || response.data[0]['employeeSurname'] == 'No Employees Found!!'){
               console.log('we wont display');
               $(objClass).css("display", "none");
                  $scope.data = "";
                  searchedDataSet = "";
               }else{
                  console.log('we should display');
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
    //$log.info("skillset selected option :: "+$scope.skillsetSelect.selectedOption.value);
     if($scope.positionSelect.availableOptions != undefined){
        $scope.positionSelect.selectedOption = $scope.positionSelect.availableOptions[0];
     }

  });

  $scope.$watch('skillsetCheck', function(newVal, oldVal, scope) {
    $log.info("new val skill : "+newVal);

     if($scope.skillsetSelect.availableOptions != undefined){
        $scope.skillsetSelect.selectedOption = $scope.skillsetSelect.availableOptions[0];
     }
  });

  $scope.$watch('deptCheck', function(newVal, oldVal, scope) {
    $log.info("new value for genre check :: "+newVal);
       if($scope.deptSelect.availableOptions != undefined){
           $scope.deptSelect.selectedOption = $scope.deptSelect.availableOptions[0];
        }
  });


  //this has to be after the onclock
  $scope.mouseLeave = function(objToHide){
    console.log('lost focus');
    $("."+objToHide).css('display', 'none');
  }

  $scope.displayNames = function(data){
    console.log('hello there : '+data);
    $scope.employeeName = data.employeeSurname;
    //$scope.data = "";
    $scope.lastSelectedSurnameItem = data.employeeSurname;
     $('.nameSearchPossibles').css("display", "none");
    var dataToFormat = [];
    dataToFormat[0] = data;
     $log.info("data :::: "+data);

    getFullEmployeeDetails(data);


  }

  $scope.displayFirstNames = function(data){
    console.log('hello there : '+data);
    $scope.empFirstName = data.employeeFirstName;
    //$scope.data = "";
    $scope.lastSelectedFirstNameItem = data.employeeFirstName;
     $('.nameSearchPossibles').css("display", "none");
    var dataToFormat = [];
    dataToFormat[0] = data;
    $log.info("data :::: "+data);
    getFullEmployeeDetails(data);


  }

  $scope.displayGivenNames = function(data){
    console.log('hello there : '+data);
    $scope.empGivenNames = data.employeeGivenNames;
    //$scope.data = "";
    $scope.lastSelectedGivenNamesItem = data.employeeGivenNames;
     $('.nameSearchPossibles').css("display", "none");
    var dataToFormat = [];
    dataToFormat[0] = data;
    $log.info("data :::: "+data);

      getFullEmployeeDetails(data);


  }

  var getFullEmployeeDetails = function(data){
	  $http({
	        url : 'getEmployeePerformanceDetails',
	        method : 'GET',
	        headers: {'Content-Type' : 'application/json'},
	        dataType: "JSON",
	        params: {
	        	empData: data

	        }


	      }).then(function successCallback(response) {
	    	  $log.info("we have success : "+response.data);
	    	  formatSearchService.formatContent(response.data);

	      }, function errorCallback(response) {
	    	  $log.info("we have an error");
          });
  }

  $scope.keys = [];
$scope.keys.push({ code: 13, action: function() { $scope.open( $scope.focusIndex ); }});
$scope.keys.push({ code: 38, action: function() { $scope.focusIndex--; }});
$scope.keys.push({ code: 40, action: function() { $scope.focusIndex++; }});



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

  $scope.setHideSearchFields = function(){
          $scope.searchFormHide = true;

  }

});


appDemoModule.controller('searchSubmitter', function($scope, $http, $log) {

    $scope.groupData = '';

   $scope.performEmployeeSearch = function () {
      $scope.setHideSearchFields();
    var html = document.getElementById("bookRevList").html;
    var innerHTML = document.getElementById("bookRevList").innerHTML;

  //  document.getElementById("resultsSection").style.display = "block";
    document.getElementById("bookRevList").innerHTML = ""; //this is the original search results div that gets displayed

    $log.info("inner html of  book rev list : "+document.getElementById("bookRevList").innerHTML);


    //as search segment can get placed outside the book list by the jscroll function we should
    //remove all searchSegments - they will be re-added by javascript or the controllers dynamically
    //anyway so no damage is done

    $log.info("we are titleVal emp surname : "+$scope.employeeName);

      var employeeName = '';

      if($scope.employeeName != undefined){
        employeeName = $scope.employeeName;
      }

      var empGivenNames = '';

      if($scope.empGivenNames != undefined){
        empGivenNames = $scope.empGivenNames;
      }

      var empFirstName = '';

      if($scope.empFirstName != undefined){
        empFirstName = $scope.empFirstName;
      }

      var deptSelect = '';

      if($scope.deptSelect.selectedOption != undefined){
        deptSelect = $scope.deptSelect.selectedOption.value;
      }

      var positionSelect = '';

      if($scope.deptSelect.selectedOption != undefined){
        positionSelect = $scope.positionSelect.selectedOption.value;
      }

      var skillsetSelect = '';

      if($scope.deptSelect.selectedOption != undefined){
        skillsetSelect = $scope.skillsetSelect.selectedOption.value;
      }

      $log.info("publisher text ::: "+empGivenNames);
      $log.info("skillsetSelect text ::: "+skillsetSelect);

       /*var dlg = $("<div class='responsive'></div>").dialog({
        hide: 'fade',
        maxWidth: 800,
        modal: true,
        height: 200,
        show: 'fade',
        title: 'Searching Employees....',
        width: ( ('__proto__' in {}) ? '700' : 700 )
      });

      $(dlg).parent().find('button').remove();

      $(dlg).html("<div class='ajax-loader-2 help-inline pull-right'></div><div><p>Searching employees...</p></div>");

      $(dlg).dialog("open"); */

      document.getElementById("waiter").style.display = "block";

    $http({
        url : 'searchForEmployee',
        method : 'GET',
        headers: {'Content-Type' : 'application/json'},
        dataType: "JSON",
        params: {
        	e1employee_surname: employeeName,
        	e1employee_given_names: empGivenNames,
        	e1employee_first_name: empFirstName,
        	dept_name: deptSelect,
        	position_name: positionSelect,
        	skillset_name: skillsetSelect
        }


      }).then(function successCallback(response) {
        document.getElementById("resultsSection").style.display = "block";
        document.getElementById("facetSidebar").style.display = "block";

        $log.info("we are here employee models list length : "+response.data['employeeModels'].length);
        $log.info("we are here facet group models list length : "+response.data['facetGroupModels'].length);

        if(response.data['employeeModels'].length <=  0){
           document.getElementById("waiter").style.display = "none";
           $('.bookRevList').append("No employees details discovered");
        }else{
            document.getElementById("search").style.display = "inline";
            $scope.groupData = response.data['facetGroupModels'];

            $log.info("$scope.groupData length : "+$scope.groupData.length);
            $log.info("$scope.groupData : "+$scope.groupData);

            $scope.formattedSearchData = '';
            if(undefined != response.data && response.data['employeeModels'].length > 0 && response.data['employeeModels'] != null){

                  $log.info("we here again :"+response.data[0]);
                  for(var i = 0; i < response.data['employeeModels'].length; i++){

                      var formattedContent = "<div class='searchSegment responsive'>"+formatSearchContent(response.data['employeeModels'][i], $log)+"</div>"

                      $('.bookRevList').append(formattedContent);
                      attachScroll(response.data['employeeModels']);
                  }

                  $('.facetSidebar').append(formatFacetContent(response.data['facetGroupModels'], response.data['employeeModels']));

            }else{
              $('.bookRevList').append("<span style='font-size:1.5em;'>No Records Found </span>");
              $scope.searchFormHide = false;
              $scope.showFacet = false;
            }

          //  $(dlg).dialog("close");
             document.getElementById("waiter").style.display = "none";
              $log.info("1bbbbbbbbbbb search form hide is true  ::: "+$scope.searchFormHide);
         }

         document.getElementById("searchAgain").style.display = "block";

      }, function errorCallback(response) {
            document.getElementById("resultsSection").style.display = "block";
            document.getElementById("facetSidebar").style.display = "none";

            document.getElementById("search").style.display = "block";
            $log.error("we errored here");

             document.getElementById("waiter").style.display = "none";
              $('.bookRevList').append("<span style='color:red'>There was an error. please wait 30 seconds and try again.</span>");
             document.getElementById("searchAgain").style.display = "block";
          });

            $log.info("2222222222222 search form hide is true  ::: "+$scope.searchFormHide);

      }


  });


})(angular);
