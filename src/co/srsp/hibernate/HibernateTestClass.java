package co.srsp.hibernate;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.srsp.constants.SessionConstants;
import co.srsp.hibernate.orm.Authorities;
import co.srsp.hibernate.orm.BookReviews;
import co.srsp.hibernate.orm.Books;
import co.srsp.hibernate.orm.Employee;
import co.srsp.hibernate.orm.Users;

public class HibernateTestClass {

	private final static Logger log = Logger.getLogger(HibernateTestClass.class); 
	
	public static void main(String[] args) {
		
		//testBooksAndReviews();
		//testUsersAuthorities();
		//testSelectSubsets();
	//	testTagsSearch();
		
		testEmployeeLoad();
	}
	
	@Test
	public static void testEmployeeLoad(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		EmployeeBusinessObject empBO = (EmployeeBusinessObject) ctx.getBean("employeeBusinessObject");
		List<Employee> list = empBO.findEmployeeBySurnameOnly("taylor",0,10);
		
		System.out.println("testEmployeeLoad");
		
		for(Employee emp : list){
		
			System.out.println("employee surname : "+emp.getEmployeeSurname());
			System.out.println("employee first name : "+emp.getEmployeeFirstName());
			System.out.println("employee address : "+emp.getEmployeeAddress());
			
		}
		
		
		
	}
	


}
