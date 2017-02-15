package co.srsp.config;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import co.srsp.viewmodel.EmployeeModel;

public class ConfigHandler {
	
	//constants that define structure to return text files contents
	public final static String LIST_RETURN_TYPE = "list";
	public final static String ARRAY_RETURN_TYPE = "array";

	private final static Logger log = Logger.getLogger(ConfigHandler.class); 
	
	public static final String CONFIG_ROOT_DIR_DEV = "../webapps/PersonnelTracker/presentationResources/";
	public static final String CONFIG_ROOT_DIR_PROD = "./webapps/ROOT/presentationResources/";
	
	private static ConfigHandler instance = null;
	
	public boolean underTest = false;
	
	public static ConfigHandler getInstance(){
		if(instance == null){
			instance = new ConfigHandler();
		}
		return instance;
	}
	
	private ConfigHandler(){	
	}
	
	
	
	public boolean isUnderTest() {
		return underTest;
	}

	public void setUnderTest(boolean underTest) {
		this.underTest = underTest;
	}

	/**
	 * type returned will be decided by the returnType param value passed
	 * currently with string array or array list
	 * @param fileName
	 * @param returnType
	 * @return
	 */
	public Object readCSVFile(String fileName, String returnType){
	
			//read entire file
		StringBuffer sb = new StringBuffer();	
		
		String configDir = "";
		
		if(new File(CONFIG_ROOT_DIR_DEV+fileName).exists()){
			configDir = CONFIG_ROOT_DIR_DEV;
		}else{
			configDir = CONFIG_ROOT_DIR_PROD;
		}
		
		try(BufferedReader br = new BufferedReader(new FileReader(configDir+fileName))) {
			// load a properties file
			String line = "";
			
			while((line = br.readLine()) != null){
				sb.append(line);
			}
			
		} catch(IOException ioe){
			 ioe.printStackTrace();
			 log.error(ioe.getMessage());
		}
		
		switch(returnType){
			case LIST_RETURN_TYPE:
				
				List<String> itemsList = new ArrayList<String>();
				String[] items = sb.toString().split(",");
				
				for(int i = 0; i < items.length; i++ ){
					itemsList.add(items[i]);
				}
				
				return itemsList;
				
			case ARRAY_RETURN_TYPE:
				return sb.toString().split(",");
    	}
		
		log.info("string buffer : "+sb.toString());
		
		System.out.println("split(,) : "+sb.toString().split(",").length);
		
		return sb.toString().split(",");

	}
	
	public String readApplicationProperty(String propLabel ) {
		Properties prop = new Properties();

		log.info("USER DIR **************** "+ System.getProperty("user.dir"));
		log.info("application properties path DEV  ::: "+CONFIG_ROOT_DIR_DEV+"application.properties");
		log.info("application properties path PROD ::: "+CONFIG_ROOT_DIR_PROD+"application.properties");
		
		String systemDir = System.getProperty("user.dir");
		
		String propertiesDir = "";
		
		String fileName = "application.properties";
		
		if(new File(CONFIG_ROOT_DIR_DEV+fileName).exists()){
			propertiesDir = CONFIG_ROOT_DIR_DEV+fileName;
		}else{
			propertiesDir = CONFIG_ROOT_DIR_PROD+fileName;
		}	
		
		try(InputStream input = new FileInputStream(propertiesDir)) {
			prop.load(input);
			return prop.getProperty(propLabel);
		} catch(IOException ioe){
			 ioe.printStackTrace();
			 log.error(ioe.getMessage());
		}
		
		return null;

	}
	
	public String readConfigProperty(String propLabel ) {
		Properties prop = new Properties();
		

		try(InputStream input = new FileInputStream("config.properties")) {
			// load a properties file
			prop.load(input);
			return prop.getProperty(propLabel);
			

		} catch(IOException ioe){
			 ioe.printStackTrace();
			 log.error(ioe.getMessage());
		}
		
		return null;

	}
	
	public void writeProperty(String propLabel, String propVal){
		
	}
	
	public static void setProfilePicData(EmployeeModel model){
	  try{
			//file system relative references are different from web application relative references 
			String fileURLPath = ConfigHandler.getInstance().readApplicationProperty("applicationProfileImagesLocation")+model.getIdemployee();
			fileURLPath += model.getEmployeeFirstName().toLowerCase()+".jpeg";
			log.info( System.getProperty("user.dir"));
			 
			
			log.info("location for file is :::: "+fileURLPath);
	
			
			model.setImageHeight(String.valueOf(190));
			model.setImageWidth(String.valueOf(190));
			model.setProfilePicURL(fileURLPath);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
	}
	
	public static void main(String args[]){
		
		ConfigHandler configHandler = ConfigHandler.getInstance();
		
		if(args != null){
			String param = args[0];
			configHandler.setUnderTest(param.contains("t"));
			
		}
		
		System.out.println(ConfigHandler.getInstance().readApplicationProperty("searchListHTML"));
	}
}
