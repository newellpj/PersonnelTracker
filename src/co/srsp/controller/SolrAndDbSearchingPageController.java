
package co.srsp.controller;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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

import co.srsp.config.ConfigHandler;
import co.srsp.constants.SessionConstants;
import co.srsp.hibernate.orm.Employee;
import co.srsp.hibernate.orm.EmployeeSkillset;
import co.srsp.rss.model.ValueNamePair;
import co.srsp.service.EmployeeDataService;
import co.srsp.solr.SolrSearchData;
import co.srsp.viewmodel.EmployeeModel;

@Controller
public class SolrAndDbSearchingPageController {

	private final static Logger log = Logger.getLogger(SolrAndDbSearchingPageController.class); 
	
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
	
	@RequestMapping(value = { "/reviewsSearchDocs"}, method = RequestMethod.GET)
	public ModelAndView addDocsSearchPage() {
		log.info("we getting in here addDocsSearchPage?");
		ModelAndView model = new ModelAndView();		
		model.setViewName("reviewsSearchDocs");
		return model;
	}
	
	@RequestMapping(value = { "/reviewsReviewBookNoneAdded"}, method = RequestMethod.GET)
	public ModelAndView reviewsReviewBookNoneAdded(HttpServletRequest request, HttpServletResponse response) {
//		log.info("we getting in here reviewsReviewBookNoneAdded?");
//		ModelAndView model = new ModelAndView();
//		
//		request.getSession().setAttribute("bookAuthorFound", "");
//		request.getSession().setAttribute("bookTitleFound", "");
//
//		model.setViewName("reviewsReviewBook");
		return null;
	}
	
	
	
