
package co.srsp.controller;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import co.srsp.config.ConfigHandler;
import co.srsp.constants.SessionConstants;
import co.srsp.hibernate.orm.Employee;
import co.srsp.markup.handlers.HTMLHelper;
import co.srsp.service.SolrSearchService;
import co.srsp.solr.SolrSearchData;
import co.srsp.viewmodel.EmployeeModel;
import co.srsp.viewmodel.EmployeeSkillsetDataModel;
import co.srsp.viewmodel.HTMLModel;
import co.srsp.viewmodel.HTMLModelSkillsets;


@Controller
@EnableWebMvc
public class PaginationController {

	private final static Logger log = Logger.getLogger(PaginationController.class); 
	
	@RequestMapping(value = { "/retrieveNextReviewsSegment"}, method = RequestMethod.GET)
	public ModelAndView retrieveNextReviewsSegment(HttpServletRequest request, HttpServletResponse response) {

		return null;
//		
//		if(request.getSession() == null){
//			return null;
//		}
//		
//		log.info("we getting in here retrieveNextSegment?");
//		
//		log.info("bookTitleFound retrieveNextSegment : "+request.getSession().getAttribute("bookTitleFound")); 
//		log.info("bookAuthorFound  retrieveNextSegment : "+request.getSession().getAttribute("bookAuthorFound")); 
//		
//		BookReviewsModel bookReviewsModel = new BookReviewsModel();
//		bookReviewsModel.setBookTitleReview(request.getSession().getAttribute("bookTitleFound").toString());
//		bookReviewsModel.setBookAuthorReview(request.getSession().getAttribute("bookAuthorFound").toString());
//		
//		BooksAndReviewsService booksService = new BooksAndReviewsService();
//		
//		String currentOffsetInSession = request.getSession().getAttribute("currentPaginationOffset").toString();
//		
//		log.info("currentOffset : "+currentOffsetInSession);
//		
//		int latestOffset = Integer.parseInt(currentOffsetInSession)+20;
//		
//		HashMap<Books, List<BookReviews>> bookMap = booksService.searchBookReviewsByTitleAndAuthor(request.getSession().getAttribute("bookTitleFound").toString(), 
//				request.getSession().getAttribute("bookAuthorFound").toString(), latestOffset, 20);
//
//		request.getSession().setAttribute("currentPaginationOffset", latestOffset);
//		
//		
//		ArrayList<String> list = new ArrayList<String>();
//		
//		for(Books book : bookMap.keySet()){	
//			bookMap.get(book);
//			
//			for(BookReviews bookRev : bookMap.get(book)){
//				
//				log.info("bookRev.getStarRating() : "+bookRev.getStarRating());
//				
//				String starRating = "0";
//				
//				if(bookRev.getStarRating() != null){
//					starRating = String.valueOf(bookRev.getStarRating());
//				}
//			
//				HTMLModel htmlModel = new HTMLModel();
//				htmlModel.setstarRating(starRating);
//				htmlModel.setreviewersUserName(bookRev.getReviewersUsername());
//				htmlModel.setreviewerText(bookRev.getReviewText());
//				
//				HTMLHelper htmlHelper = new HTMLHelper();
//				String reviewsFormattedHTML = htmlHelper.formatReviewersHTML(htmlModel);
//		
//				list.add(reviewsFormattedHTML);
//				
//				
//				//list.add(bookRev.getReviewText()+"<b> - reviewed by -  "+bookRev.getReviewersUsername()+"</b>");
//			}
//		}
//		
//		log.info("size of reviews list returned : "+list.size());
//		ModelAndView model = new ModelAndView();	
//		//model.addObject("bookReviewsModel", bookReviewsModel);
//		model.addObject("reviewLists2", list);
//		model.setViewName("reviewsPaginationPage"); //reviewsPaginationPage
//		return model;
	}
	
	
	@RequestMapping(value = { "/retrieveNextPaginatedResults"}, method = RequestMethod.GET)
	public ModelAndView retrieveNextPaginatedResults(HttpServletRequest request, HttpServletResponse response) {

		if(request.getSession() == null){
			return null;
		}
		
		log.info("we getting in here retrieveNextSegment?");
	
		List<EmployeeModel> list = (List<EmployeeModel>)request.getSession().getAttribute(SessionConstants.EMPLOYEE_FULL_PROFILE_LIST);
		int currentPaginationOffset = Integer.parseInt(request.getSession().getAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET).toString());
		
		int appPaginationValue = Integer.parseInt(ConfigHandler.getInstance().readApplicationProperty("paginationValue"));
		
		EmployeeModel[] employeeModelArray = null;
		
		int count = 0;
		
		int breakValue = -1;
		
		if(list.size() <= currentPaginationOffset + appPaginationValue){ //if we are in the last segment of the list we don't want an array index out of bounds exception
			list = list.subList(currentPaginationOffset, list.size()-1);
			employeeModelArray = new EmployeeModel[list.size() - currentPaginationOffset];
			breakValue = list.size() - currentPaginationOffset;
		}else{
			list = list.subList(currentPaginationOffset, currentPaginationOffset + appPaginationValue);
			employeeModelArray = new EmployeeModel[appPaginationValue];
			breakValue = appPaginationValue;
		}
		
		
		for(EmployeeModel model : list){
			employeeModelArray[count] = model;
			count++;
			if(count >= breakValue) break;
		}
		
