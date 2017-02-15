
package co.srsp.controller;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.srsp.config.ConfigHandler;
import co.srsp.constants.SessionConstants;
import co.srsp.hibernate.orm.CompanyPositions;
import co.srsp.hibernate.orm.Employee;
import co.srsp.hibernate.orm.EmployeeSkillset;
import co.srsp.hibernate.orm.OrgDepartment;
import co.srsp.rss.model.ValueNamePair;
import co.srsp.service.EmployeeDataService;
import co.srsp.viewmodel.EmployeeFacetWrapperModel;
import co.srsp.viewmodel.EmployeeModel;

@Controller
public class SearchingPageController {

	private final static Logger log = Logger.getLogger(SearchingPageController.class); 
	
	@RequestMapping(value = { "/trackerHome"}, method = RequestMethod.GET)
	public ModelAndView welcomePage() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info("we getting in here user logged in here - "+auth.getName());
		ModelAndView model = new ModelAndView();		 
		model.setViewName("trackerHome");
		return model;
	}
	
	@RequestMapping(value = { "/reviewsAddBook"}, method = RequestMethod.GET)
	public ModelAndView addBookPage() {
		log.info("we getting in here reviewsAddBook?");
		ModelAndView model = new ModelAndView();		
		model.setViewName("reviewsAddBook");
		return model;
	}
	
	@RequestMapping(value = { "/searchNASA"}, method = RequestMethod.GET)
	public ModelAndView addSearchAllPage() {
		log.info("we getting in here searchNASA page?");
		ModelAndView model = new ModelAndView();		
		model.setViewName("searchNASA");
		return model;
	}

	@RequestMapping(value = { "/reviewsSearchBook"}, method = RequestMethod.GET)
	public ModelAndView addSearchPage() {
		log.info("we getting in here reviewsSearchBook?");
		ModelAndView model = new ModelAndView();		
		model.setViewName("reviewsSearchBook");
		return model;
	}
	

	
	

	@RequestMapping(value = { "/sendEnquiry"}, method = RequestMethod.GET)
	public @ResponseBody String[] sendEnquiry(HttpServletRequest request, HttpServletResponse response){
		
//		String name = request.getParameter(SessionConstants.NAME);
//		String email = request.getParameter(SessionConstants.EMAIL);
//		String phone = request.getParameter(SessionConstants.PHONE);
//		String message = request.getParameter(SessionConstants.MESSAGE);
//		
		sendMail(request);
		
		String[] status = new String[1];
		status[0] = "success";
		
		return status;

	}
	
	private void sendMail(HttpServletRequest request){
		  
		String name = request.getParameter(SessionConstants.NAME);
		String sendersEmail = request.getParameter(SessionConstants.EMAIL);
		String phone = request.getParameter(SessionConstants.PHONE);
		String senderMessage = request.getParameter(SessionConstants.MESSAGE);

		log.info("senders email : "+sendersEmail);
	      // Sender's email ID needs to be mentioned
	      String from =  sendersEmail;

	      // Assuming you are sending email from localhost
	      String host =  ConfigHandler.getInstance().readApplicationProperty("mailHost");

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);
	      //properties.setProperty("mail.transport.protocol", "aws");
	      //properties.setProperty("mail.aws.user", credentials.getAWSAccessKeyId());
	      //properties.setProperty("mail.aws.password", credentials.getAWSSecretKey());
	      // Get the default Session object.
	      properties.put("mail.smtp.port", "587");
	      properties.put("mail.smtp.auth", "true");
	      properties.put("mail.smtp.starttls.enable", "true");
	      Session session = Session.getDefaultInstance(properties);
	      Transport transport = null;
	      MimeMessage message = null;
	      try {
	         // Create a default MimeMessage object.
	         message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         
	         String ourEmail = ConfigHandler.getInstance().readApplicationProperty("ourEmail");
	         
	         log.info("ourEmail : "+ourEmail);
	         
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(ourEmail));

	         // Set Subject: header field
	         message.setSubject("Enquiry for services from "+name);

	         // Now set the actual message
	         message.setText(senderMessage);
             
	         // Send message

	         transport = session.getTransport("smtp");
	         transport.connect(host, "pauljamesnewell@gmail.com", "5803871x");
	         transport.sendMessage(message, message.getAllRecipients());
	         transport.close();
	       
	         System.out.println("Sent message successfully....");
	         
	         String thankYouMsg = ConfigHandler.getInstance().readApplicationProperty("thankYouMessage");
	         
	         String logoPath = ConfigHandler.getInstance().readApplicationProperty("urlRootProd")+"/presentationResources/images/";
	         
	         URL url = new URL(logoPath);
	         log.info("logoPath 1 :::: "+logoPath);        
	         
	         File file = new File(logoPath+"emailSignScion.png");
	         
	         System.getProperty("user dir "+System.getProperty("user.dir"));
	         
	         log.info("thankYouMsg :::: "+thankYouMsg);
	         log.info("image exists? : "+file.exists());
	         
	         if(!file.exists()){
	        	 logoPath =  ConfigHandler.getInstance().readApplicationProperty("urlRootDev")+"/presentationResources/images/";
	         }
	         
	         thankYouMsg = thankYouMsg.replace(":path:", logoPath);
	         thankYouMsg = thankYouMsg.replace(":name:", name);
	         
	         log.info("image abs path? : "+file.getAbsolutePath());
	         message = new MimeMessage(session);
	         message.setFrom(new InternetAddress("info@scionsolutionsgroup.com"));
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendersEmail));
	        // message.setText(thankYouMsg);
	         message.setContent(thankYouMsg, "text/html");
	         message.setSubject("Thank you for your enquiry");      
	         transport.connect(host, "pauljamesnewell@gmail.com", "5803871x");
	         transport.sendMessage(message, message.getAllRecipients());
	         transport.close();
	         
	      }catch (Exception mex) {
	    	  log.error(" messed it up : "+mex.getMessage());
	         mex.printStackTrace();
	         try{
	           transport.connect(host, "pauljamesnewell", "5803871x");
	           transport.sendMessage(message, message.getAllRecipients());
	         }catch(Exception e){
	        	 log.error(" messed it up 2 : "+e.getMessage());
		         e.printStackTrace();
	        	 
	         }
	      }
	}
	
	
	
	@RequestMapping(value = { "/addBookReview"}, method = RequestMethod.GET)
	public @ResponseBody String[] addBookReview(HttpServletRequest request, HttpServletResponse response){
		
	    String[] strResponse = new String[1];
	    strResponse[0] = "Successfully added book review";
		//modelAndView.setViewName("reviews");
	//	modelAndView.addObject("bookReviewsModel", bookReviewsModel);
		return strResponse;
	}
	

	

	
	
	@RequestMapping(value = { "/resetSearch"}, method = RequestMethod.GET)
	public @ResponseBody EmployeeModel resetSearch(HttpServletRequest request, HttpServletResponse response){
		
		if(request.getSession() == null){
			return null;
		}
		
		request.getSession().removeAttribute("bookAuthorFound");
		request.getSession().removeAttribute("bookTitleFound");
		request.getSession().removeAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET);
		//request.getSession().removeAttribute(SessionConstants.EMPLOYEES_SEARCH_CRITERIA);
		request.getSession().removeAttribute(SessionConstants.EMPLOYEES_SEARCH_CRITERIA);
		request.getSession().removeAttribute(SessionConstants.PUBLISHER_TEXT);
		
		return new EmployeeModel();
	}
	
	private void resetSearchSessionAttributes(HttpServletRequest request){
		request.getSession().setAttribute(SessionConstants.PUBLISHER_TEXT, "");
		request.getSession().setAttribute(SessionConstants.TITLE_TEXT, "");
		request.getSession().setAttribute(SessionConstants.AUTHOR_TEXT, "");
		request.getSession().setAttribute(SessionConstants.GENRE_SELECT, "");
		request.getSession().setAttribute(SessionConstants.CATEGORY_SELECT, "");
		request.getSession().setAttribute(SessionConstants.LANGUAGE_SELECT, "");
		//request.getSession().setAttribute(SessionConstants.SEARCH_TYPE_TAG, "");
		request.getSession().setAttribute(SessionConstants.EMPLOYEES_SEARCH_CRITERIA, null);
		
		
		request.getSession().setAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET, 0);
		request.getSession().setAttribute("solrSearchListReturned", null);
		request.getSession().setAttribute("solrPaginationOffset", 0);
		request.getSession().setAttribute("solrTitleQuery", "");
		request.getSession().setAttribute("solrAuthorQuery", "");
		request.getSession().setAttribute("solrKeywordsQuery", "");
	}
	
	

	@RequestMapping(value = { "/partialSearchForName"}, method = RequestMethod.GET)
	public @ResponseBody EmployeeModel[] partialSearchForName(HttpServletRequest request, HttpServletResponse response){
		
		HttpSession session  = request.getSession();
		
		if(session == null || session.isNew()){
			log.info("$$$$$$$$$$$$$$$ESSION NULL %%%%%%%%%%%%%%%%%%%%%");
			return null;
		}
		
		log.info("request contain PARTIAL_TEXT ? : "+request.getParameter(SessionConstants.PARTIAL_TEXT));
		String partialText = request.getParameter(SessionConstants.PARTIAL_TEXT);	
		String[] keyValuePair = partialText.split("-");
		HashMap<String, String> searchCriteria = new HashMap<String, String>();
		searchCriteria.put(keyValuePair[0], keyValuePair[1]);
		EmployeeDataService dataService = new EmployeeDataService();
		
		List<Employee> employeesFound = null;
		
		int pagintionValue = Integer.parseInt(ConfigHandler.getInstance().readApplicationProperty("paginationValue"));
		
		switch(keyValuePair[0]){
		
			case "surname":
				employeesFound = dataService.getEmployeesBySurname(keyValuePair[1], 0, pagintionValue);
				break;
			case "firstName":
				employeesFound = dataService.getEmployeesByFirstName(keyValuePair[1], 0, pagintionValue);
				break;
			case "givenNames":
				employeesFound = dataService.getEmployeesByGivenNames(keyValuePair[1], 0, pagintionValue);  
				break;
		}
				
		
//		Set<Employee> removedDuplicates = new HashSet<Employee>();	//A Set object cannot contain duplicate entries	
//		log.info("books found list "+employeesFound.size());
//		
//		for(Employee employee : employeesFound){
//			log.info("books found list in loop - title : "+employee.getEmployeeSurname());
//			removedDuplicates.add(employee);
//		}
		
		request.getSession().setAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET, 0 + pagintionValue);
		request.getSession().setAttribute(SessionConstants.EMPLOYEES_SEARCH_CRITERIA, searchCriteria);	
		//employeesFound.clear(); //remove all elements and add in the set with duplicates removed.
		
		//employeesFound.addAll(removedDuplicates);
		
		log.info("books found list 2222222222 "+employeesFound.size());
		
			
		return  buildEmployeeOnlyDataModel(request, employeesFound);
	}
	
	@RequestMapping(value = { "/updateSearchCriteriaAndPaginationOffset"}, method = RequestMethod.POST)
	public void updateSearchCriteriaAndPaginationOffset(HttpServletRequest request, HttpServletResponse response){	
//		String searchCriteriaUpdate = request.getParameter(SessionConstants.SEARCH_CRITERIA_UPDATE);
//		String currentNumberDisplayed = request.getParameter(SessionConstants.CURRENT_INSTANT_SEARCH_NUMBER_DISPLAYED);
//	
//		log.info("currentNumberDisplayed :::::::::::: "+currentNumberDisplayed);
//		
//		int numberReturned = Integer.parseInt(currentNumberDisplayed);
		
		//for example - if only 3 returned after users keyup instant search we tkae that 3 and minus the current pgaintation value which is currently 10
		//that gives us a value minus 7 which will have 10 added to it when any pagination occurs so the database query will start at record 3 and select maximum of 10 records from the offset of record 3 onwards
		
		//TODO = maybe not in need of the above scenario - possible lack of understanding of Database query offsets
		
	//	int offset = Integer.parseInt(ConfigHandler.getInstance().readApplicationProperty("paginationValue"));
		
        //offset less than current pagination number (which is 10 at date of 24/10/2016)
		
//		log.info("offset is : "+offset);
//		
//		request.getSession().setAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET, String.valueOf(offset));
//			
//		String[] searchPair = searchCriteriaUpdate.split("-");		
//	
//		HashMap<String, String> booksSearchCriteria = new HashMap<String, String>();
//		booksSearchCriteria.put(searchPair[0], searchPair[1]);
//		
//		log.info("searchPair[0] :::::::::::: "+searchPair[0]);
//		log.info("searchPair[1] :::::::::::: "+searchPair[1]);
//		
//		request.getSession().setAttribute(SessionConstants.BOOKS_SEARCH_CRITERIA , booksSearchCriteria);
//		
//		HashMap<String, String> criteria = (HashMap<String, String>)request.getSession().getAttribute(SessionConstants.BOOKS_SEARCH_CRITERIA);
//		
//		log.info("criteria being set in session "+criteria.size());
//		
//		
		
	}
	
	@RequestMapping(value = { "/searchForEmployee"}, method = RequestMethod.GET)
	public @ResponseBody EmployeeFacetWrapperModel searchEmployee(HttpServletRequest request, HttpServletResponse response){
			
		log.info("request get param : "+request.getParameter(SessionConstants.EMPLOYEE_SEARCH_STRING));
		
		int paginationValue = Integer.parseInt(ConfigHandler.getInstance().readApplicationProperty("paginationValue"));
		
		EmployeeDataService eds = new EmployeeDataService();

		eds.getEmployeeRecord(request.getParameter(SessionConstants.EMPLOYEE_SEARCH_STRING), 0, (0+paginationValue));
		
		EmployeeModel[] employeeModelArray = new EmployeeModel[0];
		
		
		//return employeeModelArray;
		
		if(request.getSession() == null){
			return null;
		}
		
		resetSearchSessionAttributes(request);
		log.info("request contain surnameText ? : "+request.getParameter(SessionConstants.EMPLOYEE_SURNAME));
		log.info("request contain firstNameText ? : "+request.getParameter(SessionConstants.EMPLOYEE_FIRST_NAME));
		log.info("request contain givenNamesText ? : "+request.getParameter(SessionConstants.EMPLOYEE_GIVEN_NAMES));
		
		log.info("request contain lang text ? : "+request.getParameter("langText"));
		
		
		String surnameText = request.getParameter(SessionConstants.EMPLOYEE_SURNAME);
		String firstNameText = request.getParameter(SessionConstants.EMPLOYEE_FIRST_NAME);
		String givenNamesText = request.getParameter(SessionConstants.EMPLOYEE_GIVEN_NAMES);
	
		
		HashMap<String, String> searchCriteria = new HashMap<String, String>();
		
		if(request.getParameter(SessionConstants.EMPLOYEE_DEPT) != null && !"".equals(request.getParameter(SessionConstants.EMPLOYEE_DEPT))){
			searchCriteria.put(SessionConstants.EMPLOYEE_DEPT, request.getParameter(SessionConstants.EMPLOYEE_DEPT));
			log.info("genreText to search on : "+request.getParameter(SessionConstants.EMPLOYEE_DEPT));
		}
		
		if(request.getParameter(SessionConstants.EMPLOYEE_POSITION) != null && !"".equals(request.getParameter(SessionConstants.EMPLOYEE_POSITION))){
			log.info("catText to search on : "+request.getParameter(SessionConstants.EMPLOYEE_POSITION));
			
			searchCriteria.put(SessionConstants.EMPLOYEE_POSITION, request.getParameter(SessionConstants.EMPLOYEE_POSITION));
		}
		
		if(request.getParameter(SessionConstants.EMPLOYEE_SKILLSET) != null && !"".equals(request.getParameter(SessionConstants.EMPLOYEE_SKILLSET))){
			log.info("lang text to search on : "+request.getParameter("langText"));
			searchCriteria.put(SessionConstants.EMPLOYEE_SKILLSET, request.getParameter(SessionConstants.EMPLOYEE_SKILLSET));
		}
		
		log.info("tags and value map size : "+searchCriteria.size());
		

		log.info("just before service instantiation !");
		
		EmployeeDataService dataService = new EmployeeDataService();
		
		List<EmployeeModel> list = new ArrayList<EmployeeModel>();
		log.info("just before test !");

		request.getSession().setAttribute(SessionConstants.EMPLOYEE_GIVEN_NAMES, givenNamesText);

		
		if(surnameText != null && !"".equals(surnameText)){
			
			if(searchCriteria == null){
				searchCriteria = new HashMap<String, String>(); 
			}
			
			searchCriteria.put(SessionConstants.EMPLOYEE_SURNAME, surnameText);
			log.info("in here111");
		
		}
		
		if(!"".equals(firstNameText) && firstNameText != null){
			
			if(searchCriteria == null){
				searchCriteria = new HashMap<String, String>(); 
			}
			
			searchCriteria.put(SessionConstants.EMPLOYEE_FIRST_NAME, firstNameText);
		}
		
		if(givenNamesText != null && !"".equals(givenNamesText)){
			
			if(searchCriteria == null){
				searchCriteria = new HashMap<String, String>(); 
			}
			searchCriteria.put(SessionConstants.EMPLOYEE_GIVEN_NAMES, givenNamesText);
			log.info("in here222");
		}
		
        EmployeeFacetWrapperModel wrapperModel = dataService.findEmployeesByAnyCriteriaLazyLoad(searchCriteria, 0, 1000);
		
		//SessionConstants.EMPLOYEE_MODEL_LIZT
		 //SessionConstants.FACET_MATCHED_LIZT
        //TODO send back facet info and or store in session 
		
		int numRecordsToPaginate = Integer.parseInt(ConfigHandler.getInstance().readApplicationProperty("paginationValue"));
		
		list = wrapperModel.getEmployeeModels();
		
		if(list.size() > numRecordsToPaginate){
			
			request.getSession().setAttribute(SessionConstants.EMPLOYEE_FULL_PROFILE_LIST, list);
		}else{
			request.getSession().setAttribute(SessionConstants.EMPLOYEE_FULL_PROFILE_LIST, null); //check for null when paginating and return nothing if null
		}
		
		if(list.size() < numRecordsToPaginate){
			numRecordsToPaginate = list.size();
		}
		

		
		request.getSession().setAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET, 0+numRecordsToPaginate);
		
		log.info("employeeModelArray array size returned :: "+employeeModelArray.length);
		
		//modelView.setViewName("reviewsSearchBook");
		List<EmployeeModel> subList = list.subList(0, numRecordsToPaginate);
		wrapperModel.setEmployeeModels(subList);
		
		return wrapperModel;// buildEmployeeFullProfileDataModel(request, list);		
	}
	
	@RequestMapping(value = { "/getEmployeePerformanceDetails"}, method = RequestMethod.GET)
	public @ResponseBody EmployeeModel[] getEmployeePerformanceDetails(HttpServletRequest request, HttpServletResponse response){
		EmployeeDataService eds = new EmployeeDataService();
		
		///construct emp model here
		
		String empModelStr = request.getParameter("empData");
		
		log.info("empModelStr :::::::::::: "+empModelStr);
		
		EmployeeModel empModel = new EmployeeModel();
		
		ObjectMapper mapper = new ObjectMapper();
		
		try{
			empModel = mapper.readValue(empModelStr, EmployeeModel.class); //converting json data back into the java object
		}catch(Exception e){
			log.error(e.getMessage());
		}
		
		log.info("emp id : "+empModel.getIdemployee());
		log.info("emp surname : "+empModel.getEmployeeSurname());
		log.info("emp marital status : "+empModel.getEmployeeMaritalStatus());
		
		EmployeeModel[] array = new EmployeeModel[1];
		
		
		array[0] = eds.findEmployeePerformanceDetails(empModel);
		
		return array;
	}
	
	@RequestMapping(value = { "/getSkillsets"}, method = RequestMethod.GET)
	public @ResponseBody ValueNamePair[] getSkillsets(HttpServletRequest request, HttpServletResponse response){
		
		EmployeeDataService eds = new EmployeeDataService();
		List<EmployeeSkillset> list = eds.getAllSkillsets();
		log.info("list : "+list);
		log.info("list size :  "+list.size());
		ValueNamePair[] returnArray = new ValueNamePair[list.size()];
		
		int count = 0;
		
		ValueNamePair vnp = null;
		
		for(EmployeeSkillset skills : list){
			log.info("we in here : "+count);
			vnp = new ValueNamePair();
			vnp.setName(skills.getSkillsetName());
			vnp.setValue(skills.getSkillsetName());
			returnArray[count] = vnp;
			count++;
		}
		
		return returnArray;
	}
	
	@RequestMapping(value = { "/getOrgDepts"}, method = RequestMethod.GET)
	public @ResponseBody ValueNamePair[] getOrgDepts(HttpServletRequest request, HttpServletResponse response){
		
		EmployeeDataService eds = new EmployeeDataService();
		List<OrgDepartment> list = eds.getOrgDepts();
		log.info("list : "+list);
		log.info("list size :  "+list.size());
		ValueNamePair[] returnArray = new ValueNamePair[list.size()];
		
		int count = 0;
		
		ValueNamePair vnp = null;
		
		for(OrgDepartment depts : list){
			log.info("we in here : "+count);
			vnp = new ValueNamePair();
			vnp.setName(depts.getDeptName());
			vnp.setValue(depts.getDeptName());
			returnArray[count] = vnp;
			count++;
		}
		
		return returnArray;
	}
	
	@RequestMapping(value = { "/getCompanyPositions"}, method = RequestMethod.GET)
	public @ResponseBody ValueNamePair[] getCompanyPositions(HttpServletRequest request, HttpServletResponse response){
		
		EmployeeDataService eds = new EmployeeDataService();
		List<CompanyPositions> list = eds.getCompanyPositions();
		log.info("list : "+list);
		log.info("list size :  "+list.size());
		ValueNamePair[] returnArray = new ValueNamePair[list.size()];
		
		int count = 0;
		
		ValueNamePair vnp = null;
		
		for(CompanyPositions pos : list){
			log.info("we in here : "+count);
			vnp = new ValueNamePair();
			vnp.setName(pos.getPositionName());
			vnp.setValue(pos.getPositionName());
			returnArray[count] = vnp;
			count++;
		}
		
		return returnArray;
	}
	
	private EmployeeModel[] buildEmployeeFullProfileDataModel(HttpServletRequest request, List<Employee> empList){

		List<String> employeeStringViewList = new ArrayList<String>();
		
		log.info("empList : "+empList.size());
		request.getSession().setAttribute("currentPaginationOffset", 0);
		
			
		if(empList == null || empList.size() == 0){
			request.getSession().setAttribute("bookAuthorFound", "");
			request.getSession().setAttribute("bookTitleFound", "");
			request.getSession().setAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET, 0);
			log.info("no books found ");
			employeeStringViewList.add("No books found");
		}
		
		EmployeeModel[] employeeModelArray = new EmployeeModel[empList.size()];
		int count = 0;
		
		EmployeeModel model = null;
		
		for(Employee employee : empList){
		
			model = new EmployeeModel();
			model.setEmployeeSurname(employee.getEmployeeSurname());
			model.setEmployeeFirstName(employee.getEmployeeFirstName());			
			model.setEmployeeGivenNames(employee.getEmployeeGivenNames());
			model.setEmployeeAge(employee.getEmployeeAge());
			model.setEmployeeAddress(employee.getEmployeeAddress());
			model.setEmployeeGender(employee.getEmployeeGender());
			model.setEmployeeMaritalStatus(employee.getEmployeeMaritalStatus());
			model.setIdemployee(employee.getIdemployee());
			
			//EmployeeSkillsetDataModel skillsetsModel = new EmployeeSkillsetDataModel();
			
//			model.set(employee.getEmployeeFirstName());
//			model.setEmployeeFirstName(employee.getEmployeeFirstName());
//			model.setEmployeeFirstName(employee.getEmployeeFirstName());
//			model.setEmployeeFirstName(employee.getEmployeeFirstName());
//			model.setEmployeeFirstName(employee.getEmployeeFirstName());
			
			
	
			
		/*	try{
				//file system relative references are different from web application relative references 
				String fileURLPath = ConfigHandler.getInstance().readApplicationProperty("applicationImagesLocation")+model.getThumbnailLocation();
				log.info( System.getProperty("user.dir"));
				 
				File file = new File(fileURLPath);
				log.info("location for file is :::: "+fileURLPath);
				log.info("does file exist : "+file.exists());
				
				Image image = new ImageIcon(fileURLPath).getImage();
				
				int imgWidth = image.getWidth(null);
				int imgHeight = image.getHeight(null);
				
				log.info("imgWidth : "+imgWidth);
				log.info("imgHeight : "+imgHeight);
				
				if(imgWidth > imgHeight){
					double result = new Double(imgHeight)/ new Double(imgWidth);
					log.info("result : "+result);
					imgHeight = (int)(result * new Double(192));
					imgWidth = 192;
				}else if(imgWidth < imgHeight){
					double result = new Double(imgWidth)/ new Double(imgHeight);
					imgWidth = (int)(result * new Double(192));
					imgHeight = 192;
				}else{
					imgHeight = 192;
					imgWidth  = 192;
				}
				
				model.setImageHeight(String.valueOf(imgHeight));
				model.setImageWidth(String.valueOf(imgWidth));
				
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage());
			}
			*/
			employeeModelArray[count] = model;
			count++;
		}
		
		log.info("before testing array length");
		
		if(employeeModelArray.length == 0){
			log.info("after testing array length");
			employeeModelArray = new EmployeeModel[1];
			EmployeeModel model_ = new EmployeeModel();
			log.info("after testing array length 333");
			model_.setEmployeeSurname("No Employees Found!!");
			log.info("after testing array length 444");
			employeeModelArray[0] = model_;
		}
		
		return employeeModelArray;
		
	}
	
	
	private EmployeeModel[] buildEmployeeOnlyDataModel(HttpServletRequest request, List<Employee> empList){

		List<String> employeeStringViewList = new ArrayList<String>();
		
		log.info("empList : "+empList.size());
		request.getSession().setAttribute("currentPaginationOffset", 0);
		
			
		if(empList == null || empList.size() == 0){
			request.getSession().setAttribute("bookAuthorFound", "");
			request.getSession().setAttribute("bookTitleFound", "");
			request.getSession().setAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET, 0);
			log.info("no books found ");
			employeeStringViewList.add("No books found");
		}
		
		EmployeeModel[] employeeModelArray = new EmployeeModel[empList.size()];
		int count = 0;
		
		EmployeeModel model = null;
		
		for(Employee employee : empList){
		
			model = new EmployeeModel();
			model.setEmployeeSurname(employee.getEmployeeSurname());
			model.setEmployeeFirstName(employee.getEmployeeFirstName());			
			model.setEmployeeGivenNames(employee.getEmployeeGivenNames());
			model.setEmployeeAge(employee.getEmployeeAge());
			model.setEmployeeAddress(employee.getEmployeeAddress());
			model.setEmployeeGender(employee.getEmployeeGender());
			model.setEmployeeMaritalStatus(employee.getEmployeeMaritalStatus());
			model.setIdemployee(employee.getIdemployee());
			
			//EmployeeSkillsetDataModel skillsetsModel = new EmployeeSkillsetDataModel();
			
//			model.set(employee.getEmployeeFirstName());
//			model.setEmployeeFirstName(employee.getEmployeeFirstName());
//			model.setEmployeeFirstName(employee.getEmployeeFirstName());
//			model.setEmployeeFirstName(employee.getEmployeeFirstName());
//			model.setEmployeeFirstName(employee.getEmployeeFirstName());
			
			

		/*	try{
				//file system relative references are different from web application relative references 
				String fileURLPath = ConfigHandler.getInstance().readApplicationProperty("applicationImagesLocation")+model.getThumbnailLocation();
				log.info( System.getProperty("user.dir"));
				 
				File file = new File(fileURLPath);
				log.info("location for file is :::: "+fileURLPath);
				log.info("does file exist : "+file.exists());
				
				Image image = new ImageIcon(fileURLPath).getImage();
				
				int imgWidth = image.getWidth(null);
				int imgHeight = image.getHeight(null);
				
				log.info("imgWidth : "+imgWidth);
				log.info("imgHeight : "+imgHeight);
				
				if(imgWidth > imgHeight){
					double result = new Double(imgHeight)/ new Double(imgWidth);
					log.info("result : "+result);
					imgHeight = (int)(result * new Double(192));
					imgWidth = 192;
				}else if(imgWidth < imgHeight){
					double result = new Double(imgWidth)/ new Double(imgHeight);
					imgWidth = (int)(result * new Double(192));
					imgHeight = 192;
				}else{
					imgHeight = 192;
					imgWidth  = 192;
				}
				
				model.setImageHeight(String.valueOf(imgHeight));
				model.setImageWidth(String.valueOf(imgWidth));
				
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage());
			}
			*/
			employeeModelArray[count] = model;
			count++;
		}
		
		log.info("before testing array length");
		
		if(employeeModelArray.length == 0){
			log.info("after testing array length");
			employeeModelArray = new EmployeeModel[1];
			EmployeeModel model_ = new EmployeeModel();
			log.info("after testing array length 333");
			model_.setEmployeeSurname("No Employees Found!!");
			log.info("after testing array length 444");
			employeeModelArray[0] = model_;
		}
		
		return employeeModelArray;
		
	}
	
	public static void main(String args[]){
		
		try{
			Image image = new ImageIcon("C:/Tomcat_8/webapps/iFindit4U/presentationResources/images/plague.jpg").getImage();
			
			int imgWidth = image.getWidth(null);
			int imgHeight = image.getHeight(null);
			
			System.out.println("imgWidth BEFORE : "+imgWidth);
			System.out.println("imgHeight BEFORE : "+imgHeight);
			
			if(imgWidth > imgHeight){
				double result = new Double(imgHeight)/ new Double(imgWidth);
				log.info("result : "+result);
				imgHeight = (int)(result * new Double(192));
				imgWidth = 192;
			}else if(imgWidth < imgHeight){
				double result = new Double(imgWidth)/ new Double(imgHeight);
				imgWidth = (int)(result * new Double(192));
				imgHeight = 192;
			}else{
				imgHeight = 192;
				imgWidth  = 192;
			}
			
			System.out.println("imgWidth AFTER : "+imgWidth);
			System.out.println("imgHeight AFTER : "+imgHeight);
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}


}