	@RequestMapping(value = { "/reviewsReviewBook"}, method = RequestMethod.GET)
	public ModelAndView addReviewsPage(HttpServletRequest request, HttpServletResponse response) {
		return null;
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

	      // Get the default Session object.
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
	         
	         transport = session.getTransport("smtps");   
	         transport.connect(host, "pauljamesnewell@gmail.com", "5803871x");
	         transport.sendMessage(message, message.getAllRecipients());
	         transport.close();
	       
	         System.out.println("Sent message successfully....");
	         
	         String thankYouMsg = ConfigHandler.getInstance().readApplicationProperty("thankYouMessage");
	         thankYouMsg = thankYouMsg.replace(":path:", ConfigHandler.getInstance().readApplicationProperty("applicationImagesLocation"));
	         log.info("thankYouMsg :::: "+thankYouMsg);
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
		//request.getSession().removeAttribute(SessionConstants.SEARCH_TYPE_TAG);
		request.getSession().removeAttribute(SessionConstants.TAGS_SEARCH_CRITERIA);
		request.getSession().removeAttribute(SessionConstants.BOOKS_SEARCH_CRITERIA);
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
		request.getSession().setAttribute(SessionConstants.TAGS_SEARCH_CRITERIA, null);
		request.getSession().setAttribute(SessionConstants.BOOKS_SEARCH_CRITERIA, null);
		
		
		request.getSession().setAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET, 0);
		request.getSession().setAttribute("solrSearchListReturned", null);
		request.getSession().setAttribute("solrPaginationOffset", 0);
		request.getSession().setAttribute("solrTitleQuery", "");
		request.getSession().setAttribute("solrAuthorQuery", "");
		request.getSession().setAttribute("solrKeywordsQuery", "");
	}
	
	
	
	@RequestMapping(value = { "/partialSearchForDocs"}, method = RequestMethod.GET)
	public @ResponseBody String[] partialSearchForDocs(HttpServletRequest request, HttpServletResponse response){
		
//		SolrSearchData ssd = new SolrSearchData();
//		SolrSearchService solrService = new SolrSearchService();	
//		String partialText = request.getParameter(SessionConstants.PARTIAL_TEXT);	
//		String[] keyValuePair = partialText.split("-");
//		
//		SolrDocumentList solrDocPartialSearch = null;
//		
//		String[] returnList = null;
//		
//		if(!"".equals(partialText)){
//			solrDocPartialSearch =  solrService.performQueryPaginated
//				(keyValuePair[0]+":"+keyValuePair[1]+"*", Integer.parseInt
//						(ConfigHandler.getInstance().readApplicationProperty("paginationValue")), 0);
//			//request.getSession().setAttribute("solrAuthorQuery", "author:"+authorText);
//			returnList = new String[solrDocPartialSearch.size()];
//			log.info("list solrDocListAuthorsSearch is : "+solrDocPartialSearch.size());
//			
//			
//			Set<String> uniqueReturnList = new HashSet<String>();
//			
//			for(SolrDocument solrDoc : solrDocPartialSearch){
//				log.info("field names returned !! "+solrDoc.getFieldNames());
//				uniqueReturnList.add(solrDoc.getFieldValue(keyValuePair[0]).toString());
//			}
//			
//			int count = 0;
//
//			for(String value : uniqueReturnList){
//				returnList[count] = value;
//				count++;
//			}
//			
//		}
		
		return null;
	}
	
	@RequestMapping(value = { "/searchForDocs"}, method = RequestMethod.GET)
	public @ResponseBody SolrSearchData[] searchForDocs(HttpServletRequest request, HttpServletResponse response){
//		log.info("searchForDocs keyword text : : "+request.getParameter("keywordText"));
//		
//		resetSearchSessionAttributes(request);
//		SolrSearchData ssd = new SolrSearchData();
//		SolrSearchService solrService = new SolrSearchService();	
//		String keywords = request.getParameter("keywordText");
//		keywords = keywords.replaceAll(",", " ");
//		
//		log.info("keywords : "+keywords);
//		
//		String titleText = request.getParameter("titleText");
//		String authorText = request.getParameter("authorText");
//		
//		
//		SolrDocumentList solrDocListAuthorsSearch = null;
//		
//		if(!"".equals(authorText)){
//			solrDocListAuthorsSearch =  solrService.performQueryPaginated("author:"+authorText, 5, 0);
//			request.getSession().setAttribute("solrAuthorQuery", "author:"+authorText);
//			
//			log.info("list solrDocListAuthorsSearch is : "+solrDocListAuthorsSearch.size());
//		}
//		
//		request.getSession().setAttribute("solrPaginationOffset", 0);
//		
//		
//		
//		SolrDocumentList solrDocListTitleSearch = null;
//		
//		if(!"".equals(titleText)){
//			solrDocListTitleSearch = solrService.performQueryPaginated("title:"+titleText, 5, 0);
//			
//			request.getSession().setAttribute("solrTitleQuery", "title:"+titleText);
//	
//			
//			log.info("list solrDocListTitleSearch is : "+solrDocListTitleSearch.size());
//		}
//		
//		
//		
//		SolrDocumentList filteredList = new SolrDocumentList();
//		
//		if(solrDocListTitleSearch != null && solrDocListTitleSearch.size() > 0 && 
//				solrDocListAuthorsSearch != null && solrDocListAuthorsSearch.size() > 0){
//			
//			for(SolrDocument solrDoc : solrDocListTitleSearch){
//				
//				for(SolrDocument solrDocAuthors : solrDocListAuthorsSearch){
//					if(solrDocAuthors.getFieldValue("id").toString().equals(solrDoc.getFieldValue("id").toString())){
//						filteredList.add(solrDoc);
//					}
//				}
//			}
//			
//		}else{
//			
//			if(solrDocListAuthorsSearch != null){
//				filteredList.addAll(solrDocListAuthorsSearch);
//			}
//			
//			if(solrDocListTitleSearch != null){
//				filteredList.addAll(solrDocListTitleSearch);
//			}
//		}
//		
//		
//		log.info("filteredList size "+filteredList.size());
//		
//		SolrDocumentList solrDocListKeywordsSearch = null;
//		
//		if(!"".equals(keywords)){
//			solrDocListKeywordsSearch = solrService.performQueryPaginated(keywords,5, 0);
//			request.getSession().setAttribute("solrKeywordsQuery", keywords);
//			log.info("list solrDocListKeywordsSearch is : "+solrDocListKeywordsSearch.size());
//		}
//
//		for(SolrDocument solrDocument : filteredList){
//			log.info("solrDocument filtered list ID : "+solrDocument.getFieldValue("id"));
//		}
//		
//		SolrDocumentList finalisedFilteredList = new SolrDocumentList();
//		
//		if(solrDocListKeywordsSearch != null && solrDocListKeywordsSearch.size() > 0 && filteredList.size() > 0){
//			for(SolrDocument solrDocument : solrDocListKeywordsSearch){
//				
//				log.info("keywords list ID : "+solrDocument.getFieldValue("id"));
//				
//				for(SolrDocument solrDocFiltered : filteredList){
//					if(solrDocFiltered.getFieldValue("id").toString().equals(solrDocument.getFieldValue("id").toString())){
//						finalisedFilteredList.add(solrDocument);
//					}
//				}
//			}
//		}else{
//			finalisedFilteredList.addAll(filteredList);
//			
//			if(solrDocListKeywordsSearch != null){
//				finalisedFilteredList.addAll(solrDocListKeywordsSearch);
//			}
//			
//		}
//		
//		log.info("finalisedFilteredList size "+finalisedFilteredList.size());
//
//		List<SolrSearchData> returnList = new ArrayList<SolrSearchData>();
//
//
//		SolrSearchData[] returnArray = new SolrSearchData[finalisedFilteredList.size()];
//		
//		int count = 0;
//		
//		for(SolrDocument solrD : finalisedFilteredList){
//
//			ssd = new SolrSearchData();
//			
//			for(String field : solrService.getFieldsArray()){
//				
//				String fieldToSet = (solrD.getFieldValue(field) != null) ? solrD.getFieldValue(field).toString() : "";
//				
//				try{
//					Method method = ssd.getClass().getDeclaredMethod("set"+field, String.class);
//					method.invoke(ssd, fieldToSet);
//				}catch(Exception e){
//					e.printStackTrace();
//					log.error(e.getMessage());
//				}
//			}
//			
//			log.info("author set : "+ssd.getauthor());
//			log.info("title set : "+ssd.gettitle());
//			log.info("id set : "+ssd.getid());
//
//			String title = "";
//			
//			if(ssd.gettitle() == null || "".equals(ssd.gettitle().trim()) || "Unknown".equalsIgnoreCase(ssd.gettitle()) || "en".equalsIgnoreCase(ssd.gettitle())){
//
//				if(ssd.getid().lastIndexOf(File.separator) > -1){
//					title = ssd.getid().substring(ssd.getid().lastIndexOf(File.separator)+1);
//				}else{
//					title = ssd.getid();
//				}
//			}else{
//				title = ssd.gettitle();
//			}
//			
//			log.info("title "+title);
//
//			String largerContent = solrService.extractSpecifiedDocumentContent(ssd.getid(), 2000);
//			
//			if(largerContent.length() >= 1999){
//				largerContent = largerContent + "<i> ...open document to see more</i>";
//			}
//			
//			
//			String author = ssd.getauthor().replaceAll("\\[", "").replaceAll("\\]","");
//			
//			
//			log.info("larger content :::: "+largerContent);
//			
//			ssd.setauthor(author);
//			ssd.settitle(title);
//			ssd.setlargercontent(largerContent);
//			
//			
//			//TODO detect content
//			TikaConfig config = TikaConfig.getDefaultConfig();
//			Detector detector = new DefaultDetector(config.getMimeRepository());
//			
//	
//			try{
//				TikaInputStream stream = TikaInputStream.get(new File(ssd.getid()));
//	
//				Metadata metadata = new Metadata();
//				metadata.add(Metadata.RESOURCE_NAME_KEY, ssd.getid());
//			    MediaType mediaType = detector.detect(stream, metadata);
//			    
//			    log.info("media type : "+mediaType.getType());
//			    log.info("media base type : "+mediaType.getBaseType());
//			    log.info("media sub type : "+mediaType.getSubtype());
//			    log.info(detector.detect(stream, metadata).toString());
//			    
//			    ssd.setThumbnailLocation(solrService.getMimeTypeToThumbLocationMap().get(mediaType.getSubtype().toLowerCase().trim()));
//			    
//			}catch(Exception e){
//				e.printStackTrace();
//				log.error(e.getMessage());
//			}
//			
//			log.info("author 2 : "+author);
//			
//			ssd.setextract(solrService.extractSpecifiedDocumentContent(ssd.getid(), 600));
//			
//			returnArray[count] = ssd;
//			count++;
//		}
//
//	
//		request.getSession().setAttribute("solrSearchListReturned", returnList);		
//		log.info("list to return is : "+returnList.size());
		return null;
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
		
		switch(keyValuePair[0]){
		
			case "surname":
				employeesFound = dataService.getEmployeesBySurname(keyValuePair[1], 0, Integer.parseInt(ConfigHandler.getInstance().readApplicationProperty("paginationValue")));
				break;
			case "firstName":
				employeesFound = dataService.getEmployeesByFirstName(keyValuePair[1], 0, Integer.parseInt(ConfigHandler.getInstance().readApplicationProperty("paginationValue")));
				break;
			case "givenNames":
				employeesFound = dataService.getEmployeesByGivenNames(keyValuePair[1], 0, Integer.parseInt(ConfigHandler.getInstance().readApplicationProperty("paginationValue")));  
				break;
		}
				
		
		Set<Employee> removedDuplicates = new HashSet<Employee>();	//A Set object cannot contain duplicate entries	
		log.info("books found list "+employeesFound.size());
		
		for(Employee employee : employeesFound){
			log.info("books found list in loop - title : "+employee.getEmployeeSurname());
			removedDuplicates.add(employee);
		}
		
		request.getSession().setAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET, 0);
		request.getSession().setAttribute(SessionConstants.BOOKS_SEARCH_CRITERIA, searchCriteria);	
		employeesFound.clear(); //remove all elements and add in the set with duplicates removed.
		
		employeesFound.addAll(removedDuplicates);
		
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
	public @ResponseBody EmployeeModel[] searchEmployee(HttpServletRequest request, HttpServletResponse response){
			
		log.info("request get param : "+request.getParameter(SessionConstants.EMPLOYEE_SEARCH_STRING));
		
		int paginationValue = Integer.parseInt(ConfigHandler.getInstance().readApplicationProperty("paginationValue"));
		
		EmployeeDataService eds = new EmployeeDataService();
		
		
		eds.getEmployeeRecord(request.getParameter(SessionConstants.EMPLOYEE_SEARCH_STRING), 0, (0+paginationValue));
		
		EmployeeModel[] employeeModelArray = new EmployeeModel[0];
		
		
		
		return employeeModelArray;
		
		
//		
//		if(request.getSession() == null){
//			return null;
//		}
//		
//		resetSearchSessionAttributes(request);
//		log.info("request contain titleText ? : "+request.getParameter(SessionConstants.TITLE_TEXT));
//		log.info("request contain authorText ? : "+request.getParameter(SessionConstants.AUTHOR_TEXT));
//		log.info("request contain publisherText ? : "+request.getParameter(SessionConstants.PUBLISHER_TEXT));
//		
//		log.info("request contain lang text ? : "+request.getParameter("langText"));
//		
//	
//		
//		String titleText = request.getParameter(SessionConstants.TITLE_TEXT);
//		String authorText = request.getParameter(SessionConstants.AUTHOR_TEXT);
//		String publisherText = request.getParameter(SessionConstants.PUBLISHER_TEXT);
//
//		
//		HashMap<String, HashMap<String, String>> searchCriteria = new HashMap<String, HashMap<String, String>>();
//		
//		HashMap<String, String> tagsAndValueMap = new HashMap<String, String>();
//		
//		if(request.getParameter(SessionConstants.GENRE_SELECT) != null && !"".equals(request.getParameter(SessionConstants.GENRE_SELECT))){
//			tagsAndValueMap.put(SessionConstants.GENRE_SELECT, request.getParameter(SessionConstants.GENRE_SELECT));
//			log.info("genreText to search on : "+request.getParameter(SessionConstants.GENRE_SELECT));
//		}
//		
//		if(request.getParameter(SessionConstants.CATEGORY_SELECT) != null && !"".equals(request.getParameter(SessionConstants.CATEGORY_SELECT))){
//			log.info("catText to search on : "+request.getParameter(SessionConstants.CATEGORY_SELECT));
//			
//			tagsAndValueMap.put(SessionConstants.CATEGORY_SELECT, request.getParameter(SessionConstants.CATEGORY_SELECT));
//		}
//		
//		if(request.getParameter(SessionConstants.LANGUAGE_SELECT) != null && !"".equals(request.getParameter(SessionConstants.LANGUAGE_SELECT))){
//			log.info("lang text to search on : "+request.getParameter("langText"));
//			tagsAndValueMap.put(SessionConstants.LANGUAGE_SELECT, request.getParameter(SessionConstants.LANGUAGE_SELECT));
//		}
//		
//		log.info("tags and value map size : "+tagsAndValueMap.size());
//		
//		if(tagsAndValueMap.size() > 0){
//			searchCriteria.put(SessionConstants.TAGS_SEARCH_CRITERIA, tagsAndValueMap);
//			request.getSession().setAttribute(SessionConstants.TAGS_SEARCH_CRITERIA, tagsAndValueMap);
//		}else{
//			searchCriteria.put(SessionConstants.TAGS_SEARCH_CRITERIA, new <String, String>HashMap());
//			request.getSession().setAttribute(SessionConstants.TAGS_SEARCH_CRITERIA,  new <String, String>HashMap());
//		}
//
//		log.info("just before service instantiation !");
//		
//		BooksAndReviewsService booksService = new BooksAndReviewsService();
//		
//		List<Books> booksList = new ArrayList<Books>();
//		log.info("just before test !");
//
//		request.getSession().setAttribute(SessionConstants.PUBLISHER_TEXT, publisherText);
//
//		HashMap<String, String> booksSearchCriteria = null;
//		
//		if(titleText != null && !"".equals(titleText)){
//			
//			if(booksSearchCriteria == null){
//				booksSearchCriteria = new HashMap<String, String>(); 
//			}
//			
//			booksSearchCriteria.put(SessionConstants.TITLE_TEXT, titleText);
//			log.info("in here111");
//		
//		}
//		
//		if(!"".equals(authorText) && authorText != null){
//			
//			if(booksSearchCriteria == null){
//				booksSearchCriteria = new HashMap<String, String>(); 
//			}
//			
//			booksSearchCriteria.put(SessionConstants.AUTHOR_TEXT, authorText);
//		}
//		
//		if(publisherText != null && !"".equals(publisherText)){
//			
//			if(booksSearchCriteria == null){
//				booksSearchCriteria = new HashMap<String, String>(); 
//			}
//			booksSearchCriteria.put(SessionConstants.PUBLISHER_TEXT, publisherText);
//			log.info("in here222");
//		}
//		
//		if(booksSearchCriteria != null && booksSearchCriteria.size() > 0){
//			searchCriteria.put(SessionConstants.BOOKS_SEARCH_CRITERIA, booksSearchCriteria); 
//			request.getSession().setAttribute(SessionConstants.BOOKS_SEARCH_CRITERIA, booksSearchCriteria);
//		}else{
//			searchCriteria.put(SessionConstants.BOOKS_SEARCH_CRITERIA, new HashMap<String, String>());
//			request.getSession().setAttribute(SessionConstants.BOOKS_SEARCH_CRITERIA, new HashMap<String, String>());
//		}
//		
//		
//		booksList.addAll(booksService.findBooksByAnyCriteriaLazyLoad(searchCriteria, 0, Integer.parseInt(ConfigHandler.getInstance().readApplicationProperty("paginationValue"))));
//
//		ModelAndView modelView = new ModelAndView();
//
//		
//		modelView.setViewName("reviewsSearchBook");
//		return buildBooksFoundReturnModel(request, booksList);
		
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
			vnp = new ValueNamePair();
			vnp.setName(skills.getSkillsetName());
			vnp.setValue(skills.getSkillsetName());
			returnArray[count] = vnp;
			count++;
		}
		
		return returnArray;
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
			
			
			String loc = "./personnel/"+employee.getIdemployee();

			model.setThumbnailLocation(loc);

			log.info("1 book.getThumbnailLocation() : "+model.getThumbnailLocation());	
			
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
