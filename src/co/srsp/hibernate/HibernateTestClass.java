package co.srsp.hibernate;


import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.srsp.viewmodel.EmployeeModel;

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
		List<EmployeeModel> list = empBO.findEmployeesByAnyCriteriaLazyLoad(new HashMap<String, String>(), 0, 40);

		System.out.println("testEmployeeLoad : "+list.size());
		
		for(EmployeeModel model : list){
			
			System.out.println("employee surname : "+model.getEmployeeSurname());
		
			
		}
		
//		for(Employee emp : list){
//		
//			System.out.println("employee surname : "+emp.getEmployeeSurname());
//			System.out.println("employee first name : "+emp.getEmployeeFirstName());
//			System.out.println("employee address : "+emp.getEmployeeAddress());
//			
//		}
		
		
		
	}
	


}
