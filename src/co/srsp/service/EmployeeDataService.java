package co.srsp.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.srsp.hibernate.EmployeeBusinessObject;
import co.srsp.hibernate.orm.Employee;

public class EmployeeDataService {
	
	private ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	private EmployeeBusinessObject employeeBusinessObject = (EmployeeBusinessObject) ctx.getBean("employeeBusinessObject");
	
	public List<Employee> getEmployeeRecord(String searchString, int startOffset, int numberOfRecords){
		
		return employeeBusinessObject.findEmployeePartialSurnameMatch("", startOffset, numberOfRecords);
	}

}
