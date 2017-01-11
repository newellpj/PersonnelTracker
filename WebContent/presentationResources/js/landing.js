


(function(angular) {

		angular.module('loginSignupApp', []).component('aboutUsPage', {
			template: `<div class="aboutUsSpiel"><p>Personnel Tracker is an evolving
			organisational performance tracking
			system built using AngularJS, Java 8, Spring, Hibernate,
			MySQL and Solr. It aims to streamline the process of improving your
			employees performance and enabling better managerial decision making
			backed by structured data. It also aims to give users the ability to publish
			this information to third parties if it so chooses or the public in general with
			 application administration built-in function. If you wish to know more
			 please contact us using the contact form provided here. </p> </div>

			 <div>
      <form name="contactForm" validate>
        <!-- NAME -->

				  <input type="text" name="userFirstName" ng-model="firstName" placeholder="First name [Required]" required />
				  <input type="text" name="userLastName" ng-model="lastName" placebolder="Last name [Required]" required />
				  <input type="email" name="userEmail" ng-model="email" required placeholder="Email [Required]" required />
				  <input type="email" name="userPhoneNumber" ng-model="phoneNumber" placeholder="Contact number" />
				  <textarea type="text" name="userMessage" ng-model="message" placeholder="Enquiry [Required]" required></textarea>

        <!-- SUBMIT BUTTON --> 
        <input type="submit" ng-click="processForm()" ng-disabled="contactForm.$invalid" value="Submit" />
      </form>
    </div>`,

			controller: function($scope, $http, $log){
					var $contactFormCtrl = this;

				// 	var dlg = $("<div></div>").dialog({
				 // 	 hide: 'fade',
				 // 	 maxWidth: 600,
				 // 	 modal: true,
				 // 	 show: 'fade',
				 // 	 title: 'Submitting Correspondance',
				 // 	 width: ( ('__proto__' in {}) ? '600' : 600 )
				 //  });
				 //
				 //  $(dlg).parent().find('button').remove();
				 //  $(dlg).html("<div class='ajax-loader-2 help-inline pull-right'></div><div><p>Submitting details </p></div>");
				 //  $(dlg).dialog("open");

					$scope.formData = {};
				  //  $scope.processForm = function() {
				  //    alert('valid form!')
				  //    $http({
				  //      method  : 'GET',
				  //      url     : 'sendContactDetails',
				  //      data    : '',
				  //      headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
				  //    }).success(function(successErrorCode){
					// 		 		 $(dlg).close();
					// 	 }).error(function(data, status){
		      //           $(dlg).close();
					// 	 });
				  //  };
        }

		}).component('employmeeProfile', {
			template: ``,

			controller: function($scope, $http, $log){

			}

		}).component('welcomePage', {
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
									<section class="performReviewOnline responsive">
											<div class="sectionContent responsive">Entirely online peformance reviews streamlinging the review process. This reduces typical organisational
											 frustration with the reviews process through cutting	time spent on a task that can be quite a stressful and time consuming.
											 </div>
									</section>

              <div class="carouselAndBuzzSection">
									<div class="scalable">Scalable</div>
									<div class="customizable">Customizable</div>
									<div class="lightweight">Lightweight</div>
									<div class="enterpriseWideSolution">Enterprise wide solution</div>

									<div class="profileHeading responsive"><h3>See detailed paginated profile and performance summaries
									   of your employees based on general or faceted search results</h3></div>
										<ul id="suggestedSlider" class="bxslider responsive">
											<li><img src="./presentationResources/images/profilePics/charles.jpeg"   /><p style="margin-top:3%;"><b>Name:</b> Charles Jones </p><p><b>Title:</b>  Senior Software Engineer </p>
											<p><b>Position:</b> Senior Software Engineer Billing Division</p><p><b>Location:</b> 25 Bond Street, Sydney</p></li>
											<li><img src="./presentationResources/images/profilePics/bill.jpeg" /><p style="margin-top:3%;"><b>Name:</b> Bill Staniforth </p><p><b>Title:</b>
											 Chief Information Officer </p>
											<p><b>Position:</b> Chief Information Officer </p><p><b>Location:</b> 25 Bond Street, Sydney</p></li>
											<li><img src="./presentationResources/images/profilePics/alicia.jpeg" />
											<p style="margin-top:3%;"><b>Name:</b> Alicia Dunstan </p><p><b>Title:</b>
											 Deputy Chief Financial Officer </p>
											<p><b>Position:</b> Deputy Chief Financial Officer (too young to be chief) </p><p><b>Location:</b> 25 Bond Street, Sydney</p></li></li>
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
