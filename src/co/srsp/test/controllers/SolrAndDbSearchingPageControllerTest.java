package co.srsp.test.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;

import co.srsp.controller.SearchingPageController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class SolrAndDbSearchingPageControllerTest extends Mockito{
	

    private MockMvc mockMvc;
	
	private final static Logger log = Logger.getLogger(SolrAndDbSearchingPageControllerTest.class); 
	

	@Before
	  public void setUp() throws Exception {
	    MockitoAnnotations.initMocks(this);
	    
	   // controller = new SolrAndDbSearchingPageController();	
	   // ReflectionTestUtils.setField(controller, "socialContext", socialContext);
	  } 

	@Test
    public void testGetReviewsPage() {
        System.out.println("getReviewsPage");
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    
        
        when(request.getServletPath().equals("/reviews")).thenReturn(true);
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        SearchingPageController controller = (SearchingPageController) ctx.getBean("solrAndDbSearchController");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% "+controller.welcomePage());
       
        
    }
}
