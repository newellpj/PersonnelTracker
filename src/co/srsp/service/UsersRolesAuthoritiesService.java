package co.srsp.service;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.srsp.hibernate.UsersBusinessObject;
import co.srsp.hibernate.orm.Authorities;
import co.srsp.hibernate.orm.Users;


public class UsersRolesAuthoritiesService {

	private final static Logger log = Logger.getLogger(UsersRolesAuthoritiesService.class); 
	
	private ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	private UsersBusinessObject userBO = (UsersBusinessObject) ctx.getBean("usersBusinessObject");
	
	public boolean isUsernameAvailable(String username){
		Users user = userBO.findUsersByUsername(username);
		System.out.println("is user null : "+user);
		return user == null;
		
	}
	
	/**
	 * @param userModel
	 */
	public void addUser(String username, String password){
		Users user = new Users();
		user.setUsername(username);
		user.setPassword(userBO.encryptPassword(password));
		user.setEnabled("Y");
		
		Authorities authorities = new Authorities();
		authorities.setUsername(username);
		authorities.setAuthority("ROLE_USER");
		
		userBO.save(user, authorities);
	}
	

	
}
