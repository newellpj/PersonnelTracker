package co.srsp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import co.srsp.constants.SessionConstants;
import co.srsp.service.UsersRolesAuthoritiesService;

@Controller
public class LoginController implements AuthenticationSuccessHandler, AuthenticationFailureHandler{

	private final static Logger log = Logger.getLogger(LoginController.class); 
	
	private String defaultTargetUrl;


	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
	public ModelAndView welcomePage() {
		log.info("login code");
		
		ModelAndView model = new ModelAndView();		
		model.setViewName("landing");
		return model;

	}
	
	@RequestMapping(value = { "/loginSignup"}, method = RequestMethod.GET)
	public ModelAndView loginSignup() {
		log.info("login code");
		
		ModelAndView model = new ModelAndView();		
		model.setViewName("loginSignup");
		return model;

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		System.out.println("logout!!!!!!");
		
		request.getSession().invalidate();
	    SecurityContextHolder.getContext().setAuthentication(null);
		
		ModelAndView model = new ModelAndView();
		model.addObject("error", "Successfully logged out!");
		model.setViewName("logout");
		
		log.info(" we here again logout!!!!!!");
		
		//response.sendRedirect("logout");
		
		
		
		return model;
	}
	
	
	@RequestMapping(value = { "/signup"}, method = RequestMethod.GET)
	public @ResponseBody String[] signupUser(HttpServletRequest request, HttpServletResponse response){
		String pass = request.getParameter(SessionConstants.PASS_PARAM);
		String user = request.getParameter(SessionConstants.USER_PARAM);
		
		log.info("password found ::: "+pass);
		log.info("user found ::: "+user);
		
		UsersRolesAuthoritiesService userService = new UsersRolesAuthoritiesService();		
	
		boolean userAvailable = userService.isUsernameAvailable(user);
		
		String[] responseMessage = new String[2];
		
		if(!userAvailable){
			responseMessage[0] = SessionConstants.FAIL;
			responseMessage[1] = "User name "+user+" is not available please try another";
		}else{
			userService.addUser(user, pass);
			responseMessage[0] = SessionConstants.SUCCESS;
			responseMessage[1] = "Congratulations now please sign in";
		}
		
		return responseMessage;
	}

	
	
	@RequestMapping(value = "/landing", method = RequestMethod.GET) 
	public ModelAndView login(@RequestParam(value = "error", required = false) String error, HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		//request.getSession(true);
		
		String referrer = request.getHeader("referer");
		
		
		log.info("logger we again login and referrer is :::: "+referrer);
	
		ModelAndView model = new ModelAndView();
		if (error != null) {
			System.out.println("error != null : "+error);
			model.addObject("error", "Incorrect username and password!");
			model.setViewName("landing");	
		}else{
			model.setViewName("landing");
			log.info("ELSE!!!!!!");
			//response.sendRedirect("login");
		}
		
		
		return model;

	}
	
	@RequestMapping("accessDenied")
	public String accessDenied(){
		log.info("accessDenied");
		return "accessDenied";
		
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse response, Authentication arg2)
			throws IOException, ServletException {
		log.info("onAuthenticationSuccess");
		response.sendRedirect("trackerHome");
	}

	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2)
			throws IOException, ServletException {
		log.info("onAuthenticationFailure");
		// TODO Auto-generated method stub
		
	}

	
	
}
