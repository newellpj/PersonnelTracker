


(function(angular) {

		angular.module('loginSignupApp', [])
		.component('signupForm', {
				  template: `

							<div id="loginFields" class="loginFields responsive">

									<div id="usersSignup" class="userFields responsive">
                    <label id="errorLabel" class="errorLabel" ></label>
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
        $signupController.statusMsg = "sdfdsfds";
        $signupController.username = '';
				$signupController.password = '';

		     $log.info('we here?!?!? : '+$scope.disableSignupBtn);


				$scope.$watch('$signupController.username', function(newVal, oldVal, scope) {

								 if(undefined != newVal){
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

						var dlg = $("<div></div>").dialog({
							hide: 'fade',
							maxWidth: 600,
							modal: true,
							show: 'fade',
							title: 'Sign up',
							width: ( ('__proto__' in {}) ? '600' : 600 )
						});

						$(dlg).parent().find('button').remove();
						$(dlg).html("<div class='ajax-loader-2 help-inline pull-right'></div><div><p>Checking chosen credentials </p></div>");
						$(dlg).dialog("open");

		        $http({
		            url : 'signup',
		            method : 'GET',
		            headers: {'Content-Type' : 'application/json'},
		            dataType: "JSON",
		            params: {
		              username: $signupController.username,
		              password: $signupController.password
		            }
		        }).then(function successCallback(successErrorCode) {

                $(dlg).dialog("close");
		          $log.info("we are here : "+successErrorCode[0]);
              //the get can return successfully even though the signup failed due to username already existing
							var statusCode = successErrorCode[0];

							if(statusCode == 'FAILURE'){
								  $log.info('failure hsdfdsh : '+$signupController.statusMsg);
								  $('.errorLabel').css('display', 'block');
									$('.errorLabel').css('color', 'red');
							}else{

									$('.errorLabel').css('display', 'block');
									$('.errorLabel').css('color', 'black');
									$('input.usernameInput.responsive').val($signupController.username);
							}

              $('.errorLabel').html(successErrorCode[1]);

		          $log.info("index of ::: "+successErrorCode.indexOf("html"));
		          $(dlg).dialog("close");


		        }, function errorCallback(response) {
		          $log.error("we errored here : "+data[0]);

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
		            });



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
