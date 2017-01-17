
package co.srsp.controller;

import java.awt.Image;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

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
import co.srsp.viewmodel.HTMLModel;


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
		
		request.getSession().setAttribute(SessionConstants.EMPLOYEE_FULL_PROFILE_LIST, list);
		request.getSession().setAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET,currentPaginationOffset + appPaginationValue);
		
		
	//	log.info("books List 2 size ::: "+booksLists2.size());
		
		ModelAndView model = new ModelAndView();	
		//model.addObject("bookReviewsModel", bookReviewsModel);
		
		List<String> booksLists2 = new ArrayList<String>();

		
		
//		if(booksList != null){
//			for(Books book : booksList){	
//				booksLists2.add(formattedSearchListItem(book, book.getTitle()+" - "+book.getAuthor()));
//			}
//		}
//		
//		if(booksList != null){
//			model.addObject("booksLists2", booksLists2); //"booksLists2" matches a model in the searchPaginationPage.jsp page - can see the html and jstl below
//		}else{
//			model.addObject("booksLists2", new ArrayList<String>());
//		}
			
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
	
	private String formattedSearchListItem(Employee employee, String employeeDetails){
//		if(!"No books found".equalsIgnoreCase(bookDetails)){			
//			bookDetails =  URLEncoder.encode(bookDetails);
//		}
//		
//		String thumbLoc = getTrueThumbnailLocation(book);
//		HashMap imageDimensionsMap = getImageDimensions(thumbLoc, book);
//		
//		HTMLModel htmlModel = new HTMLModel();
//		htmlModel.setauthor(book.getAuthor());
//		htmlModel.settitle(book.getTitle());
//		htmlModel.setthumbnailLocFullPath(thumbLoc);
//		htmlModel.setthumbnailLocation(book.getThumbnailLocation());
//		htmlModel.setimageHeight(String.valueOf(imageDimensionsMap.get("imageHeight")));
//		htmlModel.setimageWidth(String.valueOf(imageDimensionsMap.get("imageWidth")));
//		htmlModel.setexcerpt(book.getExcerpt());
//		htmlModel.setpublisher(book.getPublisher());
//		htmlModel.setbookDetails(bookDetails);
//		
//		HTMLHelper helper = new HTMLHelper();
//		String formattedMarkup = helper.formatSearchHTML(htmlModel);
//		
//				
//		return formattedMarkup;
		
		return null;
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
	
	@RequestMapping(value = { "/retrieveNextSearchDocsSegment"}, method = RequestMethod.GET)
	public ModelAndView retrieveNextSearchDocsSegment(HttpServletRequest request, HttpServletResponse response) {
		log.info("searchForDocs keyword text : : "+request.getParameter("keywordText"));
		
		if(request.getSession() == null){
			return null;
		}
		
		SolrSearchData ssd = new SolrSearchData();
		SolrSearchService solrService = new SolrSearchService();	
		
		String keywordsQuery = request.getSession().getAttribute("solrKeywordsQuery").toString();
	
		
		log.info("keywords : "+keywordsQuery);
		
		String titleQueryText = request.getSession().getAttribute("solrTitleQuery").toString();
		String authorQueryText = request.getSession().getAttribute("solrAuthorQuery").toString();
		
		String offset = request.getSession().getAttribute("solrPaginationOffset").toString();
		
		log.info("OFFSET : "+offset);
		
		SolrDocumentList solrDocListAuthorsSearch = null;
		
		if(!"".equals(authorQueryText)){
			solrDocListAuthorsSearch =  solrService.performQueryPaginated(authorQueryText, 5, Integer.parseInt(offset)+5);
			log.info("list solrDocListAuthorsSearch is : "+solrDocListAuthorsSearch.size());
		}
		
		request.getSession().setAttribute("solrPaginationOffset", Integer.parseInt(offset)+5);
		
		
		
		SolrDocumentList solrDocListTitleSearch = null;
		
		if(!"".equals(titleQueryText)){
			solrDocListTitleSearch = solrService.performQueryPaginated(titleQueryText, 5, Integer.parseInt(offset)+5);
			log.info("list solrDocListTitleSearch is : "+solrDocListTitleSearch.size());
		}
		
		
		
		SolrDocumentList filteredList = new SolrDocumentList();
		
		if(solrDocListTitleSearch != null && solrDocListTitleSearch.size() > 0 && 
				solrDocListAuthorsSearch != null && solrDocListAuthorsSearch.size() > 0){
			
			for(SolrDocument solrDoc : solrDocListTitleSearch){
				
				for(SolrDocument solrDocAuthors : solrDocListAuthorsSearch){
					if(solrDocAuthors.getFieldValue("id").toString().equals(solrDoc.getFieldValue("id").toString())){
						filteredList.add(solrDoc);
					}
				}
			}
			
		}else{
			
			if(solrDocListAuthorsSearch != null){
				filteredList.addAll(solrDocListAuthorsSearch);
			}
			
			if(solrDocListTitleSearch != null){
				filteredList.addAll(solrDocListTitleSearch);
			}
		}
		
		
		log.info("filteredList size "+filteredList.size());
		
		SolrDocumentList solrDocListKeywordsSearch = null;
		
		if(!"".equals(keywordsQuery)){
			solrDocListKeywordsSearch = solrService.performQueryPaginated(keywordsQuery, 5, Integer.parseInt(offset)+5);
			log.info("list solrDocListKeywordsSearch is : "+solrDocListKeywordsSearch.size());
		}

		for(SolrDocument solrDocument : filteredList){
			log.info("solrDocument filtered list ID : "+solrDocument.getFieldValue("id"));
		}
		
		SolrDocumentList finalisedFilteredList = new SolrDocumentList();
		
		if(solrDocListKeywordsSearch != null && solrDocListKeywordsSearch.size() > 0 && filteredList.size() > 0){
			for(SolrDocument solrDocument : solrDocListKeywordsSearch){
				
				log.info("keywords list ID : "+solrDocument.getFieldValue("id"));
				
				for(SolrDocument solrDocFiltered : filteredList){
					if(solrDocFiltered.getFieldValue("id").toString().equals(solrDocument.getFieldValue("id").toString())){
						finalisedFilteredList.add(solrDocument);
					}
				}
			}
		}else{
			finalisedFilteredList.addAll(filteredList);
			
			if(solrDocListKeywordsSearch != null){
				finalisedFilteredList.addAll(solrDocListKeywordsSearch);
			}
			
		}
		
		log.info("finalisedFilteredList size "+finalisedFilteredList.size());

		List<SolrSearchData> returnList = new ArrayList<SolrSearchData>();
		List<String> formattedList = new ArrayList<String>();

		for(SolrDocument solrD : finalisedFilteredList){
			
			ssd = new SolrSearchData();
			
			for(String field : solrService.getFieldsArray()){
				String fieldToSet = (solrD.getFieldValue(field) != null) ? solrD.getFieldValue(field).toString() : "";
				
				try{
					Method method = ssd.getClass().getDeclaredMethod("set"+field, String.class);
					method.invoke(ssd, fieldToSet);
				}catch(Exception e){
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}
			
			log.info("author set : "+ssd.getauthor());
			log.info("title set : "+ssd.gettitle());
			log.info("id set : "+ssd.getid());
			
			returnList.add(ssd);
			
			String title = "";
			
			if(ssd.gettitle() == null || "".equals(ssd.gettitle().trim()) || "Unknown".equalsIgnoreCase(ssd.gettitle()) || "en".equalsIgnoreCase(ssd.gettitle())){

				if(ssd.getid().lastIndexOf(File.separator) > -1){
					title = ssd.getid().substring(ssd.getid().lastIndexOf(File.separator)+1);
				}else{
					title = ssd.getid();
				}
			}else{
				title = ssd.gettitle();
			}
			
			log.info("title "+title);

			String largerContent = solrService.extractSpecifiedDocumentContent(ssd.getid(), 2000);
			
			if(largerContent.length() >= 1999){
				largerContent = largerContent + "<i> ...open document to see more</i>";
			}
			
			TikaConfig config = TikaConfig.getDefaultConfig();
			Detector detector = new DefaultDetector(config.getMimeRepository());
			
	
			try{
				TikaInputStream stream = TikaInputStream.get(new File(ssd.getid()));
	
				Metadata metadata = new Metadata();
				metadata.add(Metadata.RESOURCE_NAME_KEY, ssd.getid());
			    MediaType mediaType = detector.detect(stream, metadata);
			    
			    log.info("media type : "+mediaType.getType());
			    log.info("media base type : "+mediaType.getBaseType());
			    log.info("media sub type : "+mediaType.getSubtype());
			    log.info(detector.detect(stream, metadata).toString());
			    
			    ssd.setThumbnailLocation(solrService.getMimeTypeToThumbLocationMap().get(mediaType.getSubtype().toLowerCase().trim()));
			    
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage());
			}
			
			
			String author = ssd.getauthor().replaceAll("\\[", "").replaceAll("\\]","");
			log.info("author 2 : "+author);
			
			String specifiedDocumentContentExtract = solrService.extractSpecifiedDocumentContent(ssd.getid(), 600);

			
			HTMLModel htmlModel = new HTMLModel();
			htmlModel.setauthor(author);
			htmlModel.settitle(title);
			htmlModel.setthumbnailLocation(ssd.getThumbnailLocation());
			htmlModel.setdocID(ssd.getid());
			htmlModel.setspecifiedDocumentContentExtract(specifiedDocumentContentExtract);
			htmlModel.setlargerContent(largerContent);
			HTMLHelper helper = new HTMLHelper();
			helper.formatSearchDocsHTML(htmlModel);
			
			formattedList.add("<div style='float:left; margin-right:1.5em;' ><img src='"+ssd.getThumbnailLocation()+"' /></div>"
					+ "<b>Title : </b>"+title+"<b> Author : </b> "+author+" &nbsp; <b> link to doc </b> <a href='file://///"+ssd.getid()+"'"+
					" target="+"'"+"_blank"+"'"+">"+title+"</a><p style='font-size:x-small;!important'>"+solrService.extractSpecifiedDocumentContent(ssd.getid(), 600)+
					"<i> <a href='#' onclick='displayFullContent();'> ...see more</a></i></p><div class='fullContent' style='color:white; display:none'>"+
					largerContent+"</div>");
			
		}

		
		request.getSession().setAttribute("solrSearchListReturned", returnList);
		
		log.info("list to return is : "+returnList.size());
		
		request.getSession().setAttribute("solrPaginationOffset", Integer.parseInt(offset)+5);
		
		// formattedList;
		
		ModelAndView model = new ModelAndView();	
		model.addObject("booksLists2", formattedList);
		model.setViewName("searchDocsPaginationPage"); 
		
		return model;
		
	}
	
}

