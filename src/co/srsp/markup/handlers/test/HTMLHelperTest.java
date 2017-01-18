package co.srsp.markup.handlers.test;

import org.junit.Assert;
import org.junit.Test;

import co.srsp.config.ConfigHandler;
import co.srsp.markup.handlers.HTMLHelper;
import co.srsp.viewmodel.HTMLModel;

public class HTMLHelperTest {
	
	
	private void setup(){
		ConfigHandler.getInstance().setUnderTest(true);
	}

	@Test
	public void testBookToReview(){

	}
	
	@Test
	public void testBookReviewsText(){

	}
	
	@Test
	public void testSearchText(){


		HTMLHelper helper = new HTMLHelper();
		

	}
	
	@Test
	public void testSearchDocsText(){
		HTMLModel htmlModel = new HTMLModel();

		
		HTMLHelper helper = new HTMLHelper();
		
		System.out.println("formatted html returned : "+helper.formatSearchDocsHTML(htmlModel));
		String formattedHTML = helper.formatSearchDocsHTML(htmlModel);
		Assert.assertNotNull(formattedHTML); 
		Assert.assertFalse(formattedHTML.contains("\""));
		
	}
}
