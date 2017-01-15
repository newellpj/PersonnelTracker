


(function(angular) {

		angular.module('landingApp', ['aboutUsTabPage']).component('employmeeProfile', {
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

									<div class="profileHeading responsive"><h3>See profile and performance summaries
									   of your employees based on general or faceted search results</h3></div>
										<ul id="suggestedSlider" class="bxslider responsive">
											<li><img src="./presentationResources/images/profilePics/charles.jpeg"   /><p style="margin-top:3%;"><b>Name:</b> Charles Jones </p><p><b>Title:</b>  Senior Software Engineer </p>
											<p><b>Position:</b> Senior Software Engineer Billing Division</p><p><b>Location:</b> 25 Bond Street, Sydney</p></li>
											<li><img src="./presentationResources/images/profilePics/bill.jpeg" /><p style="margin-top:3%;"><b>Name:</b> Bill Staniforth </p><p><b>Title:</b>
											 Chief Executive Officer </p>
											<p><b>Position:</b> Chief Executive Officer </p><p><b>Location:</b> 25 Bond Street, Sydney</p></li>
											<li><img src="./presentationResources/images/profilePics/alicia.jpeg" />
											<p style="margin-top:3%;"><b>Name:</b> Alicia Dunstan </p><p><b>Title:</b>
											 Deputy Chief Financial Officer </p>
											<p><b>Position:</b> Deputy Chief Financial Officer </p><p><b>Location:</b> 25 Bond Street, Sydney</p></li></li>
											<li><img src="./presentationResources/images/profilePics/chloe.jpeg" />
											<p style="margin-top:3%;"><b>Name:</b> Chloe Williams </p><p><b>Title:</b>
											    Senior Financial Officer </p> 	<p><b>Position:</b> 	Finance & Billing </p><p><b>Location:</b> 25 Bond Street, Sydney</p></li>
													<li><img src="./presentationResources/images/profilePics/Kapil.jpeg"  />
												<p style="margin-top:3%;"><b>Name:</b> Kapil Singh </p><p><b>Title:</b>
													Logistical Officer </p>	<p><b>Position:</b> Logistics </p><p><b>Location:</b> 64 Flinders Street, Melbourne</p></li>

												<li><img src="./presentationResources/images/profilePics/Alexis.jpeg"  />
												<p style="margin-top:3%;"><b>Name:</b> Alexis Jonas </p><p><b>Title:</b>
													Senior Financial Officer </p>	<p><b>Position:</b> 	Finance & Billing </p><p><b>Location:</b> 64 Flinders Street, Melbourne</p></li>

													<li><img src="./presentationResources/images/profilePics/russell.jpeg" />
													<p style="margin-top:3%;"><b>Name:</b> Russell Palmer </p><p><b>Title:</b>
														Software Engineer </p> 	<p><b>Position:</b> 	Finance & Billing </p><p><b>Location:</b> 64 Flinders Street, Melbourne</p></li>


															<li><img src="./presentationResources/images/profilePics/sally.jpeg"  />	<p style="margin-top:3%;"><b>Name:</b> Sally Robertson </p><p><b>Title:</b>
																	Software Team Leader </p>	<p><b>Position:</b> Software Development </p><p><b>Location:</b> 64 Flinders Street, Melbourne</p></li>

															<li><img src="./presentationResources/images/profilePics/simon.jpeg"  />	<p style="margin-top:3%;"><b>Name:</b> Simon Hetherington </p><p><b>Title:</b>
																	Chief Financial Officer </p>	<p><b>Position:</b> Finance & Billing </p><p><b>Location:</b> 64 Flinders Street, Melbourne</p></li>

																	<li><img src="./presentationResources/images/profilePics/Amanda.jpeg"  />	<p style="margin-top:3%;"><b>Name:</b> Amanda Campbell</p><p><b>Title:</b>
																								Payroll Officer </p>	<p><b>Position:</b>Finance & Billing </p><p><b>Location:</b> 64 Flinders Street, Melbourne</p></li>

												<li><img src="./presentationResources/images/profilePics/robert.jpeg" /><p style="margin-top:3%;"><b>Name:</b> Robert Carter</p><p><b>Title:</b>
														Senior Software Engineer </p>	<p><b>Position:</b>Software Development </p><p><b>Location:</b> 64 Flinders Street, Melbourne</p></li>

														<li><img src="./presentationResources/images/profilePics/jessica.jpeg" /><p style="margin-top:3%;"><b>Name:</b> Jessica Collins</p><p><b>Title:</b>
							Senior Financial Officer </p>	<p><b>Position:</b>Finance & Billing </p><p><b>Location:</b> 64 Flinders Street, Melbourne</p></li>
										</ul>
									</div>


						</div>
									`,
		  controller: function($scope, $http, $log){


		  }
		})

})(angular);

function openTabAboutUs(evt, tabName) {

			$('._stickFigureElement').toggleClass('stickFigureElement-active');
     openTab(evt, tabName);
}


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
