
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
            for(var i = 0; i < data.length; i++){

              //$log.info("first book in array : "+$('.bookRevList').html());

              //$('.bookRevList').append("<div class='searchSegment'>");
                console.log(data[i]);
                var formattedContent = "<div class='searchSegment'>"+formatSearchContent(data[i])+"</div>"

                $('.bookRevList').append(formattedContent);

            //	$('.bookRevList').append("</div>");
                      $('.ajax-loader-2').remove();
            }

            if(data == undefined || data == null || data.length < 1){

              $('.ajax-loader-2').remove();
          }else{
            detachScroll();
            //$(".search").append("<div class='next'><a href='thatone'>"+""+"</a> </div>");

            /*$('.resultsSection').jscroll({
              loadingHtml: "<center><div class='ajax-loader-2'> </div></center>",
              callback: paginateHere()
            }); */
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


function attachScroll(){
   $( window ).scroll(function() {


      //console.log($(window).scrollTop()+' R: '+$(window).height());
      //console.log(getDocHeight()+": "+document.body.scrollHeight);
      if($(window).scrollTop() + $(window).height() >= getDocHeight()) {
            // console.log("bottom bitch : "+$('.ajax-loader-2').html());

            if($('.ajax-loader-2').html() == undefined || $('.ajax-loader-2').html() == ''){

                  $('.resultsSection').append("<center><div class='ajax-loader-2'> </div></center>");
                  paginateHere();
            }
        }



   });

 }

 function detachScroll(){
      $( window ).scroll(function(){

      });
 }
