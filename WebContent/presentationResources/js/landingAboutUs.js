(function(angular) {

		angular.module('aboutUsTabPage', []).component('aboutUsPage', {
			template: `<div class="aboutUsSpiel"><p>Personnel Tracker is an evolving
			organisational performance tracking
			system built using AngularJS, Java 8, Spring, Hibernate,
			MySQL and Solr. It aims to streamline the process of improving your
			employees performance and enabling better managerial decision making
			backed by structured data. It also aims to give users the ability to publish
			this information to third parties if it so chooses or the public in general with
			 application administration built-in function. If you wish to know more
			 please contact us using the contact form provided here. </p> </div>
          <div class="culture responsive">
					   <img src="./presentationResources/images/culture.jpg">
					</div>
					<div class="quote1 responsive">
					         &quot;This system signficantly cuts down on the time wasted with paper based system and empowers
									 employees to take a leading role in their performance improvement process - <b>John Taylor HZX</b> &quot;
					</div>
					<div class="culture2 responsive">
						  <img src="./presentationResources/images/culture2.jpg">
					</div>
					<div class="quote2 responsive">
					         &quot;Removes a fair degree of subjectivity from the performance review process and increases employee satisfaction - <b>Mike Williams YZA</b> &quot;
					</div>

			 <div>
      <form name="contactForm" validate class="contactForm responsive" required ng-init="contactDetEmail = '';  contactDetPhone='';
				 contactDetName =''; contactDetMessage='';">

			        <!-- NAME -->
							  <h3>Make an Enquiry</h3>
							 	<div class="contactImg responsive"><img src="./presentationResources/images/contactUsImg.jpg" /></div>
							  <input type="text" name="contactDetName" id="contactDetName" ng-model="contactDetName" placeholder="First name [Required]" required />
										<div class="errorName responsive" ng-messages="contactForm.contactDetName.$error">
										  <div ng-hide="contactForm.contactDetName.$valid || !contactForm.$dirty" >This field is required</div>
										</div>
							  <input type="email" name="contactDetEmail" id="contactDetEmail" ng-model="contactDetEmail"  placeholder="Email [Required]" required />
										<div class="errorEmail responsive" ng-messages="contactForm.contactDetEmail.$error">
											<div ng-hide="contactForm.contactDetEmail.$valid || !contactForm.$dirty" >A valid email is required</div>
										</div>
							  <input type="email" name="contactDetPhone" id="contactDetPhone" ng-model="contactDetPhone" placeholder="Contact number" />
							  <textarea type="text" name="contactDetMessage" id="contactDetMessage" ng-model="contactDetMessage" placeholder="Enquiry [Required]" required></textarea>
										<div class="errorEnq resposive" ng-messages="contactForm.contactDetMessage.$error">
											<div ng-hide="contactForm.contactDetMessage.$valid  || !contactForm.$dirty">This field is required</div>
										</div>
								<button name="submit" type="button"  style="box-shadow: 3px 3px 10px #252728 ;" ng-click="submitEnquiry()"
									class="contactUs responsive" value="Contact us" ng-disabled="!contactForm.contactDetName.$valid  ||
									                                                             !contactForm.contactDetEmail.$valid ||
																																							 !contactForm.contactDetMessage.$valid ">
									<span class="glyphicon glyphicon-envelope"></span> &nbsp; Contact us </button>
									<button name="reset" type="button"  style="box-shadow: 3px 3px 10px #252728 ;" ng-click="contactDetEmail = '';  contactDetPhone='';
									   contactDetName =''; contactDetMessage=''; contactForm.$setPristine();"
										class="contactUs resetBtn responsive" value="Reset form" >
										<span class="glyphicon glyphicon-refresh"></span> &nbsp; Reset form </button>
      </form>
    </div>`,

			controller: function($scope, $http, $log){
					var $contactFormCtrl = this;

					$scope.submitEnquiry = function(){

								var dlg = $("<div></div>").dialog({
									hide: 'fade',
									maxWidth: 600,
									modal: true,
									show: 'fade',
									title: 'Sending enquiry',
									width: ( ('__proto__' in {}) ? '600' : 600 )
								});

								$(dlg).parent().find('button').remove();
								$(dlg).html("<div class='ajax-loader-2 help-inline pull-right'></div><div><p>Your enquiry is being sent. </p></div>");
								$(dlg).dialog("open");

								$http({
										url : 'sendEnquiry',
										method : 'GET',
										headers: {'Content-Type' : 'application/json'},
										dataType: "JSON",
										params: {
											name: $scope.contactDetName,
											email: $scope.contactDetEmail,
											number: $scope.contactDetPhone,
											message: $scope.contactDetMessage
										}
							   }).then(function successCallback(successErrorCode) {
											$log.info('successErrorCode : '+successErrorCode[0]);
											$(dlg).dialog("close");

											var confirmDialog = $("<div></div>").dialog({
													hide: 'fade',
													maxWidth: 300,
													modal: true,
													show: 'fade',
													open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
														buttons: [
															{
																'class': 'btn btn-primary',
																click: function(e) {
																	$(".resetBtn").click();
																	$(this).dialog("close");
																},
																text: 'OK'
															}

														],

													title: 'Your enquiry has been sent',
													width: ( 300 )
												});

												$(confirmDialog).dialog("open");
												$(confirmDialog).html("<div style='align:center'><p>Thank you for your enquiry. We will be in touch shortly </p></div>");


								}, function errorCallback(response) {
											$log.error("we errored here : "+data[0]);

											$(dlg).dialog("close");

											var confirmDialog = $("<div></div>").dialog({
													hide: 'fade',
													maxWidth: 300,
													modal: true,
													show: 'fade',
													open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
														buttons: [
															{
																'class': 'btn btn-primary',
																click: function(e) {
																	$(".resetBtn").click();
																	$(this).dialog("close");
																},
																text: 'OK'
															}

														],

													title: 'Enquiry could not be sent',
													width: ( 300 )
												});

												$(confirmDialog).dialog("open");
												$(confirmDialog).html("<div style='align:center'><p>Your enquiry could not be sent. Please check your email address and send again.</p></div>");
					  	  });

								return false;
                  }
			 }

		})
})(angular);