		//request.getSession().setAttribute(SessionConstants.EMPLOYEE_FULL_PROFILE_LIST, list); //this would be setting the sublist which we don't wish to do
		request.getSession().setAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET,currentPaginationOffset + appPaginationValue);
		
		
	//	log.info("books List 2 size ::: "+booksLists2.size());
		
		ModelAndView model = new ModelAndView();	
		//model.addObject("bookReviewsModel", bookReviewsModel);
		
		List<String> booksLists2 = new ArrayList<String>();
		
		if(list != null){
			for(EmployeeModel empModel : list){	
				booksLists2.add(formattedSearchListItem(empModel));
			}
		}
		

		if(list != null){
			model.addObject("booksLists2", booksLists2);
		}else{
			model.addObject("booksLists2", new ArrayList<String>());
		}
			
	
		model.setViewName("searchPaginationPage"); //reviewsPaginationPage
		return model;
		
		/*<div class="add-reviews-box">
		<div id="reviews" class="reviews">
		<ul id="bookRevList2" class="bookRevList2" >				
					
					<c:if test="${not empty booksLists2}">
							<c:forEach var="listValue2" items="${booksLists2}">
								<div class="searchSegment"> ${listValue2} </div>
							</c:forEach>
							
						
			</ul>
					 <div class="next"><a href="retrieveNextSearchSegment">next</a> </div>
		*/
		//return null; 
	}
	
	private String formattedSearchListItem(EmployeeModel employeeModel){

		
		HTMLModel htmlModel = new HTMLModel();
		htmlModel.setemployeeAge(employeeModel.getEmployeeAge());
		htmlModel.setemployeeSurname(employeeModel.getEmployeeSurname());
		htmlModel.setemployeeFirstName(employeeModel.getEmployeeFirstName());
		htmlModel.setemployeeGivenNames(employeeModel.getEmployeeGivenNames());
		htmlModel.setemployeeMaritalStatus(employeeModel.getEmployeeMaritalStatus());
		htmlModel.setemployeeGender(employeeModel.getEmployeeGender());
		htmlModel.setimageHeight(employeeModel.getImageHeight());
		htmlModel.setimageWidth(employeeModel.getImageWidth());
		htmlModel.setprofilePicURL(employeeModel.getProfilePicURL());
		htmlModel.setdepartmentName(employeeModel.getEmpSkillsetsDataModel().get(0).getDepartmentName());
		htmlModel.setcurrentPosition(employeeModel.getEmpSkillsetsDataModel().get(0).getCurrentPostionName());
		
		HTMLModelSkillsets htmlModelSkillsets = null;
		
		List<HTMLModelSkillsets> skillsets = new ArrayList<HTMLModelSkillsets>();
		
		for(EmployeeSkillsetDataModel model : employeeModel.getEmpSkillsetsDataModel()){
			htmlModelSkillsets = new HTMLModelSkillsets();
			htmlModelSkillsets.setskillSetProficiency(model.getSkillSetProficiency().toString());
			htmlModelSkillsets.setskillsetName(model.getSkillsetName());
			htmlModelSkillsets.setskillsetYearsExperience(model.getSkillsetYearsExperience().toString());
			htmlModelSkillsets.setskillsetToPositionRelevance(model.getSkillsetToPositionRelevance().toString());
			skillsets.add(htmlModelSkillsets);
		}
		
		htmlModel.setskillsetsList(skillsets);
		
		
  //profilePicURL,employeeFirstName,employeeGivenNames,employeeSurname,employeeAge,
		//employeeGender,employeeMaritalStatus,departmentName,skillsetName,skillSetProficiency,skillsetToPositionRelevance,skillsetYearsExperience
		
		HTMLHelper helper = new HTMLHelper();
		String formattedMarkup = helper.formatSearchHTML(htmlModel);
		
				
		return formattedMarkup;

	}
	
	private String getTrueThumbnailLocation(Employee employee){
		return null;
				//(employee.getThumbnailLocation() != null && employee.getThumbnailLocation().contains("http")) ? 
				//employee.getThumbnailLocation() : "./presentationResources/images/"+employee.getThumbnailLocation();
	
	}
	
	private HashMap getImageDimensions(String imageLocation, Employee employee){
		
//		HashMap<String, String> imageDimensionsMap = new HashMap<String, String>();
//		
//		try{
//			
//			String fileURLPath = (imageLocation.toLowerCase().contains("http")) ? imageLocation :
//						"../webapps/iFindit4U/presentationResources/images/"+employee.getThumbnailLocation();
//			
//			log.info( System.getProperty("user.dir"));
//			 
//			File file = new File(fileURLPath);
//			log.info("location for file is :::: "+fileURLPath);
//			log.info("does file exist : "+file.exists());
//			
//			Image image = new ImageIcon(fileURLPath).getImage();
//			
//			int imgWidth = image.getWidth(null);
//			int imgHeight = image.getHeight(null);
//			
//			log.info("imgWidth : "+imgWidth);
//			log.info("imgHeight : "+imgHeight);
//			
//			if(imgWidth > imgHeight){
//				double result = new Double(imgHeight)/ new Double(imgWidth);
//				log.info("result : "+result);
//				imgHeight = (int)(result * new Double(120));
//				imgWidth = 120;
//			}else if(imgWidth < imgHeight){
//				double result = new Double(imgWidth)/ new Double(imgHeight);
//				imgWidth = (int)(result * new Double(120));
//				imgHeight = 120;
//			}else{
//				imgHeight = 120;
//				imgWidth  = 120;
//			}
//			
//			imageDimensionsMap.put("imageWidth", String.valueOf(imgWidth));
//			imageDimensionsMap.put("imageHeight", String.valueOf(imgHeight));
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			log.error(e.getMessage());
//		}
		
		return null;//imageDimensionsMap;
	}
	
	
}

