


(function(angular) {

		angular.module('loginSignupApp', []).component('employmeeProfile', {
			template: ``,

			controller: function($scope, $http, $log){}

		})
		.component('welcomePage', {
				  template: `
							<div id="welcomeTab" class="welcomeTab responsive">
							  <div class="mainSpiel responsive">
								    <h2>Personnel Tracker 360 empowers organisations to accurately track all aspects of employee
										management - from personal details through to skillset to current role matching and performance reporting.</h2>
								</div>

									<section class="performanceTracking responsive">
									   <div class="sectionContent responsive">Track your employee performance with weighted skillset to current role matching. Indicates which skill
										 set deficiences need to be addressed most in order of priority in context of the employees current role within the organisation.
										  </div>
									</section>
	                <section class="personalDetails responsive">
											<div class="sectionContent responsive">Store and easily access your employees personal details as well as maintaining the option to
											grant them access to change their personal and payroll and other administrative details
											 </div>
									</section>
									<section class="orgGoalsMatching responsive">
											<div class="sectionContent responsive">Enter and prioritise organisation goals. Then match employees with organisational goals. Additionally
											   rank employees in terms of which employee best achieves organisation goals based on current performance rankings.
											 </div>
									</section>
                  <div class="carouselSection">
										<ul id="suggestedSlider" class="bxslider responsive">
											<li><img src="./presentationResources/images/profilePics/charles.jpeg"   /><p style="margin-top:3%;"><b>Name:</b> Charles Jones </p><p><b>Title:</b>  Senior Software Engineer </p>
											<p><b>Position:</b> Senior Software Engineer Billing Division</p><p><b>Location:</b> 25 Bond Street, Sydney</p></li>
											<li><img src="./presentationResources/images/profilePics/bill.jpeg" /></li>
											<li><img src="./presentationResources/images/profilePics/alicia.jpeg" /></li>
											<li><img src="./presentationResources/images/profilePics/chloe.jpeg" /></li>
											<li><img src="./presentationResources/images/profilePics/Kapil.jpeg"  /></li>
											<li><img src="./presentationResources/images/profilePics/Alexis.jpeg"  /></li>
											<li><img src="./presentationResources/images/profilePics/russell.jpeg" /></li>
											<li><img src="./presentationResources/images/profilePics/sally.jpeg"  /></li>
											<li><img src="./presentationResources/images/profilePics/simon.jpeg"  /></li>
											<li><img src="./presentationResources/images/profilePics/Amanda.jpeg"  /></li>
											<li><img src="./presentationResources/images/profilePics/robert.jpeg" /></li>
											<li><img src="./presentationResources/images/profilePics/jessica.jpeg" /></li>
										</ul>
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
		        }).success(function(successErrorCode){

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


		        }).error(function(data, status){
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
