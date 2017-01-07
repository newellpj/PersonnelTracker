package co.srsp.controller;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.springframework.web.servlet.ModelAndView;

import co.srsp.config.ConfigHandler;
import co.srsp.exceptions.IllegalFileUploadException;
import co.srsp.viewmodel.EmployeeModel;

@WebServlet(name = "AddBookUploadServlet", urlPatterns = {"/addNewBook"})
@MultipartConfig
public class AddBookUploadServlet extends HttpServlet {
	
	
	private final static Logger log = Logger.getLogger(AddBookUploadServlet.class); 
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBookUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.info("in do get");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.info("in do post!!!!!!!!!!!!!!!!!");
		 boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		 log.info("is multi-part content : "+isMultipart);
		 log.info("content type from servlet req context : "+ new ServletRequestContext(request).getContentType()); 
		 InputStream fileStream = null;
		 String loc = "";
		 
		 
		 
			 if(isMultipart){
				 String userEnteredFileName = "";
				 
			        String fieldName = "";
	                String fileName = "";
	                long fileSize = -1;
				 
	                List<FileItem> items = null;
	                try{
	                	items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
					}catch(FileUploadException fue){
						 throw new ServletException("Error with file upload. please try again.");
					}
	                
			        for (FileItem item : items) {
			            if (item.isFormField()) {
			                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
			                String inputFieldName = item.getFieldName();
			                String value = item.getString();
			                
			                log.info("field name : "+inputFieldName);

			                request.getSession().setAttribute(inputFieldName, value);
			                
			                log.info("request.getSession().getAttribute : "+request.getSession().getAttribute(inputFieldName).toString());
			                
			                // ... (do your job here)
			            } else {
			                // Process form file field (input type="file").
			                fieldName = item.getFieldName();
			                fileName = FilenameUtils.getName(item.getName());
			                 fileSize = item.getSize();
			                 log.info("22 file field name : "+fieldName);
			                 log.info("22 file name : "+fileName);
			                 log.info("22 item.getInputStream(): "+item.getInputStream());
			                fileStream = item.getInputStream();
			                
			                TikaConfig config = TikaConfig.getDefaultConfig();
			    			Detector detector = new DefaultDetector(config.getMimeRepository());
			    			
			    	
		    				TikaInputStream stream = TikaInputStream.get(fileStream);
		    	
		    				Metadata metadata = new Metadata();
		    				//metadata.add(Metadata.RESOURCE_NAME_KEY, ssd.getid());
		    			    MediaType mediaType = detector.detect(stream, metadata);
		    			    
		    			    log.info("media type : "+mediaType.getType());
		    			    log.info("media base type : "+mediaType.getBaseType());
		    			    log.info("media sub type : "+mediaType.getSubtype());
		    			    log.info(detector.detect(stream, metadata).toString());
		    			    
		    			    
		    			    String mediaTypeStr = mediaType.getType();
		    			    
		    			    if(mediaTypeStr == null || (!"image".equalsIgnoreCase(mediaTypeStr) && !mediaTypeStr.toLowerCase().contains("image"))){
		    			    	log.info("not of image type");
		    			    	throw new ServletException("file is not of image type");
		    			    }

			    			
			                loc = copyFile(fileStream, fileName);
			            }
			        }
			        
			        if(!"".equals(loc)){ 
			        	
	
							String fileURLPath = (loc.toLowerCase().contains("http")) ? loc : "../webapps/SearchRetailServicesProject/presentationResources/images/"+loc.substring(loc.lastIndexOf("/")+1);
							log.info( System.getProperty("user.dir"));
							 
							request.getSession().setAttribute("thumbnailLocation", "./presentationResources/images/"+loc.substring(loc.lastIndexOf("/")+1));
	
							log.info("thumbnailLocation ::: "+request.getSession().getAttribute("thumbnailLocation"));
							
							//File file = new File(fileStream);
							log.info("location for file is :::: "+fileURLPath);
							//log.info("does file exist : "+file.exists());
							
							Image image = new ImageIcon(fileURLPath).getImage();
							
							int imgWidth = image.getWidth(null);
							int imgHeight = image.getHeight(null);
							
							log.info("imgWidth : "+imgWidth);
							log.info("imgHeight : "+imgHeight);
							
							if(imgWidth > imgHeight){
								double result = new Double(imgHeight)/ new Double(imgWidth);
								log.info("result : "+result);
								imgHeight = (int)(result * new Double(120));
								imgWidth = 120;
							}else if(imgWidth < imgHeight){
								double result = new Double(imgWidth)/ new Double(imgHeight);
								imgWidth = (int)(result * new Double(120));
								imgHeight = 120;
							}else{
								imgHeight = 120;
								imgWidth  = 120;
							}
	
							log.info("resized imgWidth :::: "+String.valueOf(imgWidth));
							log.info("resized imgHeight :::: "+String.valueOf(imgHeight));
							
							request.getSession().setAttribute("imageWidth", String.valueOf(imgWidth));
							request.getSession().setAttribute("imageHeight", String.valueOf(imgHeight));
							
							
						
			        }else{
			        	//add no image available image
			        	
			        	request.getSession().setAttribute("thumbnailLocation", "./presentationResources/images/noimage.jpg");
			        	
			        }
			        
			        log.info("creating book reviews model");
					
			       
			        
					log.info("creating book reviews model 1111");
//					
//					EmployeeModel bookReviewsModel = new EmployeeModel();
//					bookReviewsModel.setTitleText(request.getSession().getAttribute("bookTitleFound").toString());
//					bookReviewsModel.setAuthorText(request.getSession().getAttribute("bookAuthorFound").toString());
//					
//					if(request.getSession().getAttribute("bookPublisherFound") != null){
//						bookReviewsModel.setPublisherText(request.getSession().getAttribute("bookPublisherFound").toString());
//					}
//					
//					if(request.getSession().getAttribute("excerpt") != null){
//						bookReviewsModel.setExcerpt(request.getSession().getAttribute("excerpt").toString());
//					}
//					
//					if(request.getSession().getAttribute("thumbnailLocation") != null){
//						
//						String tempStr = request.getSession().getAttribute("thumbnailLocation").toString();
//						
//						String imageFileNameMinusDirPath = tempStr.substring(tempStr.lastIndexOf("/")+1);
//						
//						bookReviewsModel.setThumbnnalLocation(imageFileNameMinusDirPath);
//					}else{
//						bookReviewsModel.setThumbnnalLocation("noimage.jpg");
//					}
					
					
					
					HashMap<String, String> tagsAndValueMap = new HashMap<String, String>();
					
					
					if(request.getSession().getAttribute("genreText") != null){
						tagsAndValueMap.put("genreText", request.getSession().getAttribute("genreText").toString());
					}
					
					if(request.getSession().getAttribute("catText") != null){
						tagsAndValueMap.put("catText", request.getSession().getAttribute("catText").toString());
					}
					
					if(request.getSession().getAttribute("langText") != null){
						tagsAndValueMap.put("langText", request.getSession().getAttribute("langText").toString());
					}

					log.info("creating book reviews model 3333");
					
					//TODO add employee with upload
					//booksService.addBook(bookReviewsModel, tagsAndValueMap);
				
					log.info("creating book reviews model 4444");
					
					ModelAndView modelAndView = new ModelAndView();
					
			 }

	}
	
	
	 protected String copyFile(InputStream fileInputStream, String fileName) throws IOException{ 

	    	log.info("copyFile to fileName before truncate : "+fileName);
	    	
	    	String uploadPath = ConfigHandler.getInstance().readApplicationProperty("applicationImagesLocation");
	    	String copiedFileNameAndPath = "";
	    	if(!"".equals(fileName)){

		        try { 
		        	
		        	log.info("copyFile to tempUploadDir : "+uploadPath);
		        	log.info("copyFile to fileName : "+fileName);
		        	
		        	log.info("full file path : "+uploadPath +File.separator+ fileName);

		         	String uniqueFileNameIdentifier = UUID.randomUUID().toString();	
		         	
		         	//fileEntity.setUniqueFileIdentifier(uniqueFileNameIdentifier);
		        
	        		copiedFileNameAndPath = uploadPath +"/"+uniqueFileNameIdentifier+fileName;
	        		
	        		//fileEntity.setFilePath(copiedFileNameAndPath);
	        		
	        		log.info("path only : "+uploadPath);
	        		log.info("copiedFileNameAndPath : "+copiedFileNameAndPath);
		         	
		            OutputStream out = new FileOutputStream(new File(copiedFileNameAndPath));

		            InputStream in = fileInputStream;
		            
		            int read = 0; 
		            byte[] bytes = new byte[1024]; 
		
		            while ((read = in.read(bytes)) != -1) { 
		                out.write(bytes, 0, read); 
		            } 
		
		            in.close(); 
		            out.flush(); 
		            out.close(); 
		
		            log.info("New file created!"); 
		
		        } catch (IOException e) {
		        	String errMsg = "error Copying file to tempupload directory "+copiedFileNameAndPath+" message="+e.getMessage(); 
		        	log.error(errMsg);
		        	throw new IOException(errMsg,e);
		        }
	    	}else{
	    		String errMsg = "file name "+fileName+" is null or blank : ";
	    		log.error(errMsg);
	    		throw new IOException(errMsg);
	    	}
	    	
	    	
	
			
	    	
	    	return copiedFileNameAndPath;
	    } 
	
	
	


}
