


(function(angular) {

		angular.module('loginSignupApp', [])
		.component('signupForm', {
				  template: `

							<div id="loginFields" class="loginFields responsive">


									<div id="usersSignup" class="userFields responsive">

										<input class="usernameInput responsive" type='text' name='username' ng-model="$signupController.username"
										    placeholder="Choose username" style="width:60%;">
									 </div>
									<div id="passSignup"  class="passFields responsive">
										<input class="usernameInput responsive"
										placeholder="Choose password" type='password' name='password' ng-model="$signupController.password"
										           style="width:60%; margin-left:0.3em;"/>
									</div>


								</div>
								<div class="signupBtnsDiv">
							<button class="loginButton responsive" value="Signup" ng-click="submitUserDetails()" ng-disabled="disableSignupBtn">
								<span class="glyphicon glyphicon-log-in"></span> &nbsp; Signup </button>
								</div>

						</div>
									`,
		  controller: function($scope, $http, $log){
				var $signupController = this;
        $scope.disableSignupBtn = true;

        $signupController.username = '';
				$signupController.password = '';

		     $log.info('we here?!?!? : '+$scope.disableSignupBtn);


				$scope.$watch('$signupController.username', function(newVal, oldVal, scope) {

								 if(undefined != newVal){
											$log.info('hefhdskfhdkdsfhds,lfh');
									 		$signupController.username = newVal;
								 }


								$log.info('user : '+$signupController.username+' pass : '+$signupController.password);

								 if($signupController.username != '' && $signupController.password != ''){
									 $scope.disableSignupBtn = false;
								 }else{
									 $scope.disableSignupBtn = true;
								 }
				});

				$scope.$watch('$signupController.password', function(newVal, oldVal, scope) {

						if(undefined != newVal){
						 		$log.info('2222 hefhdskfhdkdsfhds,lfh');
								$signupController.password = newVal;
						}
              	$log.info(' 222 user : '+$signupController.username+' pass : '+$signupController.password);

							if($signupController.username != '' && $signupController.password != ''){
								$scope.disableSignupBtn = false;
							}else{
								$scope.disableSignupBtn = true;
							}
				});


		  $scope.submitUserDetails = function(){
              $log.info('we here 222222?!?!?');
		        $http({
		            url : 'signup',
		            method : 'GET',
		            headers: {'Content-Type' : 'application/json'},
		            dataType: "JSON",
		            params: {
		              username: $signupController.username,
		              password: $signupController.password
		            }
		        }).success(function(successErrorCode){


		          $log.info("we are here : "+successErrorCode);

		          $log.info("index of ::: "+bookReviewsModelArray.indexOf("html"));
		          $(dlg).dialog("close");


		        }).error(function(data, status){
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
		              title: 'Registration Error',
		              width: ( 300 )
		            });

		            msg = "There was an error registering please try again";

		            $(errorDialog).html('<p>'+msg+'</p>');
		            $('.ui-dialog-buttonset').css("backgroundImage", "url('')");
		            $('.ui-dialog-buttonset').css("backgroundColor", "#c3c3c3");


		             $(errorDialog).dialog("open");
		              window.parent.location.href = 'logout';
		            })



		    }
		  }
		})


})(angular);

function openTab(evt, tabName) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the link that opened the tab
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}
